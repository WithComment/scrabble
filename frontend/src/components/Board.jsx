import React, { useState } from 'react';
import styles from './Board.module.css';
import './Board.css';

function Board({ board }){
    return (
        <div className={styles.board}>
            {board.map((row, x) =>{
                return (
                <div className={styles.row}>
                    {row.map((tile, y) =>{
                    return (
                        <div className={styles.tile} id={`t${x}-${y}`} key={`${x}, ${y}`}>
                            {tile}
                        </div>
                        )
                    })}
                </div>
                )
            })}
        </div>
    )
}

export default Board;