import { apiService as api } from "app/store/apiService.js";

const UniversityApi = api.injectEndpoints({
  endpoints: (build) => ({
    getRowsCourses: build.mutation({
      query: (enterpriseGetRowsRequest) => ({
        url: `/courses/get-rows`,
        method: "POST",
        data: enterpriseGetRowsRequest,
      }),
    }),
    createCourse: build.mutation({
      query: (newCourse) => ({
        url: `/courses`,
        method: "POST",
        data: newCourse,
      }),
    }),
    updateCourse: build.mutation({
      query: (updatedCourse) => ({
        url: `/courses/${updatedCourse.id}`,
        method: "PUT",
        data: updatedCourse,
      }),
    }),
    deleteCourse: build.mutation({
      query: (courseId) => ({
        url: `/courses/${courseId}`,
        method: "DELETE",
      }),
    }),
    getAllCourses: build.query({
      query: (courseId) => ({
        url: `/courses/get-all/${courseId}`,
        method: "GET",
      }),
    }),
    createCoursePrerequisite: build.mutation({
      query: (newCoursePrerequisite) => ({
        url: `/course-prerequisites`,
        method: "POST",
        data: newCoursePrerequisite,
      }),
    }),
    updateCoursePrerequisite: build.mutation({
      query: (updatedCourse) => ({
        url: `/course-prerequisites/${updatedCourse.id}`,
        method: "PUT",
        data: updatedCourse,
      }),
    }),
    deleteCoursePrerequisite: build.mutation({
      query: (courseId) => ({
        url: `/course-prerequisites/${courseId}`,
        method: "DELETE",
      }),
    }),
    getRowsCoursePrerequisites: build.mutation({
      query: (enterpriseGetRowsRequest) => ({
        url: `/course-prerequisites/get-rows`,
        method: "POST",
        data: enterpriseGetRowsRequest,
      }),
    }),
    getAllColleges: build.query({
      query: () => ({
        url: `/university/get-all-colleges`,
        method: "GET",
      }),
    }),
    getDepartmentsByCollegeCode: build.query({
      query: (collegeCode) => ({
        url: `/university/get-departments-by-college-code/${collegeCode}`,
        method: "GET",
      }),
    }),
    getFieldOfStudyByDepartmentCode: build.query({
      query: (studyByDepartmentCode) => ({
        url: `/university/get-field-of-study-by-department-code/${studyByDepartmentCode}`,
        method: "GET",
      }),
    }),
    createCourseSchedule: build.mutation({
      query: (courseSchedule) => ({
        url: `/course-schedules`,
        method: "POST",
        data: courseSchedule,
      }),
    }),
    updateCourseSchedule: build.mutation({
      query: (courseSchedule) => ({
        url: `/course-schedules/${courseSchedule.id}`,
        method: "PUT",
        data: courseSchedule,
      }),
    }),
    deleteCourseSchedule: build.mutation({
      query: (id) => ({
        url: `/course-schedules/${id}`,
        method: "DELETE",
      }),
    }),
    getRowsCourseSchedule: build.mutation({
      query: (enterpriseGetRowsRequest) => ({
        url: `/course-schedules/get-rows`,
        method: "POST",
        data: enterpriseGetRowsRequest,
      }),
    }),
    updateUser: build.mutation({
      query: ({ uid, body }) => ({
        url: `/student/${uid}`,
        method: "PUT",
        data: body,
      }),
    }),
    getUser: build.query({
      query: ({ uid }) => ({
        url: `/student/${uid}`,
        method: "GET",
      }),
    }),
    getRowsExclusiveCoursesForStd: build.mutation({
      query: (enterpriseGetRowsRequest) => ({
        url: `/courses/get-exclusive-courses-for-student`,
        method: "POST",
        data: enterpriseGetRowsRequest,
      }),
    }),
    createCourseStudent: build.mutation({
      query: (courseStudent) => ({
        url: `/course-student`,
        method: "POST",
        data: courseStudent,
      }),
    }),
    deleteCourseStudent: build.mutation({
      query: (id) => ({
        url: `/course-student/${id}`,
        method: "DELETE",
      }),
    }),
    getRowsCourseStudent: build.mutation({
      query: (enterpriseGetRowsRequest) => ({
        url: `/course-student/get-rows`,
        method: "POST",
        data: enterpriseGetRowsRequest,
      }),
    }),
    getStudentEnrollmentResult: build.mutation({
      query: (enterpriseGetRowsRequest) => ({
        url: `/courses/get-student-enrollment-result`,
        method: "POST",
        data: enterpriseGetRowsRequest,
      }),
    }),
  }),
  overrideExisting: false,
});

export default UniversityApi;

export const {
  useGetRowsCoursesMutation,
  useCreateCourseMutation,
  useUpdateCourseMutation,
  useDeleteCourseMutation,
  useGetAllCoursesQuery,
  useGetRowsCoursePrerequisitesMutation,
  useCreateCoursePrerequisiteMutation,
  useUpdateCoursePrerequisiteMutation,
  useDeleteCoursePrerequisiteMutation,
  useGetAllCollegesQuery,
  useLazyGetDepartmentsByCollegeCodeQuery,
  useLazyGetFieldOfStudyByDepartmentCodeQuery,
  useCreateCourseScheduleMutation,
  useUpdateCourseScheduleMutation,
  useDeleteCourseScheduleMutation,
  useGetRowsCourseScheduleMutation,
  useUpdateUserMutation,
  useLazyGetUserQuery,
  useGetRowsExclusiveCoursesForStdMutation,
  useCreateCourseStudentMutation,
  useDeleteCourseStudentMutation,
  useGetRowsCourseStudentMutation,
  useGetStudentEnrollmentResultMutation,
} = UniversityApi;
