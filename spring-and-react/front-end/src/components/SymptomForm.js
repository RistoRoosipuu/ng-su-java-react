import React, {Component} from 'react';
import axios from "axios/index";
import API from "../common/SharedInfo";


class SymptomForm extends Component {

    constructor() {
        super();
        this.state = {
            name: '',
            symptoms: [{name: ''}],
            ourDate: '',
            possibleDiseases: ['None Known']
        };
    }

    componentDidMount() {
    }

    test() {
        const knownSymptoms = this.state.possibleDiseases;
        const symptomsFromState = this.state.symptoms;
        const symptomsToBeSent = [];

        symptomsFromState.forEach(i => {
            symptomsToBeSent.push(i.name);
        });

        axios.post(API.postSymptomsUrl,
            {
                'possibleSymptoms': symptomsToBeSent
            }, {
                "headers": {
                    'Content-Type': 'application/json',
                }
            }).then((response) => {
            this.compareTwo(knownSymptoms, response.data.possibleDiseases);
        })
            .catch((error) => {
                console.log("axios error:", error);
            });
    }

    compareTwo(knownSymptoms, possibleDiseases) {
        if (JSON.stringify(knownSymptoms) !== JSON.stringify(possibleDiseases)) {
            this.setState({
                possibleDiseases: possibleDiseases
            })
        }
    }

    handleSymptomNameChange = (idx) => (evt) => {
        const newSymptoms = this.state.symptoms.map((symptom, sidx) => {
            if (idx !== sidx) return symptom;
            return {...symptom, name: evt.target.value};
        });
        this.setState({symptoms: newSymptoms});
    };

    handleAddSymptom = () => {
        this.setState({symptoms: this.state.symptoms.concat([{name: ''}])});
    };

    handleRemoveSymptom = (idx) => () => {
        this.setState({symptoms: this.state.symptoms.filter((s, sidx) => idx !== sidx)});
    };

    render() {
        return (
            <div className="card bg-dark text-white h-100">
                <div className="card-body">
                    <h4>Symptoms You wish to Post about</h4>
                    {this.state.symptoms.map((symptom, idx) => (
                        <div key={`${idx}`}>
                            <input
                                type="text"
                                placeholder={`Sympton #${idx + 1} name`}
                                value={symptom.name}
                                onChange={this.handleSymptomNameChange(idx)}
                            />
                            <button type="button" className="btn-warning" onClick={this.handleRemoveSymptom(idx)}>Remove Sympton</button>
                        </div>
                    ))}
                    <button type="button" className="btn mt-1" onClick={this.handleAddSymptom}>Add Sympton</button>
                    {this.state.possibleDiseases.map((disease, index) => (
                        <p key={`${index}`}>{"Name of the Disease: " + disease}</p>
                    ))}
                </div>
                <button className="btn btn-success" onClick={() => {
                    this.test()
                }}> Submit to Spring API
                </button>
            </div>
        )
    }
}

export default SymptomForm;