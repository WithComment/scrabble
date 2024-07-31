class ViewModel{
    constructor(gameId, board, hand, leaderboard, setBoard, setHand, setLeaderboard){
        this.gameId = gameId;
        this.baseUrl = 'http://localhost:8080';
        this.board = board;
        this.hand = hand;
        this.leaderboard = leaderboard;
        this.setLeaderboard = setLeaderboard;
        this.setBoard = setBoard;
        this.setHand = setHand;
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
    async sendInput(input){
        let response = await fetch(this.baseUrl, {
            method: 'POST',
            inputType : input.type,
            x: input.x,
            y: input.y,
        })
    }
}

export default ViewModel;