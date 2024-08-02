import React,{useEffect,useState} from 'react';
import {getCategories} from "../services/CategoriesService";
import {CategoryContext} from "./CategoryContext";

export const CategoryProvider = ({children}) => {
    const [categories,setCategories] = useState(null);
        console.log(categories)

    useEffect(() => {
        getCategories(0, 10)
            .then((res) => {
                setCategories(res.content);

            })
            .catch((err) => {
                console.log(err);
            });

    },[]);
    return(
        <CategoryContext.Provider value={{categories: categories}}>
            {children}
        </CategoryContext.Provider>
    )
}