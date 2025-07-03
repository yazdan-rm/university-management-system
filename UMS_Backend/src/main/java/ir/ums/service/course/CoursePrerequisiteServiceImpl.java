package ir.ums.service.course;

import ir.ums.dao.course.ICourseDao;
import ir.ums.dao.course.ICoursePrerequisiteDao;
import ir.ums.dto.course.CoursePrerequisiteDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.mapper.course.CoursePrerequisiteMapper;
import ir.ums.model.course.Course;
import ir.ums.model.course.CoursePrerequisite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoursePrerequisiteServiceImpl implements ICoursePrerequisiteService {

    private final ICourseDao courseDao;
    private final CoursePrerequisiteMapper coursePrerequisiteMapper;
    private final ICoursePrerequisiteDao coursePrerequisiteDao;

    @Override
    public CoursePrerequisiteDTO getCoursePrerequisiteById(UUID id) {
        return coursePrerequisiteMapper.toDto(coursePrerequisiteDao.getCoursePrerequisiteById(id));
    }

    @Override
    public List<CoursePrerequisiteDTO> getCoursePrerequisiteByCourseId(UUID id) {
        return coursePrerequisiteMapper.toDtoList(coursePrerequisiteDao.getCoursePrerequisiteByCourseId(id));
    }

    @Override
    public List<CoursePrerequisiteDTO> getCoursePrerequisiteByPrerequisiteId(UUID prerequisiteId) {
        return coursePrerequisiteMapper.toDtoList(coursePrerequisiteDao.getCoursePrerequisiteByPrerequisiteId(prerequisiteId));
    }

    @Override
    public void saveCoursePrerequisite(CoursePrerequisiteDTO dto) {
        Course course = courseDao.getCourseById(dto.getCourseId());
        course.setHasPrerequisiteCourse(Boolean.TRUE);
        courseDao.saveOrUpdateCourse(course);

        Course prerequisite = courseDao.getCourseById(dto.getPrerequisiteId());
        CoursePrerequisite coursePrerequisite = new CoursePrerequisite();
        coursePrerequisite.setCourse(course);
        coursePrerequisite.setPrerequisite(prerequisite);
        coursePrerequisite.setPrerequisiteType(dto.getPrerequisiteType());

        coursePrerequisiteDao.saveOrUpdateCoursePrerequisite(coursePrerequisite);
    }

    @Override
    public void updateCoursePrerequisite(UUID id, CoursePrerequisiteDTO dto) {
        var coursePrerequisite = coursePrerequisiteDao.getCoursePrerequisiteById(id);
        Course course = courseDao.getCourseById(dto.getCourseId());
        Course prerequisite = courseDao.getCourseById(dto.getPrerequisiteId());
        coursePrerequisite.setCourse(course);
        coursePrerequisite.setPrerequisite(prerequisite);
        coursePrerequisite.setPrerequisiteType(dto.getPrerequisiteType());

        coursePrerequisiteDao.saveOrUpdateCoursePrerequisite(coursePrerequisite);
    }

    @Override
    public void deleteCoursePrerequisite(UUID id) {
        coursePrerequisiteDao.deleteCoursePrerequisite(id);
    }

    @Override
    public EnterpriseGetRowsResponse getRowsCoursePrerequisites(EnterpriseGetRowsRequest request) {
        return coursePrerequisiteDao.getRowsCoursePrerequisite(request);
    }
}
