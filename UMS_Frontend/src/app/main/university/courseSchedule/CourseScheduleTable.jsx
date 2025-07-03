import {
  useDeleteCourseScheduleMutation,
  useGetRowsCourseScheduleMutation,
} from "../UniversityApi.js";
import { useAppDispatch, useAppSelector } from "app/store/hooks.js";
import EditOutlinedIcon from "@mui/icons-material/EditOutlined";
import DeleteIcon from "@mui/icons-material/Delete";
import {
  refreshAgGrid,
  selectRefreshGridFlag,
  updateData,
} from "../universitySlice.js";
import { useParams } from "react-router-dom";
import { showMessage } from "@fuse/core/FuseMessage/fuseMessageSlice.js";
import { toShamsiDate } from "../../../utils/UmsUtils.js";
import AgGrid from "app/shared-components/AgGrid.jsx";
import * as React from "react";
import Button from "@mui/material/Button";

function CourseScheduleTable() {
  const [trigger] = useGetRowsCourseScheduleMutation();
  const refreshGrid = useAppSelector(selectRefreshGridFlag);
  const dispatch = useAppDispatch();
  const [deleteTrigger] = useDeleteCourseScheduleMutation();
  const { courseId } = useParams();

  const handleEdit = (data) => {
    dispatch(updateData(data));
  };

  const handleDelete = (data) => {
    deleteTrigger(data.id)
      .unwrap()
      .then((data) => {
        dispatch(showMessage({ message: data.message }));
        dispatch(refreshAgGrid());
      })
      .catch((e) => {
        dispatch(
          showMessage({ message: e.response.data.message, variant: "error" }),
        );
        dispatch(refreshAgGrid());
      });
  };

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
      headerName: "روز ارائه",
      field: "daysOfWeek",
      valueFormatter: (params) => params.value,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "ساعت شروع ارائه",
      field: "courseStartTime",
      valueFormatter: (params) => params.value,
      tooltipValueGetter: (params) => params.value,
    },
    {
      headerName: "ساعت پایان ارائه",
      field: "courseEndTime",
      valueFormatter: (params) => params.value,
      tooltipValueGetter: (params) => params.value,
    },
    {
      headerName: "تاریخ امتحان",
      field: "courseExamDate",
      valueFormatter: (params) => params.value,
      tooltipValueGetter: (params) => params.value,
    },
    {
      headerName: "ساعت شروع امتحان",
      field: "courseExamTime",
      valueFormatter: (params) => params.value,
      tooltipValueGetter: (params) => params.value,
    },
    {
      headerName: "ویرایش",
      cellRenderer: (params) => (
        <Button type="button" onClick={() => handleEdit(params.data)}>
          <EditOutlinedIcon className="text-blue-500" fontSize="medium" />
        </Button>
      ),
      width: 30,
      sortable: false,
    },
    {
      headerName: "حذف",
      cellRenderer: (params) => (
        <Button type="button" onClick={() => handleDelete(params.data)}>
          <DeleteIcon className="text-red-500" fontSize="medium" />
        </Button>
      ),
      width: 30,
      sortable: false,
    },
  ];

  return (
    <AgGrid
      refreshGrid={refreshGrid}
      columnDefs={columnDefs}
      fetchData={trigger}
      masterId={courseId}
    />
  );
}

export default CourseScheduleTable;
