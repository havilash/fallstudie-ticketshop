import Link from "next/link";
import {Container, Nav, Navbar} from "react-bootstrap";

import NavigationLink from "./NavigationLink";

export default function Navigation({session}) {
    const {ready, logOut, user} = session;
    const loggedIn = ready && user !== null;

    return (
        <Navbar bg="dark" variant="dark">
            <Container>
                <Link href="/" passHref>
                    <Navbar.Brand>Home</Navbar.Brand>
                </Link>

                <Nav className="me-auto">
                    <NavigationLink href="/events">Events</NavigationLink>
                    <NavigationLink href="/events/create"> Create a new event </NavigationLink>
                </Nav>
                <Nav>
                    {!loggedIn && <NavigationLink href="/signin">Sign in</NavigationLink>}
                    {!loggedIn && <Navbar.Text>/</Navbar.Text>}
                    {!loggedIn && <NavigationLink href="/signup">Sign up</NavigationLink>}
                    {loggedIn && (
                        <NavigationLink
                            href="/"
                            onClick={logOut}
                        >
                            Sign out
                        </NavigationLink>
                    )}
                </Nav>
            </Container>
        </Navbar>
    );
};

