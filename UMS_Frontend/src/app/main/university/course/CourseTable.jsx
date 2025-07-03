import * as React from "react";
import { AllCommunityModule, ModuleRegistry } from "ag-grid-community";
import { useGetRowsCoursesMutation } from "../UniversityApi.js";
import {
  getEducationalLevelText,
  getGenderText,
  getSemesterTitleByCode,
  toShamsiDate,
} from "../../../utils/UmsUtils.js";
import AgGrid from "app/shared-components/AgGrid.jsx";
import { useAppSelector } from "app/store/hooks.js";
import { selectRefreshGridFlag } from "../universitySlice.js";
import ActionButtons from "./ActionButtons.jsx";

ModuleRegistry.registerModules([AllCommunityModule]);

function CourseTable() {
  const [trigger] = useGetRowsCoursesMutation();
  const refreshGrid = useAppSelector(selectRefreshGridFlag);

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
      minWidth: 150,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "مقطع تحصیلی",
      field: "educationalLevel",
      filter: false,
      minWidth: 110,
      valueFormatter: (params) => getEducationalLevelText(params.value),
      tooltipValueGetter: (params) => {
        return `${getEducationalLevelText(params.value)}`;
      },
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
      headerName: "نیم سال تحصیلی",
      field: "semester",
      cellDataType: "number",
      minWidth: 170,
      valueFormatter(params) {
        return getSemesterTitleByCode(params.value);
      },
      tooltipValueGetter: (params) => {
        return getSemesterTitleByCode(params.value);
      },
    },
    { headerName: "ظرفیت دوره", minWidth: 110, field: "capacity" },
    {
      headerName: "تعداد واحد",
      field: "courseUnits",
      minWidth: 105,
    },
    {
      headerName: "جنسیت مجاز",
      field: "allowedGenders",
      minWidth: 120,
      valueFormatter: (params) => getGenderText(params.value),
    },
    {
      headerName: "مکان برگزاری",
      field: "location",
      minWidth: 120,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "عملیات ها",
      cellRenderer: (params) => {
        return <ActionButtons data={params.data} />;
      },
      pinned: "left",
      filter: false,
      width: 20,
      sortable: false,
    },
    {
      headerName: "دانشکده",
      field: "collegeName",
      minWidth: 100,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "گروه آموزشی",
      field: "departmentName",
      minWidth: 115,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
    {
      headerName: "رشته تحصیلی",
      field: "fieldOfStudy",
      minWidth: 150,
      tooltipValueGetter: (params) => {
        return `${params.value}`;
      },
    },
  ];

  return (
    <AgGrid
      refreshGrid={refreshGrid}
      columnDefs={columnDefs}
      fetchData={trigger}
    />
  );
}

export default CourseTable;
