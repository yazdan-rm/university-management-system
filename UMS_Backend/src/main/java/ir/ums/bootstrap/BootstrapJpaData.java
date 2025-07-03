package ir.ums.bootstrap;

import com.github.javafaker.Faker;
import ir.ums.model.course.Course;
import ir.ums.model.course.CoursePrerequisite;
import ir.ums.model.university.University;
import ir.ums.repository.course.ICoursePrerequisiteRepository;
import ir.ums.repository.course.ICourseRepository;
import ir.ums.repository.university.IUniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class BootstrapJpaData implements CommandLineRunner {

    private final ICourseRepository courseRepository;
    private final IUniversityRepository universityRepository;
    private final ICoursePrerequisiteRepository coursePrerequisiteRepository;

    Faker faker = new Faker(Locale.of("fa"));

    @Override
    public void run(String... args) {
//        universityData();
//        courseData();
//        coursePrerequisite();
    }

    private void coursePrerequisite() {
        if(coursePrerequisiteRepository.count() == 0){
            Course mainCourse = courseRepository.findAll().getFirst();
            Course relatedCourse1 = courseRepository.findAll().get(1);
            Course relatedCourse2 = courseRepository.findAll().get(2);

            var coursePrerequisite1 = new CoursePrerequisite();
            coursePrerequisite1.setCourse(mainCourse);
            coursePrerequisite1.setPrerequisite(relatedCourse1);
            coursePrerequisite1.setPrerequisiteType("همنياز");
            coursePrerequisite1.setCreateDate(LocalDateTime.now());
            coursePrerequisiteRepository.save(coursePrerequisite1);

            var coursePrerequisite2 = new CoursePrerequisite();
            coursePrerequisite2.setCourse(mainCourse);
            coursePrerequisite2.setPrerequisite(relatedCourse2);
            coursePrerequisite2.setPrerequisiteType("پیش نیاز");
            coursePrerequisite2.setCreateDate(LocalDateTime.now());
            coursePrerequisiteRepository.save(coursePrerequisite2);
        }
    }

    private void courseData() {
        if (courseRepository.count() == 0) {
            List<University> universities = universityRepository.findAll();

            if (universities.isEmpty()) {
                // If universities aren't loaded yet, we skip to avoid exceptions
                System.out.println("⚠️ No universities found to assign to courses.");
                return;
            }

            Random random = new Random();

            List<Course> courses = Stream.generate(() -> {
                Course course = new Course();
                course.setCreateDate(faker.date().birthday(18, 65)
                        .toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDateTime());

                course.setCourseName(faker.book().title());
                course.setSemester("400" + faker.number().numberBetween(1, 4));
                course.setCapacity(faker.number().numberBetween(20, 31));
                course.setAllowedGenders(faker.number().numberBetween(1, 4));
                course.setEnrolledCount(0);
                course.setHasPrerequisiteCourse(Boolean.FALSE);
                course.setLocation(faker.university().name());
                course.setInstructorName(faker.name().fullName());
                course.setEducationalLevel(List.of(1, 3, 4, 5, 6, 8, 9, 14).get(faker.number().numberBetween(0, 3)));
                course.setCourseUnits(3);

                // Assign a random university to the course
                course.setUniversity(universities.get(random.nextInt(universities.size())));

                return course;
            }).limit(1000).toList();

            courseRepository.saveAll(courses);
        }
    }


    private void universityData() {
        if(universityRepository.count() == 0) {
            University university = new University();
            university.setUniversity("علامه طباطبایی");
            university.setUniversityCode(1L);
            university.setCollege("کلیه دانشکده ها");
            university.setCollegeCode(1L);
            university.setDepartment("کلیه گروه های آموزشی");
            university.setDepartmentCode(1L);
            university.setFieldOfStudy("مجازی");
            university.setFieldOfStudyCode(1L);
            universityRepository.save(university);


            University university2 = new University();
            university2.setUniversity("علامه طباطبایی");
            university2.setUniversityCode(1L);
            university2.setCollege("دانشجوی مهمان");
            university2.setCollegeCode(2L);
            university2.setDepartment("کلیه گروه های آموزشی");
            university2.setDepartmentCode(1L);
            university2.setFieldOfStudy("کلیه رشته ها");
            university2.setFieldOfStudyCode(2L);
            universityRepository.save(university2);

            University university1 = new University();
            university1.setUniversity("علامه طباطبایی");
            university1.setUniversityCode(1L);
            university1.setCollege("دانشجوی مهمان");
            university1.setCollegeCode(2L);
            university1.setDepartment("دانشجوی مهمان");
            university1.setDepartmentCode(2L);
            university1.setFieldOfStudy("کلیه رشته ها");
            university1.setFieldOfStudyCode(2L);
            universityRepository.save(university1);

            University university3 = new University();
            university3.setUniversity("علامه طباطبایی");
            university3.setUniversityCode(1L);
            university3.setCollege("ادبيات فارسي و زبانهاي خارجي");
            university3.setCollegeCode(3L);
            university3.setDepartment("کلیه گروه های آموزشی");
            university3.setDepartmentCode(1L);
            university3.setFieldOfStudy("آموزش زبان انگليسي/دانشوري");
            university3.setFieldOfStudyCode(3L);
            universityRepository.save(university3);

            University university4 = new University();
            university4.setUniversity("علامه طباطبایی");
            university4.setUniversityCode(1L);
            university4.setCollege("ادبيات فارسي و زبانهاي خارجي");
            university4.setCollegeCode(3L);
            university4.setDepartment("کلیه گروه های آموزشی");
            university4.setDepartmentCode(1L);
            university4.setFieldOfStudy("مجازی");
            university4.setFieldOfStudyCode(4L);
            universityRepository.save(university4);

            University university5 = new University();
            university5.setUniversity("علامه طباطبایی");
            university5.setUniversityCode(1L);
            university5.setCollege("ادبيات فارسي و زبانهاي خارجي");
            university5.setCollegeCode(3L);
            university5.setDepartment("ادبيات عرب");
            university5.setDepartmentCode(3L);
            university5.setFieldOfStudy("مجازي");
            university5.setFieldOfStudyCode(5L);
            universityRepository.save(university5);

            University university6 = new University();
            university6.setUniversity("علامه طباطبایی");
            university6.setUniversityCode(1L);
            university6.setCollege("ادبيات فارسي و زبانهاي خارجي");
            university6.setCollegeCode(3L);
            university6.setDepartment("ادبيات عرب");
            university6.setDepartmentCode(3L);
            university6.setFieldOfStudy("مترجمي زبان عربي");
            university6.setFieldOfStudyCode(6L);
            universityRepository.save(university6);

            University university7 = new University();
            university7.setUniversity("علامه طباطبایی");
            university7.setUniversityCode(1L);
            university7.setCollege("ادبيات فارسي و زبانهاي خارجي");
            university7.setCollegeCode(3L);
            university7.setDepartment("ادبيات عرب");
            university7.setDepartmentCode(3L);
            university7.setFieldOfStudy("زبان و ادبيات عربي");
            university7.setFieldOfStudyCode(7L);
            universityRepository.save(university7);

            University university8 = new University();
            university8.setUniversity("علامه طباطبایی");
            university8.setUniversityCode(1L);
            university8.setCollege("ادبيات فارسي و زبانهاي خارجي");
            university8.setCollegeCode(3L);
            university8.setDepartment("ادبيات عرب");
            university8.setDepartmentCode(3L);
            university8.setFieldOfStudy("ادبيات عربي");
            university8.setFieldOfStudyCode(8L);
            universityRepository.save(university8);

            University university9 = new University();
            university9.setUniversity("علامه طباطبایی");
            university9.setUniversityCode(1L);
            university9.setCollege("ادبيات فارسي و زبانهاي خارجي");
            university9.setCollegeCode(3L);
            university9.setDepartment("ادبيات فارسي");
            university9.setDepartmentCode(4L);
            university9.setFieldOfStudy("زبان و ادبيات فارسي");
            university9.setFieldOfStudyCode(9L);
            universityRepository.save(university9);

            University university10 = new University();
            university10.setUniversity("علامه طباطبایی");
            university10.setUniversityCode(1L);
            university10.setCollege("ادبيات فارسي و زبانهاي خارجي");
            university10.setCollegeCode(3L);
            university10.setDepartment("ادبيات فارسي");
            university10.setDepartmentCode(4L);
            university10.setFieldOfStudy("زبان و ادبيات فارسي ويژه غيرفاسی زبان ها");
            university10.setFieldOfStudyCode(10L);
            universityRepository.save(university10);

            University university11 = new University();
            university11.setUniversity("علامه طباطبایی");
            university11.setUniversityCode(1L);
            university11.setCollege("آمار، رياضي و رايانه");
            university11.setCollegeCode(4L);
            university11.setDepartment("کليه گروه هاي آموزشي");
            university11.setDepartmentCode(5L);
            university11.setFieldOfStudy("مجازی");
            university11.setFieldOfStudyCode(11L);
            universityRepository.save(university11);

            University university13 = new University();
            university13.setUniversity("علامه طباطبایی");
            university13.setUniversityCode(1L);
            university13.setCollege("آمار، رياضي و رايانه");
            university13.setCollegeCode(4L);
            university13.setDepartment("رایانه");
            university13.setDepartmentCode(6L);
            university13.setFieldOfStudy("مجازی");
            university13.setFieldOfStudyCode(12L);
            universityRepository.save(university13);

            University university14 = new University();
            university14.setUniversity("علامه طباطبایی");
            university14.setUniversityCode(1L);
            university14.setCollege("آمار، رياضي و رايانه");
            university14.setCollegeCode(4L);
            university14.setDepartment("رایانه");
            university14.setDepartmentCode(6L);
            university14.setFieldOfStudy("مهندسی کامپیوتر");
            university14.setFieldOfStudyCode(13L);
            universityRepository.save(university14);

            University university15 = new University();
            university15.setUniversity("علامه طباطبایی");
            university15.setUniversityCode(1L);
            university15.setCollege("آمار، رياضي و رايانه");
            university15.setCollegeCode(4L);
            university15.setDepartment("رايانه");
            university15.setDepartmentCode(6L);
            university15.setFieldOfStudy("علوم كامپيوتر - زمينه سيستم هاي كامپيوتري");
            university15.setFieldOfStudyCode(14L);
            universityRepository.save(university15);

            University university16 = new University();
            university16.setUniversity("علامه طباطبایی");
            university16.setUniversityCode(1L);
            university16.setCollege("آمار، رياضي و رايانه");
            university16.setCollegeCode(4L);
            university16.setDepartment("رايانه");
            university16.setDepartmentCode(6L);
            university16.setFieldOfStudy("علوم كامپيوتر - زمينه هاي نظريه محاسبه");
            university16.setFieldOfStudyCode(15L);
            universityRepository.save(university16);

            University university17 = new University();
            university17.setUniversity("علامه طباطبایی");
            university17.setUniversityCode(1L);
            university17.setCollege("آمار، رياضي و رايانه");
            university17.setCollegeCode(4L);
            university17.setDepartment("رايانه");
            university17.setDepartmentCode(6L);
            university17.setFieldOfStudy("علوم كامپيوتر");
            university17.setFieldOfStudyCode(16L);
            universityRepository.save(university17);

        }
    }
}
