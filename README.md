# CSC207 Final Project: 3 billion devices run Java

Our final project is a multiplayer Scrabble game. It is live on Github Pages at https://withcomment.github.io/scrabble/, and the backend is live on Google Cloud Run at https://scrabble-2ii47ihutq-ue.a.run.app/

## Instructions for running project:
<ul>
<li> Easy Way: simply go to https://withcomment.github.io/scrabble/ and click Submit under the Create Game section (note that it will take about 30 seconds for you to initialy be routed to a game lobby because the server has to turn back on for the first time. Don't worry it's working!) You can open the link in another tab, type in the Game ID from the first tab and press join game. From here you can press start game and start playing. <br>
Controls: <ul>
  <li>Click on a Letter in your hand to select it</li>
  <li>Click on a spot on the Board to place your letter on that spot</li>
  <li>Right Click a spot on the Board to remove the letter there, provided you played that letter this turn</li>
  <li>Right Click on the Letters in your hand to select them for redraw, then click Redraw Selected to redraw them</li>
  <li>Click End Turn to end your turn</li>
  <li>Click Contest or Do Nothing to go to the next turn</li>
</ul>
(Note that the first play of the game must have a Letter placed on the center of the Board)</li>
  <br>
<li>Hard Way: if you want to run this locally, follow these steps: <ul>
  <li>Clone this repo</li>
  <li>Run ScrabbleApplication.java</li>
  <li>Now that the server is running navigate to /frontend</li>
  <li>Make sure you have Nodejs installed on your system (if not, download it here https://nodejs.org/en/download/prebuilt-installer). Run node -v in the terminal to see if it works</li>
  <li>In /frontend, run npm install to install all dependencies</li>
  <li>Now, run npm start to serve the frontend locally. The project should now run properly</li>
</ul>
</li>
</ul>
