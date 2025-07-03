import Typography from "@mui/material/Typography";

function CourseHeader() {
  return (
    <div className="flex flex-col p-24 w-full sm:py-5 sm:px-40">
      <div className="flex items-center w-full mt-2 -mx-10">
        <Typography
          component="h2"
          className="flex-1 text-3xl md:text-3xl font-extrabold tracking-tight leading-7 sm:leading-10 truncate"
        >
          تعریف دوره جدید
        </Typography>
      </div>
    </div>
  );
}

export default CourseHeader;
