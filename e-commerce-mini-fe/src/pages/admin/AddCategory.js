import {
    Alert,
    Button,
    Col,
    Container,
    Row,
    Spinner,
} from "react-bootstrap";
import React, { useRef, useState } from "react";
import { SideBar } from "../../components/SideBar";
import { toast } from "react-toastify";
import { IKUpload,IKContext } from 'imagekitio-react';
import { Formik,Form,Field,ErrorMessage} from "formik";
import {categoryWithFileSchema} from "../../utils/schema/categoryWithFileSchema";
import {addCategory} from "../../services/CategoriesService";

export const AddCategory = () =>{
    document.title = "Add Category";

    // state to show/hide sidebar
    const [show, setShow] = useState(false);

    const [loading, setLoading] = useState(false);

    const [previewImage, setPreviewImage] = useState(null);

    const [serverError, setServerError] = useState(null);
    // methods for sidebar
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    // reference to the hidden image input element
    const imageRef = useRef(null);
    const publicKey = "public_BFygarEYPUWnf8M+hyAUqGv2Kq8=";
    const urlEndpoint ="https://ik.imagekit.io/mduf5wsgw"
    const authenticator = async  () =>{
        try{
            const response = await fetch('http://localhost:3001/auth');
            if(!response.ok){
                const errorText = await response.text();
                throw new Error(`Request failed with status ${response.status}: ${errorText}`);
            }
            const data = await response.json();
            console.log(data);
            const { signature, expire, token } = data;
            return { signature, expire, token };
        }catch (error) {
            throw new Error(`Authentication request failed: ${error.message}`);
        }
    }

    const onSubmit = (values,actions) =>{
        console.log(values)
        setLoading(true);
        addCategory(values)
            .then((res) =>{
                toast.success("Category added successfully");
                setServerError(null);
                actions.resetForm();
            })
            .catch((err) => {
                if (err?.response?.data?.errors) {
                    setServerError(err.response.data.errors);
                } else {
                    toast.error("Something went wrong! Please try again later");
                }
            })
            .finally(() => {
                setLoading(false);
            })

    }
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

                <Formik
                    initialValues={{
                        categoryTitle : '',
                        description: '',
                        categoryImage: null}}
                        validationSchema={categoryWithFileSchema}
                        onSubmit={(values,actions) =>onSubmit(values,actions)}
                        enableReinitialize={true}
                        >
                    {({setFieldValue,errors }) =>(
                        <Form noValidate >
                            <IKContext
                                publicKey={publicKey}
                                urlEndpoint={urlEndpoint}
                                transformationPosition="query"

                                authenticator={authenticator} >
                            <Row>
                                <Col className="mb-3">

                                        {/* Image */}
                                        <div>
                                            {previewImage === null ? (
                                                <i className="fa-regular fa-image fs-1 ms-1"/>
                                            ) : (
                                                <img
                                                    src={previewImage}
                                                    alt="Category"
                                                    width="200px"
                                                    height="200px"
                                                    style={{ objectFit: "cover", borderRadius: "4%" }}
                                                />
                                            )}
                                        </div>
                                            <IKUpload
                                                onSuccess={(response) => {
                                                    const file = imageRef.current.files[0];
                                                    const allowedExtensions = ["jpg", "jpeg", "png"];
                                                    const fileExtension = file.name.split(".").pop().toLowerCase();

                                                    if (file.size > 2 * 1024 * 1024) {
                                                        console.log("File too large");
                                                        errors.image = "File size is too large (max 2MB)";
                                                        return;
                                                    } else if (!allowedExtensions.includes(fileExtension)) {
                                                        console.log("Invalid file type");
                                                        errors.image = "Invalid file type. Only JPG, JPEG, PNG are allowed";
                                                        return;
                                                    }
                                                    console.log("Upload successful:", response);
                                                    const filename = response.url.split("/").pop();
                                                    console.log(filename);
                                                    setPreviewImage(response.url);
                                                    setFieldValue("categoryImage", `categories/${filename}`);
                                                }}
                                                onError={(error) => {
                                                    console.log("Upload error:", error.message);
                                                }}
                                                ref={imageRef}
                                                folder="categories"
                                                hidden={true}

                                            />


                                    <Button
                                        variant="primary"
                                        onClick={() => {
                                            imageRef.current.click();
                                        }}
                                    >
                                        Choose Category Image
                                    </Button>
                                    <br/>
                                    {/*Error message*/}
                                    <ErrorMessage name="image" component="span" style={{color: "red"}}/>
                                </Col>
                            </Row>
                        </IKContext>
                            <Row >
                                    <Field
                                        name="categoryTitle"
                                        type="text"
                                        label="Category Title"
                                        placeholder="Category Title"
                                        className="mb-3"
                                    />
                                <ErrorMessage name="categoryTitle" component="span" style={{color: "red"}}/>
                            </Row>
                            <Row>

                                    <Field
                                        name="description"
                                        type="text"
                                        as="textarea"
                                        label="Description"
                                        placeholder="Provide a clear and concise description of your product category"
                                        rows={5}
                                        className="mb-3"
                                    />
                                <ErrorMessage name="description" component="span" style={{color: "red"}}/>
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
                    )}
                </Formik>
                </Container>
            </Container>
        </>
    )
}