package ir.ums.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiPaths {

    public static final String BASE_API = "/api/v1";

    public static final class Course {
        public static final String BASE = BASE_API + "/courses";
        public static final String BY_ID = "/{id}";
        public static final String GET_ALL = "/get-all/{courseId}";
        public static final String GET_ROWS = "/get-rows";
        public static final String GET_EXCLUSIVE_COURSES_FOR_STUDENT = "/get-exclusive-courses-for-student";
        public static final String GET_STUDENT_ENROLLMENT_RESULT = "/get-student-enrollment-result";
    }

    public static final class CoursePrerequisite {
        public static final String BASE = BASE_API + "/course-prerequisites";
        public static final String BY_ID = "/{id}";
        public static final String BY_COURSE_ID = "/{courseId}";
        public static final String GET_ROWS = "/get-rows";
    }

    public static final class CourseSchedule {
        public static final String BASE = BASE_API + "/course-schedules";
        public static final String BY_ID = "/{id}";
        public static final String GET_ROWS = "/get-rows";
    }

    public static final class University {
        public static final String BASE = BASE_API + "/university";
        public static final String GET_ALL_COLLEGES = "/get-all-colleges";
        public static final String GET_DEPARTMENTS_BY_COLLEGE_CODE = "/get-departments-by-college-code/{collegeCode}";
        public static final String GET_FIELD_OF_STUDY_BY_DEPARTMENT_CODE = "/get-field-of-study-by-department-code/{departmentCode}";
    }

    public static final class Student {
        public static final String BASE = BASE_API + "/student";
        public static final String BY_USER_ID = "/{userId}";
    }

    public static final class CourseStudent {
        public static final String BASE = BASE_API + "/course-student";
        public static final String BY_ID = "/{id}";
        public static final String GET_ROWS = "/get-rows";
    }
}
