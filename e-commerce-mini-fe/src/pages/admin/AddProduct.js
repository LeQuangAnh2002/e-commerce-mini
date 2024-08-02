import React, { useRef, useState,useEffect } from "react";
import { SideBar } from "../../components/SideBar";
import { toast } from "react-toastify";
import {
    Alert,
    Button,
    Col,
    Container,
    Form,
    InputGroup,
    Row,
    Spinner,
} from "react-bootstrap";
import {Editor} from "@tinymce/tinymce-react";

export const AddProduct = () => {
    document.title = "Add Product";

    // state to show/hide sidebar
    const [show, setShow] = useState(false);

    // state to store categories
    const [categories, setCategories] = useState([]);

    const [selectedCategoryId, setSelectedCategoryId] = useState(undefined);

    const [loading, setLoading] = useState(false);
    const [serverError, setServerError] = useState(null);
    // reference to the hidden image input element
    const imageRef = useRef(null);

    // reference to the rich text editor
    const editorRef = useRef(null);

    // state to store the preview image
    const [previewImage, setPreviewImage] = useState(null);

    // methods for sidebar
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);


    return (
        <>
            <SideBar show={show} handleClose={handleClose}/>
            <Container>
                <Row>
                    <Col>
                        <h2 className="mt-3">
                            <i
                                className="fa-solid fa-bars me-2"
                                style={{ cursor: "pointer" }}
                                onClick={handleShow}
                            />
                            Add Product
                        </h2>
                        <hr />
                    </Col>
                </Row>
                    <Container>
                        {serverError && (
                            <Row>
                                <Col>
                                    {typeof serverError === "string" ? (
                                        <Alert variant="danger" className="p-2 mt-2">
                                            {serverError}
                                        </Alert>
                                    ) : (
                                        <Alert variant="danger" className="p-2 mt-2">
                                            <ul>
                                                {serverError.map((error) => (
                                                    <li key={error}>{error}</li>
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
                                    <Form.Group controlId="formFile" className="mb-3">
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
                                            {/*{errors.productImage}*/}
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                    {/*Button to trigger the hidden image input */}
                                    <Button
                                        variant="primary"
                                        onClick={() => {
                                            imageRef.current.click();
                                        }}
                                    >
                                        Choose Product Image
                                    </Button>
                                </Col>
                            </Row>
                            <Row>
                                {/* Product Category */}
                                <Form.Group as={Col} controlId="category" md={6} className="mb-3">
                                    <Form.Label>Product Category</Form.Label>
                                    <Form.Select
                                        onChange={(e) => setSelectedCategoryId(e.target.value)}
                                        value={selectedCategoryId}
                                    >
                                        <option disabled selected>Select a Category</option>
                                        {categories ? (
                                            <>
                                                {/* Show category options */}
                                                {categories.map((category) => {
                                                    return (
                                                        <option
                                                            key={category.categoryId}
                                                            value={category.categoryId}
                                                        >
                                                            {category.categoryTitle}
                                                        </option>
                                                    );
                                                })}
                                            </>
                                        ) : (
                                            ""
                                        )}
                                    </Form.Select>
                                </Form.Group>
                            </Row>
                            <Row>
                                <Form.Group as={Col} controlId="brand" md={6} className="mb-3">
                                    <Form.Label>Brand</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Brand"
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        {/*{errors.brand}*/}
                                    </Form.Control.Feedback>
                                </Form.Group>
                                <Form.Group as={Col} controlId="title" md={6} className="mb-3">
                                    <Form.Label>Product Name</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Product Name"
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        {/*{errors.title}*/}
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </Row>
                            <Row>
                                <Form.Group
                                    as={Col}
                                    controlId="unitPrice"
                                    md={4}
                                    className="mb-3"
                                >
                                    <Form.Label>Unit Price</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type="text"
                                            placeholder="Unit Price"
                                        />
                                        <InputGroup.Text>CAD</InputGroup.Text>
                                        <Form.Control.Feedback type="invalid">
                                            {/*{errors.unitPrice}*/}
                                        </Form.Control.Feedback>
                                    </InputGroup>
                                </Form.Group>
                                <Form.Group
                                    as={Col}
                                    controlId="discountedPrice"
                                    md={4}
                                    className="mb-3"
                                >
                                    <Form.Label>Discounted Price</Form.Label>
                                    <InputGroup>
                                        <Form.Control
                                            type="text"
                                            placeholder="Discounted Price"
                                        />
                                        <InputGroup.Text>CAD</InputGroup.Text>
                                        <Form.Control.Feedback type="invalid">
                                            {/*{errors.discountedPrice}*/}
                                        </Form.Control.Feedback>
                                    </InputGroup>
                                </Form.Group>
                                <Form.Group as={Col} controlId="quantity" md={4} className="mb-3">
                                    <Form.Label>Stock Quantity</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Stock Quantity"
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        {/*{errors.quantity}*/}
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </Row>
                            <Row>
                                <Form.Group
                                    as={Col}
                                    controlId="shortDescription"
                                    className="mb-3"
                                >
                                    <Form.Label>Short Description</Form.Label>
                                    <Form.Control
                                        as="textarea"
                                        placeholder="Short Description"
                                        rows={3}
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        {/*{errors.shortDescription}*/}
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </Row>
                            <Row>
                                <Form.Group as={Col} controlId="description" md={12} className="mb-3">
                                    <Form.Label>Description</Form.Label>
                                    <Editor
                                        apiKey="gku5gc9n0qoj9wusg6paj45p3wrvc27b4k96uzk53fyvcwgk"
                                        onInit={(evt, editor) => (editorRef.current = editor)}
                                        init={{
                                            selector: "textarea#basic-example",
                                            icons: "bootstrap",
                                            plugins: [
                                                "advlist",
                                                "autolink",
                                                "lists",
                                                "link",
                                                "charmap",
                                                "preview",
                                                "anchor",
                                                "searchreplace",
                                                "visualblocks",
                                                "fullscreen",
                                                "insertdatetime",
                                                "table",
                                                "help",
                                            ],
                                            toolbar: 'undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | ltr rtl',
                                            browser_spellcheck: true,
                                            autosave_interval: "30s",
                                            content_style: `
                        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');
                        body { font-family: 'Roboto', sans-serif; }`,
                                        }}
                                    >
                                        <div className="text-danger" style={{ fontSize: "0.875rem" }}>
                                            {/*{errors.description}*/}
                                        </div>
                                    </Editor>
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
                                <span>Add Product</span>
                            </Button>
                        </Form>
                    </Container>
            </Container>
        </>
    )

}