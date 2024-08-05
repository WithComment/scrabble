import React from 'react';
import styles from './Board.module.css'

function Tile({ id, content, x, y, handleClick}){
    const xCoord = x;
    const yCoord = y;
    return (
        <div className={styles.tile} id={id} key={id} onClick={(e)=>handleClick(e, xCoord, yCoord)} onContextMenu={(e) => handleClick(e, xCoord, yCoord, )}>{content}</div>
    )
}

export default Tile;