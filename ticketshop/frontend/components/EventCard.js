import {Card} from "react-bootstrap";
import Link from "next/link";

export function EventCard(props) {
    return <Card className="mb-3">
        <Card.Body>
            <Card.Title>{props.name}</Card.Title>
            <Card.Subtitle className="mt-2 text-muted">
                {new Date(props.date).toLocaleDateString("de-CH")}
            </Card.Subtitle>
            <Card.Text className="mt-2">{props.description}</Card.Text>
            <Link href={`events/${props.id}`} passHref>
                <Card.Link href="#">Details</Card.Link>
            </Link>
            {props.editable &&
                <Link href={`events/${props.id}/edit`} passHref>
                    <Card.Link href="#">Edit</Card.Link>
                </Link>}
        </Card.Body>
    </Card>;
}