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

function Game() {
    const { state } = useLocation();
    const [board, setBoard] = useState(state.board);
    const [hand, setHand] = useState(state.hand);
    const [leaderboard, setLeaderboard] = useState(state.leaderboard);
    const [gameStarted, setGameStarted] = useState(true);
    const [constestPhase, setContestPhase] = useState(false);
    const [yourTurn, setYourTurn] = useState(true);

    const viewModel = new ViewModel(
        state.gameId,
        state.playerId,
        state.board,
        state.hand,
        state.leaderboard,
        setHand,
        setBoard,
        setLeaderboard, 
        setGameStarted,
        setContestPhase,
        setYourTurn
    );
    viewModel.testIfWorking();
    let gameId = state.gameId;
    const playerId = state.playerId;
    gameId = Number(gameId)
    return (
        <div className="Game">
        <Leaderboard players={leaderboard}/>
        <Board board={board} boardViewModel={viewModel}/>
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