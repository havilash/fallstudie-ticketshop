import {useRouter} from "next/router";

const EventPage = () => {
    const router = useRouter();
    const {id} = router.query;

    return <div>Hier kommt die Detailansicht für Event {id}</div>;
};

export default EventPage;
