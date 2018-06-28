import API from "../common/SharedInfo";
import React, {Component} from 'react';
import axios from 'axios';

class Game extends Component {

    state = {
        symptomShownToUser: '',
        //gameObject: null
        diseaseName: '',
        symptomsItHas: [],
        symptomsChosenCorrectly: [],
        allSymptoms: [],
        tempAllSymptoms: []
    };


    startInteraction = () => {
        var symptomShownToUser = '';
        var symptomsItHas, symptomsChosenCorrectly, allSymptoms, tempAllSymptoms;
        var diseaseName = '';
        this.getFromInteraction().then(response => {
            symptomShownToUser = response.data.symptomShownToUser;
            diseaseName = response.data.diseaseName;
            symptomsItHas = response.data.symptomsItHas;
            symptomsChosenCorrectly = response.data.symptomsChosenCorrectly;
            allSymptoms = response.data.allSymptoms;
            tempAllSymptoms = response.data.tempAllSymptoms;


            //gameObject = response.data;
            //symptoms = response.data.symptoms;
            //diseases = response.data.diseases;
            console.log("Inside setter: " + symptomShownToUser);
            console.log("Disease name: " + diseaseName);
            this.setState({
                symptomShownToUser: symptomShownToUser,
                //gameObject: gameObject
                diseaseName: diseaseName,
                symptomsItHas: symptomsItHas,
                symptomsChosenCorrectly: symptomsChosenCorrectly,
                allSymptoms: allSymptoms,
                tempAllSymptoms: tempAllSymptoms
            })
        });


    };

    getFromInteraction = () => {

        //this.setState({loading: true});

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
        const tempAllSymptoms = this.state.tempAllSymptoms;
        axios.post(apiPath,
            {
                //'possibleSymptoms': payload
                //'possibleSymptoms': symptomsToBeSent
                //another
                //payload
                'diseaseName': diseaseName,
                'symptomShownToUser': symptomShownToUser,
                'symptomsItHas': symptomsItHas,
                'symptomsChosenCorrectly': symptomsChosenCorrectly,
                'allSymptoms': allSymptoms,
                'tempAllSymptoms': tempAllSymptoms


            }, {
                "headers": {
                    'Content-Type': 'application/json',
                }
            }).then((response) => {
            console.log("From Agree Get symptom: " + response.data.symptomShownToUser);
            console.log("Disease name: " + response.data.diseaseName);

            this.setState({
                symptomShownToUser: response.data.symptomShownToUser,
                //gameObject: gameObject
                diseaseName: response.data.diseaseName,
                symptomsItHas: response.data.symptomsItHas,
                symptomsChosenCorrectly: response.data.symptomsChosenCorrectly,
                allSymptoms: response.data.allSymptoms,
                tempAllSymptoms: response.data.tempAllSymptoms
            })

        })
            .catch((error) => {
                console.log("axios error:", error);
            });
    }


    render() {
        const symptomShownToUser = this.state.symptomShownToUser;
        const diseaseName = this.state.diseaseName;

        let result;

        if (symptomShownToUser !== 'success') {
            result = <div>
                <h1>Is this the right symptom: {symptomShownToUser} ?</h1>
            </div>
        } else {
            result = <div>
                <h1>Disease found: It was: {diseaseName}</h1>
            </div>
        }
        return (
            <div>
                <p>From Game</p>
                <button onClick={() => {
                    this.startInteraction()
                }}>Fetch a new Disease
                </button>
                <button onClick={() => this.agree(API.agreeUrl)}>Yes</button>
                <button onClick={() => this.agree(API.denyUrl)}>No</button>
                {result}
            </div>
        )
    }


}

export default Game;