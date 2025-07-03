import { useState } from "react";
import type { PageProps } from "../../types.ts";
import { Box, Button, Paper, TextField, Typography } from "@mui/material";
import logoBg from '../assets/img/logo.png'

export default function Login({ kcContext }: PageProps<"login.ftl">) {
    // const { social, realm, url, usernameHidden, login, auth, registrationDisabled, messagesPerField } = kcContext;
    const { url, messagesPerField } = kcContext;
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    return (
        <div className="flex h-[100vh] min-w-0 flex-auto flex-col items-center sm:justify-center md:p-32 bg-login">
            <Paper
                elevation={4}
                className="flex min-h-full w-full overflow-hidden rounded-0 sm:min-h-auto sm:w-2xl sm:rounded-2xl md:w-full md:max-w-[77.6rem]"
            >
                <Box
                    className="relative hidden h-full flex-auto items-center justify-center overflow-hidden p-48 md:flex lg:px-[10rem]"
                    sx={{ backgroundColor: "primary.main" }}
                >
                    <svg
                        className="pointer-events-none absolute inset-0"
                        viewBox="0 0 960 540"
                        width="100%"
                        height="100%"
                        preserveAspectRatio="xMidYMax slice"
                        xmlns="http://www.w3.org/2000/svg"
                    >
                        <Box component="g" sx={{ color: "primary.light" }} className="opacity-20" fill="none" stroke="currentColor" strokeWidth="100">
                            <circle r="234" cx="196" cy="23" />
                            <circle r="234" cx="790" cy="491" />
                        </Box>
                    </svg>
                    <Box
                        component="svg"
                        className="absolute -right-64 -top-64 opacity-20"
                        sx={{ color: "primary.light" }}
                        viewBox="0 0 220 192"
                        width="220px"
                        height="192px"
                        fill="none"
                    >
                        <defs>
                            <pattern id="837c3e70-6c3a-44e6-8854-cc48c737b659" x="0" y="0" width="20" height="20" patternUnits="userSpaceOnUse">
                                <rect x="0" y="0" width="4" height="4" fill="currentColor" />
                            </pattern>
                        </defs>
                        <rect width="220" height="192" fill="url(#837c3e70-6c3a-44e6-8854-cc48c737b659)" />
                    </Box>

                    <div className="relative z-10 w-full max-w-2xl">
                        <div className="text-7xl font-bold leading-none text-gray-100">
                            <Typography dir={"rtl"} variant="h3" className="font-extrabold leading-tight tracking-tight font-yekan">
                                سامانه جامع گلستان
                            </Typography>
                        </div>
                        <Typography dir={"rtl"} className="mt-24 text-md leading-6 tracking-tight text-gray-400 font-yekan">
                            سامانه گلستان، یکپارچه‌ترین سیستم مدیریت اطلاعات آموزشی، پژوهشی و دانشجویی برای دانشگاه‌های ایران. با ورود به این سامانه،
                            به تمامی خدمات آموزشی، مالی و پژوهشی خود به‌صورت آنلاین دسترسی خواهید داشت.
                        </Typography>
                        <div className="mt-32 flex items-center">
                            <Typography dir={"rtl"} className="ml-16 font-medium tracking-tight text-gray-400 font-yekan">
                                به جامعه‌ی گلستان بپیوندید و از امکانات پیشرفته‌ی ما استفاده کنید.
                            </Typography>
                        </div>
                    </div>
                </Box>

                <div className="w-full px-16 py-32 ltr:border-l-1 rtl:border-r-1 sm:w-auto sm:p-45 md:p-48">
                    <div className="mx-auto w-full max-w-[16rem] sm:mx-0 sm:w-[16rem]" dir={"rtl"}>
                        <img className="w-[8rem]" src={logoBg} alt="logo" />

                        <Typography className=" mt-[1.3rem] text-17 font-extrabold leading-tight tracking-tight font-yekan text-gray-800" dir="rtl">
                            ورود به حساب کاربری
                        </Typography>
                        <form
                            noValidate
                            name="loginForm"
                            className="mt-20 flex w-full flex-col justify-center"
                            action={url.loginAction}
                            method="POST"
                        >
                            <TextField
                                sx={{
                                    "& .MuiOutlinedInput-root": {
                                        borderRadius: "7px"
                                    },
                                    "& .MuiInputBase-input": {
                                        fontFamily: "yekan"
                                    },
                                    "& .MuiFormHelperText-root": {
                                        fontFamily: "yekan"
                                    },
                                    "& .MuiInputLabel-root": {
                                        fontFamily: "yekan"
                                    }
                                }}
                                size="medium"
                                onChange={e => setUsername(e.target.value)}
                                className="mb-16"
                                label="نام کاربری"
                                name="username"
                                autoFocus
                                type="text"
                                error={messagesPerField.existsError("username")}
                                helperText={messagesPerField.existsError("username") && "نام کابری یا رمز عبور نادرست است."}
                                variant="outlined"
                                required
                                fullWidth
                            />
                            <TextField
                                sx={{
                                    "& .MuiOutlinedInput-root": {
                                        borderRadius: "7px"
                                    },
                                    "& .MuiInputBase-input": {
                                        fontFamily: "yekan"
                                    },
                                    "& .MuiFormHelperText-root": {
                                        fontFamily: "yekan"
                                    },
                                    "& .MuiInputLabel-root": {
                                        fontFamily: "yekan"
                                    }
                                }}
                                size="medium"
                                onChange={e => setPassword(e.target.value)}
                                label="رمز عبور"
                                name="password"
                                className="mb-10"
                                type="password"
                                variant="outlined"
                                required
                                fullWidth
                            />

                            <Button
                                variant="contained"
                                color="primary"
                                className=" mt-11 w-full font-yekan rounded-2xl"
                                aria-label="Sign in"
                                disabled={!username || !password}
                                type="submit"

                            >
                                ورود
                            </Button>
                        </form>
                    </div>
                </div>
            </Paper>
        </div>
    );
}
