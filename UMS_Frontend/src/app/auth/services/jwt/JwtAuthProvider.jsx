import {
  createContext,
  useCallback,
  useEffect,
  useMemo,
  useState,
} from "react";
import axios from "axios";
import jwtDecode from "jwt-decode";
import config from "./jwtAuthConfig";
import UserModel from "../../user/models/UserModel.js";
import Keycloak from "keycloak-js";
import {
  useLazyGetUserQuery,
  useUpdateUserMutation,
} from "../../../main/university/UniversityApi.js";

const defaultAuthContext = {
  keycloak: null,
  isAuthenticated: false,
  isLoading: false,
  user: null,
  updateUser: null,
  signIn: null,
  signUp: null,
  signOut: null,
  refreshToken: null,
  setIsLoading: () => {},
  authStatus: "configuring",
};
export const JwtAuthContext = createContext(defaultAuthContext);

const initOption = {
  url: config.authUrl,
  realm: config.realm,
  clientId: config.clientId,
};

function JwtAuthProvider(props) {
  const [updateUserMutation] = useUpdateUserMutation();
  const [triggerGetUser] = useLazyGetUserQuery();
  const [keycloak, setKeycloak] = useState(null);
  const [user, setUser] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [authStatus, setAuthStatus] = useState("configuring");
  const { children } = props;
  /**
   * Handle sign-in success
   */
  const handleSignInSuccess = useCallback((userData, accessToken) => {
    setSession(accessToken);
    setIsAuthenticated(true);
    setUser(userData);
  }, []);
  /**
   * Handle sign-up success
   */
  const handleSignUpSuccess = useCallback((userData, accessToken) => {
    setSession(accessToken);
    setIsAuthenticated(true);
    setUser(userData);
  }, []);
  /**
   * Handle sign-in failure
   */
  const handleSignInFailure = useCallback((error) => {
    resetSession();
    setIsAuthenticated(false);
    setUser(null);
    handleError(error);
  }, []);
  /**
   * Handle sign-up failure
   */
  const handleSignUpFailure = useCallback((error) => {
    resetSession();
    setIsAuthenticated(false);
    setUser(null);
    handleError(error);
  }, []);
  /**
   * Handle error
   */
  const handleError = useCallback((_error) => {
    resetSession();
    setIsAuthenticated(false);
    setUser(null);
  }, []);
  // Set session
  const setSession = useCallback((accessToken) => {
    if (accessToken) {
      localStorage.setItem(config.jwtTokenStorageKey, accessToken);
      axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
    }
  }, []);
  // Reset session
  const resetSession = useCallback(() => {
    localStorage.removeItem(config.jwtTokenStorageKey);
    delete axios.defaults.headers.common.Authorization;
  }, []);
  // Get access token from local storage
  const getAccessToken = useCallback(() => {
    return localStorage.getItem(config.jwtTokenStorageKey);
  }, []);
  // Check if the access token is valid
  const isTokenValid = useCallback((accessToken) => {
    if (accessToken) {
      try {
        const decoded = jwtDecode(accessToken);
        const currentTime = Date.now() / 1000;
        return decoded.exp > currentTime;
      } catch (error) {
        return false;
      }
    }

    return false;
  }, []);
  // Check if the access token exist and is valid on mount
  useEffect(() => {
    const attemptAutoLogin = async () => {
      const accessToken = getAccessToken();
      if (isTokenValid(accessToken)) {
        try {
          setIsLoading(true);
          const response = await axios.get(config.getUserUrl, {
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
          });
          const userData = response?.data;

          handleSignInSuccess(userData, accessToken);
          return true;
        } catch (error) {
          handleSignInFailure(error);
          return false;
        }
      } else {
        resetSession();
        return false;
      }
    };

    if (!isAuthenticated) {
      attemptAutoLogin().then((signedIn) => {
        setIsLoading(false);
        setAuthStatus(signedIn ? "authenticated" : "unauthenticated");
      });
    }
  }, [
    isTokenValid,
    setSession,
    handleSignInSuccess,
    handleSignInFailure,
    handleError,
    getAccessToken,
    isAuthenticated,
  ]);
  const handleRequest = async (url, data, handleSuccess, handleFailure) => {
    try {
      const response = await axios.post(url, data);
      const userData = response?.data?.user;
      const accessToken = response?.data?.access_token;
      handleSuccess(userData, accessToken);
      return userData;
    } catch (error) {
      const axiosError = error;
      handleFailure(axiosError);
      return axiosError;
    }
  };

  useEffect(() => {
    const kc = new Keycloak(initOption);
    setKeycloak(kc);

    const initKeycloak = async () => {
      try {
        const authenticated = await kc.init({
          onLoad: "login-required",
          checkLoginIframe: false,
          pkceMethod: "S256",
          enableAutoRefresh: true,
          scope: "openid profile email",
        });

        setIsLoading(false);

        if (authenticated) {
          // Get basic info from the token
          const user = kc.tokenParsed;
          const token = kc.token;
          axios.defaults.headers.common.Authorization = `Bearer ${token}`;
          // Fetch additional user info including picture
          const userInfo = await kc.loadUserInfo();
          const additionalUserInfo = await triggerGetUser({
            uid: user.sub,
          }).unwrap();

          const userModel = UserModel({
            uid: user.sub,
            preferred_username: userInfo?.preferred_username,
            role:
              user?.resource_access?.["ums-client-id"]?.roles?.[0] || "guest",
            data: {
              displayName: user.name || user.preferred_username,
              // Use picture from userInfo if available
              photoURL: userInfo?.picture || "",
              email: user?.email || userInfo?.email || "",
              shortcuts: [],
              settings: JSON.parse(
                additionalUserInfo?.data?.studentUiSetting
                  ? additionalUserInfo?.data?.studentUiSetting
                  : "{}",
              ),
              studentId: additionalUserInfo?.data?.id,
              educationalLevel: additionalUserInfo?.data?.educationalLevel,
              university: additionalUserInfo?.data?.university
                ? additionalUserInfo?.data?.university
                : "{}",
            },
          });

          handleSignInSuccess(userModel, token);
        }
      } catch (error) {
        console.error("Keycloak init failed:", error);
        handleSignInFailure(error);
      }
    };

    initKeycloak();
  }, []);

  // Refactor signUp function
  const signUp = useCallback((data) => {
    return handleRequest(
      config.signUpUrl,
      data,
      handleSignUpSuccess,
      handleSignUpFailure,
    );
  }, []);

  /**
   * Sign out
   */
  const signOut = useCallback(() => {
    resetSession();
    setIsAuthenticated(false);
    setUser(null);

    keycloak?.logout({
      responseType: window.location.origin,
    });
  }, [keycloak]);
  /**
   * Update user
   */
  const updateUser = useCallback(async (userData) => {
    const studentDto = {
      semester: userData.data.semester,
      educationalLevel: parseInt(userData.data.educationalLevel),
      nationalCode: userData.data.nationalCode,
      keycloakUserId: userData.uid,
      studentUiSetting: JSON.stringify(userData.data.settings),
      universityDTO: userData.data.university,
    };
    try {
      console.log(userData);
      if (userData?.data?.studentId) {
        const response = await updateUserMutation({
          uid: userData.uid,
          body: studentDto,
        }).unwrap();

        const updatedUserData = {
          ...response.data,
          setting: JSON.parse(response.data.studentUiSetting), // parsed coming in
        };

        setUser(updatedUserData);
        return null;
      }
    } catch (error) {
      const axiosError = error;
      handleError(axiosError);
      return axiosError;
    }
  }, []);
  /**
   * Refresh access token
   */
  const refreshToken = async () => {
    try {
      setIsLoading(true);
      await keycloak?.updateToken(30);
      setSession(keycloak.token);
      setIsLoading(false);
      return keycloak.token;
    } catch (error) {
      console.error(error);
      keycloak.logout({
        redirectUri: window.location.origin,
      });
    }
  };

  useEffect(() => {
    if (config.updateTokenFromHeader && isAuthenticated) {
      const interceptor = axios.interceptors.response.use(
        (response) => {
          const newAccessToken = response?.headers?.["Authorization"];
          if (newAccessToken) {
            setSession(newAccessToken);
          }
          return response;
        },
        async (error) => {
          const originalRequest = error.config;

          if (
            error.response?.status === 401 &&
            !originalRequest._retry &&
            isTokenValid(getAccessToken()) === false
          ) {
            originalRequest._retry = true;

            try {
              const newAccessToken = await refreshToken();

              if (typeof newAccessToken === "string") {
                originalRequest.headers["Authorization"] =
                  `Bearer ${newAccessToken}`;
                return axios(originalRequest); // Retry the original request
              }
            } catch (refreshError) {
              console.warn("Refresh token failed:", refreshError);
              signOut();
            }
          }

          return Promise.reject(error);
        },
      );

      return () => {
        axios.interceptors.response.eject(interceptor); // Cleanup on unmount
      };
    }
  }, [
    isAuthenticated,
    refreshToken,
    getAccessToken,
    isTokenValid,
    setSession,
    signOut,
  ]);

  useEffect(() => {
    if (user) {
      setAuthStatus("authenticated");
    } else {
      setAuthStatus("unauthenticated");
    }
  }, [user]);

  const authContextValue = useMemo(
    () => ({
      user,
      isAuthenticated,
      authStatus,
      isLoading,
      signUp,
      signOut,
      updateUser,
      refreshToken,
      setIsLoading,
    }),
    [
      user,
      isAuthenticated,
      isLoading,
      signUp,
      signOut,
      updateUser,
      refreshToken,
      setIsLoading,
    ],
  );
  return (
    <JwtAuthContext.Provider value={authContextValue}>
      {children}
    </JwtAuthContext.Provider>
  );
}

export default JwtAuthProvider;
