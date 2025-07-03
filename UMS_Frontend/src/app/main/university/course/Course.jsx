import FusePageCarded from "@fuse/core/FusePageCarded/index.js";
import CourseHeader from "./CourseHeader.jsx";
import CourseForm from "./CourseForm.jsx";
import CourseTable from "./CourseTable.jsx";

function Course() {
  return (
    <FusePageCarded
      header={<CourseHeader />}
      content={
        <>
          <CourseForm />
          <CourseTable />
        </>
      }
      scroll="content"
    />
  );
}

export default Course;
