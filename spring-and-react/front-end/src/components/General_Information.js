import React, {Component} from 'react';
import * as axios from "axios/index";
import API from "../common/SharedInfo";


class General_Info extends Component {
    state = {
        count: null,
        diseases: [],
        symptoms: [],
        loading: true,
        error: "",
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


    render() {
        const {loading, error, count, diseases, symptoms} = this.state;

        return (
            <div>
                <button onClick={this.handler}>Refresh API</button>
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
        );
    }
}

export default General_Info;