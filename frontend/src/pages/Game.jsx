import '../App.css';
import Board from '../components/Board';
import React, { useState } from 'react';
import ViewModel from '../ViewModel';
import Hand from '../components/Hand'
import Redraw from '../components/buttons/Redraw';
import Contest from '../components/buttons/Contest';
import Leaderboard from '../components/Leaderboard';
import { useParams } from 'react-router-dom';

function Game() {
    let tempBoard = []
    for (let i = 0; i < 15; i++) {
        let row = []
        for (let j = 0; j < 15; j++) {
        row.push("__")
        }
        tempBoard.push(row)
    }

    let tempHand = ['A', 'B', 'C', 'D', 'E', 'F', 'G']
    const [board, setBoard] = useState(tempBoard);
    const [hand, setHand] = useState(tempHand);
    const [leaderboard, setLeaderboard] = useState(
    [
      {
        name: 'Oscar',
        score: 32
      },
      {
        name: 'Alex',
        score: 12
      },
    ]);
    const { gameId, playerId } = useParams();
    console.log(gameId, playerId)
    const viewModel = new ViewModel(gameId, playerId, board, hand, leaderboard, setBoard, setHand, setLeaderboard)
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