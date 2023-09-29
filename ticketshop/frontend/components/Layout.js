import Header from "./Header";
import {Container} from "react-bootstrap";

export default function Layout({children, ...props}) {
    return (
        <>
            <Header {...props} />
            <Container>{children}</Container>
        </>
    );
}
