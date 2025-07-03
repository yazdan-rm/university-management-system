package ir.ums.service.course;

import ir.ums.dao.course.ICourseDao;
import ir.ums.dao.course.ICourseScheduleDao;
import ir.ums.dto.course.CourseScheduleDTO;
import ir.ums.dto.getRows.request.EnterpriseGetRowsRequest;
import ir.ums.dto.getRows.response.EnterpriseGetRowsResponse;
import ir.ums.mapper.course.CourseScheduleMapper;
import ir.ums.model.course.Course;
import ir.ums.model.course.CourseSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseScheduleServiceImpl implements ICourseScheduleService {

    private final ICourseScheduleDao courseScheduleDao;
    private final ICourseDao courseDao;
    private final CourseScheduleMapper courseScheduleMapper;

    @Override
    public CourseScheduleDTO getCourseScheduleById(UUID id) {
        return courseScheduleMapper.toDto(courseScheduleDao.getCourseScheduleById(id));
    }

    @Override
    public List<CourseScheduleDTO> getCourseSchedulesByCourseId(UUID id) {
        Course course = courseDao.getCourseById(id);
        return courseScheduleMapper.toDtoList(courseScheduleDao.getCourseScheduleByCourse(course));
    }

    @Override
    public void saveCourseSchedule(CourseScheduleDTO courseScheduleDTO) {
        var course = courseDao.getCourseById(courseScheduleDTO.getCourseId());
        CourseSchedule courseSchedule = courseScheduleMapper.toEntity(courseScheduleDTO);
        courseSchedule.setCourse(course);
        courseScheduleDao.saveOrUpdateCourseSchedule(courseSchedule);
    }

    @Override
    public void updateCourseSchedule(UUID courseScheduleId, CourseScheduleDTO courseScheduleDTO) {
        CourseSchedule courseSchedule = courseScheduleDao.getCourseScheduleById(courseScheduleId);
        courseScheduleMapper.updateCourseScheduleFromDto(courseScheduleDTO, courseSchedule);
        courseScheduleDao.saveOrUpdateCourseSchedule(courseSchedule);
    }

    @Override
    public void deleteCourseSchedule(UUID id) {
        courseScheduleDao.deleteCourseSchedule(id);
    }

    @Override
    public EnterpriseGetRowsResponse getRowsCourseSchedule(EnterpriseGetRowsRequest request) {
        return courseScheduleDao.getRowsCourseSchedule(request);
    }
}
