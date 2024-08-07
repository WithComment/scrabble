import '../App.css';
import Board from '../components/Board';
import React, { useState, useRef, useEffect } from 'react';
import ViewModel from '../ViewModel';
import Hand from '../components/Hand'
import Redraw from '../components/buttons/Redraw';
import Contest from '../components/buttons/Contest';
import Leaderboard from '../components/Leaderboard';
import { useLocation } from 'react-router-dom';
import StartGame from '../components/buttons/StartGame';
import DontContest from '../components/buttons/DontContest';
import ConfirmPlay from '../components/buttons/ConfirmPlay';
import diceBag from '../diceBag.png';

function Game() {
    const [bgMusic, setBgMusic] = useState(null);
    useEffect(() => {
      const audio = new Audio('/ScrabbleSerenade.mp3');
      audio.load();
      audio.loop = true;
      audio.play();
  }, []);
    const { state } = useLocation();
    console.log(state.hand)
    const [board, setBoard] = useState(state.board);
    const [hand, setHand] = useState(state.hand);
    const [leaderboard, setLeaderboard] = useState(state.leaderboard);
    const [gameState, setGameState] = useState('Not Started');
    const [tilesLeft, setTilesLeft] = useState(state.tilesLeft);
    const [events, setEvents] = useState(['Welcome to the beginning of the game!']);

    const viewModelRef = useRef(null);

    if (!viewModelRef.current) {
        viewModelRef.current = new ViewModel(
            state.gameId,
            state.playerId,
            state.board,
            state.hand,
            state.leaderboard,
            tilesLeft,
            gameState,
            events,
            setHand,
            setBoard,
            setLeaderboard, 
            setTilesLeft,
            setGameState,
            setEvents
        );
        viewModelRef.current.testIfWorking();
    }

    const viewModel = viewModelRef.current;
    let gameId = state.gameId;
    const playerId = state.playerId;
    gameId = Number(gameId)
    return (
        <div className="Game">
        <Leaderboard players={leaderboard}/>
        <div className='board-container'>
          <div className='info'>
            <div className='game-state'>Game State: {gameState}</div>
            <div className='game-id'>Game ID: {gameId}</div>
            <div className='player-id'>Player ID: {playerId}</div>
            <div className='event-log'>{events.map((event, index) => {
              return (
                <div key={index}>{event}</div>
                )}
              )}
            </div>
          </div>
          <Board board={board} boardViewModel={viewModel}/>
          <div className='letter-container'>
            <img className='letter-bag'src={diceBag} alt='dice-bag'/>
            <div className='letter-count'>Letters left: {tilesLeft}</div>
          </div>
        </div>
        <div class='inputs-container'>
            <div class='buttons-container'>
            {(gameState === 'Your Turn') && <><Redraw ButtonViewModel={viewModel}/><ConfirmPlay ButtonViewModel={viewModel}/></>}
            {(gameState === 'Contest') && <><Contest ButtonViewModel={viewModel}/> <DontContest ButtonViewModel={viewModel}/></>}
            {(gameState === 'Not Started') && <StartGame ButtonViewModel={viewModel}/>}
            </div>
            <Hand handLetters={hand} handViewModel={viewModel}/>
        </div>
        </div>
    );
    }
    export default Game;