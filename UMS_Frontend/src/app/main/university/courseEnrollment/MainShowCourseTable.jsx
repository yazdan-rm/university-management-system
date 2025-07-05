import {
  useCreateCourseStudentMutation,
  useGetRowsExclusiveCoursesForStdMutation,
} from "../UniversityApi.js";
import {useAppDispatch, useAppSelector} from "app/store/hooks.js";
import {refreshAgGrid, selectRefreshGridFlag} from "../universitySlice.js";
import AgGrid from "app/shared-components/AgGrid.jsx";
import {showMessage} from "@fuse/core/FuseMessage/fuseMessageSlice.js";
import useJwtAuth from "../../../auth/services/jwt/useJwtAuth.jsx";
import Button from "@mui/material/Button";
import DoneOutlineRoundedIcon from "@mui/icons-material/DoneOutlineRounded";
import CoursePrerequisiteDialog from "./CoursePrerequisiteDialog.jsx";
import {useState} from "react";

function MainShowCourseTable() {
  const [trigger] = useGetRowsExclusiveCoursesForStdMutation();
  const [createCourseStudentTrigger] = useCreateCourseStudentMutation();
  const refreshGrid = useAppSelector(selectRefreshGridFlag);
  const dispatch = useAppDispatch();
  const {user: jwtUser} = useJwtAuth();
  const [dialogOpen, setDialogOpen] = useState(false);
  const [selectedCourseId, setSelectedCourseId] = useState(null);

  const handleDialogOpen = (courseId) => {
    setSelectedCourseId(courseId);
    setDialogOpen(true);
  };

  const handleDialogClose = () => {
    setDialogOpen(false);
    setSelectedCourseId(null);
  };


  const onSubmit = (data) => {
    const payload = {
      courseId: data.courseId,
      keycloakUserId: jwtUser.uid,
    };

    createCourseStudentTrigger(payload)
        .unwrap()
        .then((data) => {
          dispatch(showMessage({message: data.message}));
          dispatch(refreshAgGrid());
        })
        .catch((e) => {
          dispatch(
              showMessage({message: e.response.data.message, variant: "error"}),
          );
          dispatch(refreshAgGrid());
        });
  };

  const columnDefs = [
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
      headerName: "انواع پیش نیاز",
      field: "hasPrerequisiteCourse",
      minWidth: 105,
      cellRenderer: (params) => {
        return params.data ? (
            <Button
                size="small"
                color="primary"
                variant="contained"
                type="button"
                onClick={() => handleDialogOpen(params.data.courseId)}
                disabled={params.value !== 1}
            >
              {params.value === 1 ? "دارد" : "ندارد"}
            </Button>
        ) : null;
      },
    },
    {headerName: "ظرفیت دوره", minWidth: 110, field: "capacity"},
    {headerName: "ثبت نام شده", minWidth: 110, field: "enrolledCount"},
    {
      headerName: "نام استاد",
      field: "instructorName",
      minWidth: 150,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "عملیات",
      pinned: "left",
      field: "instructorName",
      minWidth: 20,
      cellRenderer: (params) =>
          params.data ? (
              <Button
                  size="small"
                  color="primary"
                  variant="contained"
                  type="button"
                  onClick={() => onSubmit(params.data)}
                  endIcon={<DoneOutlineRoundedIcon fontSize="medium"/>}
              >
                ثبت نام
              </Button>
          ) : (
              ""
          ),
      filter: false,
      sortable: false,
    },
  ];
  return (
      <>
        <CoursePrerequisiteDialog
            open={dialogOpen}
            onClose={handleDialogClose}
            courseId={selectedCourseId}
        />
        <AgGrid
            title={"لیست دروه برای ثبت نام"}
            containerStyle={{width: "100%", height: "40vh"}}
            refreshGrid={refreshGrid}
            fetchData={trigger}
            columnDefs={columnDefs}
        />
      </>
  );
}

export default MainShowCourseTable;
