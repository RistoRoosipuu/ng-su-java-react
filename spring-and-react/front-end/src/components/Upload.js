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
                                    {this.renderRedirect()}
                                    <button className="float-center" onClick={() => {
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
                                                    <li>Press the Forward button for</li>
                                                    <li>Assignment 1 and 2</li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </div>
                                    <button className="float-right" onClick={this.setRedirect}>After uploading, press to go forward</button>
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