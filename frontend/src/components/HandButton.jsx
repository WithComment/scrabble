import React, { useState } from 'react';
import styles from './Hand.module.css';

function HandButton({ letter, handViewModel, index }) {
    const [selected, setSelected] = useState(false);
    const viewModel = handViewModel;
    const pointMapping = {
        'A': 1,
        'B': 3,
        'C': 3,
        'D': 2,
        'E': 1,
        'F': 4,
        'G': 2,
        'H': 4,
        'I': 1,
        'J': 8,
        'K': 5,
        'L': 1,
        'M': 3,
        'N': 1,
        'O': 1,
        'P': 3,
        'Q': 10,
        'R': 1,
        'S': 1,
        'T': 1,
        'U': 1,
        'V': 4,
        'W': 4,
        'X': 8,
        'Y': 4,
        'Z': 10,
        ' ': 0
    };

    function handleClick(e, letter){
        if (e.type === "click") {
            viewModel.setSelectedLetter(letter);
        } else if (e.type === "contextmenu") {
            if (!selected) {
                viewModel.setRedrawLetter(letter);
                setSelected(true);
            } else {
                viewModel.removeRedrawLetter(letter);
                setSelected(false);
            }
        }
    }

    return(
        <button style={selected ? {border:'1px solid red'} : {}} className={styles.handtile} key={index} onContextMenu={(e, letter) => handleClick(e)} onClick={(e) => handleClick(e, letter)}>
            <p>
                {letter}
            </p>
        <div className={styles.value}>{pointMapping[letter]}</div>
    </button>
    )
};

export default HandButton;