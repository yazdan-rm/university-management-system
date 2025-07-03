import { lazy } from "react";

const ExclusiveCoursesForStudent = lazy(
  () => import("./ExclusiveCoursesForStudent.jsx"),
);

const ExclusiveCoursesForStdConfig = {
  auth: ["student"],
  routes: [
    {
      path: "app/university/reports/exclusive-courses-for-student",
      element: <ExclusiveCoursesForStudent />,
    },
  ],
};

export default ExclusiveCoursesForStdConfig;
