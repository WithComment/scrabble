import React, { useState, useEffect } from 'react';
import styles from './Board.module.css';
import './Board.css';
import Tile from './Tile'

function Board({ board, boardViewModel }){
    const [clickSound, setClickSound] = useState(null);
    useEffect(() => {
        const audio = new Audio('/click.mp3');
        audio.load();
        setClickSound(audio);
    }, []);

    let viewModel = boardViewModel;
    async function handleClick(e, xCoord, yCoord){
        if (clickSound) {
            clickSound.currentTime = 0;
            clickSound.play();
        }
        if (e.type === "click"){
            let input = {
                type : 'lclick',
                x : xCoord,
                y : yCoord,
            }
            viewModel.handleInput(input)
        } else if (e.type === "contextmenu"){
            e.preventDefault();
            let input = {
                type : 'rclick',
                x : xCoord,
                y : yCoord,
            }
            viewModel.handleInput(input)
        }
    }

    return (
        <div className={styles.board}>
            {board.map((row, y) =>{
                return (
                <div className={styles.row}>
                    {row.map((tile, x) =>{
                    return (
                        <Tile id={`t${x}-${y}`} content={tile} x={x} y={y} handleClick={handleClick}/>
                        )
                    })}
                </div>
                )
            })}
        </div>
    )
}

export default Board;