import React, {useState} from "react";
import Link from "next/link";
import {Alert, Button, Form} from "react-bootstrap";
import {useRedirectToHome} from "../lib/session";
import {signup} from "@lib/api";

export default function SignUpPage({session}) {
    useRedirectToHome(session)
    const [formData, setFormData] = useState({
        email: "",
        password: "",
    });
    const [validated, setValidated] = useState(false);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState();

    const submit = async (event) => {
        const form = event.currentTarget;
        event.preventDefault();
        event.stopPropagation();

        if (form.checkValidity() === true) {
            try {
                const {email, password} = formData;
                setError("");
                await signup({email, password});
                setSuccess(true);
            } catch (error) {
                console.error(error);
                setError("An error occurred during authentication");
                setSuccess(false);
            }
        }

        setValidated(true);
    };

    const onInputChange = (e) => {
        setFormData((data) => ({
            ...data,
            [e.target.name]: e.target.value,
        }));
    };

    return (
        <>
            <h2 className="mt-5">Create new Profile</h2>
            <Form className="mt-3" noValidate validated={validated} onSubmit={submit}>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>Email *</Form.Label>
                    <Form.Control
                        required
                        name="email"
                        type="email"
                        placeholder="Enter email"
                        onChange={onInputChange}
                        value={formData.email}
                    />
                    <Form.Control.Feedback type="invalid">
                        Please choose an email address.
                    </Form.Control.Feedback>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>Password *</Form.Label>
                    <Form.Control
                        required
                        name="password"
                        type="password"
                        placeholder="Password"
                        onChange={onInputChange}
                        value={formData.password}
                    />
                    <Form.Control.Feedback type="invalid">
                        Please choose a password.
                    </Form.Control.Feedback>
                </Form.Group>

                <Button variant="primary" type="submit">
                    Submit
                </Button>

                {!success && (
                    <>
                        <p className="mt-3">
                            Already a member? <Link href="/signin">Sign in</Link>
                        </p>
                    </>
                )}

                {success && (
                    <Alert className="mt-3" variant="success">
                        <strong>Profile successfully created</strong>
                        <br/>
                        Please{" "}
                        <Link href="/signin" passHref>
                            <Alert.Link href="/signin">sign in</Alert.Link>
                        </Link>{" "}
                        to your newly created profile.
                    </Alert>
                )}

                {error && (
                    <Alert className="mt-3" variant="danger">
                        {error}
                    </Alert>
                )}
            </Form>
        </>
    );
}
