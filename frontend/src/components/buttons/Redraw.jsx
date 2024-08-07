import React, { useState, useEffect} from 'react'
import '../../App.css'

function Redraw({ ButtonViewModel }){
    const [clickSound, setClickSound] = useState(null);
    useEffect(() => {
        const audio = new Audio('/click.mp3');
        audio.load();
        setClickSound(audio);
    }, []);
    const viewModel = ButtonViewModel;
    function handleClick(){
        if (clickSound) {
            clickSound.currentTime = 0;
            clickSound.play();
        }
        let input = {
            type : 'redraw',
            x : 0,
            y : 0,
            };
        viewModel.handleInput(input);
        }
    return (
        <button className='input-button' id='redraw-button' onClick={handleClick}>
            Redraw Selected
        </button>
    )
}

export default Redraw;