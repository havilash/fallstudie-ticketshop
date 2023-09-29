import {BASE_URL} from "./api";

const API_URL = `${BASE_URL}/events`;

export async function create(event, session) {
    const url = API_URL;
    const payload = {...event, ownerId: session.user.id};
    const response = await fetch(url, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${session.token}`,
            "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
    });

    if (response.status >= 400) {
        throw new Error("Error occurred during creation of event");
    }

    return await response.json();
}

export async function getEvents(onlyUpcomming) {
    const response = await fetch(API_URL, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    });

    if (response.status >= 400) {
        throw new Error("Error occurred getting events");
    }

    const allEvents = await response.json();

    if (!onlyUpcomming) {
        return allEvents
    }

    const today = new Date().setHours(0, 0, 0, 0);

    return allEvents
        .slice()
        .sort((a, b) => b.date - a.date)
        .filter((event) => Date.parse(event.date) > today);
}
