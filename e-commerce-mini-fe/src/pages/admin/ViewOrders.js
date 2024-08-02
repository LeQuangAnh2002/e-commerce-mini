import React, { useState,useEffect } from "react";
import { OrderView } from "../../components/admin/OrderView";
import { IKContext, IKImage } from "imagekitio-react";
import { toast } from "react-toastify";

const ViewOrders = () => {
    document.title = "View Orders";

    // state to show/hide sidebar
    const [show, setShow] = useState(false);

    // methods for sidebar
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [loading, setLoading] = useState(false); // loading state for save button

    const [orders, setOrders] = useState(undefined);

    const [selectedOrder, setSelectedOrder] = useState(undefined);
    const [showOrderViewModal, setShowOrderViewModal] = useState(false);


}