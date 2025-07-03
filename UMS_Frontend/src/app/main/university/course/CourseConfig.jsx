import { lazy } from "react";

const Course = lazy(() => import("./Course.jsx"));

const CourseConfig = {
  auth: ["admin"],
  routes: [
    {
      path: "app/university/course",
      element: <Course />,
    },
  ],
};

export default CourseConfig;
