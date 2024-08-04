import React, { useState } from 'react';
import styles from './Hand.module.css'
import HandButton from './HandButton';

function Hand({ handLetters, handViewModel }){
    const viewModel = handViewModel;
    const [tileToPlay, setTileToPlay] = useState(null);
    function selectTile(letter){
        setTileToPlay(letter);
    }
    return (
        <div className={styles.hand}>
            {handLetters.map((letter, index) => {
                console.log('Rendering letter:', letter); // Debugging log
                return (
                    <HandButton selectedTile={tileToPlay} handViewModel={viewModel} letter={letter} index={index} select={selectTile}/>
                )
            })}
        </div>
    )
}

export default Hand;