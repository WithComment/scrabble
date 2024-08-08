import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

class ViewModel{
    constructor(gameId, playerId, board, hand, leaderboard, tilesLeft, gameState, events, setHand, setBoard, setLeaderboard, setTilesLeft,  setGameState, setEvents){
        this.playerId = playerId;
        this.gameId = gameId;
        this.baseUrl = `http://localhost:8080/game/${this.gameId}/`;
        this.board = board;
        this.hand = hand;
        this.leaderboard = leaderboard;
        this.tilesLeft = tilesLeft;
        this.gameState = gameState;
        this.events = events;
        this.selectedLetter = null;
        this.selectedLettersRedraw = [];
        this.setHand = setHand;
        this.setBoard = setBoard;
        this.setLeaderboard = setLeaderboard;
        this.setTilesLeft = setTilesLeft;
        this.setGameState = setGameState;
        this.setEvents = setEvents;
        this.lastMessageReceivedTime = 0;
        this.connectWebSocket()
    }

    connectWebSocket() {
        const socket = new SockJS('http://localhost:8080/ws', null, {withCredentials: true});
        this.stompClient = Stomp.over(socket);

        this.stompClient.connect({}, (frame) => {
            this.stompClient.subscribe(`/topic/game/${this.gameId}`, (message) => {
                this.handleWebSocketMessage(JSON.parse(message.body));
            }, (error) => {
                console.error('WebSocket connection error:', error);
            });
        });
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
        this.updateLeaderboardFromRaw(newLeaderboard);
        this.setTilesLeft(game.letterBag.bag.length);

        if (message.type === 'start') {
            this.addNewEvent('- Game Started');
            this.setNewTurn(game.currentPlay.player.name, game.currentPlay.player.id);
        } else if (message.type === 'valid-confirm-play'){
            if (this.gameState === 'Your Turn'){
                this.updateGameState('Inactive');
            } else{
                this.updateGameState('Contest');
            }
        } else if (message.type === 'contest-success'){
            this.addNewEvent('- Contest Successful');
            this.setNewTurn(game.currentPlay.player.name, game.currentPlay.player.id);
        } else if (message.type === 'contest-fail'){
            this.addNewEvent('- Contest Failed');
            this.setNewTurn(game.currentPlay.player.name, game.currentPlay.player.id);
        } else if (message.type === 'no-contests'){
            this.addNewEvent('- No Contests');
            this.setNewTurn(game.currentPlay.player.name, game.currentPlay.player.id);
        } else if (message.type === 'redraw'){
            this.setNewTurn(game.currentPlay.player.name, game.currentPlay.player.id);
        }
    };

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
        let newLeaderboard = [];
        for (let i = 0; i < rawLeaderboard.length; i++){
            newLeaderboard.push({name: rawLeaderboard[i].name, score: rawLeaderboard[i].score});
        }
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

    updateGameState(newState){
        this.setGameState(newState);
        this.gameState = newState;
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
            let requestBody = {            
                gameId: this.gameId,
                playerId: this.playerId,
                isContest: true
            }
            response = await fetch(`${this.baseUrl}contest/`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                  },
                method: 'POST',
                body : JSON.stringify(requestBody)
            });
            response = await response.json();
            console.log(response);
            if (response.invalidWords.length > 0){
                response = await fetch(`${this.baseUrl}broadcast_contest_success/`, {
                    method: 'POST'
                })
            } else{
                response = await fetch(`${this.baseUrl}broadcast_contest_fail/`, {
                    method: 'POST'
                })
            }
        } else if (input.type === 'no-contest'){
            let requestBody = {            
                gameId: this.gameId,
                playerId: this.playerId,
                isContest: false
            }
            response = await fetch(`${this.baseUrl}contest/`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                  },
                method: 'POST',
                body : JSON.stringify(requestBody)
            });
            response = await response.json();
            if (response.goToNextTurn){
                response = await fetch(`${this.baseUrl}broadcast_no_contests/`, {
                    method: 'POST'
                });
            }
        }else if (input.type === 'redraw'){
            let requestBody = {            
                gameId: this.gameId,
                characters: this.selectedLettersRedraw,
            }
            console.log('Redrawing characters:', requestBody);
            response = await fetch(`${this.baseUrl}redraw_letters/`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                  },
                method: 'POST',
                body : JSON.stringify(requestBody)
            });
            response = await fetch(`${this.baseUrl}broadcast_redraw/`, {method: 'POST'});
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
            response = await fetch(`${this.baseUrl}confirm_play/`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                  },
                method: 'POST',
                body : JSON.stringify(requestBody)
            });
            response = await response.json();
            console.log('received response')
            console.log(response);
            console.log(this.gameState);
            if (response.valid){
                response = await fetch(`${this.baseUrl}broadcast_valid_play/`, {
                    method: 'POST'
                })
            }

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
                    body: JSON.stringify(requestBody),
                });
            }
        }
    }

    handleResponse(response){
        let reponse = response.json();
        console.log('Response:', response);
    }

    addNewEvent(event){
        this.setEvents([...this.events, event]);
        this.events.push(event);
    }

    setNewTurn(name, id){
        this.updateGameState(name + 's Turn');
        if (id === this.playerId){
            this.updateGameState('Your Turn');
        }
        this.addNewEvent('- ' + name + '\'s Turn');
    }

}

export default ViewModel;