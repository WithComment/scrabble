import React, { useState, useContext } from 'react';
import styles from './Board.module.css';
import './Board.css';
import Tile from './Tile'

function Board({ board, boardViewModel }){
    let viewModel = boardViewModel;
    async function handleClick(e, xCoord, yCoord){
        if (e.type === "click"){
            let input = {
                type : 'lclick',
                x : xCoord,
                y : yCoord,
            }
            viewModel.handleInput(input)
        } else if (e.type === "contextmenu"){
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