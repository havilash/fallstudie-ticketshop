import "bootstrap/dist/css/bootstrap.min.css";
import "../styles/globals.css";

import Layout from "../components/Layout";
import useSession from "../lib/session";

function MyApp({Component, pageProps}) {

    const session = useSession()
    const newPageProps = {
        ...pageProps,
        session
    }

    return (
        <Layout session={session}>
            <Component {...newPageProps} />
        </Layout>
    );
}

export default MyApp;
