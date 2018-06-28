import React, {Component} from 'react';
import axios from 'axios';
import API from '../common/SharedInfo';
import {Redirect} from "react-router-dom";

class Upload extends Component {
    state = {selectedFile: null, redirectHospital: false, redirectGame: false}

    setRedirectHospital = () => {
        this.setState({
            redirectHospital: true
        })
    };

    setRedirectGame = () => {
        this.setState({
            redirectGame: true
        })
    };
    renderRedirectToHospital = () => {
        if (this.state.redirectHospital) {
            return <Redirect to='/hospital'/>
        }
    };

    renderRedirectToGame = () => {
        if (this.state.redirectGame) {
            return <Redirect to='/game'/>
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
                console.log("Response: " + response.headers);
            })
            .catch(function (response) {
                console.log(response);
            });
    };

    render() {
        return (
            <div className="container">
                <header id="main-header">
                    <div className="row no-gutters">
                        <div className="col-lg-12 col-md-12 col-sm-6 p-4 bg-dark">
                        </div>
                    </div>
                    <div className="row no-gutters">
                        <div className="col-lg-12 col-md-12 col-sm-6">
                            <div id="uploadCard" className="bg-secondary text-white h-100">
                                <div className="p-4 ">
                                    <input type="file" accept=".csv" onChange={this.fileChangedHandler}/>
                                    {this.renderRedirectToHospital()} {this.renderRedirectToGame()}
                                    <button className="btn-success float-center" onClick={() => {
                                        this.uploadHandler()
                                    }}>Press to Upload file :)
                                    </button>
                                </div>
                                <div className="p-5 bg-dark text-white">
                                    <div>
                                        <ul className="list-unstyled">
                                            <li>Instructions
                                                <ul>
                                                    <li>Load CSV file by pressing Browse</li>
                                                    <li>Press Upload button to submit it</li>
                                                    <li>Press the other buttons for navigation</li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </div>
                                    <div>
                                        <button className="btn mx-1 float-right" onClick={this.setRedirectGame}>Go to
                                            Assignment 3
                                        </button>
                                    </div>
                                    <div>
                                        <button className="btn mx-1 float-right" onClick={this.setRedirectHospital}>Go
                                            to
                                            Assignment 1/2
                                        </button>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </header>
            </div>
        );
    }
}


export default Upload;