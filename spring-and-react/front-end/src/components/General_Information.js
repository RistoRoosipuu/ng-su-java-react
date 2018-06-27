import React, {Component} from 'react';
import * as axios from "axios/index";
import API from "../common/SharedInfo";
import Redirect from "react-router-dom/es/Redirect";


class General_Info extends Component {
    state = {
        count: null,
        diseases: [],
        symptoms: [],
        loading: true,
        error: "",
        redirect: false
    };

    componentDidMount() {
        //this.generalInfoHandler();

        this.handler();

    }

    handler = () => {
        var count, symptoms, diseases = []
        this.generalInfoHandler().then(response => {
            count = response.data.count;
            symptoms = response.data.symptoms;
            diseases = response.data.diseases;
            this.setState({count: count, symptoms: symptoms, diseases: diseases})
        })
    };

    generalInfoHandler = () => {

        //this.setState({loading: true});

        return axios.get(API.generalInfoUrl)
            .then(data => {
                console.log(data);
                return data;
            })
            .catch((error) => {
                console.log(error.message)
            });


    };

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };
    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to='/'/>
        }
    };

    render() {
        const {count, diseases, symptoms} = this.state;

        return (
            <div className="card bg-dark text-white h-100">
                {this.renderRedirect()}
                <button className="btn btn-dark btn-outline-danger" onClick={this.setRedirect}>Back to Upload</button>
                <div className="card-body">

                    <div>
                        <h1>Number Of Symptoms known by the API </h1>
                        <p>{count}</p>
                    </div>
                    <div>
                        <h1>Diseases With The Most Symptoms</h1>
                        <ul>
                            {diseases.map(function (name, index) {
                                return <li key={index}>{name}</li>;
                            })}
                        </ul>
                    </div>
                    <div>
                        <h1>Most Reccuring Symptoms </h1>
                        <ul>
                            {symptoms.map(function (name, index) {
                                return <li key={index}>{name}</li>;
                            })}
                        </ul>
                    </div>
                </div>
                <button className="btn btn-success" onClick={this.handler}>Refresh API</button>
            </div>
        );
    }
}

export default General_Info;