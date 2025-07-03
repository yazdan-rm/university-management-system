import i18next from "i18next";
import en from "./navigation-i18n/en";
import fa from "./navigation-i18n/fa";

i18next.addResourceBundle("en", "navigation", en);
i18next.addResourceBundle("fa", "navigation", fa);
/**
 * The navigationConfig object is an array of navigation items for the Fuse application.
 */
const navigationConfig = [
  {
    id: "dashboard",
    title: "Dashboard",
    translate: "DASHBOARD",
    type: "item",
    icon: "heroicons-solid:clipboard-list",
    url: "/",
    auth: ["student", "admin"],
  },
  {
    id: "courses_create",
    title: "Create New Course",
    translate: "CREATE_NEW_COURSE",
    type: "item",
    icon: "material-solid:school",
    url: "/app/university/course",
    auth: ["admin"],
  },
  {
    id: "exclusive_courses_for_student",
    title: "EXCLUSIVE_COURSES_FOR_STUDENT",
    translate: "EXCLUSIVE_COURSES_FOR_STUDENT",
    type: "item",
    icon: "material-solid:assignment_ind",
    url: "/app/university/reports/exclusive-courses-for-student",
    auth: ["admin", "student"],
  },
  {
    id: "enrollment_process",
    title: "ENROLLMENT_PROCESS",
    translate: "ENROLLMENT_PROCESS",
    type: "collapse",
    icon: "material-solid:app_registration",
    auth: ["admin", "student"],
    children: [
      {
        id: "main_enrollment",
        title: "MAIN_ENROLLMENT",
        translate: "MAIN_ENROLLMENT",
        type: "item",
        icon: "material-solid:how_to_reg",
        url: "/app/university/course-enrollment/main-enrollment",
        auth: ["admin", "student"],
      },
    ],
  },
  {
    id: "student_enrollment_result",
    title: "STUDENT_ENROLLMENT_RESULT",
    translate: "STUDENT_ENROLLMENT_RESULT",
    type: "item",
    icon: "material-solid:assignment",
    url: "/app/university/reports/student-enrollment-result",
    auth: ["admin", "student"],
  },
];
export default navigationConfig;
