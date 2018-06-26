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
            possibleDiseases: []
        };
    }

    componentDidMount() {
    }

    test() {
        const knownSymptoms = this.state.possibleDiseases;
        axios.post(API.postSymptomsUrl,
            {
                'name': "someNameFromTheEather"
            }, {
                "headers": {
                    'Content-Type': 'application/json',
                }
            }).then((response) => {
            console.log("Full response: " + JSON.stringify(response.data.possibleDiseases));


            this.compareTwo(knownSymptoms, response.data.possibleDiseases);
            //this.setState({possibleDiseases: everything})
            //this.forceUpdate();
            //console.log(allDiseases);
        })
            .catch((error) => {
                console.log("axios error:", error);
            });
    }

    compareTwo(knownSymptoms, possibleDiseases) {

        if (JSON.stringify(knownSymptoms) != JSON.stringify(possibleDiseases)) [
            this.setState({
                possibleDiseases: possibleDiseases
            })
        ]
    }


    handleSymptomNameChange = (idx) => (evt) => {
        const newSymptoms = this.state.symptoms.map((symptom, sidx) => {
            if (idx !== sidx) return symptom;
            return {...symptom, name: evt.target.value};
        });

        this.setState({symptoms: newSymptoms});
    }

    /**
     handleSubmit = (evt) => {
        const {name, symptoms} = this.state;
        //alert(`Post?: ${name} with ${symptoms.length} symptoms with ${symptoms[2].name}`);

        var someData = this.handlePostSymptoms();


        alert(someData);
    }
     **/

    handleAddSymptom = () => {
        this.setState({symptoms: this.state.symptoms.concat([{name: ''}])});
    }

    handleRemoveSymptom = (idx) => () => {
        this.setState({symptoms: this.state.symptoms.filter((s, sidx) => idx !== sidx)});
    }

    /**
     handler = () => {
        var ourDate = '';
        this.generalInfoHandler().then(data => {
            ourDate = data.date;
            this.setState({ourDate: ourDate})
        })

        console.log(this.state.ourDate)
    };

     generalInfoHandler = () => {

        //this.setState({loading: true});

        return axios.get(API.postSymptomsUrl)
            .then(data => {
                console.log(data);
                return data;
            })
            .catch((error) => {
                console.log(error.message)
            });
    };
     **/

    render() {
        return (
            <form>
                <h4>Symptoms You wish to Post about</h4>
                {this.state.symptoms.map((symptom, idx) => (
                    <div key={`${idx}`}>
                        <input
                            type="text"
                            placeholder={`Sympton #${idx + 1} name`}
                            value={symptom.name}
                            onChange={this.handleSymptomNameChange(idx)}
                        />
                        <button type="button" onClick={this.handleRemoveSymptom(idx)}>Remove Sympton</button>
                    </div>
                ))}
                <button type="button" onClick={this.handleAddSymptom}>Add Sympton</button>
                <button onClick={this.test()}> Submit to Spring API</button>
                {this.state.possibleDiseases.map((disease, index) => (
                    <p>{"Name of the Disease: " + disease}</p>
                ))}
                {console.log("In render: " + this.state.possibleDiseases)}
            </form>
        )
    }
}

export default SymptomForm;