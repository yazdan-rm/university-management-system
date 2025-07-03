import { createApi } from "@reduxjs/toolkit/query/react";
import Axios from "axios";
import i18next from "i18next";

const axiosBaseQuery =
  () =>
  async ({ url, method, data, params }) => {
    try {
      Axios.defaults.baseURL = "https://localhost:9999/api/v1";
      const language = i18next.language;

      const result = await Axios({
        url,
        method,
        data,
        // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
        params,
        headers: {
          "Accept-Language": language, // Add the Accept-Language header
        },
      });
      return { data: result.data };
    } catch (axiosError) {
      const error = axiosError;
      return {
        error,
      };
    }
  };

export const apiService = createApi({
  baseQuery: axiosBaseQuery(),
  endpoints: () => ({}),
  reducerPath: "apiService",
});
export default apiService;
