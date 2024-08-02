import axios from "axios";
import { API_ENDPOINTS } from "./HelperService";
import { toast } from "react-toastify";
import {doLogoutLocalStorage, getTokenFromLocalStorage} from "./HelperAuth";


export const publicAxios = axios.create({
    baseURL: API_ENDPOINTS.BASE_URL,
});
export const privateAxios = axios.create({
    baseURL: API_ENDPOINTS.BASE_URL,
});
privateAxios.interceptors.request.use(
    (config) => {
        const tokens = getTokenFromLocalStorage();
        if (tokens !== null) {
            config.headers.common.Authorization = `Bearer ${tokens}`
        }
        return config;
    },
    (error) => {
        console.log("Error in request interceptor: ", error);
        Promise.reject(error);
    }
)

privateAxios.interceptors.request.use(
    (config) => {
        // Get the token from local storage
        const tokens = getTokenFromLocalStorage();
        if (tokens !== null) {
            // Set the Authorization header for the request
            config.headers.common.Authorization = `Bearer ${tokens.accessToken}`;
        }
        return config;
    },
    (error) => {
        console.log("Error in request interceptor: ", error);
        Promise.reject(error);
    }
);


// privateAxios.interceptors.response.use(
//     (response) => response,
//     async (error) => {
//         const originalRequest = error.config;
//
//         // Check if the error response has a status code of 401 (unauthorized)
//         if (
//             error.response &&
//             error.response.status === 401 &&
//             !originalRequest._retry
//         ) {
//             originalRequest._retry = true;
//
//             try {
//                 // Refresh the access token
//                 const newAccessToken = await refreshToken();
//
//                 // Update the access token in local storage
//                 updateAccessTokenInLocalStorage(newAccessToken);
//
//                 // Update the Authorization header with the new access token
//                 originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
//
//                 // Retry the original request
//                 return privateAxios(originalRequest);
//             } catch (refreshError) {
//                 // Clear the tokens in local storage
//                 doLogoutLocalStorage();
//
//                 // show a msg
//                 toast.info("Your session has expired! please login again", {
//                     autoClose: false,
//                 });
//
//                 // redirect to login page
//                 window.location.href = "/login";
//             }
//         }
//
//         return Promise.reject(error);
//     }
// );
