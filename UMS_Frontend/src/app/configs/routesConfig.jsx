import FuseUtils from "@fuse/utils";
import FuseLoading from "@fuse/core/FuseLoading";
import { Navigate } from "react-router-dom";
import settingsConfig from "app/configs/settingsConfig";
import SignUpConfig from "../main/sign-up/SignUpConfig";
import SignOutConfig from "../main/sign-out/SignOutConfig";
import Error404Page from "../main/404/Error404Page";
import UniversityAppConfig from "../main/university/UniversityAppConfig.jsx";
import CourseConfig from "../main/university/course/CourseConfig.jsx";
import CoursePrerequisitesConfig from "../main/university/coursePrerequisites/CoursePrerequisitesConfig.jsx";
import CourseScheduleConfig from "../main/university/courseSchedule/CourseScheduleConfig.jsx";
import CourseEnrollmentConfig from "../main/university/courseEnrollment/CourseEnrollmentConfig.jsx";
import StudentEnrollmentConfigResult from "../main/university/reports/studentEnrollmentResult/StudentEnrollmentConfigResult.jsx";
import ExclusiveCoursesForStudent from "../main/university/reports/exclusiveCoursesForStudent/ExclusiveCoursesForStdConfig.jsx";

const routeConfigs = [
  ExclusiveCoursesForStudent,
  StudentEnrollmentConfigResult,
  CourseEnrollmentConfig,
  CourseScheduleConfig,
  CourseConfig,
  CoursePrerequisitesConfig,
  UniversityAppConfig,
  SignOutConfig,
  SignUpConfig,
];
/**
 * The routes of the application.
 */
const routes = [
  ...FuseUtils.generateRoutesFromConfigs(
    routeConfigs,
    settingsConfig.defaultAuth,
  ),
  {
    path: "/",
    element: <Navigate to="app/university/dashboard" />,
    auth: settingsConfig.defaultAuth,
  },
  {
    path: "loading",
    element: <FuseLoading />,
  },
  {
    path: "404",
    element: <Error404Page />,
    auth: settingsConfig.defaultAuth,
  },
  {
    path: "*",
    element: <Navigate to="404" />,
  },
];
export default routes;
