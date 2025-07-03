import Paper from "@mui/material/Paper";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import CloseRoundedIcon from "@mui/icons-material/CloseRounded";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { Controller, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import FormHelperText from "@mui/material/FormHelperText";
import Grid from "@mui/material/Grid";
import { useAppDispatch, useAppSelector } from "app/store/hooks.js";
import {
  useCreateCourseMutation,
  useGetAllCollegesQuery,
  useLazyGetDepartmentsByCollegeCodeQuery,
  useLazyGetFieldOfStudyByDepartmentCodeQuery,
  useUpdateCourseMutation,
} from "../UniversityApi.js";
import {
  refreshAgGrid,
  resetData,
  selectDataObject,
} from "../universitySlice.js";
import { showMessage } from "@fuse/core/FuseMessage/fuseMessageSlice.js";
import { useEffect, useState } from "react";
import { getCurrentSemester } from "../../../utils/UmsUtils.js";
import Autocomplete from "@mui/material/Autocomplete";
import DoneOutlineRoundedIcon from "@mui/icons-material/DoneOutlineRounded";
import Divider from "@mui/material/Divider";
import Typography from "@mui/material/Typography";

const defaultValues = {
  id: "",
  semester: getCurrentSemester(),
  courseUnits: "",
  allowedGenders: "",
  courseName: "",
  instructorName: "",
  capacity: "",
  location: "",
  educationalLevel: "",
  collegeCode: "",
  departmentCode: "",
  fieldOfStudyCode: "",
};

const schema = z.object({
  id: z.any().optional(),
  semester: z.coerce.string().min(1, "مقدار فیلد نیم سال تحصیلی اجباریست"),
  courseUnits: z.coerce
    .number()
    .min(1, "حداقل مقدار 1 می باشد")
    .max(3, "حداکثر مقدار 3 می باشد"),
  allowedGenders: z.number({
    required_error: "مقدار فیلد جنسیت مجاز اجباریست",
    invalid_type_error: "مقدار وارد شده درست نیست",
  }),
  instructorName: z.string(),
  courseName: z.string().min(1, "مقدار فیلد نام دوره اجباریست"),
  capacity: z.coerce
    .number({ invalid_type_error: "عدد وارد کنید" })
    .int()
    .min(10, "حداقل مقدار 10 است"),
  location: z.string().min(1, "مقدار فیلد مکان دوره اجباریست"),
  educationalLevel: z.number({
    required_error: "مقدار فیلد مقطع تحصیلی اجباریست",
    invalid_type_error: "مقدار وارد شده درست نیست",
  }),
  collegeCode: z.coerce.number({
    invalid_type_error: "مقدار فیلد دانشکده مجاز اجباریست",
  }),
  departmentCode: z.coerce.number({
    invalid_type_error: "مقدار فیلد گروه آموزشی مجاز اجباریست",
  }),
  fieldOfStudyCode: z.coerce.number({
    invalid_type_error: "مقدار فیلد رشته تحصیلی اجباریست",
  }),
});

function CourseForm() {
  const [collegeCode, setCollegeCode] = useState(null);
  const [departmentCode, setDepartmentCode] = useState(null);
  const dispatch = useAppDispatch();
  const [createCourse] = useCreateCourseMutation();
  const { data: collegeData, isLoading: isCollegesLoading } =
    useGetAllCollegesQuery();

  const [
    triggerGetDepartmentsByCollegeCode,
    { data: departmentData, isLoading: isDepartmentsLoading },
  ] = useLazyGetDepartmentsByCollegeCodeQuery();

  const [
    triggerGetFieldOfStudyByDepartmentCode,
    { data: fieldOfStudyData, isLoading: isFieldOfStudyLoading },
  ] = useLazyGetFieldOfStudyByDepartmentCodeQuery();

  const [updateCourse] = useUpdateCourseMutation();
  const dataForUpdate = useAppSelector(selectDataObject);
  const colleges = collegeData?.data || [];
  const departments = departmentData?.data || [];
  const fieldOfStudy = fieldOfStudyData?.data || [];

  const {
    handleSubmit,
    control,
    reset,
    formState: { errors, isValid, dirtyFields },
  } = useForm({
    defaultValues,
    resolver: zodResolver(schema),
    mode: "all",
  });

  useEffect(() => {
    if (collegeCode) triggerGetDepartmentsByCollegeCode(collegeCode);
  }, [collegeCode]);

  useEffect(() => {
    if (departmentCode) triggerGetFieldOfStudyByDepartmentCode(departmentCode);
  }, [departmentCode]);

  useEffect(() => {
    reset(dataForUpdate);
    setCollegeCode(dataForUpdate?.collegeCode);
    setDepartmentCode(dataForUpdate?.departmentCode);
  }, [dataForUpdate]);

  const onSubmit = (data) => {
    const payload = {
      ...data,
      instructorName: data.instructorName?.trim() || "گروه اساتید",
    };

    if (payload.id) {
      updateCourse(data)
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
      createCourse(payload)
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
          <Typography>مشخصات دوره</Typography>
        </Divider>
        <Grid container spacing={1}>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="courseName"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  size="small"
                  label="نام دوره"
                  error={!!errors.courseName}
                  helperText={errors.courseName?.message}
                  fullWidth
                />
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="semester"
              control={control}
              render={({ field }) => (
                <TextField
                  {...field}
                  size="small"
                  disabled
                  label="شماره ترم"
                  error={!!errors.semester}
                  helperText={errors.semester?.message}
                  fullWidth
                />
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="instructorName"
              control={control}
              render={({ field }) => (
                <TextField
                  size="small"
                  {...field}
                  label="نام استاد"
                  fullWidth
                />
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="capacity"
              control={control}
              render={({ field }) => (
                <TextField
                  size="small"
                  {...field}
                  label="ظرفیت دوره"
                  type="number"
                  error={!!errors.capacity}
                  helperText={errors.capacity?.message}
                  fullWidth
                />
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="courseUnits"
              control={control}
              render={({ field }) => (
                <TextField
                  size="small"
                  {...field}
                  label="تعداد واحد درس"
                  type="number"
                  error={!!errors.courseUnits}
                  helperText={errors.courseUnits?.message}
                  fullWidth
                />
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="allowedGenders"
              control={control}
              render={({ field }) => (
                <FormControl
                  size="small"
                  fullWidth
                  error={!!errors.allowedGenders}
                >
                  <InputLabel id="allowed-genders-label">
                    جنسیت های مجاز
                  </InputLabel>
                  <Select
                    {...field}
                    labelId="allowed-genders-label"
                    label="جنسیت های مجاز"
                  >
                    <MenuItem value={1}>مرد</MenuItem>
                    <MenuItem value={2}>زن</MenuItem>
                    <MenuItem value={3}>مختلط</MenuItem>
                  </Select>
                  <FormHelperText>
                    {errors.allowedGenders?.message}
                  </FormHelperText>
                </FormControl>
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="educationalLevel"
              control={control}
              render={({ field }) => (
                <FormControl
                  size="small"
                  fullWidth
                  error={!!errors.educationalLevel}
                >
                  <InputLabel id="status-label">مقطع تحصیلی</InputLabel>
                  <Select {...field} labelId="status-label" label="مقطع تحصیلی">
                    <MenuItem value={1}>كارداني</MenuItem>
                    <MenuItem value={3}>كارشناسي</MenuItem>
                    <MenuItem value={4}>كارشناسي ارشد</MenuItem>
                    <MenuItem value={5}>دكتري</MenuItem>
                    <MenuItem value={6}>دانشوري</MenuItem>
                    <MenuItem value={8}>ارشـد</MenuItem>
                    <MenuItem value={9}>حوزوي</MenuItem>
                    <MenuItem value={14}>نامشخص</MenuItem>
                  </Select>
                  <FormHelperText>
                    {errors.educationalLevel?.message}
                  </FormHelperText>
                </FormControl>
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="location"
              control={control}
              render={({ field }) => (
                <TextField
                  size="small"
                  {...field}
                  label="مکان برگزاری"
                  error={!!errors.location}
                  helperText={errors.location?.message}
                  fullWidth
                />
              )}
            />
          </Grid>
        </Grid>
        <Divider>
          <Typography>مشخصات گروه آموزشی</Typography>
        </Divider>
        <Grid container spacing={1}>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="collegeCode"
              control={control}
              render={({
                field: { onChange, value },
                fieldState: { error },
              }) => (
                <Autocomplete
                  disablePortal
                  options={colleges}
                  loading={isCollegesLoading}
                  getOptionLabel={(option) => option.title || ""}
                  value={
                    colleges?.find((college) => college.code === value) || null
                  }
                  onChange={(_, newValue) => {
                    const code = newValue?.code ?? null;
                    onChange(code);
                    setCollegeCode(code);
                  }}
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      label="دانشکده"
                      size="small"
                      error={!!error}
                      helperText={error ? error.message : ""}
                    />
                  )}
                />
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="departmentCode"
              control={control}
              render={({
                field: { onChange, value },
                fieldState: { error },
              }) => (
                <Autocomplete
                  disablePortal
                  options={departments}
                  loading={isDepartmentsLoading}
                  getOptionLabel={(option) => option.title || ""}
                  value={departments?.find((dep) => dep.code === value) || null}
                  onChange={(_, newValue) => {
                    const code = newValue?.code ?? null;
                    onChange(code);
                    setDepartmentCode(code);
                  }}
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      label="گروه آموزشی"
                      size="small"
                      error={!!error}
                      helperText={error ? error.message : ""}
                    />
                  )}
                />
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="fieldOfStudyCode"
              control={control}
              render={({
                field: { onChange, value },
                fieldState: { error },
              }) => (
                <Autocomplete
                  disablePortal
                  options={fieldOfStudy}
                  loading={isFieldOfStudyLoading}
                  getOptionLabel={(option) => option.title || ""}
                  value={
                    fieldOfStudy.find((field) => field.code === value) || null
                  }
                  onChange={(_, newValue) => {
                    const code = newValue?.code ?? null;
                    onChange(code);
                  }}
                  renderInput={(params) => (
                    <TextField
                      {...params}
                      label="رشته تحصیلی"
                      size="small"
                      error={!!error}
                      helperText={error ? error.message : ""}
                    />
                  )}
                />
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
            size="medium"
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

export default CourseForm;
