import React, { useState } from 'react';
import { Navigate } from 'react-router-dom';

function Login(){
    const submitUrl = 'http://localhost:8080/game/create/'
    const [name, setName] = useState('Username');
    const [gameId, setGameId] = useState(0);
    const [submitted, setSubmitted] = useState(false);
    function handleNameChange(e){
        setName(e.target.value);
    }

    function handleIdChange(e){
        setGameId(e.target.value)
    }

    async function handleSubmit(e){
        e.preventDefault();
        console.log('test')
        console.log(name + gameId);
        // let response = await fetch(submitUrl, {
        //     method: 'GET',
        //     username: name,
        //     gameId: gameId
        // })
        setSubmitted(true);
    }

    function handleSubmitClick(e){
        window.scrollTo(0, 0);
    }
    return (
        <>
        {submitted && <Navigate state={{username: name}} to={`/game/${gameId}`} />}
        <div className='container'>
            <h1 className='title'>Create a game or join an existing one!</h1>
            <form id='login' onSubmit={(e) => handleSubmit(e)}>
                <input type='text' value={name} onChange={(e) => handleNameChange(e)}></input>
                <input type='number' value={gameId} onChange={(e) => handleIdChange(e)}></input>
                <button type='submit' value='Find Game' onClick={(e) => handleSubmitClick(e)}>Submit</button>
            </form>
        </div>
        </>
    )
}

export default Login;