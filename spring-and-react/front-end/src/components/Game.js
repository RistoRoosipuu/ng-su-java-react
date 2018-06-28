import API from "../common/SharedInfo";
import React, {Component} from 'react';
import axios from 'axios';
import Redirect from "react-router-dom/es/Redirect";

class Game extends Component {

    state = {
        symptomShownToUser: '',
        diseaseName: '',
        symptomsItHas: [],
        symptomsChosenCorrectly: [],
        allSymptoms: [],
        redirect: false
    };

    startInteraction = () => {
        var symptomShownToUser = '';
        var symptomsItHas, symptomsChosenCorrectly, allSymptoms;
        var diseaseName = '';
        this.getFromInteraction().then(response => {
            symptomShownToUser = response.data.symptomShownToUser;
            diseaseName = response.data.diseaseName;
            symptomsItHas = response.data.symptomsItHas;
            symptomsChosenCorrectly = response.data.symptomsChosenCorrectly;
            allSymptoms = response.data.allSymptoms;

            console.log("Inside setter: " + symptomShownToUser);
            console.log("Disease name: " + diseaseName);
            this.setState({
                symptomShownToUser: symptomShownToUser,
                //gameObject: gameObject
                diseaseName: diseaseName,
                symptomsItHas: symptomsItHas,
                symptomsChosenCorrectly: symptomsChosenCorrectly,
                allSymptoms: allSymptoms,
            })
        });
    };

    getFromInteraction = () => {
        return axios.get(API.startInteractionUrl)
            .then(data => {
                console.log(data);
                return data;
            })
            .catch((error) => {
                console.log(error.message)
            });
    };

    agree(apiPath) {
        const diseaseName = this.state.diseaseName;
        const symptomShownToUser = this.state.symptomShownToUser;
        const symptomsItHas = this.state.symptomsItHas;
        const symptomsChosenCorrectly = this.state.symptomsChosenCorrectly;
        const allSymptoms = this.state.allSymptoms;
        axios.post(apiPath,
            {
                'diseaseName': diseaseName,
                'symptomShownToUser': symptomShownToUser,
                'symptomsItHas': symptomsItHas,
                'symptomsChosenCorrectly': symptomsChosenCorrectly,
                'allSymptoms': allSymptoms
            }, {
                "headers": {
                    'Content-Type': 'application/json',
                }
            }).then((response) => {
            console.log("From Agree Get symptom: " + response.data.symptomShownToUser);
            console.log("Disease name: " + response.data.diseaseName);

            this.setState({
                symptomShownToUser: response.data.symptomShownToUser,
                diseaseName: response.data.diseaseName,
                symptomsItHas: response.data.symptomsItHas,
                symptomsChosenCorrectly: response.data.symptomsChosenCorrectly,
                allSymptoms: response.data.allSymptoms,
            })
        })
            .catch((error) => {
                console.log("axios error:", error);
            });
    }

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
        const symptomShownToUser = this.state.symptomShownToUser;
        const diseaseName = this.state.diseaseName;
        let result;
        if (symptomShownToUser !== 'success' && symptomShownToUser !== '') {
            result = <div>
                <h2 className="d-inline">Could this symptom be in the disease: </h2><h4
                className="d-inline">{symptomShownToUser}</h4>
            </div>
        } else {
            if (diseaseName !== '') {
                result = <div>
                    <h2 className="d-inline">Disease found: It was: </h2><h4 className="d-inline text-success">{diseaseName}</h4>
                </div>
            } else {
                result = <div>
                    <h2>Fetch a Symptom from the API :)</h2>
                </div>
            }
        }
        return (
            <div className="card bg-dark text-white h-100">
                {this.renderRedirect()}
                <button className="btn btn-dark btn-outline-danger" onClick={this.setRedirect}>Back to Upload</button>
                <div className="card-body">
                    {result}
                    <div>
                        <button className="btn mx-2 form-inline float-right"
                                onClick={() => this.agree(API.agreeUrl)}>Yes
                        </button>
                    </div>
                    <div>
                        <button className="btn form-inline float-right" onClick={() => this.agree(API.denyUrl)}>No
                        </button>
                    </div>
                </div>
                <button className="btn btn-success" onClick={() => {
                    this.startInteraction()
                }}> Fetch a new Disease
                </button>
            </div>
        )
    }
}

export default Game;