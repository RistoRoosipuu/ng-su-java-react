import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Home from "./routes/Home";
import HospitalOverlay from "./routes/HospitalOverlay";
import GameOverlay from './routes/GameOverlay';

const Router = () => (
    <BrowserRouter>
        <Switch>
            <Route exact path="/" component={Home}/>
            <Route exact path="/hospital" component={HospitalOverlay}/>
            <Route exact path="/game" component={GameOverlay}/>
        </Switch>

    </BrowserRouter>
);

export default Router;


