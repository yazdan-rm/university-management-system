import * as React from "react";
import { toShamsiDate } from "../../../utils/UmsUtils.js";
import AgGrid from "app/shared-components/AgGrid.jsx";
import { useGetRowsCoursePrerequisitesMutation } from "../UniversityApi.js";
import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import DialogTitle from "@mui/material/DialogTitle";

function CoursePrerequisiteDialog({ courseId, open, onClose }) {
  const [trigger] = useGetRowsCoursePrerequisitesMutation();

  const columnDefs = [
    {
      headerName: "تاریخ ایجاد",
      field: "createDate",
      initialSort: "desc",
      filter: false,
      valueFormatter: (params) => toShamsiDate(params.value),
      tooltipValueGetter: (params) => {
        return `${toShamsiDate(params.value)}`;
      },
    },
    {
      headerName: "نام دوره",
      field: "courseName",
      minWidth: 150,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "نام دوره پیش نیاز",
      field: "prerequisiteName",
      minWidth: 150,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "نوع پیش نیازی دوره",
      field: "prerequisiteType",
      minWidth: 150,
      valueFormatter: (params) => params.value,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
  ];

  return (
    <Dialog open={open} onClose={onClose} fullWidth={true} maxWidth={"md"}>
      <DialogTitle sx={{ m: 0, p: 2 }}>انواع دروس پیش نیاز</DialogTitle>
      <IconButton
        onClick={onClose}
        sx={(theme) => ({
          position: "absolute",
          right: 8,
          top: 8,
          color: theme.palette.grey[500],
        })}
      >
        <CloseIcon />
      </IconButton>
      <DialogContent dividers>
        {open && (
            <AgGrid
                columnDefs={columnDefs}
                fetchData={trigger}
                masterId={courseId}
            />
        )}
      </DialogContent>
    </Dialog>
  );
}

export default CoursePrerequisiteDialog;
