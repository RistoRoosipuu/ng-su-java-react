import React from 'react';

import SymptomForm from '../components/SymptomForm';
import General from '../components/General_Information';

export default () => {
    return (
        <div>
            <General/>
            <SymptomForm/>
        </div>

    );
}