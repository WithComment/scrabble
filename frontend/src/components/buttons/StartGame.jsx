import React from 'react'
import '../../App.css'

function StartGame({ ButtonViewModel }){
    const viewModel = ButtonViewModel;
    function handleClick(){
        const click = new Audio('/click.mp3');
        click.play();
        let input = {
            type : 'start',
            x : 0,
            y : 0,
            };
        viewModel.handleInput(input);
        }
    return (
        <button className='input-button' id='start-button' onClick={handleClick}>
            Start Game
        </button>
    )
}

export default StartGame;