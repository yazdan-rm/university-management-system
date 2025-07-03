import { lazy } from "react";

const Dashboard = lazy(() => import("./dashboard/Dashboard.jsx"));

const UniversityAppConfig = {
  auth: ["admin", "student"],
  routes: [
    {
      path: "app/university/dashboard",
      element: <Dashboard />,
    },
  ],
};

export default UniversityAppConfig;
