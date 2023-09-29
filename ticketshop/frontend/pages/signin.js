import {login} from "@lib/api"
import {useRedirectToHome} from "@lib/session"
import {useState} from "react"
import {useRouter} from "next/router";
import {Alert, Button, Form} from "react-bootstrap";


const defaultModel = {
    email: "",
    password: ""
}


export default function LoginPage({session}) {
    useRedirectToHome(session)

    const router = useRouter()
    const [error, setError] = useState("");
    const [isLoading, setIsLoading] = useState(false)
    const [formData, setFormData] = useState(defaultModel)
    const [validated, setValidated] = useState(false);

    const handleChange = (e) => {
        const name = e.target.name
        const value = e.target.value?.trim()

        setFormData({
            ...formData,
            [name]: value
        })
    }

    const submit = async (event) => {
        const form = event.currentTarget;
        event.preventDefault();
        event.stopPropagation();
        setIsLoading(true)

        if (form.checkValidity() === true) {
            try {
                const resp = await login(formData)
                session.signIn(resp)
                router.push("/");

            } catch (error) {
                console.error(error);
                setError("An error occurred during authentication")
            }
        }
        setIsLoading(false)
        setValidated(true);
    };


    return session.user ? null : (
        <>
            <h2 className="mt-5">Sign into your Profile</h2>
            <Form className="mt-3" noValidate validated={validated} onSubmit={submit}>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>Email *</Form.Label>
                    <Form.Control
                        required
                        name="email"
                        type="email"
                        placeholder="Enter email"
                        onChange={handleChange}
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
                        onChange={handleChange}
                        value={formData.password}
                    />
                    <Form.Control.Feedback type="invalid">
                        Please choose a password.
                    </Form.Control.Feedback>
                </Form.Group>

                <Button variant="primary" type="submit" disabled={isLoading}>
                    {isLoading ? "Loading..." : "Submit"}
                </Button>

                {error ? (
                    <Alert className="mt-3" variant="danger">
                        {error}
                    </Alert>
                ) : (
                    ""
                )}
            </Form>
        </>
    );
}
