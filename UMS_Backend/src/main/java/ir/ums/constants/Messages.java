package ir.ums.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Messages {
    public static final String DATA_WAS_FOUND = "data.was.found";
    public static final String DATA_SAVED_SUCCESSFULLY = "data.saved.successfully";
    public static final String DATA_UPDATED_SUCCESSFULLY = "data.updated.successfully";
    public static final String DATA_HAS_BEEN_DELETED = "data.has.been.deleted";
    public static final String DELETE_COURSE_PREREQUISITE_AND_COURSE_SCHEDULE_FIRST= "delete.course.prerequisite.and.course.schedule.first";
    public static final String THIS_COURSE_IS_PREREQUISITE_FOR_OTHER_COURSES ="this.course.is.prerequisite.for.other.courses";
    public static final String USER_IS_NOT_ENROLLED_AS_STUDENT = "user.is.not.enrolled.as.student";
    public static final String STUDENT_ENROLLED_IN_THIS_COURSE_PREVIOUSLY = "student.enrolled.in.this.course.previously";
    public static final String STUDENT_HAS_NOT_PASSED_FOLLOWING_PREREQUISITES= "student.has.not.passed.following.prerequisites";
    public static final String STUDENT_HAS_NOT_PASSED_OR_ENROLLED_IN_FOLLOWING_COREQUISITES = "student.has.not.passed.or.enrolled.in.following.courses";
    public static final String STUDENT_PASSED_EQUIVALENTS_AND_DOES_NOT_NEED_TO_ENROLL_AGAIN = "student.passed.equivalents.and.not.need.enroll.again";
    public static final String COURSE_CAPACITY_HAS_BEEN_FILLED = "course.capacity.has.been.filled";
    public static final String TOTAL_UNITS_IS_MORE_THAN_CEIL = "total.units.is.more.than.ceil";
}
