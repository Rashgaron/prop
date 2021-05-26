package DataBase.Controllers;

import Domain.DataControllers.ICtrlGame;
import Domain.Models.Game.Board;
import Domain.Models.Game.Game;
import Domain.Models.Usuario.Persona;
import Domain.Models.Usuario.Player;
import Exceptions.BoardDoesNotExists;
import Exceptions.GameDoesNotExists;
import Exceptions.PersonaDoesNotExists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Rodriguez
 */
public class CtrlGame implements ICtrlGame {
    @Override

    public void saveGame(int gameID, long data, int boardID, int blackPlayerID, int whitePlayerID) {

        JSONObject newGame = new JSONObject();

        newGame.put("gameID", gameID);
        newGame.put("boardID", boardID);
        newGame.put("data", data);
        newGame.put("blackPlayerID", blackPlayerID);
        newGame.put("whitePlayerID", whitePlayerID);

        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("Game.json")){

            Object obj = jsonParser.parse(reader);

            JSONArray Games = (JSONArray) obj;
            Games.add(newGame);
            writeFile(Games, "Game.json");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            initGameFile();

            saveGame(gameID, data, boardID, blackPlayerID, whitePlayerID);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            initGameFile();

            saveGame(gameID, data, boardID, blackPlayerID, whitePlayerID);

        }


    }
    @Override
    public ArrayList<Game> getAllGames() {
        ArrayList<Game> games = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        Persona dummyPersona = new Persona(-1, "dummy", "dummy");

        try(FileReader reader = new FileReader("Game.json")){
           Object obj = jsonParser.parse(reader);
           JSONArray Games = (JSONArray) obj;
           for( int i = 0 ; i < Games.size(); i ++){

               Game g = createGame(Games, i, dummyPersona);
               games.add(g);
           }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return games;
    }

    @Override
    public ArrayList<Game> getPersonaGames(Persona user) {
        ArrayList<Game> games = new ArrayList<Game>();

        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("Game.json")){

            Object obj = jsonParser.parse(reader);
            JSONArray Games = (JSONArray) obj;

            for( int i = 0; i < Games.size(); i++){

                JSONObject game = (JSONObject) Games.get(i);
                int idBlackPlayer = Integer.parseInt(String.valueOf(game.get("blackPlayerID")));
                int idWhitePlayer = Integer.parseInt(String.valueOf(game.get("whitePlayerID")));
                int idPersona = user.getID();
                if(idPersona == idBlackPlayer || idPersona == idWhitePlayer){
                    Game g = createGame(Games, i, user);
                    games.add(g);
                }

            }
            return games;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Game file is not init");
            initGameFile();
            getPersonaGames(user);

        }

        return null;
    }

    @Override
    public boolean existsGame(int gameID) {
        boolean exists = false;

        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("Game.json")){

            Object obj = jsonParser.parse(reader);
            JSONArray Games = (JSONArray) obj;
            exists = findGame(gameID, Games) != -1 ? true: false;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            initGameFile();
            existsGame(gameID);
        }

        return exists;

    }

    @Override
    public boolean deleteGame(int gameID) {

        JSONParser jsonParser = new JSONParser();
        boolean deleted = false;

        try(FileReader reader = new FileReader("Game.json")){

            Object obj = jsonParser.parse(reader);
            JSONArray Games = (JSONArray) obj;

            int index = findGame(gameID, Games);
            if(index != -1){
                Games.remove(index);
                deleted = true;
            }
            writeFile(Games, "Game.json");
            return deleted;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;

    }



    private int findGame(int gameID, JSONArray Games){
        int index = -1;

        for(int i = 0; i < Games.size(); i++){

            JSONObject game = (JSONObject) Games.get(i);
            int dbGameID = Integer.parseInt(String.valueOf(game.get("gameID")));

            if(dbGameID == gameID){
                index = i;
                break;
            }
        }
        return index;
    }


    private void initGameFile(){
        try{
            FileWriter file = new FileWriter("Game.json");
            file.write("[]");
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void writeFile(JSONArray data, String fileName) throws IOException {
        FileWriter file = new FileWriter(fileName);
        file.write(data.toJSONString());
        file.flush();
    }

    private Game createGame(JSONArray Games, int index, Player user){

        CtrlPersona ctrlPersona = new CtrlPersona();


        JSONObject game = (JSONObject) Games.get(index);

        int gameID = Integer.parseInt(String.valueOf(game.get("gameID")));
        int boardID = Integer.parseInt(String.valueOf(game.get("boardID")));
        long localDate = Long.parseLong(String.valueOf(game.get("data")));
        int blackPlayerID = Integer.parseInt(String.valueOf(game.get("blackPlayerID")));
        int whitePlayerID = Integer.parseInt(String.valueOf(game.get("whitePlayerID")));

        Player blackPlayer, whitePlayer;

        if(user.getID() == blackPlayerID){
            blackPlayer = user;
        }else {
            try {
                blackPlayer = ctrlPersona.getPersonaOnly(blackPlayerID);
            } catch (PersonaDoesNotExists personaDoesNotExists) {
                blackPlayer = null;
            }
        }

        if(user.getID() == whitePlayerID){
            whitePlayer = user;
        }else {
            try {
                whitePlayer = ctrlPersona.getPersonaOnly(whitePlayerID);
            } catch (PersonaDoesNotExists personaDoesNotExists) {
                whitePlayer = null;
            }
        }
        CtrlBoard ctrlBoard = new CtrlBoard();

        try {
            Board board = ctrlBoard.getBoard(boardID);

            Game resultGame = new Game(gameID, localDate, boardID, blackPlayer, whitePlayer);
            resultGame.setBoard(board);

            return resultGame;

        } catch (BoardDoesNotExists boardDoesNotExists) {
            boardDoesNotExists.printStackTrace();
        }

        return null;

    }
}
