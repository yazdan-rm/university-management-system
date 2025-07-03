package ir.ums.service.course;

import ir.ums.constants.Messages;
import ir.ums.dao.course.ICourseDao;
import ir.ums.dao.university.IUniversityDao;
import ir.ums.dto.course.CourseDTO;
import ir.ums.dto.course.CourseTitleIdDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.exception.CustomException;
import ir.ums.mapper.course.CourseMapper;
import ir.ums.model.course.Course;
import ir.ums.model.university.University;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {

    private final ICourseDao courseDao;
    private final IUniversityDao universityDao;
    private final CourseMapper courseMapper;
    private final ICourseScheduleService courseScheduleService;
    private final ICoursePrerequisiteService coursePrerequisiteService;


    @Override
    public CourseDTO getCourseById(UUID id) {
        return courseMapper.toDto(courseDao.getCourseById(id));
    }

    @Override
    public void saveCourse(CourseDTO courseDTO) {
        University universityEntity = universityDao.getUniversityByCodes(
                courseDTO.getCollegeCode(), courseDTO.getDepartmentCode(), courseDTO.getFieldOfStudyCode()
        );
        Course courseEntity = courseMapper.toEntity(courseDTO);
        courseEntity.setUniversity(universityEntity);
        courseEntity.setHasPrerequisiteCourse(Boolean.FALSE);
        courseEntity.setEnrolledCount(0);
        courseDao.saveOrUpdateCourse(courseEntity);
    }

    @Override
    public void updateCourse(UUID courseId, CourseDTO courseDTO) {
        checkCourseHasPrerequisiteOrNot(courseId);

        var course = courseDao.getCourseById(courseId);
        courseMapper.updateCourseFromDto(courseDTO, course);
        courseDao.saveOrUpdateCourse(course);
    }

    private void checkCourseHasPrerequisiteOrNot(UUID courseId) {
        var prerequisitesByCourseId = coursePrerequisiteService.getCoursePrerequisiteByCourseId(courseId);
        var courseSchedulesByCourseId = courseScheduleService.getCourseSchedulesByCourseId(courseId);
        if(!prerequisitesByCourseId.isEmpty() || !courseSchedulesByCourseId.isEmpty())
            throw new CustomException(Messages.DELETE_COURSE_PREREQUISITE_AND_COURSE_SCHEDULE_FIRST);
    }

    @Override
    public void deleteCourse(UUID id) {
        checkThisCourseIsPrerequisitesForOtherCoursesOrNot(id);
        courseDao.deleteCourse(id);
    }

    private void checkThisCourseIsPrerequisitesForOtherCoursesOrNot(UUID courseId) {
        var data = coursePrerequisiteService.getCoursePrerequisiteByPrerequisiteId(courseId);
        if(!data.isEmpty())
            throw new CustomException(Messages.THIS_COURSE_IS_PREREQUISITE_FOR_OTHER_COURSES);
    }

    @Override
    public List<CourseTitleIdDTO> getAllCourses(UUID courseId) {
        return courseDao.getAllCourses().stream().filter(data -> !data.id().equals(courseId)).toList();
    }

    @Override
    public EnterpriseGetRowsResponse getRowsCourses(EnterpriseGetRowsRequest request) {
        return courseDao.getRowsCourses(request);
    }

    @Override
    public EnterpriseGetRowsResponse getExclusiveCoursesForStudent(EnterpriseGetRowsRequest request) {
        return courseDao.getExclusiveCoursesForStudent(request);
    }

    @Override
    public EnterpriseGetRowsResponse getStudentEnrollmentResult(EnterpriseGetRowsRequest request) {
        return courseDao.getStudentEnrollmentResult(request);
    }
}
