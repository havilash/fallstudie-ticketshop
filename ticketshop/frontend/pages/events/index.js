import EventCardList from "@components/EventCardList";
import {checkHasAnyOfScopes} from "@lib/session";
import Link from "next/link";

export default function EventsPage({session}) {

    const isAdminOrEventmanager = checkHasAnyOfScopes(["ADMIN", "EVENT_MANAGEMENT"], session)

    return (

        <>
            <h2 className="mt-5 mb-4">All Events</h2>
            <EventCardList session={session}/>

            {isAdminOrEventmanager && <p className="mt-3">
                <Link href="/events/create">Create a new Event</Link>
            </p>}
        </>
    );
};

