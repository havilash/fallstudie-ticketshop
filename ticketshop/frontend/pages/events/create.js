import {useState} from "react";
import {Alert, Button, Form} from "react-bootstrap";

import {checkHasAnyOfScopes} from "@lib/session";
import {create as createEvent} from "@lib/event.api";
import {useRouter} from "next/router";

const Title = () => <h2 className="mt-5">Create event</h2>;
const privileges = ["ADMIN", "EVENT_MANAGEMENT"]

export default function CreateEventPage({session}) {
    //useRedirectIfNotAllowed(privileges, session)

    const router = useRouter();

    const [formData, setFormData] = useState({
        name: "",
        description: "",
        date: new Date().toISOString().substring(0, 10),
    });
    const [validated, setValidated] = useState(false);
    const [error, setError] = useState();

    const onInputChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const submit = async (event) => {
        const form = event.currentTarget;
        event.preventDefault();
        event.stopPropagation();

        if (form.checkValidity() === true) {
            try {
                setError(null);
                const createdEvent = await createEvent(formData, session);
                router.push("/events/" + createdEvent.id)
            } catch (error) {
                console.error(error);
                setError("An error occurred during event creation");
            }
        }

        setValidated(true);
    };

    if (!checkHasAnyOfScopes(privileges, session)) {
        return (
            <>
                <Title/>
                <Alert className="mt-3" variant="danger">
                    Only Administrator or Event Manager can create new events.
                </Alert>
            </>
        );
    }

    return (
        <>
            <Title/>
            <p>Create a new event.</p>
            <Form className="mt-3" noValidate validated={validated} onSubmit={submit}>
                <Form.Group className="mb-3" controlId="formBasicName">
                    <Form.Label>Name *</Form.Label>
                    <Form.Control
                        required
                        name="name"
                        type="text"
                        placeholder="Enter event name"
                        onChange={onInputChange}
                        value={formData.name}
                    />
                    <Form.Control.Feedback type="invalid">
                        Please choose an event name.
                    </Form.Control.Feedback>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicDescription">
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        name="description"
                        as="textarea"
                        rows={3}
                        onChange={onInputChange}
                        placeholder="Description"
                        value={formData.description}
                    />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicDate">
                    <Form.Label>Date</Form.Label>
                    <Form.Control
                        required
                        type="date"
                        name="date"
                        placeholder="Event date"
                        value={formData.date}
                        onChange={onInputChange}
                    />
                    <Form.Control.Feedback type="invalid">
                        Please choose an event date.
                    </Form.Control.Feedback>
                </Form.Group>

                <Button variant="primary" type="submit">
                    Submit
                </Button>
            </Form>

            {error && (
                <Alert className="mt-3" variant="danger">
                    {error}
                </Alert>
            )}
        </>
    );
};
