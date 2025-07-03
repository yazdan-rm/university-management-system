import {
  useDeleteCourseStudentMutation,
  useGetRowsCourseStudentMutation,
} from "../UniversityApi.js";
import { useAppDispatch, useAppSelector } from "app/store/hooks.js";
import { refreshAgGrid, selectRefreshGridFlag } from "../universitySlice.js";
import { showMessage } from "@fuse/core/FuseMessage/fuseMessageSlice.js";
import AgGrid from "app/shared-components/AgGrid.jsx";
import { toShamsiDate } from "../../../utils/UmsUtils.js";
import Button from "@mui/material/Button";
import CloseRoundedIcon from "@mui/icons-material/CloseRounded";

function MainCourseEnrollmentTable() {
  const [trigger] = useGetRowsCourseStudentMutation();
  const [deleteCourseStudentTrigger] = useDeleteCourseStudentMutation();
  const refreshGrid = useAppSelector(selectRefreshGridFlag);
  const dispatch = useAppDispatch();

  const handleDelete = (data) => {
    deleteCourseStudentTrigger(data.id)
      .unwrap()
      .then((data) => {
        dispatch(showMessage({ message: data.message }));
        dispatch(refreshAgGrid());
      })
      .catch((e) => {
        dispatch(
          showMessage({ message: e.response.data.message, variant: "error" }),
        ),
          dispatch(refreshAgGrid());
      });
  };

  const columnDefs = [
    {
      headerName: "تاریخ ایجاد",
      field: "createDate",
      filter: false,
      minWidth: 110,
      initialSort: "desc",
      valueFormatter: (params) => toShamsiDate(params.value),
      tooltipValueGetter: (params) => {
        return `${toShamsiDate(params.value)}`;
      },
    },
    {
      headerName: "نام دوره",
      field: "courseName",
      minWidth: 450,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "تعداد واحد",
      field: "courseUnits",
      minWidth: 105,
    },
    {
      headerName: "عملیات",
      pinned: "left",
      field: "instructorName",
      minWidth: 30,
      cellRenderer: (params) =>
        params.data ? (
          <Button
            size="small"
            color="primary"
            variant="contained"
            type="button"
            endIcon={<CloseRoundedIcon fontSize="medium" />}
            onClick={() => handleDelete(params.data)}
          >
            حذف ثبت نام
          </Button>
        ) : (
          ""
        ),
      filter: false,
      sortable: false,
    },
  ];
  return (
    <AgGrid
      title={"لیست دوره های ثبت نام شده"}
      containerStyle={{ width: "100%", height: "38vh" }}
      refreshGrid={refreshGrid}
      fetchData={trigger}
      columnDefs={columnDefs}
    />
  );
}

export default MainCourseEnrollmentTable;
