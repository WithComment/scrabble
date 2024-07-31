import React from 'react';
import '../App.css'

function Leaderboard({ players }){
    return (
        <div className='leaderboard'>
            {players.map((player, index) => {
                return (
                    <div key={index}>{player.name}: {player.score}</div>
                )
            })}
        </div>
    )
}

export default Leaderboard;