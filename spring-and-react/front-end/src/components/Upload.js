import React, {Component} from 'react';
import axios from 'axios';
import API from '../common/SharedInfo';
import {Redirect} from "react-router-dom";

class Upload extends Component {
    state = {selectedFile: null, redirect: false}


    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };
    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to='/hospital'/>
        }
    };

    fileChangedHandler = (event) => {
        this.setState({selectedFile: event.target.files[0]});
        console.log((event.target.files[0]));
    };

    uploadHandler = () => {
        const formData = new FormData();
        formData.set('file', this.state.selectedFile);

        axios({
            method: 'post',
            url: API.uploadUrl,
            data: formData
        })
            .then(function (response) {
                //handle success

                console.log("Response: " + response.headers);
            })
            .catch(function (response) {
                //handle error
                console.log(response);
            });


    };

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <h1 className="App-title">{this.state.message}</h1>
                </header>
                <br/>
                <br/>
                <input type="file" onChange={this.fileChangedHandler}/>
                {this.renderRedirect()}
                <button onClick={() => {
                    this.uploadHandler();
                    this.setRedirect();
                }}>Upload!
                </button>
            </div>

        );
    }
}


export default Upload;