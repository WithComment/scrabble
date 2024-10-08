import React, { useState } from 'react';
import { Navigate } from 'react-router-dom';
import ViewModel from '../ViewModel';

function Login(){
    const createUrl = 'https://scrabble-2ii47ihutq-ue.a.run.app/game/create/'
    const [name, setName] = useState('Username');
    const [gameId, setGameId] = useState(0);
    const [submitted, setSubmitted] = useState(false);
    const [data, setData] = useState({});
    const joinUrl = `https://scrabble-2ii47ihutq-ue.a.run.app/game/${gameId}/join/`

    function handleNameChange(e){
        setName(e.target.value);
    }

    function handleIdChange(e){
        setGameId(e.target.value)
    }

    async function handleJoinGameSubmit(e){
        e.preventDefault();
        console.log("Name is " + name);
        let request = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                playerName: name,
                gameId: gameId
            })
        }
        console.log(request);
        let response = await fetch(joinUrl, request);
        response = await response.json();
        console.log(response);
        let newGameId = response.id;
        let tilesLeft = response.letterBag.bag.length;
        let newPlayerId = response.players.at(-1).id;
        let hand = response.players.at(-1).inventory.map((letter) => letter.letter);
        let newBoard = []
        for (let i = 0; i < 15; i++) {
            let row = []
            for (let j = 0; j < 15; j++) {
            row.push("__")
            }
            newBoard.push(row)
        };
        let leaderboard = response.leaderboard.map((player) => {return {name: player.name, score: player.score}});

        let tempData = {
            leaderboard: leaderboard,
            hand: hand,
            tilesLeft: tilesLeft,
            board: newBoard,
            gameId: newGameId,
            playerId: newPlayerId,
            name: name,
        }
        setData(tempData);
        setSubmitted(true);
    }
    async function handleCreateGameSubmit(e){
        e.preventDefault();
        let request = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                playerNames: [name],
            })
        }
        let response = await fetch(createUrl, request);
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
            tilesLeft: 91,
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
            <form id='create' onSubmit={(e) => handleCreateGameSubmit(e)}>
                <label for='name'>Create Game: </label>
                <input type='text' id='name' value={name} onChange={(e) => handleNameChange(e)}></input>
                <button type='submit' value='Find Game' onClick={(e) => handleSubmitClick(e)}>Submit</button>
            </form>
            <form id='join' onSubmit={(e) => handleJoinGameSubmit(e)}>
                <label for='name'>Or join an existing game with Game ID: </label>
                <input type='text' value={name} onChange={(e) => handleNameChange(e)}></input>
                <input type='number' value={gameId} onChange={(e) => handleIdChange(e)}></input>
                <button type='submit' value='Find Game' onClick={(e) => handleSubmitClick(e)}>Submit</button>
            </form>
        </div>
        </>
    )
}

export default Login;