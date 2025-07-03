import FusePageCarded from "@fuse/core/FusePageCarded/FusePageCarded.jsx";
import CoursePrerequisitesHeader from "./CoursePrerequisitesHeader.jsx";
import CoursePrerequisitesForm from "./CoursePrerequisitesForm.jsx";
import CoursePrerequisitesTable from "./CoursePrerequisitesTable.jsx";

function CoursePrerequisites() {
  return (
    <FusePageCarded
      header={<CoursePrerequisitesHeader />}
      content={
        <>
          <CoursePrerequisitesForm />
          <CoursePrerequisitesTable />
        </>
      }
      scroll="content"
    />
  );
}

export default CoursePrerequisites;
