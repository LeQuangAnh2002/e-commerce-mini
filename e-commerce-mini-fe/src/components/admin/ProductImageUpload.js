import React,{useState,useRef,useEffect} from 'react';
import { Button, Col, Form, Row, Spinner } from "react-bootstrap";
import { IKContext, IKImage } from "imagekitio-react";

export const ProductImageUpload = ({props}) => {
    const [loading, setLoading] = useState(false);

    // reference to the hidden image input element
    const imageRef = useRef(null);

    // state to store the preview image
    const [previewImage, setPreviewImage] = useState(undefined);

    return(
        <>
            <Form>
                <Row>
                    <Col className="text-center mb-3">
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
                                {/*{formik.errors.image}*/}
                            </Form.Control.Feedback>
                        </Form.Group>
                        {/*Button to trigger the hidden image input */}

                        <Button
                        variant="primary"
                        // onClick={() => {
                        //     imageRef.current.click();
                        // }}
                        >
                            Choose image
                        </Button>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        {/* Submit button */}
                        <Button
                            variant="primary"
                            className="mb-3"
                            type="submit"
                            // disabled={loading || formik.values.image === null}
                        >
                            <Spinner
                                animation="border"
                                as="span"
                                size="sm"
                                className="me-2"
                                // loading state for save button
                                hidden={!loading}
                            />
                            <i
                                className="fa-solid fa-arrow-up-from-bracket me-2"
                                hidden={loading}
                            />
                            <span>Upload</span>
                        </Button>
                    </Col>
                </Row>
            </Form>
        </>
    )
}