import React, { useState,useEffect } from "react";
import { SideBar } from "../../components/SideBar";
import {
    Button,
    Col,
    Container,
    Form,
    Modal,
    Row,
    Spinner,
} from "react-bootstrap";
import { toast } from "react-toastify";
import { CategoryView } from "../../components/admin/CategoryView";
import { CategoryImageUpload } from "../../components/admin/CategoryImageUpload";
import {getCategories} from "../../services/CategoriesService";
import InfiniteScroll from "react-infinite-scroll-component";

export const ViewCategories = () => {
    document.title = "View Categories";

    const deleteIcon = {
        margin: "20px",
        border: "4px solid",
        padding: "10px 14px",
        borderRadius: "50%",
    };

    // state to show/hide sidebar
    const [show, setShow] = useState(false);
    // methods for sidebar
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    // state to store the category id to be deleted
    const [deleteCategoryId, setDeleteCategoryId] = useState(null);
    const [selectedCategory, setSelectedCategory] = useState(null);

    const [loading, setLoading] = useState(false);

    // state to store the categories
    const [categories, setCategories] = useState(
        []
    );

    // state to show/hide delete modal
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    // state to show/hide category modal
    const [showCategoryModal, setShowCategoryModal] = useState(false);

    const [currentPage, setCurrentPage] = useState(0);

    // method to show delete modal
    const handleDeleteModalClose = () => setShowDeleteModal(false);
    const handleDeleteModalShow = (categoryId) => {
        setShowDeleteModal(true);
        setDeleteCategoryId(categoryId);
    };
    // method to show category modal
    const handleCategoryModalClose = () => setShowCategoryModal(false);
    const handleCategoryModalShow = (category) => {
        setShowCategoryModal(true);
        setSelectedCategory(category);
    };
// method to load more categories(next page)
    const loadNextPage = () => {
        setCurrentPage(currentPage + 1);
    };

    useEffect(() =>{
        if (currentPage === 0){
            setLoading(true);
            getCategories()
                .then((data) => {
                setCategories(data);

            })
                .catch(() => {
                    toast.error("Something went wrong! Please try again later");
                })
                .finally(() => {
                    setLoading(false);
                })

        }
    },[])
    return(
        <>

            {/* Category Details Modal */}

           <Modal show={showCategoryModal} onHide={handleCategoryModalClose}>
               <Modal.Header>
                   <Modal.Title>Category Details</Modal.Title>
                   <Modal.Body>
                       <CategoryImageUpload
                       // image={selectedCategory?.categoryImage}
                       // categoryId={selectedCategory?.categoryId}
                       // handleUploadCategoryImage={handleUploadCategoryImage}
                       />

                       {/* Category Details Form */}
                       <Form noValidate >
                           <Row>
                               <Form.Group as={Col} controlId="categoryTitle" className="mb-3">
                                   <Form.Label>Category Title</Form.Label>
                                   <Form.Control
                                       type="text"
                                       placeholder="Enter Category Title"
                                       required
                                   />
                                   <Form.Control.Feedback type="invalid">
                                       Please provide a valid category title.
                                   </Form.Control.Feedback>
                               </Form.Group>
                           </Row>
                           <Row>
                               <Form.Group as={Col} controlId="description" className="mb-3">
                                   <Form.Label>Description</Form.Label>
                                   <Form.Control
                                       type="text"
                                       as="textarea"
                                       placeholder="Provide a clear and concise description of your product category"
                                       rows={8}
                                   />
                                   <Form.Control.Feedback type="invalid">
                                       Please provide a valid description.
                                   </Form.Control.Feedback>
                               </Form.Group>
                           </Row>
                           <Button
                               variant="primary"
                               type="submit"
                               disabled={loading}
                               className="me-2"
                           >
                               <Spinner
                                   animation="border"
                                   as="span"
                                   size="sm"
                                   className="me-3"
                                   // loading state for save button
                                   hidden={!loading}
                               />
                               <span>Update</span>
                           </Button>
                           <Button variant="secondary" onClick={handleCategoryModalClose}>
                               Close
                           </Button>
                       </Form>
                   </Modal.Body>
               </Modal.Header>
           </Modal>
            {/*Sidebar*/}
            <SideBar show={show} handleClose={handleClose} />
            <Container>
                <Row>
                    <Col>
                        <h2 className="mt-3">
                            <i
                                className="fa-solid fa-bars me-2"
                                style={{cursor: 'pointer'}}
                                onClick={handleShow}
                            />
                            Categories
                        </h2>
                        <hr/>
                    </Col>
                </Row>
                {/*Loading spinner*/}
                {
                    loading ? (
                        <Spinner animation="border" as="span" size="lg"/>
                     ): categories.length === 0 ? (
                         <div className="text-center mb-3">
                             <h3>No categories found!</h3>
                         </div>
                    ) : (
                        <InfiniteScroll
                        dataLength={categories.content.length}
                        next={loadNextPage}
                        hasMore={!categories.lastPage}
                        loader={
                            <div className="text-center mb-3">
                                <Spinner animation="border" as="span" size="lg"/>
                            </div>
                        }
                        >
                        <Container>
                            <Row xs={1} md={2} lg={3} xl={4}>
                                {categories.content.map((category) => {
                                    return (
                                        <CategoryView
                                            category={category}
                                            key={category.categoryId}
                                            showDeleteModal={handleDeleteModalShow}
                                            showCategoryModal={handleCategoryModalShow}
                                        />
                                    );
                                })}
                            </Row>
                        </Container>
                        </InfiniteScroll>)}
            </Container>
            </>
    );
};