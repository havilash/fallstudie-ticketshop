import Link from "next/link";
import EventCardList from "@components/EventCardList";


export default function Home({session}) {


    return (
        <>
            <h2 className="mt-5 mb-4">Upcoming events</h2>


            <EventCardList session={session} upcomming={true}/>


            <p className="mt-3">
                <Link href="/events">Show all events</Link>
            </p>


        </>
    );
}
