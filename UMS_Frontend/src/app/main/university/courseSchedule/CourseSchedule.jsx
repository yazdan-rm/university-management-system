import FusePageCarded from "@fuse/core/FusePageCarded/FusePageCarded.jsx";
import CourseScheduleForm from "./CourseScheduleForm.jsx";
import CourseScheduleTable from "./CourseScheduleTable.jsx";
import CourseScheduleHeader from "./CourseScheduleHeader.jsx";

function CourseSchedule() {
  return (
    <FusePageCarded
      header={<CourseScheduleHeader />}
      content={
        <>
          <CourseScheduleForm />
          <CourseScheduleTable />
        </>
      }
      scroll="content"
    />
  );
}

export default CourseSchedule;
