import React from 'react'
import '../../App.css'

function DontContest({ ButtonViewModel }){
    const viewModel = ButtonViewModel;
    function handleClick(){
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