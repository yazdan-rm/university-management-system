import * as React from "react";
import { useCallback, useEffect, useMemo, useRef } from "react";
import Paper from "@mui/material/Paper";
import {
  AllCommunityModule,
  ModuleRegistry,
  themeQuartz,
} from "ag-grid-community";
import { AgGridReact } from "ag-grid-react";
import { useTheme } from "@mui/styles";
import { AG_GRID_LOCALE_IR } from "@ag-grid-community/locale";
import { useAppDispatch } from "app/store/hooks.js";
import { showMessage } from "@fuse/core/FuseMessage/fuseMessageSlice.js";
import Typography from "@mui/material/Typography";

ModuleRegistry.registerModules([AllCommunityModule]);

const AgGrid = ({
  // Required props
  fetchData, // Function to fetch data (should return a promise)
  columnDefs, // Array of column definitions

  // Optional props
  masterId = null, // New optional prop for master-detail behavior
  gridRef = useRef(null),
  title = null,
  refreshGrid,
  containerStyle = { width: "100%", height: "52vh" },
  gridStyle = { height: "100%", width: "100%" },
  pageSize = 20,
  cacheBlockSize = 20,
  maxBlocksInCache = 2,
  rowNumberColumnWidth = 60,
  enableRtl = true,
  enableSorting = true,
  enablePagination = true,
  localeText = AG_GRID_LOCALE_IR,
}) => {
  const userTheme = useTheme();
  const dispatch = useAppDispatch();
  const theme = useMemo(() => {
    return themeQuartz.withParams({
      headerTextColor: "#fff",
      textColor: userTheme.palette.text.secondary,
      headerBackgroundColor: userTheme.palette.primary.main,
      oddRowBackgroundColor: userTheme.palette.background.default,
      headerColumnResizeHandleColor: userTheme.palette.primary.light,
      fontFamily: "yekan",
    });
  }, [userTheme]);

  const defaultColDef = useMemo(() => {
    return {
      flex: 1,
      minWidth: 100,
      filter: true,
      sortable: enableSorting,
    };
  }, []);

  useEffect(() => {
    gridRef?.current?.api?.paginationGoToPage(0);
    gridRef?.current?.api?.refreshInfiniteCache();
  }, [refreshGrid, masterId]);

  const finalColumnDefs = useMemo(() => {
    return [
      {
        headerName: "سطر",
        maxWidth: rowNumberColumnWidth,
        valueGetter: "node.id",
        sortable: false,
        filter: false,
        cellRenderer: (props) => {
          if (props.value !== undefined) {
            return props.value;
          } else {
            return (
              <img src="../../../assets/images/loading.gif" alt={"loader"} />
            );
          }
        },
      },
      ...columnDefs,
    ];
  }, [columnDefs, rowNumberColumnWidth]);

  const onGridReady = useCallback(
    (params) => {
      const dataSource = {
        rowCount: undefined,
        getRows: (gridParams) => {
          fetchData({
            startRow: gridParams.startRow,
            endRow: gridParams.endRow,
            filterModel: gridParams.filterModel,
            sortModel: gridParams.sortModel,
            masterId,
          })
            .unwrap()
            .then((result) => {
              const { data, lastRow } = result?.data;
              gridParams.successCallback(data, lastRow);
            })
            .catch((e) => {
              const message =
                e?.response?.data?.message || "خطای نامشخص در شبکه";
              gridParams.failCallback();
              dispatch(showMessage({ message, variant: "error" }));
            });
        },
      };
      params.api.setGridOption("datasource", dataSource);
    },
    [fetchData, masterId],
  );

  return (
    <Paper
      elevation={4}
      sx={{ m: 2, mb: 0, p: 2, pt: 1, width: "98%", overflow: "hidden" }}
    >
      <Typography
        className={`text-xl font-400 ${title ? "" : "pt-8"}`}
        color="text.secondary"
      >
        {title}
      </Typography>
      <div style={containerStyle}>
        <div style={gridStyle}>
          <AgGridReact
            ref={gridRef}
            enableRtl={enableRtl}
            columnDefs={finalColumnDefs}
            onGridReady={onGridReady}
            defaultColDef={defaultColDef}
            rowModelType={"infinite"}
            tooltipShowDelay={100}
            tooltipHideDelay={2000}
            cacheOverflowSize={2}
            cacheBlockSize={cacheBlockSize}
            paginationPageSize={pageSize}
            maxConcurrentDatasourceRequests={2}
            infiniteInitialRowCount={1}
            maxBlocksInCache={maxBlocksInCache}
            pagination={enablePagination}
            localeText={localeText}
            theme={theme}
          />
        </div>
      </div>
    </Paper>
  );
};

export default AgGrid;
