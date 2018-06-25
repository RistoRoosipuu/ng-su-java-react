import API from "../common/SharedInfo";
import React, {Component} from 'react';
import * as axios from "axios";

class Date extends Component {

    state = {
        loading: true,
        error: "",
        data: null
    };

    componentDidMount() {
        this.helloDateHandler();

    }

    helloDateHandler = () => {

        this.setState({loading: true});
        return axios.get(API.dateUrl)
            .then((result) => {
                console.log(result);
                this.setState({
                    count: result.count,
                    loading: false,
                    error: false
                });
            })
            .catch(error => {
                console.error("error: ", error);
                this.setState({
                    error: `${error}`,
                    loading: false
                });
            })


    };


    render() {
        const {loading, error, data} = this.state;
        if (loading) {
            return <p>Loading ...</p>;
        }
        if (error) {
            return (
                <p>
                    There was an error loading from the API.{" "}
                    <button onClick={this.helloDateHandler}>Try again</button>
                </p>
            );
        }
        return (
            <div>
                {data}
            </div>
        );
    }

}

export default Date;