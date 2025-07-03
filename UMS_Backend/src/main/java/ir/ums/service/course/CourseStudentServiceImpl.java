package ir.ums.service.course;

import ir.ums.constants.Messages;
import ir.ums.constants.enums.PrerequisiteTypeEnum;
import ir.ums.dao.course.ICourseDao;
import ir.ums.dao.course.ICoursePrerequisiteDao;
import ir.ums.dao.course.ICourseStudentDao;
import ir.ums.dto.course.CourseStudentDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.exception.CustomException;
import ir.ums.mapper.course.CourseStudentMapper;
import ir.ums.mapper.user.StudentMapper;
import ir.ums.model.course.Course;
import ir.ums.model.course.CoursePrerequisite;
import ir.ums.model.course.CourseStudent;
import ir.ums.model.user.Student;
import ir.ums.service.user.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseStudentServiceImpl implements ICourseStudentService {

    private final ICourseStudentDao courseStudentDao;
    private final ICoursePrerequisiteDao coursePrerequisiteDao;
    private final ICourseDao courseDao;
    private final IStudentService studentService;
    private final CourseStudentMapper courseStudentMapper;
    private final StudentMapper studentMapper;

    @Override
    public void saveCourseStudent(CourseStudentDTO courseStudentDTO) {
        var courseId = courseStudentDTO.getCourseId();
        var userKeycloakId = courseStudentDTO.getKeycloakUserId();
        Course course = courseDao.getCourseById(courseId);
        Student student = studentMapper.toEntity(studentService.getStudentByKeycloakUserId(userKeycloakId));
        var studentId = student.getId();

        checkMaxUnitsCountHitOrNot(studentId, course);
        checkEnrollmentIsNotDuplicate(courseId, studentId);
        checkSatisfiedPrerequisiteCoursesOrNot(courseId, studentId);
        checkCourseCapacityBeforeEnrollment(course);

        course.setEnrolledCount(course.getEnrolledCount() + 1);
        courseDao.saveOrUpdateCourse(course);

        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setStudent(student);
        courseStudent.setCourse(course);
        courseStudent.setScoreStatus(false);
        courseStudent.setCourseScore(null);
        courseStudent.setScoreResult(null);

        courseStudentDao.saveOrUpdateCourseStudent(courseStudent);
    }

    private void checkMaxUnitsCountHitOrNot(UUID studentId, Course course) {
        Integer courseUnits = course.getCourseUnits();
        Integer totalUnits = getStudentCoursesByScoreResult(studentId, null)
                .stream()
                .map(Course::getCourseUnits)
                .reduce(0, Integer::sum);

        // ceil of total unit is hard coded for educational goals but should determine with average of student score.
        if(totalUnits + courseUnits> 20)
            throw new CustomException(Messages.TOTAL_UNITS_IS_MORE_THAN_CEIL, "20");
    }

    private void checkEnrollmentIsNotDuplicate(UUID courseId, UUID studentId) {
        List<UUID> stdCurrentTermCourseIds = courseStudentDao.getCoursesByStudentIdAndScoreResult(studentId, null)
                .stream()
                .map(cs -> cs.getCourse().getId())
                .toList();
        if (stdCurrentTermCourseIds.contains(courseId))
            throw new CustomException(Messages.STUDENT_ENROLLED_IN_THIS_COURSE_PREVIOUSLY);
    }

    private void checkSatisfiedPrerequisiteCoursesOrNot(UUID courseId, UUID studentId) {
        List<Course> stdAcceptedCourses = getStudentCoursesByScoreResult(studentId, Boolean.TRUE);
        List<Course> stdCurrentTermCourses = getStudentCoursesByScoreResult(studentId, null);

        validatePrerequisitesCrs(courseId, stdAcceptedCourses);
        validateCorequisitesCrs(courseId, stdAcceptedCourses, stdCurrentTermCourses);
        validateEquivalentsCrs(courseId, stdAcceptedCourses);
    }

    private List<Course> getStudentCoursesByScoreResult(UUID studentId, Boolean scoreResult) {
        return courseStudentDao.getCoursesByStudentIdAndScoreResult(studentId, scoreResult)
                .stream()
                .map(CourseStudent::getCourse)
                .toList();
    }

    private void validatePrerequisitesCrs(UUID courseId, List<Course> stdAcceptedCourses) {
        List<String> missing = coursePrerequisiteDao
                .getCoursePrerequisiteByCourseIdAndPreType(courseId, PrerequisiteTypeEnum.PREREQUISITE.getLabel())
                .stream()
                .map(CoursePrerequisite::getPrerequisite)
                .filter(prereq -> !stdAcceptedCourses.contains(prereq))
                .map(Course::getCourseName)
                .toList();

        if (!CollectionUtils.isEmpty(missing)) {
            throw new CustomException(Messages.STUDENT_HAS_NOT_PASSED_FOLLOWING_PREREQUISITES, String.join(", ", missing));
        }
    }

    private void validateCorequisitesCrs(UUID courseId, List<Course> stdAcceptedCourses, List<Course> stdCurrentTermCourses) {
        List<String> missing = coursePrerequisiteDao
                .getCoursePrerequisiteByCourseIdAndPreType(courseId, PrerequisiteTypeEnum.COREQUISITE.getLabel())
                .stream()
                .map(CoursePrerequisite::getPrerequisite)
                .filter(coreq -> !stdAcceptedCourses.contains(coreq) && !stdCurrentTermCourses.contains(coreq))
                .map(Course::getCourseName)
                .toList();

        if (!CollectionUtils.isEmpty(missing)) {
            throw new CustomException(Messages.STUDENT_HAS_NOT_PASSED_OR_ENROLLED_IN_FOLLOWING_COREQUISITES, String.join(", ", missing));
        }
    }

    private void validateEquivalentsCrs(UUID courseId, List<Course> stdAcceptedCourses) {
        List<String> alreadyAccepted = coursePrerequisiteDao
                .getCoursePrerequisiteByCourseIdAndPreType(courseId, PrerequisiteTypeEnum.EQUIVALENT.getLabel())
                .stream()
                .map(CoursePrerequisite::getPrerequisite)
                .filter(stdAcceptedCourses::contains)
                .map(Course::getCourseName)
                .toList();

        if (!CollectionUtils.isEmpty(alreadyAccepted)) {
            throw new CustomException(Messages.STUDENT_PASSED_EQUIVALENTS_AND_DOES_NOT_NEED_TO_ENROLL_AGAIN, String.join(", ", alreadyAccepted));
        }
    }

    private void checkCourseCapacityBeforeEnrollment(Course course) {
        if (course.getCapacity() <= course.getEnrolledCount())
            throw new CustomException(Messages.COURSE_CAPACITY_HAS_BEEN_FILLED);
    }

    @Override
    public void updateCourseStudent(UUID uuid, CourseStudentDTO courseStudentDTO) {
        CourseStudent courseStudent = courseStudentDao.getCourseStudentById(uuid);
        courseStudentMapper.updateCourseStudentFromDto(courseStudentDTO, courseStudent);
        courseStudentDao.saveOrUpdateCourseStudent(courseStudent);
    }

    @Override
    public void deleteCourseStudentById(UUID uuid) {
        Course course = courseStudentDao.getCourseStudentById(uuid).getCourse();
        course.setEnrolledCount(course.getEnrolledCount() - 1);
        courseDao.saveOrUpdateCourse(course);
        courseStudentDao.deleteCourseStudentById(uuid);
    }

    @Override
    public EnterpriseGetRowsResponse getRowsCourseStudent(EnterpriseGetRowsRequest request) {
        return courseStudentDao.getRowsCourseStudent(request);
    }
}
