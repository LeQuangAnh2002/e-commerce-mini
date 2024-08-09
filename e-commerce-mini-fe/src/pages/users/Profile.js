import React,{useContext,useState,useEffect} from 'react'
import {Alert,Button,Col,Container,Form,Row,Spinner} from 'react-bootstrap'
import {SideBar} from "../../components/SideBar";
import { UserContext } from "../../context/UserContext";
import { toast } from "react-toastify";
import axios from "axios";
import {ImageUpload} from "../../components/users/ImageUpload";

export const Profile = () =>{
    document.title = " Profile";
    const userContext = useContext(UserContext);

    const [loading, setLoading] = useState(false);
    const [show,setShow] = useState(false);
    const [user, setUser] = useState(null);
    const [serverError, setServerError] = useState(null);

    // state for image file (can be a string or file object)
    const [image, setImage] = useState(null);

    // state for address
    const [address, setAddress] = useState("");
    // methods for sidebar
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return(
        <>
        <SideBar show={show} handleClose={handleClose} />

        <Container>
            <Row>
                <Col>
                    <h2 className="mt-3">
                        <i className="fa-solid fa-bars me-2"
                        style={{cursor: 'pointer'}}
                           onClick={handleShow}/>
                        Profile
                    </h2>
                    <hr/>
                </Col>
            </Row>
            <Container>
                {/* show spinner if user is null and data is still being fetched otherwise show form with values */}
                {user == null ? (
                    <div className="text-center mb-3">
                        <Spinner animation="border" as="span" size="lg"/>
                    </div>
                ) : (
                    <>
                        {/* server side validation alert */}
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
                        {/* Image upload component */}
                        {image == null ? (
                            ""
                        ) : (
                            <ImageUpload image={image} userId={user.userId} />
                        )}

                        {/* Profile Form */}
                        <Form noValidate >
                            <Row>
                                <Form.Group
                                    as={Col}
                                    controlId="fname"
                                    md={6}
                                    className="mb-3"
                                >
                                    <Form.Label>First Name</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="First Name"
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        {/*{errors.fname}*/}
                                    </Form.Control.Feedback>
                                </Form.Group>
                                <Form.Group
                                    as={Col}
                                    controlId="lname"
                                    md={6}
                                    className="mb-3"
                                >
                                    <Form.Label>Last Name</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Last Name"
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        {/*{errors.lname}*/}
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </Row>
                            <Row>
                                <Form.Group
                                    as={Col}
                                    controlId="email"
                                    md={6}
                                    className="mb-3"
                                >
                                    <Form.Label>Email</Form.Label>
                                    <p
                                        className="form-control mb-0 text-muted bg-light"
                                        disabled
                                    >
                                        {/*{user.email}*/}
                                    </p>
                                </Form.Group>
                                <Form.Group
                                    as={Col}
                                    controlId="phone"
                                    md={6}
                                    className="mb-3"
                                >
                                    <Form.Label>Phone</Form.Label>
                                    <Form.Control
                                        type="text"
                                        placeholder="Phone"
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        {/*{errors.phone}*/}
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </Row>
                            {/* Adding mapbox address autofill */}
                            {/*<AddressAutofill*/}
                            {/*    accessToken={process.env.REACT_APP_MAPBOX_TOKEN}*/}
                            {/*    options={{*/}
                            {/*        country: "CA",*/}
                            {/*        language: "en",*/}
                            {/*    }}*/}
                            {/*>*/}
                                <Row>
                                    <Form.Group
                                        as={Col}
                                        controlId="address"
                                        md={12}
                                        className="mb-3"
                                    >
                                        <Form.Label>Address</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="Address"
                                            autoComplete="address-line-1"
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            {/*{errors.address}*/}
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                </Row>
                                <Row>
                                    <Form.Group
                                        as={Col}
                                        controlId="city"
                                        md={4}
                                        className="mb-3"
                                    >
                                        <Form.Label>City</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="City"
                                            autoComplete="address-level2"
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            {/*{errors.city}*/}
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                    <Form.Group
                                        as={Col}
                                        controlId="province"
                                        md={4}
                                        className="mb-3"
                                    >
                                        <Form.Label>Province</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="Province"
                                            autoComplete="address-level1"
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            {/*{errors.province}*/}
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                    <Form.Group
                                        as={Col}
                                        controlId="postalCode"
                                        md={4}
                                        className="mb-3"
                                    >
                                        <Form.Label>Postal Code</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="Postal Code"
                                            autoComplete="postal-code"
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            {/*{errors.postalCode}*/}
                                        </Form.Control.Feedback>
                                    </Form.Group>
                                </Row>
                            {/*</AddressAutofill>*/}
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
                                <span>Save</span>
                            </Button>
                        </Form>
                    </>
                )}
            </Container>
        </Container>
        </>
    )
}