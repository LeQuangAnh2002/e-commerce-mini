import React,{useState} from 'react';
import { Button, Col, Container, Form, Row, Spinner } from "react-bootstrap";
import { NavLink, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { ROLES } from "../utils/Roles";
import { UserContext } from "../context/UserContext";
import { useContext } from "react";

export const Login = () =>{
    document.title = "Login";

    // Loading state for spinner
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    // user context
    const userContext = useContext(UserContext);

    return(
        <Container fluid="sm" style={{maxWidth: "900px"}}>
            <Row>
                <Col className="text-center mt-3">
                    <div>
                        <img
                            src="/assets/logo.png"
                            width={50}
                            fluid="true"
                            className="d-inline-block align-top"
                            alt="QuickPik Logo"
                        />
                        <div className="d-flex flex-column justify-content-center">
                            <h4 className="m-0" style={{ fontSize: "1rem" }}>
                                QuickPik
                            </h4>
                            <small style={{ fontSize: "0.8rem" }}>
                                Rapid Reflection, Swift Selection
                            </small>
                        </div>
                    </div>
                </Col>
            </Row>
            <Row>
                <h3>Login</h3>
            </Row>
        {/*    Login Form*/}
            <Form noValidate className="mt-2">
                <Row className="mb-3">
                    <Form.Group as={Col} controlId="email">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            type="email"
                            placeholder="Email"
                            autoComplete="on"
                        />
                        <Form.Control.Feedback type="invalid">
                            {/*{errors.email}*/}
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
                <Button variant="primary" type="submit" disabled={loading}>
                    <Spinner
                        animation="border"
                        as="span"
                        size="sm"
                        className="me-2"
                        // loading state for register button
                        hidden={!loading}
                    />
                    <span>Login</span>
                </Button>
                <small className="text-left mt-2 mb-2 d-block">
                    Don't have an account?{" "}
                    <NavLink to="/register" className="text-decoration-none">
                        register
                    </NavLink>
                </small>
            </Form>
        </Container>
    )
}