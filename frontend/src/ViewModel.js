class ViewModel{
    constructor(gameId, board, hand, leaderboard, setBoard, setHand, setLeaderboard){
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
        console.log('input handling')
        let response = null;
        let request = {            
            method: 'POST',
            gameId: this.gameId,
            inputType : input.type,
            x: input.x,
            y: input.y,
            char: this.selectedLetter
        }
        if (input.type === 'contest'){
            response = await fetch(`${this.baseUrl}contest/`, request);
        } else if (input.type === 'redraw'){
            response = await fetch(`${this.baseUrl}redraw/`, request);
        } else{
            if (input.type === 'lclick'){
                if (this.selectedLetter != null){
                    response = await fetch(`${this.baseUrl}place_letter/`, request);
                } else {
                    return;
                }
            } else if (input.type === 'rclick'){
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