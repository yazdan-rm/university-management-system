import { lazy } from "react";

const StudentEnrollmentResult = lazy(
  () => import("./StudentEnrollmentResult.jsx"),
);

const StudentEnrollmentConfigResult = {
  auth: ["student"],
  routes: [
    {
      path: "app/university/reports/student-enrollment-result",
      element: <StudentEnrollmentResult />,
    },
  ],
};

export default StudentEnrollmentConfigResult;
