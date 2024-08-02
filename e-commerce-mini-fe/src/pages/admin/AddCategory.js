import {
    Alert,
    Button,
    Col,
    Container,
    Form,
    Row,
    Spinner,
} from "react-bootstrap";
import React, { useRef, useState } from "react";
import { SideBar } from "../../components/SideBar";
import { toast } from "react-toastify";

export const AddCategory = () =>{
    document.title = "Add Category";

    // state to show/hide sidebar
    const [show, setShow] = useState(false);

    const [loading, setLoading] = useState(false);

    const [serverError, setServerError] = useState(null);
    // methods for sidebar
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    // reference to the hidden image input element
    const imageRef = useRef(null);

    return(
        <>
        <SideBar show={show} handleClose={handleClose}/>
            <Container>
                <Row>
                    <Col>
                        <h2 className="mt-3">
                            <i className="fa-solid fa-bars me-2"
                            style={{cursor : 'pointer'}}
                               onClick={handleShow}
                            />
                            Add Category
                        </h2>
                        <hr />
                    </Col>
                </Row>
                <Container>
                    {serverError && (
                        <Row>
                            <Col>
                                {typeof serverError === 'string' ? (
                                    <Alert variant="danger" className="p-2 mt-2">
                                        {serverError}
                                    </Alert>
                                ):(
                                    <Alert variant="danger" className="p-2 mt-2">
                                        <ul>
                                            {serverError.map((error,index) =>(
                                             <li key={index}>{error}</li>
                                            ))}
                                        </ul>
                                    </Alert>
                                )}
                            </Col>
                        </Row>
                    )}
                    <Form noValidate>
                        <Row>
                            <Col className="mb-3">
                                <Form.Group controlId="image" className="mb-3">
                                    {/* Hidden Image input */}
                                    <Form.Control
                                        hidden
                                        ref={imageRef}
                                        type="file"
                                        multiple={false}
                                        accept="image/*"

                                    />
                                    {/* Error message */}
                                    <Form.Control.Feedback type="invalid">
                                        {/*{errors.image}*/}
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row>
                            <Form.Group as={Col}
                                        controlId="categoryTitle"
                                        md={8}
                                        className="mb-3">
                                <Form.Label>Category Title</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Category Title"

                                />
                                <Form.Control.Feedback type="invalid">
                                    {/*{errors.categoryTitle}*/}
                                </Form.Control.Feedback>
                            </Form.Group>
                        </Row>
                        <Row>
                            <Form.Group
                                as={Col}
                                controlId="description"
                                md={8}
                                className="mb-3"
                            >
                                <Form.Label>Description</Form.Label>
                                <Form.Control
                                    type="text"
                                    as="textarea"
                                    placeholder="Provide a clear and concise description of your product category"
                                    rows={5}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {/*{errors.description}*/}
                                </Form.Control.Feedback>
                            </Form.Group>
                        </Row>
                        <Button
                            variant="primary"
                            className="mb-3"
                            type="submit"
                            disabled={loading}
                        >
                            <Spinner
                                animation="border"
                                as="span"
                                size="sm"
                                className="me-2"
                                // loading state for save button
                                hidden={!loading}
                            />
                            <span>Add Category</span>
                        </Button>
                </Form>
                </Container>
            </Container>
        </>
    )
}