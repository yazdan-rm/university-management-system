import { z } from "zod";
import { useAppDispatch, useAppSelector } from "app/store/hooks.js";
import {
  refreshAgGrid,
  resetData,
  selectDataObject,
} from "../universitySlice.js";
import { Controller, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect } from "react";
import { showMessage } from "@fuse/core/FuseMessage/fuseMessageSlice.js";
import Paper from "@mui/material/Paper";
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";
import DoneOutlineRoundedIcon from "@mui/icons-material/DoneOutlineRounded";
import { useParams } from "react-router-dom";
import CloseRoundedIcon from "@mui/icons-material/CloseRounded";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";
import {
  useCreateCoursePrerequisiteMutation,
  useGetAllCoursesQuery,
  useUpdateCoursePrerequisiteMutation,
} from "../UniversityApi.js";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import FormHelperText from "@mui/material/FormHelperText";
import Divider from "@mui/material/Divider";
import Typography from "@mui/material/Typography";

const defaultValues = {
  id: "",
  courseId: "",
  prerequisiteId: "",
  prerequisiteType: "",
};

const schema = z.object({
  id: z.any().optional(),
  courseId: z.any().optional(),
  prerequisiteId: z.coerce.string().min(1, "مقدار فیلد دوره پیش نیاز اجباریست"),
  prerequisiteType: z.coerce
    .string()
    .min(1, "مقدار فیلد نوع دوره پیش نیاز اجباریست"),
});

function CoursePrerequisitesForm() {
  const { courseId } = useParams();
  const dispatch = useAppDispatch();
  const dataForUpdate = useAppSelector(selectDataObject);
  const [createCoursePrerequisite] = useCreateCoursePrerequisiteMutation();
  const [updateCoursePrerequisite] = useUpdateCoursePrerequisiteMutation();
  const {
    data: allCoursesData,
    isLoading: isCoursesLoading,
    refetch,
  } = useGetAllCoursesQuery(courseId);
  const allCourses = allCoursesData?.data || [];

  useEffect(() => {
    refetch();
  }, []);

  useEffect(() => {
    reset(dataForUpdate);
  }, [dataForUpdate]);

  const {
    handleSubmit,
    control,
    reset,
    formState: { errors, isValid },
  } = useForm({
    defaultValues,
    resolver: zodResolver(schema),
    mode: "all",
  });

  const onSubmit = (data) => {
    const payload = {
      ...data,
      courseId,
    };

    if (payload.id) {
      updateCoursePrerequisite(payload)
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
    } else {
      createCoursePrerequisite(payload)
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
    }

    reset(defaultValues);
    dispatch(resetData());
  };

  const onClean = () => {
    dispatch(resetData());
    reset(defaultValues);
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Paper
        elevation={4}
        sx={{
          padding: 4,
          margin: 2,
          display: "flex",
          flexDirection: "column",
          gap: 1,
          "& .MuiTextField-root": {
            maxWidth: "100%",
          },
          "& .MuiOutlinedInput-root": {
            borderRadius: "7px",
            fontWeight: 700,
          },
          "& .MuiInputLabel-root": {
            fontWeight: 700,
          },
        }}
      >
        <Divider>
          <Typography>مشخصات پیش نیاز دوره</Typography>
        </Divider>
        <Grid container spacing={1}>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="prerequisiteId"
              control={control}
              render={({
                field: { onChange, value },
                fieldState: { error },
              }) => (
                <Autocomplete
                  disablePortal
                  options={allCourses}
                  filterOptions={(options, { inputValue }) =>
                    options
                      .filter((option) =>
                        option.title
                          .toLowerCase()
                          .includes(inputValue.toLowerCase()),
                      )
                      .slice(0, 100)
                  }
                  loading={isCoursesLoading}
                  getOptionLabel={(option) => option.title || ""}
                  value={allCourses.find((field) => field.id === value) || null}
                  renderOption={(props, option) => (
                    <li {...props} key={option.id}>
                      {option.title}
                    </li>
                  )}
                  onChange={(_, newValue) => {
                    const code = newValue?.id ?? null;
                    onChange(code);
                  }}
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      label="لیست دروس تعریف شده"
                      size="small"
                      error={!!error}
                      helperText={errors.prerequisiteId?.message}
                    />
                  )}
                />
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="prerequisiteType"
              control={control}
              render={({ field }) => (
                <FormControl
                  size="small"
                  fullWidth
                  error={!!errors.prerequisiteType}
                >
                  <InputLabel id="status-label">نوع دوره پیش نیاز</InputLabel>
                  <Select
                    {...field}
                    labelId="prerequisiteType-label"
                    label="نوع دوره پیش نیاز"
                  >
                    <MenuItem value={"پیش نیاز"}>پیش نیاز</MenuItem>
                    <MenuItem value={"همنياز"}>همنياز</MenuItem>
                    <MenuItem value={"معادل"}>معادل</MenuItem>
                    <MenuItem value={"متضاد"}>متضاد</MenuItem>
                  </Select>
                  <FormHelperText>
                    {errors.prerequisiteType?.message}
                  </FormHelperText>
                </FormControl>
              )}
            />
          </Grid>
        </Grid>

        <div className="flex gap-10 justify-end mt-2">
          <Button
            size="small"
            color="primary"
            variant="outlined"
            type="button"
            endIcon={<CloseRoundedIcon fontSize="medium" />}
            onClick={onClean}
          >
            پاک کردن
          </Button>
          <Button
            size="large"
            color="primary"
            variant="contained"
            type="submit"
            endIcon={<DoneOutlineRoundedIcon fontSize="medium" />}
            disabled={!isValid}
          >
            ثبت
          </Button>
        </div>
      </Paper>
    </form>
  );
}

export default CoursePrerequisitesForm;
