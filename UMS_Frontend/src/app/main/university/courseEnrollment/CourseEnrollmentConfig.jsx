import { lazy } from "react";

const MainCourseEnrollment = lazy(() => import("./MainCourseEnrollment.jsx"));

const CourseEnrollmentConfig = {
  auth: ["student"],
  routes: [
    {
      path: "app/university/course-enrollment/main-enrollment",
      element: <MainCourseEnrollment />,
    },
  ],
};

export default CourseEnrollmentConfig;
