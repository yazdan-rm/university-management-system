import { refreshAgGrid, updateData } from "../universitySlice.js";
import MoreVertRoundedIcon from "@mui/icons-material/MoreVertRounded";
import Button from "@mui/material/Button";
import DeleteIcon from "@mui/icons-material/Delete";
import EditOutlinedIcon from "@mui/icons-material/EditOutlined";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { useState } from "react";
import { useAppDispatch } from "app/store/hooks.js";
import { useDeleteCourseMutation } from "../UniversityApi.js";
import { showMessage } from "@fuse/core/FuseMessage/fuseMessageSlice.js";
import AddBoxRoundedIcon from "@mui/icons-material/AddBoxRounded";
import { Link } from "react-router-dom";
import CalendarMonthRoundedIcon from "@mui/icons-material/CalendarMonthRounded";

function ActionButtons({ data }) {
  const dispatch = useAppDispatch();
  const [removeItem] = useDeleteCourseMutation();
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);

  const handleEdit = () => {
    dispatch(updateData(data));
    handleClose();
  };

  const handleRedirections = () => {
    handleClose();
  };

  const handleDelete = () => {
    removeItem(data.id)
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
    handleClose();
  };

  const handleClick = (e) => {
    setAnchorEl(e.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <>
      <Button className="flex justify-center" onClick={handleClick}>
        <MoreVertRoundedIcon fontSize="medium" />
      </Button>

      <Menu open={open} anchorEl={anchorEl} onClose={handleClose}>
        <Link
          to={`/app/university/course/course-schedules/${data?.id}`}
          style={{ textDecoration: "none", color: "inherit" }}
        >
          <MenuItem
            className="flex gap-4 justify-between"
            onClick={handleRedirections}
          >
            <p>برنامه ارائه</p>
            <CalendarMonthRoundedIcon
              className="text-gray-700"
              fontSize="medium"
            />
          </MenuItem>
        </Link>
        <Link
          to={`/app/university/course/course-prerequisites/${data?.id}`}
          style={{ textDecoration: "none", color: "inherit" }}
        >
          <MenuItem
            className="flex gap-4 justify-between"
            onClick={handleRedirections}
          >
            <p>پیش نیاز</p>
            <AddBoxRoundedIcon className="text-gray-700" fontSize="medium" />
          </MenuItem>
        </Link>
        <MenuItem className="flex gap-4 justify-between" onClick={handleEdit}>
          <p>ویرایش</p>
          <EditOutlinedIcon className="text-blue-500" fontSize="medium" />
        </MenuItem>
        <MenuItem className="flex gap-4 justify-between" onClick={handleDelete}>
          <p>حذف</p>
          <DeleteIcon className="text-red-500" fontSize="medium" />
        </MenuItem>
      </Menu>
    </>
  );
}

export default ActionButtons;
