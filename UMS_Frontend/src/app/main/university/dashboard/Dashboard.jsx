import { motion } from "framer-motion";
import ShortcutLink from "./ShortcutLink.jsx";
import SchoolRoundedIcon from "@mui/icons-material/SchoolRounded";
import Typography from "@mui/material/Typography";
import AssignmentIndIcon from "@mui/icons-material/AssignmentInd";
import HowToRegIcon from "@mui/icons-material/HowToReg";
import AssignmentIcon from "@mui/icons-material/Assignment";
import i18next from "i18next";
import en from "./dashboard-i18n/en.js";
import fa from "./dashboard-i18n/fa.js";
import { useTranslation } from "react-i18next";
import useJwtAuth from "../../../auth/services/jwt/useJwtAuth.jsx";

i18next.addResourceBundle("en", "dashboardPage", en);
i18next.addResourceBundle("fa", "dashboardPage", fa);

function Dashboard() {
  const { user: jwtUser } = useJwtAuth();

  const { t } = useTranslation("dashboardPage");
  const container = {
    show: {
      transition: {
        staggerChildren: 0.1,
      },
    },
  };
  const item = {
    hidden: { opacity: 0, y: 50 },
    show: { opacity: 1, y: 0 },
  };

  return (
    <motion.div
      className="flex flex-wrap justify-center md:justify-start md:items-start gap-40 p-6 m-24"
      variants={container}
      initial="hidden"
      animate="show"
    >
      {jwtUser?.role === "admin" && (
        <motion.div variants={item}>
          <ShortcutLink redirectUrl={"/app/university/course"}>
            <SchoolRoundedIcon
              sx={{ margin: "0 auto" }}
              className={`text-[12rem] h-[15rem] text-gray-300`}
            />
            <Typography className={"text-gray-300"}>
              {t("CREATE_COURSE")}
            </Typography>
          </ShortcutLink>
        </motion.div>
      )}
      {jwtUser?.role === "student" && (
        <motion.div variants={item}>
          <ShortcutLink
            redirectUrl={
              "/app/university/reports/exclusive-courses-for-student"
            }
          >
            <AssignmentIndIcon
              sx={{ margin: "0 auto" }}
              className={`text-[12rem] h-[15rem] text-gray-300`}
            />
            <Typography className={"text-gray-300"}>
              {t("EXCLUSIVE_COURSES_FOR_STUDENT")}
            </Typography>
          </ShortcutLink>
        </motion.div>
      )}
      {jwtUser?.role === "student" && (
        <motion.div variants={item}>
          <ShortcutLink
            redirectUrl={"/app/university/course-enrollment/main-enrollment"}
          >
            <HowToRegIcon
              sx={{ margin: "0 auto" }}
              className={`text-[12rem] h-[15rem] text-gray-300`}
            />
            <Typography className={"text-gray-300"}>
              {t("MAIN_ENROLLMENT")}
            </Typography>
          </ShortcutLink>
        </motion.div>
      )}
      {jwtUser?.role === "student" && (
        <motion.div variants={item}>
          <ShortcutLink
            redirectUrl={"/app/university/reports/student-enrollment-result"}
          >
            <AssignmentIcon
              sx={{ margin: "0 auto" }}
              className={`text-[12rem] h-[15rem] text-gray-300`}
            />
            <Typography className={"text-gray-300"}>
              {t("STUDENT_ENROLLMENT_RESULT")}
            </Typography>
          </ShortcutLink>
        </motion.div>
      )}
    </motion.div>
  );
}

export default Dashboard;
