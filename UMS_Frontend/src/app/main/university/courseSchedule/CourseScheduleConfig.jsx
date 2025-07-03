import { lazy } from "react";

const CourseSchedule = lazy(() => import("./CourseSchedule.jsx"));

const CourseScheduleConfig = {
  auth: ["admin"],
  routes: [
    {
      path: "app/university/course/course-schedules/:courseId",
      element: <CourseSchedule />,
    },
  ],
};

export default CourseScheduleConfig;
