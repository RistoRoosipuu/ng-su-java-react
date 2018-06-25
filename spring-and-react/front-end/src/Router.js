import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Home from "./routes/Home";
import HospitalOverlay from "./routes/HospitalOverlay";

const Router = () => (
    <BrowserRouter>
        <Switch>
            <Route exact path="/"  component={Home}/>
            <Route exact path="/hospital"  component={HospitalOverlay}/>
        </Switch>

    </BrowserRouter>
);

export default Router;


