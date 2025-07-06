import FusePageSimple from "@fuse/core/FusePageSimple/FusePageSimple.jsx";
import { motion } from "framer-motion";
import MainCourseEnrollmentHeader from "./MainCourseEnrollmentHeader.jsx";
import MainCourseEnrollmentTable from "./MainCourseEnrollmentTable.jsx";
import MainShowCourseTable from "./MainShowCourseTable.jsx";

function MainCourseEnrollment() {
  const container = {
    show: {
      transition: {
        staggerChildren: 0.04,
      },
    },
  };
  const item = {
    hidden: { opacity: 0, y: 20 },
    show: { opacity: 1, y: 0 },
  };

  return (
    <FusePageSimple
      header={<MainCourseEnrollmentHeader />}
      content={
        <motion.div
          className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 w-full "
          variants={container}
          initial="hidden"
          animate="show"
        >
          <motion.div variants={item} className="sm:col-span-2 lg:col-span-3">
            <MainCourseEnrollmentTable />
          </motion.div>
          <motion.div variants={item} className="sm:col-span-2 lg:col-span-3">
            <MainShowCourseTable />
          </motion.div>
        </motion.div>
      }
      scroll="content"
    />
  );
}

export default MainCourseEnrollment;
