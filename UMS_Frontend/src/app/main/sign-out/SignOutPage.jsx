import Typography from "@mui/material/Typography";
import Paper from "@mui/material/Paper";
import { Link } from "react-router-dom";
import { useState } from "react";

/**
 * The sign out page.
 */
function SignOutPage() {
  const [countdown, setCountdown] = useState(5);
  return (
    <div
      className="flex min-w-0 flex-auto flex-col items-center sm:justify-center bg-cover bg-center"
      style={{ backgroundImage: "url('/assets/images/background.jpg')" }}
    >
      <Paper
        elevation={4}
        className="flex min-h-full w-full items-center rounded-0 px-16 py-32 sm:min-h-auto sm:w-auto sm:rounded-2xl sm:p-48 bg-white/80"
      >
        <div className="mx-auto w-full max-w-320 sm:mx-0 sm:w-320">
          <img
            className="mx-auto w-48"
            src="assets/images/logo/logo.svg"
            alt="logo"
          />
          <Typography className="mt-32 text-center text-4xl font-extrabold leading-tight tracking-tight">
            شما از سامانه خارج شدید!
          </Typography>
          <Typography className="mt-2 flex justify-center font-medium">
            انتقال به صفحه ورود تا {countdown} ثانیه دیگر
          </Typography>
          <Typography
            className="mt-32 text-center text-md font-medium"
            color="text.secondary"
          >
            <span> رفتن به </span>
            <Link className="ml-4" to="/sign-in">
              صفحه ورود
            </Link>
          </Typography>
        </div>
      </Paper>
    </div>
  );
}

export default SignOutPage;
