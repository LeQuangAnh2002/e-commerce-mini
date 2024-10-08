import React from "react";
import { Button } from "react-bootstrap";

export const OrderView = ({ order,index,handleOrderViewModalShow}) =>{
    return(
        <>
        <tr>
            {/*<td className="small">{index + 1}</td>*/}
            {/*<td className="small">{order.orderNumber}</td>*/}
            {/*<td className="small">{order.orderName}</td>*/}
            {/*<td className="small">$ {order.orderAmount}</td>*/}
            {/*<td className="small">{order.orderStatus}</td>*/}
            {/*<td className="small">{order.paymentStatus}</td>*/}
            {/*<td className="small">{formatDate(order.createdAt)}</td>*/}
            <td className="small">
                <div style={{ minWidth: "80px" }}>
                    <Button
                        variant="primary"
                        size="sm"
                        className="me-2"
                        // onClick={() => {handleOrderViewModalShow(order)}}
                    >
                        <i className="fa-solid fa-eye me-2"/>
                        <span>View</span>
                    </Button>
                </div>
            </td>
        </tr>
        </>
    )
}