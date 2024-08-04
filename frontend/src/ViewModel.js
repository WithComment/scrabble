import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

class ViewModel{
    constructor(gameId, playerId, board, hand, leaderboard, setHand, setBoard, setLeaderboard){
        this.playerId = playerId;
        this.gameId = gameId;
        this.baseUrl = 'http://localhost:8080/game/';
        this.board = board;
        this.hand = hand;
        this.leaderboard = leaderboard;
        this.selectedLetter = null;
        this.selectedLettersRedraw = [];
        this.setHand = setHand;
        this.setBoard = setBoard;
        this.setLeaderboard = setLeaderboard;
        this.connectWebSocket()
    }

    connectWebSocket() {
        const socket = new SockJS('http://localhost:8080/ws', null, {withCredentials: true});
        this.stompClient = Stomp.over(socket);

        this.stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            this.stompClient.subscribe('/topic/messages', (message) => {
                this.handleWebSocketMessage(JSON.parse(message.body));
            });
        });
    }

    handleWebSocketMessage(message) {
        console.log('Received message:', message);
        //TODO: Handle message
    }

    testIfWorking(){
        console.log('View Model properly initialized');
    }
    
    setRedrawLetter(letter){
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
            let request = {            
                gameId: this.gameId,
                x: input.x,
                y: input.y,
                char: this.selectedLetter
            }
            response = await fetch(`${this.baseUrl}redraw/`, request);
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
        
    }

}

export default ViewModel;