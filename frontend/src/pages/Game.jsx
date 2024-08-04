import '../App.css';
import Board from '../components/Board';
import React, { useState } from 'react';
import ViewModel from '../ViewModel';
import Hand from '../components/Hand'
import Redraw from '../components/buttons/Redraw';
import Contest from '../components/buttons/Contest';
import Leaderboard from '../components/Leaderboard';
import { useParams } from 'react-router-dom';
import { useLocation } from 'react-router-dom';

function Game() {
    const { state } = useLocation();
    const [board, setBoard] = useState(state.board);
    const [hand, setHand] = useState(state.hand);
    const [leaderboard, setLeaderboard] = useState(state.leaderboard);
    const viewModel = new ViewModel(
      state.gameId,
      state.playerId,
      state.board,
      state.hand,
      state.leaderboard,
      setHand,
      setBoard,
      setLeaderboard
    );
    viewModel.testIfWorking();
    let gameId = state.gameId;
    const playerId = state.playerId;
    gameId = Number(gameId)
    console.log(gameId, playerId)
    return (
        <div className="Game">
        <Leaderboard players={leaderboard}/>
        <Board board={board} boardViewModel={viewModel}/>
        <div class='inputs-container'>
            <div class='buttons-container'>
            <Redraw ButtonViewModel={viewModel}/>
            <Contest ButtonViewModel={viewModel}/>
            </div>
            <Hand handLetters={hand} handViewModel={viewModel}/>
        </div>
        </div>
    );
    }
    export default Game;