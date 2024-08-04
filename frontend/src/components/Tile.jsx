import React, { useState } from 'react';
import styles from './Board.module.css'

function Tile({ id, intitialContent, x, y, handleClick}){
    const SERVER_URL = ""
    const xCoord = x;
    const yCoord = y;
    const [content, setContent] = useState(intitialContent);
    return (
        <div className={styles.tile} id={id} key={id} onClick={(e)=>handleClick(e, xCoord, yCoord)} onContextMenu={(e) => handleClick(e, xCoord, yCoord, )}>{content}</div>
    )
}

export default Tile;