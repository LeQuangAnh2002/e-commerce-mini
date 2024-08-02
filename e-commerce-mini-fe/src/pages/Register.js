import React, {useState} from 'react';
import {Alert,Button,Col,Container,Form,Row,Spinner} from "react-bootstrap";
import {NavLink,useNavigate} from "react-router-dom";
import {toast} from "react-toastify";

export const Register = () =>{
    document.title = "Register";
    const navigate = useNavigate();

    // Server side validation error
    const [serverError, setServerError] = useState(null);
    // Loading state for register button
    const [loading, setLoading] = useState(false);

    return(
        <>
        <Container fluid="sm" style={{maxWidth: "900px"}}>
            <Row>
                <Col className="text-center mt-3">
                    <div>
                        <img src="/assets/logo.png"
                            width={50}
                            fluid="true"
                            className="d-inline-block align-top"
                            />
                            <div className="d-flex flex-column justify-content-center">
                                <h4 className="m-0" style={{fontSize: "1rem"}}>
                                    QuickPik
                                </h4>
                                <small style={{fontSize:"0.8rem"}}>
                                    Rapid Reflection, Swift Selection
                                </small>
                            </div>
                    </div>
                </Col>
            </Row>
            {/* server side validation alert */}
            {serverError && (
                <Row>
                    <Col>
                        {
                            typeof serverError === "string" ?(
                                <Alert variant="danger" className="p-2 mt-2">
                                    {serverError}}
                                </Alert>
                            ) :(
                               <Alert variant="danger" className="p-2 mt-2">
                                   <ul>
                                       {serverError.map((error) =>(
                                           <li key={error}>{error}</li>
                                       ))}
                                   </ul>
                               </Alert>
                            )
                        }
                    </Col>
                </Row>
            )}
            {/* Register Form */}
            <Row>
                <Col>
                    <h3>Register</h3>
                </Col>
            </Row>
            <Form noValidate className="mt-2">
                <Row className="mb-3 justify-content-center" md={10}>
                    <Form.Group as={Col} controlId="firstName">
                        <Form.Label>First Name</Form.Label>
                        <Form.Control
                        type="text"
                        placeholder="First Name"
                        />
                        <Form.Control.Feedback type="invalid">
                        {/* Error First Name*/}
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group as={Col} controlId="lastName">
                        <Form.Label>Last Name</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Last Name"
                        />
                        <Form.Control.Feedback type="invalid">
                            {/* Error Last Name*/}
                        </Form.Control.Feedback>
                    </Form.Group>

                </Row>
                <Row className="mb-3">
                    <Form.Group as={Col} controlId="email">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            type="email"
                            placeholder="Email"
                        />
                        <Form.Control.Feedback type="invalid">
                            {/* Error Email*/}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Row>
                <Row className="mb-3">
                    <Form.Group as={Col} controlId="password">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            placeholder="Password"
                            autoComplete="on"
                        />
                        <Form.Control.Feedback type="invalid">
                            {/*{errors.password}*/}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Row>
                <Row className="mb-3">
                    <Form.Group as={Col} controlId="cpassword">
                        <Form.Label>Confirm Password</Form.Label>
                        <Form.Control
                            type="password"
                            placeholder="Confirm Password"
                            autoComplete="on"
                        />
                        <Form.Control.Feedback type="invalid">
                            {/*{errors.cpassword}*/}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Row>
                <Button variant="primary" type="submit" disabled={loading}>
                    <Spinner
                        animation="border"
                        as="span"
                        size="sm"
                        className="me-2"
                        // loading state for register button
                        hidden={!loading}
                    />
                    <span>Register</span>
                </Button>
                <small className="text-left mt-2 mb-2 d-block">
                    Already have an account?{" "}
                    <NavLink to="/login" className="text-decoration-none">
                        login
                    </NavLink>
                </small>
            </Form>
        </Container>
        </>
    );
};
