import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

class ViewModel{
    constructor(gameId, playerId, board, hand, leaderboard, gameState, setHand, setBoard, setLeaderboard, setGameStarted, setContestPhase, setYourTurn, setGameState){
        this.playerId = playerId;
        this.gameId = gameId;
        this.baseUrl = `http://localhost:8080/game/${this.gameId}/`;
        this.board = board;
        this.hand = hand;
        this.leaderboard = leaderboard;
        this.gameState = gameState;
        this.selectedLetter = null;
        this.selectedLettersRedraw = [];
        this.setHand = setHand;
        this.setBoard = setBoard;
        this.setLeaderboard = setLeaderboard;
        this.setGameStarted = setGameStarted;
        this.setGameState = setGameState;
        this.connectWebSocket()
    }

    connectWebSocket() {
        const socket = new SockJS('http://localhost:8080/ws', null, {withCredentials: true});
        this.stompClient = Stomp.over(socket);

        this.stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            this.stompClient.subscribe(`/topic/game/${this.gameId}`, (message) => {
                console.log('Received message:', message);
                this.handleWebSocketMessage(JSON.parse(message.body));
            }, (error) => {
                console.error('WebSocket connection error:', error);
            });        });
    }

    handleWebSocketMessage(message) {
        console.log('Handling message:', message, 'of type ', message.type);
        let game = message.data;
        let newBoard = game.board.board;
        let newHand = game.players.filter((player) => player.id === this.playerId)[0].inventory.map((letter) => letter.letter);
        let newLeaderboard = game.leaderboard;
        // Updates the View
        this.updateBoardFromRaw(newBoard);
        this.updateHand(newHand);
        this.updateLeaderboard(newLeaderboard);
        if (message.type === 'start'){
            this.setGameStarted(true);
        }
    }

    updateBoardFromRaw(rawBoard){
        console.log('Updating board from raw: ' , rawBoard);
        let newBoard = [];
        for (let i = 0; i < 15; i++) {
            let row = [];
            for (let j = 0; j < 15; j++) {
                if (rawBoard[i][j].letter === null){
                    row.push("__")
                }else{
                    row.push(rawBoard[i][j].letter.letter);
                };
            };
            newBoard.push(row);
        };
        console.log('New board:', newBoard);
        this.updateBoard(newBoard);
    }

    updateHandFromRaw(rawHand){
        console.log('Updating hand from raw:', rawHand);
        let newHand = rawHand.map((letter) => letter.letter);
        console.log('New hand:', newHand);
        this.updateHand(newHand);
    }

    updateLeaderboardFromRaw(rawLeaderboard){
        console.log('Updating leaderboard from raw:', rawLeaderboard);
        let newLeaderboard = rawLeaderboard;
        console.log('New leaderboard:', newLeaderboard);
        this.updateLeaderboard(newLeaderboard);
    }

    testIfWorking(){
        console.log('View Model properly initialized');
    }
    
    addToRedraws(letter){
        console.log('Setting redraw letter:', letter);
        this.selectedLettersRedraw.push(letter);
        console.log('Selected letters redraw:', this.selectedLettersRedraw);
    }

    removeRedrawLetter(letter){
        this.selectedLettersRedraw = this.selectedLettersRedraw.filter((l) => l !== letter);
    }

    setTile(x, y, letter){
        this.board[x][y] = letter;
    }

    updateBoard(board){
        this.setBoard(board);
    }

    updateHand(hand){
        this.setHand(hand);
    }

    updateLeaderboard(leaderboard){
        this.setLeaderboard(leaderboard);
    }

    getBoard(){
        return this.board;
    }

    getHand(){
        return this.hand;
    }

    getLeaderboard(){
        return this.leaderboard;
    }

    getSelectedLetter(){
        return this.selectedLetter;
    }

    setSelectedLetter(selectedLetter){
        this.selectedLetter = selectedLetter;
    }

    async handleInput(input){
        console.log('Handling input');
        let response = null;
        if (input.type === 'contest'){
            let request = {            
                gameId: this.gameId,
                playerId : this.playerId,
            }
            response = await fetch(`${this.baseUrl}contest/`, request);
        } else if (input.type === 'redraw'){
            console.log(this.selectedLettersRedraw);
            let request = {            
                gameId: this.gameId,
                char: this.selectedLettersRedraw,
            }
            response = await fetch(`${this.baseUrl}redraw/`, request);
        } else if (input.type === 'start'){
            let requestBody = {      
                gameId: this.gameId,
            };
            response = await fetch(`${this.baseUrl}start_game/`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                  },
                method: 'POST',
                body : JSON.stringify(requestBody)
            });
        } else if (input.type === 'confirm-play'){
            let requestBody = {      
                gameId: this.gameId,
            };
            response = await fetch(`${this.baseUrl}end_turn/`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                  },
                method: 'POST',
                body : JSON.stringify(requestBody)
            });

        } else{
            if (input.type === 'lclick'){
                if (this.selectedLetter != null){
                    let requestBody = {      
                        gameId: this.gameId,
                        x: input.x,
                        y: input.y,
                        letter: this.selectedLetter
                    };
                    response = await fetch(`${this.baseUrl}place_letter/`, {
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                          },
                        method: 'POST',
                        body : JSON.stringify(requestBody),
                    });
                } else {
                    return;
                }
            } else if (input.type === 'rclick'){
                let requestBody = {            
                    gameId: this.gameId,
                    x: input.x,
                    y: input.y,
                }
                response = await fetch(`${this.baseUrl}remove_letter/`, {
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                      },
                    method: 'POST',
                    body: requestBody,
                });
            }
        }
        this.handleResponse(response);
    }

    handleResponse(response){
        let reponse = response.json();
        console.log('Response:', response);
    }

}

export default ViewModel;