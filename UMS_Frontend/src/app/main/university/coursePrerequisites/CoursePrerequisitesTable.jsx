import * as React from "react";
import { AllCommunityModule, ModuleRegistry } from "ag-grid-community";
import { toShamsiDate } from "../../../utils/UmsUtils.js";
import AgGrid from "app/shared-components/AgGrid.jsx";
import { useAppDispatch, useAppSelector } from "app/store/hooks.js";
import {
  refreshAgGrid,
  selectRefreshGridFlag,
  updateData,
} from "../universitySlice.js";
import {
  useDeleteCoursePrerequisiteMutation,
  useGetRowsCoursePrerequisitesMutation,
} from "../UniversityApi.js";
import EditOutlinedIcon from "@mui/icons-material/EditOutlined";
import DeleteIcon from "@mui/icons-material/Delete";
import Button from "@mui/material/Button";
import { showMessage } from "@fuse/core/FuseMessage/fuseMessageSlice.js";
import { useParams } from "react-router-dom";

ModuleRegistry.registerModules([AllCommunityModule]);

function CoursePrerequisiteTable() {
  const [trigger] = useGetRowsCoursePrerequisitesMutation();
  const refreshGrid = useAppSelector(selectRefreshGridFlag);
  const dispatch = useAppDispatch();
  const [deleteTrigger] = useDeleteCoursePrerequisiteMutation();
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

export default CoursePrerequisiteTable;
