import React, {useState, useEffect} from 'react'
import '../../App.css'

function DontContest({ ButtonViewModel }){
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
            type : 'no-contest',
            x : 0,
            y : 0,
            };
        viewModel.handleInput(input);
        };
    return (
        <button className='input-button' id='dont-contest-button' onClick={handleClick}>
            Do Nothing
        </button>
    )
}

export default DontContest;