import { lazy } from "react";

const CoursePrerequisites = lazy(() => import("./CoursePrerequisites.jsx"));

const CoursePrerequisitesConfig = {
  auth: ["admin"],
  routes: [
    {
      path: "app/university/course/course-prerequisites/:courseId",
      element: <CoursePrerequisites />,
    },
  ],
};

export default CoursePrerequisitesConfig;
