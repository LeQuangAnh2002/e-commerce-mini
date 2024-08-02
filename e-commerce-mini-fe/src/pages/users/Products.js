import React, { useState,useEffect } from "react";
import { toast } from "react-toastify";
import { Container, Row, Spinner } from "react-bootstrap";
import { ProductCard } from "../../components/users/ProductCard";

export const Product = () =>{
    const [products,setProducts] = useState(null);
    const [loading, setLoading] = useState(true);
    const [currentPage, setCurrentPage] = useState(0);

    // loading next page
    const loadNextPage = () => {
        setCurrentPage(currentPage + 1);
    };


}