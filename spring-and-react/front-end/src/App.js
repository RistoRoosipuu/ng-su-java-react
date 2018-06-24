import React, {Component} from 'react';
import logo from './logo.svg';
import axios from 'axios';
import './App.css';

class App extends Component {


    state = {selectedFile: null}

    fileChangedHandler = (event) => {
        this.setState({selectedFile: event.target.files[0]});
        console.log((event.target.files[0]));
    };

    uploadHandler = () => {
        const formData = new FormData();
        formData.set('file', this.state.selectedFile);

        axios({
            method: 'post',
            url: '/uploadFile',
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


        /*
        axios.post('/uploadFile', formData);
        */
    };

    /*
    state = {};

    componentDidMount() {
        setInterval(this.hello, 250);
    }

    hello = () => {
        fetch('/hello')
            .then(response => response.text())
            .then(message => {
                this.setState({message: message});
            });
    };

    */

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h1 className="App-title">{this.state.message}</h1>
                </header>
                <br/>
                <br/>
                <input type="file" onChange={this.fileChangedHandler}/>
                <button onClick={this.uploadHandler}>Upload!</button>
            </div>

        );
    }
}

export default App;
