import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Login from './pages/Login';
import Game from './pages/Game';
import Footer from './components/Footer';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Login/>}/>
        <Route path='/game/:gameId/:playerId' element={<Game/>}/>
      </Routes>
      <Footer/>
    </BrowserRouter>
  )
}
export default App;
