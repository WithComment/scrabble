import React from 'react'
import '../../App.css'

function Contest({ ButtonViewModel }){
    const viewModel = ButtonViewModel;
    function handleClick(){
        let input = {
            type : 'contest',
            x : 0,
            y : 0,
            };
        viewModel.sendInput(input);
        };
    return (
        <button className='input-button' id='contest-button' onClick={handleClick}>
            Contest Last Turn
        </button>
    )
}

export default Contest;