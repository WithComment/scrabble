import React, { useState } from 'react';
import styles from './Hand.module.css'
import HandButton from './HandButton';

function Hand({ handLetters, handViewModel }){
    const [redrawSelected, setRedrawSelected] = useState(false);
    const viewModel = handViewModel;
    return (
        <div className={styles.hand}>
            {handLetters.map((letter, index) => {
                console.log('Rendering letter:', letter); // Debugging log
                return (
                    <HandButton handViewModel={viewModel} letter={letter} index={index}/>
                )
            })}
        </div>
    )
}

export default Hand;