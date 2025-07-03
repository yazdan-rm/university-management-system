import { z } from "zod";
import { useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "app/store/hooks.js";
import { format } from "date-fns";
import {
  refreshAgGrid,
  resetData,
  selectDataObject,
} from "../universitySlice.js";
import {
  useCreateCourseScheduleMutation,
  useUpdateCourseScheduleMutation,
} from "../UniversityApi.js";
import CloseRoundedIcon from "@mui/icons-material/CloseRounded";
import DoneOutlineRoundedIcon from "@mui/icons-material/DoneOutlineRounded";
import { Controller, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { showMessage } from "@fuse/core/FuseMessage/fuseMessageSlice.js";
import Grid from "@mui/material/Grid";
import { DatePicker, TimePicker } from "@mui/x-date-pickers";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import FormHelperText from "@mui/material/FormHelperText";
import Divider from "@mui/material/Divider";
import Typography from "@mui/material/Typography";
import { useEffect } from "react";
import { toLocalTime, toShamsiDate } from "../../../utils/UmsUtils.js";

const defaultValues = {
  id: "",
  courseStartTime: "",
  courseEndTime: "",
  dayOfWeek: "",
  courseExamTime: "",
  courseExamDate: "",
};

const schema = z.object({
  id: z.any().optional(),
  courseStartTime: z.coerce.string({
    invalid_type_error: "مقدار فیلد ساعت شروع ارائه اجباریست",
  }),
  courseEndTime: z.coerce
    .string()
    .min(1, "مقدار فیلد ساعت پایان ارائه اجباریست"),
  dayOfWeek: z.coerce.string().min(1, "مقدار فیلد روز ارائه اجباریست"),
  courseExamTime: z.coerce.string({
    invalid_type_error: "مقدار فیلد ساعت امتحان اجباریست",
  }),
  courseExamDate: z.coerce.string({
    invalid_type_error: "مقدار فیلد تاریخ امتحان اجباریست",
  }),
});

function CourseScheduleForm() {
  const { courseId } = useParams();
  const dispatch = useAppDispatch();
  const dataForUpdate = useAppSelector(selectDataObject);
  const [createCourseSchedule] = useCreateCourseScheduleMutation();
  const [updateCourseSchedule] = useUpdateCourseScheduleMutation();

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

  useEffect(() => {
    reset(dataForUpdate);
  }, [dataForUpdate]);

  const onSubmit = (data) => {
    const payload = {
      ...data,
      courseStartTime: toLocalTime(data.courseStartTime),
      courseEndTime: toLocalTime(data.courseEndTime),
      courseExamTime: toLocalTime(data.courseExamTime),
      courseExamDate: toShamsiDate(data.courseExamDate),
      courseId,
    };

    if (payload.id) {
      updateCourseSchedule(data)
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
      createCourseSchedule(payload)
        .unwrap()
        .then((data) => {
          dispatch(showMessage({ message: data.message }));
        })
        .catch((e) => {
          dispatch(
            showMessage({ message: e.response.data.message, variant: "error" }),
          );
          dispatch(refreshAgGrid());
        });
    }

    setTimeout(() => {
      reset(defaultValues);
      dispatch(refreshAgGrid());
      dispatch(resetData());
    }, 500);
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
          <Typography>زمان ارائه</Typography>
        </Divider>
        <Grid container spacing={1}>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="dayOfWeek"
              control={control}
              render={({ field }) => (
                <FormControl size="small" fullWidth error={!!errors.dayOfWeek}>
                  <InputLabel id="status-label">روز ارائه</InputLabel>
                  <Select {...field} labelId="day-of-week" label="روز  ارائه">
                    <MenuItem value={"شنبه"}>شنبه</MenuItem>
                    <MenuItem value={"یک‌شنبه"}>یک‌شنبه</MenuItem>
                    <MenuItem value={"دوشنبه"}>دوشنبه</MenuItem>
                    <MenuItem value={"سه‌شنبه"}>سه‌شنبه</MenuItem>
                    <MenuItem value={"چهارشنبه"}>چهارشنبه</MenuItem>
                    <MenuItem value={"پنج‌شنبه"}>پنج‌شنبه</MenuItem>
                  </Select>
                  <FormHelperText>{errors.dayOfWeek?.message}</FormHelperText>
                </FormControl>
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="courseStartTime"
              control={control}
              render={({ field: { onChange, value } }) => (
                <TimePicker
                  onChange={(val) =>
                    onChange(format(val, "yyyy-MM-dd'T'HH:mm:ss.SSS"))
                  }
                  value={value ? new Date(value) : null}
                  ampm={false}
                  localeText={{
                    todayButtonLabel: "امروز",
                    clearButtonLabel: "پاک کردن",
                  }}
                  slotProps={{
                    textField: {
                      id: "course-start-time",
                      variant: "outlined",
                      fullWidth: true,
                      size: "small",
                      error: !!errors.courseStartTime,
                      helperText: errors?.courseStartTime?.message,
                      placeholder: "دقیقه:ساعت",
                      label: "ساعت شروع ارائه",
                    },
                    actionBar: {
                      actions: ["today", "clear"],
                    },
                  }}
                />
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="courseEndTime"
              control={control}
              render={({ field: { onChange, value } }) => (
                <TimePicker
                  onChange={(val) =>
                    onChange(format(val, "yyyy-MM-dd'T'HH:mm:ss.SSS"))
                  }
                  value={value ? new Date(value) : null}
                  ampm={false}
                  localeText={{
                    todayButtonLabel: "امروز",
                    clearButtonLabel: "پاک کردن",
                  }}
                  slotProps={{
                    textField: {
                      id: "course-end-time",
                      variant: "outlined",
                      fullWidth: true,
                      size: "small",
                      error: !!errors.courseEndTime,
                      helperText: errors?.courseEndTime?.message,
                      placeholder: "دقیقه:ساعت",
                      label: "ساعت پایان ارائه",
                    },
                    actionBar: {
                      actions: ["today", "clear"],
                    },
                  }}
                />
              )}
            />
          </Grid>
        </Grid>
        <Divider>
          <Typography>زمان امتحان</Typography>
        </Divider>
        <Grid container spacing={1}>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="courseExamDate"
              control={control}
              render={({ field: { onChange, value } }) => (
                <DatePicker
                  onChange={(val) =>
                    onChange(format(val, "yyyy-MM-dd'T'HH:mm:ss.SSS"))
                  }
                  value={value ? new Date(value) : null}
                  localeText={{
                    todayButtonLabel: "امروز",
                    clearButtonLabel: "پاک کردن",
                  }}
                  slotProps={{
                    textField: {
                      id: "course-exam-date",
                      variant: "outlined",
                      fullWidth: true,
                      size: "small",
                      error: !!errors.courseExamDate,
                      helperText: errors?.courseExamDate?.message,
                      placeholder: "روز/ماه/سال",
                      label: "تاریخ امتحان",
                    },
                    actionBar: {
                      actions: ["today", "clear"],
                    },
                  }}
                />
              )}
            />
          </Grid>
          <Grid item xs={12} sm={6} md={3}>
            <Controller
              name="courseExamTime"
              control={control}
              render={({ field: { onChange, value } }) => (
                <TimePicker
                  onChange={(val) =>
                    onChange(format(val, "yyyy-MM-dd'T'HH:mm:ss.SSS"))
                  }
                  value={value ? new Date(value) : null}
                  ampm={false}
                  localeText={{
                    todayButtonLabel: "امروز",
                    clearButtonLabel: "پاک کردن",
                  }}
                  slotProps={{
                    textField: {
                      id: "course-exam-time",
                      variant: "outlined",
                      fullWidth: true,
                      size: "small",
                      error: !!errors.courseExamTime,
                      helperText: errors?.courseExamTime?.message,
                      placeholder: "دقیقه:ساعت",
                      label: "ساعت شروع امتحان",
                    },
                    actionBar: {
                      actions: ["today", "clear"],
                    },
                  }}
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

export default CourseScheduleForm;
