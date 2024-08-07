import React from 'react'
import '../../App.css'

function DontContest({ ButtonViewModel }){
    const viewModel = ButtonViewModel;
    function handleClick(){
        const click = new Audio('/click.mp3');
        click.play();        let input = {
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