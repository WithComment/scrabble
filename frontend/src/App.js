import logo from './logo.svg';
import './App.css';
import Board from './components/Board';
import React, { useState } from 'react';
import ViewModel from './ViewModel';

function App() {
  let tempBoard = []
  for (let i = 0; i < 15; i++) {
    let row = []
    for (let j = 0; j < 15; j++) {
      row.push("__")
    }
    tempBoard.push(row)
  }
  const [board, setBoard] = useState(tempBoard);
  const [hand, setHand] = useState([]);
  const [leaderboard, setLeaderboard] = useState([]);
  let viewModel = new ViewModel(board, hand, leaderboard, setBoard, setHand, setLeaderboard);
  return (
    <div className="App">
      <Board board={board}/>
    </div>
  );
}

export default App;
