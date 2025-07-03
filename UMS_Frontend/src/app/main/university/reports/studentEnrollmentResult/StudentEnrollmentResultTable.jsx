import { useAppSelector } from "app/store/hooks.js";
import { selectRefreshGridFlag } from "../../universitySlice.js";
import AgGrid from "app/shared-components/AgGrid.jsx";
import { useGetStudentEnrollmentResultMutation } from "../../UniversityApi.js";

function StudentEnrollmentResultTable() {
  const [trigger] = useGetStudentEnrollmentResultMutation();
  const refreshGrid = useAppSelector(selectRefreshGridFlag);

  const columnDefs = [
    {
      headerName: "نام دوره",
      field: "courseName",
      minWidth: 150,
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
      headerName: "نام استاد",
      field: "instructorName",
      minWidth: 160,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "شنبه",
      field: "saturday",
      minWidth: 160,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "یکشنبه",
      field: "sunday",
      minWidth: 160,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "دوشنبه",
      field: "monday",
      minWidth: 160,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "سه شنبه",
      field: "tuesday",
      minWidth: 160,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "چهار شنبه",
      field: "wednesday",
      minWidth: 160,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "پنج شنبه",
      field: "thursday",
      minWidth: 160,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "امتحان",
      field: "examTime",
      minWidth: 180,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
  ];
  return (
    <AgGrid
      containerStyle={{ width: "100%", height: "62vh" }}
      refreshGrid={refreshGrid}
      fetchData={trigger}
      columnDefs={columnDefs}
    />
  );
}

export default StudentEnrollmentResultTable;
