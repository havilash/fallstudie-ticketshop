import {Alert} from "react-bootstrap";
import React from "react";

export default function () {
    return <Alert className="mt-3" variant="danger">
        Oooops! It seems as you are not allowed to do this. If you should, please Log out and log in with apropriate
        credentials or contact your administrator.
    </Alert>
}