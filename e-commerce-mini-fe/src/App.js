
import './App.css';
import {NavbarMenu} from "./components/NavbarMenu";
import React, {useState} from "react";
import {CategorySideBar} from "./components/CategorySideBar";
import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import {Login} from "./pages/Login";
import {Register} from "./pages/Register";
import {About} from "./pages/About";
import {Contact} from "./pages/Contact";
import {Footer} from "./components/Footer";
import {CategoryProvider} from "./context/CategoryProvider";
import {Profile} from "./pages/users/Profile";
import {ShoppingCart} from "./pages/users/ShoppingCart";
import {Error404} from "./pages/Error404";
import {AddCategory} from "./pages/admin/AddCategory";
import {ViewCategories} from "./pages/admin/ViewCategories";
import {AddProduct} from "./pages/admin/AddProduct";
function App() {
  // state for category sidebar
  const [showCategorySidebar, setShowCategorySidebar] = useState(false);

  // functions for category sidebar
  const handleCloseCategorySidebar = () => setShowCategorySidebar(false);
  const handleShowCategorySidebar = () => setShowCategorySidebar(true);
  return (
    <>
        <CategoryProvider>
            <NavbarMenu handleShowCategorySidebar={handleShowCategorySidebar}/>
            <CategorySideBar
                showCategorySideBar={showCategorySidebar}
                handleCloseCategorySideBar={handleCloseCategorySidebar}/>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login/>}/>
                <Route path="/register" element={<Register />}/>
                <Route path="/about" element={<About />}/>
                <Route path="/contact" element={<Contact />}/>
                {/*<Route path="/products" element={<Products />}/>*/}
                {/*<Route path="/product/:productId" element={<SingleProductPage />}/>*/}
                {/*<Route path="/category/:categoryId/products" element={<CategoryProductsPage />}/>*/}

                {/* Routes only admin and logged in user can access*/}
                <Route
                    // element={<PrivateRoutes allowedRole={[ROLES.NORMAL, ROLES.ADMIN]} />}
                >

                    <Route path="/profile" element={<Profile />}/>
                    <Route path="/cart" element={<ShoppingCart />}/>
                    {/*<Route path="/place-order" element={<OrderCheckout />}/>*/}
                    {/*<Route path="/orders" element={<Orders />}/>*/}
                    {/*<Route path="/order/:orderId" element={<OrderDetail />}/>*/}
                </Route>
                {/* Routes only admin can access*/}
                <Route
                    // element={<PrivateRoutes allowedRole={[ROLES.ADMIN]} />}
                    >
                    <Route path="/admin/add-category" element={<AddCategory />}/>
                    <Route path="/admin/categories" element={<ViewCategories />}/>
                    <Route path="/admin/add-product" element={<AddProduct />}/>
                    {/*<Route path="/admin/products" element={<ViewProducts />}/>*/}
                    {/*<Route path="/admin/orders" element={<ViewOrders />}/>*/}
                    {/*<Route path="/admin/users" element={<ViewUsers />}/>*/}
                </Route>

                <Route path="*" element={<Error404/>}/>

            </Routes>
            <Footer/>
        </CategoryProvider>
    </>
  );
}

export default App;
