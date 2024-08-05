import '../App.css';
import Board from '../components/Board';
import React, { useState } from 'react';
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
    const { state } = useLocation();
    const [board, setBoard] = useState(state.board);
    const [hand, setHand] = useState(state.hand);
    const [leaderboard, setLeaderboard] = useState(state.leaderboard);
    const [gameStarted, setGameStarted] = useState(false);
    const [constestPhase, setContestPhase] = useState(false);
    const [yourTurn, setYourTurn] = useState(false);
    const [gameState, setGameState] = useState('Not started');

    const viewModel = new ViewModel(
        state.gameId,
        state.playerId,
        state.board,
        state.hand,
        state.leaderboard,
        gameState,
        setHand,
        setBoard,
        setLeaderboard, 
        setGameStarted,
        setContestPhase,
        setYourTurn,
        setGameState
    );
    
    viewModel.testIfWorking();
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
          </div>
          <Board board={board} boardViewModel={viewModel}/>
          <div className='letter-container'>
            <img className='letter-bag'src={diceBag} alt='dice-bag'/>
            <div className='letter-count'>Letters left: 50</div>
          </div>
        </div>
        <div class='inputs-container'>
            <div class='buttons-container'>
            {yourTurn && <><Redraw ButtonViewModel={viewModel}/><ConfirmPlay ButtonViewModel={viewModel}/></>}
            {constestPhase && <><Contest ButtonViewModel={viewModel}/> <DontContest ButtonViewModel={viewModel}/></>}
            {!gameStarted && <StartGame ButtonViewModel={viewModel}/>}
            </div>
            <Hand handLetters={hand} handViewModel={viewModel}/>
        </div>
        </div>
    );
    }
    export default Game;