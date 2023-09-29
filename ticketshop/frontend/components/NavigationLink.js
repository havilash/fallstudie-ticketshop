import Link from "next/link";
import {Nav} from "react-bootstrap";
import {useRouter} from "next/router";

const NavigationLink = ({href, onClick, children}) => {
    const router = useRouter();
    const isActive = router.pathname == href;
    return (
        <Link href={href} passHref>
            <Nav.Link href={href} onClick={onClick} active={isActive}>
                {children}
            </Nav.Link>
        </Link>
    );
};

export default NavigationLink;
