import React, { useState } from 'react';
import { Navigate } from 'react-router-dom';
import ViewModel from '../ViewModel';

function Login(){
    const submitUrl = 'http://localhost:8080/game/create/'
    const [name, setName] = useState('Username');
    const [gameId, setGameId] = useState(0);
    const [submitted, setSubmitted] = useState(false);
    const [data, setData] = useState({});
    function handleNameChange(e){
        setName(e.target.value);
    }

    function handleIdChange(e){
        setGameId(e.target.value)
    }

    async function handleCreateGameSubmit(e){
        e.preventDefault();
        console.log('test')
        console.log(name + gameId);
        let request = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                playerNames: [name],
            })
        }
        let response = await fetch(submitUrl, request);
        response = await response.json();
        console.log(response);
        let newGameId = response.gameId;
        let newPlayerId = response.players[0].id;
        let hand = response.players[0].inventory.map((letter) => letter.letter);
        let newBoard = []
        for (let i = 0; i < 15; i++) {
            let row = []
            for (let j = 0; j < 15; j++) {
            row.push("__")
            }
            newBoard.push(row)
        };
        let leaderboard = [{name: name, score: 0}];

        let tempData = {
            leaderboard: leaderboard,
            hand: hand,
            board: newBoard,
            gameId: newGameId,
            playerId: newPlayerId,
            name: name,
        }
        setData(tempData);
        setSubmitted(true);
    }

    function handleSubmitClick(e){
        window.scrollTo(0, 0);
    }
    return (
        <>
        {submitted && <Navigate state={data} to={`/game/${gameId}/${name}`} />}
        <div className='container'>
            <h1 className='title'>Create a game or join an existing one!</h1>
            <form id='login' onSubmit={(e) => handleCreateGameSubmit(e)}>
                <input type='text' value={name} onChange={(e) => handleNameChange(e)}></input>
                <input type='number' value={gameId} onChange={(e) => handleIdChange(e)}></input>
                <button type='submit' value='Find Game' onClick={(e) => handleSubmitClick(e)}>Submit</button>
            </form>
        </div>
        </>
    )
}

export default Login;