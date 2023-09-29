import Link from "next/link";
import {NavDropdown} from "react-bootstrap";

const NavigationDropDownLink = ({href, children}) => {
    return (
        <Link href={href} passHref>
            <NavDropdown.Item href={href}>{children}</NavDropdown.Item>
        </Link>
    );
};

export default NavigationDropDownLink;
