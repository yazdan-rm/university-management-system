import AgGrid from "app/shared-components/AgGrid.jsx";
import { useGetRowsExclusiveCoursesForStdMutation } from "../../UniversityApi.js";
import { useAppSelector } from "app/store/hooks.js";
import { selectRefreshGridFlag } from "../../universitySlice.js";
import { getGenderText } from "../../../../utils/UmsUtils.js";

function ExclusiveCoursesForStdTable() {
  const [trigger] = useGetRowsExclusiveCoursesForStdMutation();
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
    { headerName: "ظرفیت دوره", minWidth: 110, field: "capacity" },
    {
      headerName: "جنسیت مجاز",
      field: "allowedGenders",
      minWidth: 120,
      valueFormatter: (params) => getGenderText(params.value),
    },
    {
      headerName: "نام استاد",
      field: "instructorName",
      minWidth: 150,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "ساعت ارائه",
      field: "scheduleTime",
      minWidth: 300,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "ساعت امتحان",
      field: "examTime",
      minWidth: 210,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "دروس پیش نیاز، همنیاز، متضاد، معادل",
      field: "prerequisiteCourse",
      minWidth: 310,
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

export default ExclusiveCoursesForStdTable;
