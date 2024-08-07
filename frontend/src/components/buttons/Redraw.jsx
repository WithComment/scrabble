import React from 'react'
import '../../App.css'

function Redraw({ ButtonViewModel }){
    const viewModel = ButtonViewModel;
    function handleClick(){
        const click = new Audio('/click.mp3');
        click.play();
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