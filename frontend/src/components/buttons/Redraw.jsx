import React from 'react'
import '../../App.css'

function Redraw({ ButtonViewModel }){
    const viewModel = ButtonViewModel;
    function handleClick(){
        let input = {
            type : 'redraw',
            x : 0,
            y : 0,
            };
        viewModel.sendInput(input);
        }
    return (
        <button className='input-button' id='redraw-button' onClick={handleClick}>
            Redraw
        </button>
    )
}

export default Redraw;