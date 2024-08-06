import React, { useState, useRef } from 'react';
import styles from './Hand.module.css';

function HandButton({ letter, handViewModel, index, select, selectedTile }) {
    const [selectedForRedraw, setSelectedForRedraw] = useState(false);
    const [selectedForPlay, setSelectedForPlay] = useState(false);
    const ref = useRef();
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

    function handleClick(e){
        console.log(letter);
        if (e.type === "click") {
            setSelectedForRedraw(false);
            setSelectedForPlay(true);
            select(ref);
            viewModel.setSelectedLetter(letter);
        } else if (e.type === "contextmenu") {
            e.preventDefault()
            if (!selectedForRedraw) {
                viewModel.addToRedraws(letter);
                setSelectedForRedraw(true);
                setSelectedForPlay(false);
            } else {
                viewModel.removeRedrawLetter(letter);
                setSelectedForRedraw(false);
            }
        }
    }

    return(
        <button ref={ref} style={selectedForRedraw ? {border:'3px solid red'} : (selectedTile === ref) ? {border:'3px solid #32de84'} :  {}} className={styles.handtile} key={index} onContextMenu={(e) => handleClick(e)} onClick={(e) => handleClick(e)}>
            <p>
                {letter}
            </p>
        <div className={styles.value}>{pointMapping[letter]}</div>
    </button>
    )
};

export default HandButton;