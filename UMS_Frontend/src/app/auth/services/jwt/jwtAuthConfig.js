const jwtAuthConfig = {
  jwtTokenStorageKey: "jwt_access_token",
  authUrl: import.meta.env.VITE_AUTH_URL,
  realm: import.meta.env.VITE_REALM,
  clientId: import.meta.env.VITE_CLIENT_ID,
  signInUrl: "mock-api/auth/sign-in",
  signUpUrl: "mock-api/auth/sign-up",
  tokenRefreshUrl: "mock-api/auth/refresh",
  getUserUrl:
    import.meta.env.VITE_AUTH_URL +
    "realms/UMS/protocol/openid-connect/userinfo",
  updateUserUrl: "/student",
  updateTokenFromHeader: true,
};
export default jwtAuthConfig;
