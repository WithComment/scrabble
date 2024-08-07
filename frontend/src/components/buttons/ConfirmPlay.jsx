import React from 'react'
import '../../App.css'

function ConfirmPlay({ ButtonViewModel }){
    const viewModel = ButtonViewModel;
    function handleClick(){
        const click = new Audio('/click.mp3');
        click.play();
        let input = {
            type : 'confirm-play',
            x : 0,
            y : 0,
            };
        viewModel.handleInput(input);
        };
    return (
        <button className='input-button' id='confirm-play-button' onClick={handleClick}>
            End Turn
        </button>
    )
}

export default ConfirmPlay;