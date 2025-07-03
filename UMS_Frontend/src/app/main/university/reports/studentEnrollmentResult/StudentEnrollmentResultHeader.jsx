import useJwtAuth from "../../../../auth/services/jwt/useJwtAuth.jsx";
import Typography from "@mui/material/Typography";
import {
  getCurrentSemester,
  getEducationalLevelText,
  getSemesterTitleByCode,
} from "../../../../utils/UmsUtils.js";
import LiveShamsiClock from "app/shared-components/LiveShamsiClock.jsx";
import { useGetRowsCourseStudentMutation } from "../../UniversityApi.js";
import { useEffect, useState } from "react";

function StudentEnrollmentResultHeader() {
  const { user: jwtUser } = useJwtAuth();
  const [trigger] = useGetRowsCourseStudentMutation();
  const [totalEnrolledUnits, setTotalEnrolledUnits] = useState(0);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await trigger({});

        setTotalEnrolledUnits(
          res.data.data.data
            .map((c) => c.courseUnits)
            .reduce((a, b) => a + b, 0),
        );
      } catch (err) {
        console.error("Fetch failed", err);
      }
    };

    fetchData();
  }, [trigger]);

  return (
    <div className="flex flex-col w-full container">
      <div className="flex flex-col flex-auto sm:items-center min-w-0 p-24 md:p-32 pb-0 md:pb-0">
        <Typography className="font-medium " color="text.secondary">
          {`دانشگاه ${jwtUser?.data?.university?.university ? jwtUser?.data?.university?.university : ""}`}
        </Typography>
        <Typography
          className="text-2xl font-semibold  leading-8"
          color="secondary.main"
        >
          {`${getSemesterTitleByCode(getCurrentSemester())}`}
        </Typography>
        <Typography
          className="text-3xl font-semibold  leading-8"
          color="secondary.main"
        >
          برنامه هفتگی دانشجو در طول ثبت نام
        </Typography>
      </div>
      <div className="flex flex-col sm:flex-row flex-auto sm:items-center min-w-0 p-14 md:p-22 pb-0 md:pb-0">
        <div className="flex flex-col flex-auto">
          <Typography className="font-medium" color="text.secondary">
            سیستم جامع دانشگاهی گلستان
          </Typography>
          <Typography className="font-700">
            {`شماره دانشجو: ${jwtUser?.preferred_username}`}
          </Typography>
          <Typography className="font-700">
            {`دانشکده: ${jwtUser?.data?.university?.college ? jwtUser?.data?.university?.college : ""}`}
          </Typography>
          <Typography className="font-700">
            {`رشته تحصیلی: ${jwtUser?.data?.university?.fieldOfStudy ? jwtUser?.data?.university?.fieldOfStudy : ""}`}
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
          <Typography className="font-700">{`مقطع: ${getEducationalLevelText(jwtUser?.data?.educationalLevel)}`}</Typography>
          <Typography className="font-700">{`تعداد واحد های اخذ شده: ${totalEnrolledUnits}`}</Typography>
        </div>
      </div>
    </div>
  );
}

export default StudentEnrollmentResultHeader;
