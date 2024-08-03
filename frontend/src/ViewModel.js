class ViewModel{
    constructor(gameId, playerId, board, hand, leaderboard, setBoard, setHand, setLeaderboard){
        this.playerId = playerId;
        this.gameId = gameId;
        this.baseUrl = 'http://localhost:8080/game/';
        this.board = board;
        this.hand = hand;
        this.leaderboard = leaderboard;
        this.setLeaderboard = setLeaderboard;
        this.setBoard = setBoard;
        this.setHand = setHand;
        this.selectedLetter = null;
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
        console.log('input handling');
        console.log(input);
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
                    let request = {            
                        gameId: this.gameId,
                        x: input.x,
                        y: input.y,
                        char: this.selectedLetter
                    };
                    console.log(request);
                    response = await fetch(`${this.baseUrl}place_letter/`, request);
                } else {
                    return;
                }
            } else if (input.type === 'rclick'){
                let request = {            
                    gameId: this.gameId,
                    x: input.x,
                    y: input.y,
                }
                response = await fetch(`${this.baseUrl}remove_letter/`, request);
            }
        }
        this.handleResponse(response);
    }

    handleResponse(response){
        let reponse = response.json();
        
    }

}

export default ViewModel;