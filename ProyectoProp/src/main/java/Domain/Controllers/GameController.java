package Domain.Controllers;

import Domain.DataControllers.*;
import Domain.Models.Game.Board;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Estadistiques;
import Domain.Models.Usuario.Persona;
import Domain.Models.Usuario.Player;
import Domain.UserLogged;
import Domain.utility.IRandomNumber;
import Domain.utility.RandomNumber;
import Exceptions.PersonaDoesNotExists;
import Exceptions.StatisticDoesNotExists;
import Exceptions.emailEqualThanLoggedUser;
import UI.Vistas.Controllers.helpers.ContinueGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.javatuples.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class GameController {

    /**
     * @author Daniel Rodriguez
     */

    /**
     * Variables para facilitar la inyección de dependencias en los tests
     */
    private CurrentGame currentGameSingleton = CurrentGame.Instance();
    private ICtrlDataFactory ctrlDataFactory = CtrlDataFactory.Instance();
    private IRandomNumber randomNumber = new RandomNumber();
    private Persona userLogged = UserLogged.Instance().getUserLogged();
    public void setRandomNumberGenerator(IRandomNumber randomNumber){
        this.randomNumber = randomNumber;
    }
    public void setCtrlDataFactory(ICtrlDataFactory ctrlDataFactory){
        this.ctrlDataFactory = ctrlDataFactory;
    }
    public void setUserLogged(Persona user){
        this.userLogged = user;
    }

    /**
     * Dado un email de oponente, se crea un nuevo game
     * @param opponentEmail
     * @return gameID
     */

    public int createGame(String opponentEmail) {

        UserLogged user = UserLogged.Instance();
        Persona userLogged = user.getUserLogged();
        Player secondPlayer = getPlayer(opponentEmail);

        int gameID = createPlayerOrBotGame(userLogged, secondPlayer, userLogged);
        return gameID;
    }

    /**
     * Dado un whitePlayer email y un blackPlayer email, se crea un nuevo game tipo bot
     * @param blackPlayer
     * @param whitePlayer
     * @return gameID
     */
    public int createBotGame(String blackPlayer, String whitePlayer) {
        UserLogged user = UserLogged.Instance();
        Persona userLogged = user.getUserLogged();
        Player firstPlayer = getPlayer(blackPlayer);
        Player secondPlayer = getPlayer(whitePlayer);

        int gameID = createPlayerOrBotGame(firstPlayer, secondPlayer, userLogged);
        return gameID;
    }



    public int createPlayerOrBotGame(Player firstPlayer, Player secondPlayer, Persona userLogged){
        long millis=System.currentTimeMillis();
        java.util.Date date=new java.util.Date(millis);
        long data = date.getTime();
        int boardID =  (int) new Date().getTime();
        int gameID = (int) new Date().getTime();

        generateOrderOfGameAndSave(gameID, data, boardID, firstPlayer, secondPlayer, userLogged);
        return gameID;
    }

    /**
     * Se crea el nuevo Game, randomizando el color de los jugadores
     * @param gameID
     * @param data
     * @param boardID
     * @param firstPlayer
     * @param secondPlayer
     * @param userLogged
     */
    public void generateOrderOfGameAndSave(int gameID, long data, int boardID, Player firstPlayer, Player secondPlayer, Persona userLogged){
        int firstPlayerID = firstPlayer.getID();
        int secondPlayerID = secondPlayer.getID();
        
        Game game;
        if(randomNumber.getRandomNumber()%2 == 0){
            game = new Game(gameID, data, boardID, secondPlayer, firstPlayer);
            userLogged.addGame(game);
            saveNewGame(gameID, data, boardID, secondPlayerID, firstPlayerID);

            makeBotFirstMove(game);
        }else{
            game = new Game(gameID, data, boardID, firstPlayer, secondPlayer);
            userLogged.addGame(game);
            saveNewGame(gameID, data, boardID, firstPlayerID, secondPlayerID);
        }
        saveBoardState(game.getBoard());       
    }

    public int getSelectedGameID(int selectedGame){
        Game game = userLogged.getSelectedGame(selectedGame);
        if(game == null) return -1;
        return game.getID();

    }


    /**
     * Dado un email de oponente, se intenta buscar la persona en bd
     * Se comprueba que la persona exista y que no sea la misma que el oponente
     *
     * @param opponentEmail
     * @return instancia de player
     */
    private Player getPlayer(String opponentEmail){

        ICtrlPersona ctrlPersona = ctrlDataFactory.getCtrlPersona();
        String userLoggedEmail = userLogged.getEmail();

        Player secondPlayer = null;
        boolean error = false;

        while(secondPlayer == null) {

            error = false;

            try {

                secondPlayer = findPlayer(opponentEmail, ctrlPersona, userLoggedEmail);

            }  catch (PersonaDoesNotExists personaDoesNotExists) {

                personaDoesNotExists.printStackTrace();
                System.out.println("Enter a valid email please:");
                error = true;

            } catch (emailEqualThanLoggedUser emailEqualThanLoggedUser) {

                emailEqualThanLoggedUser.printStackTrace();
                System.out.println("You can't play against yourself ...");
                System.out.println("Enter a valid email please:");
                error = true;

            }

            if(error){
                Scanner scan = new Scanner(System.in);
                opponentEmail = scan.nextLine();
            }

        }

        return secondPlayer;
    }

    /**
     * Auxiliar de la funcion getPlayer, para poder hacerle tests
     * @param opponentEmail
     * @param ctrlPersona
     * @param userLoggedEmail
     * @return
     * @throws PersonaDoesNotExists
     * @throws emailEqualThanLoggedUser
     */
    public Player findPlayer(String opponentEmail, ICtrlPersona ctrlPersona, String userLoggedEmail) throws PersonaDoesNotExists, emailEqualThanLoggedUser{

        if(userLoggedEmail.equals(opponentEmail) ) throw new emailEqualThanLoggedUser("User equal than logged user");
        Player result = ctrlPersona.getPersona(opponentEmail);

       return  result;
    }


    /**
     * Se guarda el game
     * @param gameID
     * @param data
     * @param boardID
     * @param blackPlayerID
     * @param whitePlayerID
     */
    private void saveNewGame(int gameID, long data, int boardID, int blackPlayerID, int whitePlayerID){
        ICtrlGame ctrlGame = ctrlDataFactory.getCtrlGame();
        ctrlGame.saveGame(gameID, data, boardID, blackPlayerID, whitePlayerID);

    }


    public Player getCurrentPlayer(boolean isTurnOfBlacks, Game game){
        if(isTurnOfBlacks)return game.getBlackPlayer();
        else return game.getWhitePlayer();
    }
    public Player getOpponentPlayer(boolean isTurnOfBlacks, Game game){
        if(isTurnOfBlacks)return game.getWhitePlayer();
        else return game.getBlackPlayer();
    }



    public char[][] play(Pair<Integer,Integer> jugada){
        Game currentGame = currentGameSingleton.getCurrentGame();


        Board board = currentGame.getBoard();
        boolean isTurnOfBlacks = board.getTurn();

        Player  player = getCurrentPlayer(isTurnOfBlacks, currentGame);
        Player  opponent = getOpponentPlayer(isTurnOfBlacks, currentGame);

        boolean haveValidMoves = currentGame.calculamove(player);

        if(haveValidMoves){
            makeTheMove(jugada, currentGame, player);
            saveBoardState(board);
            changeTurnIfOpponentHaveValidMoves(currentGame, opponent);
        }

        if(currentGame.calculamove(opponent))
            moveIfIsABot(opponent, currentGame, board, isTurnOfBlacks);

        saveBoardState(board);

        char [][] res = generateBoard();
        return res;
    }

    public char[][] playP(Pair<Integer,Integer> jugada){
        Game currentGame = currentGameSingleton.getCurrentGame();

        Board board = currentGame.getBoard();
        boolean isTurnOfBlacks = board.getTurn();

        Player  player = getCurrentPlayer(isTurnOfBlacks, currentGame);
        Player  opponent = getOpponentPlayer(isTurnOfBlacks, currentGame);

        boolean haveValidMoves = currentGame.calculamove(player);

        if(haveValidMoves){
            makeTheMove(jugada, currentGame, player);
            saveBoardState(board);
            changeTurnIfOpponentHaveValidMoves(currentGame, opponent);
        }
        saveBoardState(board);

        char [][] res = generateBoard();
        return res;
    }

    public char[][] playCPU(){
        Game currentGame = currentGameSingleton.getCurrentGame();

        Board board = currentGame.getBoard();
        boolean isTurnOfBlacks = board.getTurn();

        Player  player = getCurrentPlayer(isTurnOfBlacks, currentGame);

        if(currentGame.calculamove(player))
            moveIfIsABot(player, currentGame, board, isTurnOfBlacks);

        saveBoardState(board);

        char [][] res = generateBoard();
        return res;
    }

    /**
     * Si el oponente tiene movimientos validos, se cambia el turno
     * @param currentGame
     * @param opponent
     */
    public void changeTurnIfOpponentHaveValidMoves(Game currentGame, Player opponent){
       boolean haveValidMoves = currentGame.calculamove(opponent);
       Board board = currentGame.getBoard();
       if(haveValidMoves)board.setTurn(!board.getTurn());
       else System.out.println("Skipping turn");
    }

    public char [][] generateBoard(){
        Game currentGame = currentGameSingleton.getCurrentGame();
        Board board = currentGame.getBoard();
        Player player = getCurrentPlayer( board.getTurn(),currentGame);
        currentGame.calculamove(player);
        boolean [][] boardWidthValidPos = currentGame.getTaulell().clone();
        char [][] boardWithPos = currentGame.getBoard().getBoard().clone();
        char [][] res = new char[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                res[i][j] = boardWithPos[i][j];
                if (boardWidthValidPos[i][j]) res[i][j] = 'o';
            }
        }
        return res;
    }

    public char [][] generateBoardPersonalitzada(char [][] taulell){
        Game currentGame = currentGameSingleton.getCurrentGame();
        Board board = new Board(1);
        board.setBoard(taulell);
        currentGame.setBoard(board);
        Player player = getCurrentPlayer( board.getTurn(),currentGame);
        currentGame.calculamove(player);
        boolean [][] boardWidthValidPos = currentGame.getTaulell().clone();
        char [][] boardWithPos = currentGame.getBoard().getBoard().clone();
        char [][] res = new char[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                res[i][j] = boardWithPos[i][j];
                if (boardWidthValidPos[i][j]) res[i][j] = 'o';
            }
        }
        return res;
    }

    public boolean checkIfGameIsFinished(Game game, Player opponent){
        return game.calculamove(opponent);
    }


    public void makeBotMove(Player bot, Game game){
        boolean haveMoves = game.calculamove(bot);
        if(haveMoves) {
            Pair<Integer, Integer> jugadaBot = bot.makeYourMove(game);
            Pair<Integer, Integer> jugada = new Pair<Integer,Integer>(jugadaBot.getValue1(), jugadaBot.getValue0());
            makeTheMove(jugada, game, bot);
        }


    }

    public void moveIfIsABot(Player opponent, Game game, Board board, boolean isTurnOfBlacks){
        if(!isNotABot(opponent.getID())){

            makeBotMove(opponent, game);
            changeTurnIfOpponentHaveValidMoves(game, getOpponentPlayer(board.getTurn(), game));
        }
    }
    public void makeBotFirstMove(Game game){
        Board board = game.getBoard();
        boolean isTurnOfBlacks = board.getTurn();
        Player bot = getCurrentPlayer(isTurnOfBlacks, game);
        moveIfIsABot(bot, game, board, isTurnOfBlacks);
    }

    public boolean isGameFinished(){
        Game game = currentGameSingleton.getCurrentGame();

        boolean isFinished = !game.calculamove(game.getBlackPlayer()) && !game.calculamove(game.getWhitePlayer());


        return isFinished;
    }


    /**
     * versión de play para terminal
     * @param gameID
     * @return
     */
    public boolean play(int gameID){

        UserLogged userLogged = UserLogged.Instance();
        Persona user = userLogged.getUserLogged();
        Game game = user.getGame(gameID);
        Board board = game.getBoard();

        boolean playing = true;
        boolean blackTurn = board.getTurn();
        int finishCounter = 0;

        showWelcomeMessage();
        game.printBoard();

        while(playing){

            Player jugador;

            if(blackTurn){
                jugador = game.getBlackPlayer();
            }else{
                jugador = game.getWhitePlayer();
            }

            boolean jugadaValida = false;

            while(!jugadaValida){

                printTurn(blackTurn, jugador, game);

                boolean haveValidMoves = game.calculamove(jugador);
                game.printGameBoard(haveValidMoves);

                if(!haveValidMoves){
                    finishCounter ++;
                    break;
                }

                Pair<Boolean,Boolean> dataMove = move(jugador, game);

                jugadaValida = dataMove.getValue0();
                playing = dataMove.getValue1();

                if(!playing)break;


                finishCounter = 0;

            }

            if(playing){
                blackTurn = !blackTurn;
                board.setTurn(blackTurn);
            }
            saveBoardState(board);

            if(finishCounter == 2) playing = false;
        }

        if(finishCounter == 2){
            finishTheGame(game);

        }

        System.out.println("Saving the board state, see you !");

        return true;
    }

    private void printTurn(boolean blackTurn, Player player, Game game){

        System.out.println("--------------------------------------\n");
        Board board= game.getBoard();
        int black = board.getBlacks();
        int white = board.getWhites();
        System.out.println("Result: Blacks = " + black + " Whites = " + white);

        System.out.print("Turn of ");
        if(!blackTurn) System.out.println("whites");
        else System.out.println("blacks");
        System.out.println("Player email: " + player.getEmail());

    }


    /**
     * funcion que pide al player un movimiento y comprueba que sea valido
     * @param player
     * @param game
     * @return
     */

    private Pair<Boolean, Boolean> move(Player player, Game game){

        boolean playing = false;
        boolean jugadaValida = false;

        Pair<Integer, Integer> playerMove = player.makeYourMove(game);

        game.calculamove(player);
        int yPos = playerMove.getValue0();
        int xPos = playerMove.getValue1();

        if(xPos == -1 || yPos == -1){
            return new Pair<>(jugadaValida, playing);
        }else{
            playing = true;
        }
        jugadaValida = game.move(player, yPos, xPos);

        return new Pair<>(jugadaValida, playing);
    }


    /**
     * retorna la jugada del player
     * @param jugada
     * @param game
     * @param player
     * @return
     */

    public boolean makeTheMove(Pair<Integer,Integer> jugada, Game game, Player player){
        game.calculamove(player);
        boolean jugadaValida = game.move(player, jugada.getValue1(), jugada.getValue0());
        return jugadaValida;

    }

    /**
     * Una vez que el juego ha acabado, se actualizan los datos en bd de la partida
     * @param game
     */
    public void finishTheGame(Game game){

        Board board= game.getBoard();
        int black = board.getBlacks();
        int white = board.getWhites();
        System.out.println("The final result is: Blacks = " + black + ", Whites = " + white);

        int winnerID = game.getWinnerPlayerId();
        int loserID = game.getLoserPlayerId();

        if(winnerID != -1){
            String winnerName = updateStatistics(winnerID, true);
            updateStatistics(loserID, false);

            System.out.println("Congrats " + winnerName + " you've won the match");

        }else {
            int whitePlayerId = game.getWhitePlayer().getID();
            int blackPlayerId = game.getBlackPlayer().getID();
            updateStatistics(whitePlayerId, false);
            updateStatistics(blackPlayerId, false);

            System.out.println("Oops, it's a draw!");

        }

        updateStatisticsOfLoggedUser();

    }

    public void finishTheGame(){
        Game game = currentGameSingleton.getCurrentGame();

        Board board= game.getBoard();
        int black = board.getBlacks();
        int white = board.getWhites();
        System.out.println("The final result is: Blacks = " + black + ", Whites = " + white);

        int winnerID = game.getWinnerPlayerId();
        int loserID = game.getLoserPlayerId();

        if(winnerID != -1){
            String winnerName = updateStatistics(winnerID, true);
            updateStatistics(loserID, false);

            System.out.println("Congrats " + winnerName + " you've won the match");

        }else {
            int whitePlayerId = game.getWhitePlayer().getID();
            int blackPlayerId = game.getBlackPlayer().getID();
            updateStatistics(whitePlayerId, false);
            updateStatistics(blackPlayerId, false);

            System.out.println("Oops, it's a draw!");

        }

        updateStatisticsOfLoggedUser();

    }

    /**
     * Se actualizan las estadisticas del usuario logeado
     */

    private void updateStatisticsOfLoggedUser(){
        UserLogged userLogged = UserLogged.Instance();
        Persona user = userLogged.getUserLogged();
        int playerID = user.getID();

        ICtrlStatistics ctrlStatistics = ctrlDataFactory.getCtrlStatistics();

        try {
            Estadistiques estadistiques = ctrlStatistics.getStatistic(playerID);
            userLogged.setStatistics(estadistiques);

        } catch (StatisticDoesNotExists statisticDoesNotExists) {
            statisticDoesNotExists.printStackTrace();
        }


    }


    /**
     *
     * Se actualizan las estadisticas del usuario con id == id, en base de datos
     * @param id
     * @param isWinner
     * @return
     */

    private String updateStatistics(int id, boolean isWinner){
        ICtrlPersona ctrlPersona = ctrlDataFactory.getCtrlPersona();
        ICtrlStatistics ctrlStatistics = ctrlDataFactory.getCtrlStatistics();



        if(isNotABot(id)){
            try {
                Persona user = ctrlPersona.getPersona(id);
                if(isWinner)user.winGame();
                else user.lostGame();

                Estadistiques estadistica = user.getEstadistiques();
                ctrlStatistics.saveStatistic(estadistica.getPlayerID(), (int)estadistica.getPguanyats(), (int)estadistica.getPperduts());


                return user.getEmail();
            } catch (PersonaDoesNotExists personaDoesNotExists) {
                personaDoesNotExists.printStackTrace();
            }

        }

        return "";

    }


    private boolean isNotABot(int id){
        return !(id > 0 && id < 4);
    }

    private void showWelcomeMessage(){
        System.out.println("Welcome to Ohtello game!");
        System.out.println("If you want to quit the game, write: -1");
        System.out.println("Otherwise, print the correct pos of your play");
        System.out.println("Enjoy the GAME!");
    }

    private boolean checkIfExit(int pos){
        if(pos == -1) return false;
        return true;
    }


    public String getGames() {
        UserLogged userLogged = UserLogged.Instance();
        Persona user = userLogged.getUserLogged();
        ArrayList<Game> games = user.getGames();

        String gamesStr = "";
        if(games!= null)
            for(int i = 0; i < games.size(); i++){
                gamesStr += i + ":\n" + games.get(i).toString() + "\n";
            }
        else{
            gamesStr = "No game played yet";
        }

        return gamesStr;
    }

    public ObservableList<ContinueGame> getGamesVistes() {

        ArrayList<Game> games = userLogged.getGames();
        ObservableList<ContinueGame> list = FXCollections.observableArrayList();

        for (int i = 0; i < games.size(); ++i) {

            Game game = games.get(i);
            String white = game.getWhitePlayer().getEmail();
            String black = game.getBlackPlayer().getEmail();
            Date data = new Date(game.getDate());
            int gameID = game.getID();
            list.add(new ContinueGame(i+1, white, black, data, gameID));
        }
        return list;
    }


    public void setCurrentGame(int gameID){
        Game g = userLogged.getGame(gameID);
        currentGameSingleton.setCurrentGame(g);
    }

    public void changeCurrentBoard(Board board){
        Game g = getCurrentGame();
        int id = g.getBoard().getID();
        board.setID(id);
        g.setBoard(board);
        currentGameSingleton.setCurrentGame(g);
    }


    public void setCurrentGame(Game game){
        currentGameSingleton = CurrentGame.Instance();
        currentGameSingleton.setCurrentGame(game);
    }

    public Game getCurrentGame(){
        return currentGameSingleton.getCurrentGame();
    }

    public char[][] getBoardCurrentGame(){
        char[][] board = generateBoard();
        return board;
    }


    public void saveBoardState(Board board){
        ICtrlBoard ctrlBoard = ctrlDataFactory.getCtrlBoard();

        ctrlBoard.saveBoard(board.getID(), board.getBoard(), board.getTurn());


    }

    public Pair<String,String> getEmailPlayers(){
        Game currentGame = currentGameSingleton.getCurrentGame();
        Player white = currentGame.getWhitePlayer();
        Player black = currentGame.getBlackPlayer();
        return new Pair <String,String> (white.getEmail(), black.getEmail());

    }

    public Pair<Integer,Integer> getPuntuations(){
        Game currentGame = currentGameSingleton.getCurrentGame();
        Board board = currentGame.getBoard();
        return new Pair<Integer,Integer>(board.getWhites(), board.getBlacks());
    }

    public String getWinner(){
        Game currentGame = currentGameSingleton.getCurrentGame();
        return currentGame.getWinnerEmail();
    }

    public boolean getTurn() {
        Game currentGame = currentGameSingleton.getCurrentGame();
        return currentGame.getBoard().getTurn();
    }

    public Pair<Integer, Integer> getUltimMov () {
        Game currentGame = currentGameSingleton.getCurrentGame();
        return currentGame.getUltimMov();
    }

}
