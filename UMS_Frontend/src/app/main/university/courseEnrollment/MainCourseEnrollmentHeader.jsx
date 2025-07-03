import Typography from "@mui/material/Typography";
import LiveShamsiClock from "app/shared-components/LiveShamsiClock.jsx";
import useJwtAuth from "../../../auth/services/jwt/useJwtAuth.jsx";

function MainCourseEnrollmentHeader() {
  const { user: jwtUser } = useJwtAuth();

  return (
    <div className="flex flex-col w-full container">
      <div className="flex flex-col flex-auto sm:items-center min-w-0 p-24 md:p-32 pb-0 md:pb-0">
        <Typography className="font-medium " color="text.secondary">
          {`دانشگاه ${jwtUser?.data?.university?.university ? jwtUser?.data?.university?.university : ""}`}
        </Typography>
        <Typography
          className="text-3xl font-semibold  leading-8"
          color="secondary.main"
        >
          ثبت نام اصلی دانشجو
        </Typography>
      </div>
      <div className="flex flex-col sm:flex-row flex-auto sm:items-center min-w-0 p-14 md:p-22 pb-0 md:pb-0">
        <div className="flex flex-col flex-auto">
          <Typography className="font-700">حداکثر واحد قابل اخذ: 20</Typography>
          <Typography className="font-700">
            {`شماره دانشجو: ${jwtUser?.preferred_username}`}
          </Typography>
        </div>

        <div className="flex flex-col h-full justify-end">
          <Typography
            className="font-medium inline-flex w-[168px] justify-between"
            color="text.secondary"
          >
            <span>زمان:</span>
            <LiveShamsiClock />
          </Typography>
          <Typography className="font-700">{`نام و نام خانوادگی: ${jwtUser?.data?.displayName}`}</Typography>
        </div>
      </div>
    </div>
  );
}

export default MainCourseEnrollmentHeader;
