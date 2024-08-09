import { privateAxios, publicAxios } from "./AxiosService";
import { API_ENDPOINTS } from "./HelperService";
import axios from "axios";

// export const getCategories = async (
//     currentPage = 0 ,
//     pageSize = API_ENDPOINTS.CATEGORY_PAGE_SIZE) =>{
//         try {
//             const result = publicAxios.get(`${API_ENDPOINTS.CATEGORIES}?pageNumber=${currentPage}&pageSize=${pageSize}`);
//             // const result = axios.get(`${API_ENDPOINTS.BASE_URL}/${API_ENDPOINTS.CATEGORIES}?pageNumber=${currentPage}&pageSize=${pageSize}`);
//             console.log(result);
//             return result.data;
//     }catch (e) {
//         console.log(e)
//     }
//
// }

export const getCategories = (
    currentPage = 0,
    pageSize = API_ENDPOINTS.CATEGORY_PAGE_SIZE
) => {
    return publicAxios
        .get(
            `${API_ENDPOINTS.CATEGORIES}?pageNumber=${currentPage}&pageSize=${pageSize}`
        )
        .then((res) => {
            console.log(res);
            return res.data;
        });
};

export const getCategoryById = async (categoryId) => {
    const result =  await publicAxios.get(`${API_ENDPOINTS.CATEGORIES}/${categoryId}`);
    return result.data;
}
export const addCategory = async (data) => {
    try {
        const result = await publicAxios.post(API_ENDPOINTS.CATEGORIES,data);
        return  result.data;
    }catch (e) {
        console.log(e)
    }
}

export const updateCategory = async (categoryId,data) =>{
    try {
        const result = await privateAxios.put(`${API_ENDPOINTS.CATEGORIES}/${categoryId}`,data);
        return result.data;
    }catch (e) {
        console.log(e)
    };


};

export const deleteCategory = async (categoryId) => {
    try {
        await privateAxios.delete(`${API_ENDPOINTS.CATEGORIES}/${categoryId}`);
    }catch (e) {
        console.log(e)
    }
};

