import Breadcrumbs from "@mui/material/Breadcrumbs";
import FuseSvgIcon from "@fuse/core/FuseSvgIcon";
import { Link, useParams } from "react-router-dom";
import Typography from "@mui/material/Typography";

function CourseScheduleHeader() {
  const { courseId } = useParams();
  return (
    <div className="flex flex-col p-24 w-full sm:py-5 sm:px-40">
      <Typography
        component="h2"
        className="flex-1 text-3xl md:text-3xl font-extrabold tracking-tight leading-7 sm:leading-10 truncate"
      >
        تعریف برنامه ارائه و امتحان
      </Typography>
      <div className="flex items-center w-full my-5 -mx-10">
        <Breadcrumbs
          separator={
            <FuseSvgIcon size={20}>heroicons-solid:chevron-left</FuseSvgIcon>
          }
          aria-label="breadcrumb"
        >
          <Link
            className="font-medium hover:underline"
            key="1"
            color="inherit"
            to="/app/university/course"
          >
            تعریف دوره جدید
          </Link>
          <Link
            className="font-medium hover:underline"
            key="2"
            color="inherit"
            to={`/app/university/course/course-prerequisites/${courseId}`}
          >
            تعریف پیش نیاز
          </Link>
          <Link
            className="font-medium hover:underline"
            key="3"
            color="inherit"
            to={``}
          >
            تعریف برنامه ارائه و امتحان
          </Link>
        </Breadcrumbs>
      </div>
    </div>
  );
}

export default CourseScheduleHeader;
