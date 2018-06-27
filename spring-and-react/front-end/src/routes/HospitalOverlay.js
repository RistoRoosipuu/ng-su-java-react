import React from 'react';

import SymptomForm from '../components/SymptomForm';
import General from '../components/General_Information';

export default () => {
    return (
        <div className="container">
            <div className="row no-gutters">
                <div className="col-lg-6">
                    <General/>
                </div>
                <div className="col-lg-6">
                    <SymptomForm/>
                </div>
            </div>


        </div>

    );
}