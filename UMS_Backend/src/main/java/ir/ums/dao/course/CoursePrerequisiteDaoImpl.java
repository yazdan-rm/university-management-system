package ir.ums.dao.course;

import ir.ums.builder.ViewQueryDao;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.model.course.Course;
import ir.ums.model.course.CoursePrerequisite;
import ir.ums.repository.course.ICoursePrerequisiteRepository;
import ir.ums.repository.course.ICourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CoursePrerequisiteDaoImpl implements ICoursePrerequisiteDao {

    private final ViewQueryDao viewQueryDao;
    private final ICourseRepository courseRepository;
    private final ICoursePrerequisiteRepository coursePrerequisiteRepository;

    @Override
    public CoursePrerequisite getCoursePrerequisiteById(UUID id) {
        return coursePrerequisiteRepository.findById(id).orElse(null);
    }

    @Override
    public List<CoursePrerequisite> getCoursePrerequisiteByCourseId(UUID courseId) {
        return coursePrerequisiteRepository.findCoursePrerequisiteByCourse_Id(courseId);
    }

    @Override
    public List<CoursePrerequisite> getCoursePrerequisiteByCourseIdAndPreType(UUID courseId, String preType) {
        return coursePrerequisiteRepository.findCoursePrerequisiteByCourse_idAndPrerequisiteType(courseId, preType);
    }

    @Override
    public List<CoursePrerequisite> getCoursePrerequisiteByPrerequisiteId(UUID prerequisiteId) {
        Course prerequisiteCourse = courseRepository.findById(prerequisiteId).orElseThrow();
        return coursePrerequisiteRepository.findByPrerequisite(prerequisiteCourse);
    }

    @Override
    public void saveOrUpdateCoursePrerequisite(CoursePrerequisite coursePrerequisite) {
        coursePrerequisiteRepository.save(coursePrerequisite);
    }

    @Override
    public void deleteCoursePrerequisite(UUID id) {
        coursePrerequisiteRepository.deleteById(id);
    }

    @Override
    public EnterpriseGetRowsResponse getRowsCoursePrerequisite(EnterpriseGetRowsRequest request) {
        String viewQuery = """
                WITH course_pre_view AS (SELECT cp.id                AS id,
                                                cp.create_date       AS createDate,
                                                crs.id               AS courseId,
                                                crs.course_name      AS courseName,
                                                cpp.id               AS prerequisiteId,
                                                cpp.course_name      AS prerequisiteName,
                                                cp.prerequisite_type AS prerequisiteType
                                         FROM course_prerequisite cp
                                                  INNER JOIN course crs ON cp.fk_course_id = crs.id
                                                  INNER JOIN course cpp ON cpp.id = cp.fk_prerequisite_id
                                                  where cp.fk_course_id = '::courseId')
                SELECT *
                FROM course_pre_view
                """;
        viewQuery = viewQuery.replace("::courseId", request.getMasterId());
        return viewQueryDao.getRows(request, viewQuery);
    }
}
