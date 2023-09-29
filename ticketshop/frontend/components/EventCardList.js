import {useEffect, useState} from "react";
import {Alert} from "react-bootstrap";
import {getEvents} from "@lib/event.api";
import {EventCard} from "@components/EventCard";
import {checkHasAnyOfScopes} from "@lib/session";

export default function EventCardList({session, upcomming}) {

    const [loaded, setLoaded] = useState(false);
    const [error, setError] = useState(null);
    const [events, setEvents] = useState([]);


    useEffect(() => {
        const loadEvents = async () => {
            try {
                setError(null);
                const upcomingEvents = await getEvents(upcomming);
                setEvents(upcomingEvents.slice(0, 3));
            } catch (error) {
                console.error(error);
                setError("Error loading upcoming events");
            }

            setLoaded(true);
        };

        loadEvents();
    }, [upcomming]);

    const isAdmin = checkHasAnyOfScopes(["ADMIN"], session)

    return (<>

        {loaded && !error && events.length < 1 && (
            <Alert className="mt-3" variant="info">
                {upcomming ? "There are currently no events planned. Come back soon!" : "There are  no events available. Come back soon!"}
            </Alert>
        )}
        {events.map(event => {
                const isOwner = event.ownerId === session.user?.id

                return (
                    <EventCard key={event.id} {...event} editable={isOwner || isAdmin}/>
                );
            }
        )}


        {error && <Alert className="mt-3" variant="danger">
            {error}
        </Alert>}

    </>)
}