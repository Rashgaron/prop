package DataBase.Controllers;

import Domain.DataControllers.ICtrlBoard;
import Domain.Models.Game.Board;
import Exceptions.BoardDoesNotExists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Daniel Rodriguez
 */
public class CtrlBoard implements ICtrlBoard {
    @Override
    public boolean saveBoard(int boardID, char[][] boardState, boolean turn) {
        JSONObject board = new JSONObject();

        String boardStateString = transformMatToString(boardState);
        board.put("boardID", boardID);
        board.put("boardState", boardStateString);
        board.put("turn", turn);

        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("Board.json")){

            Object obj = jsonParser.parse(reader);

            JSONArray Boards = (JSONArray) obj;

            int index = findBoard(boardID, Boards);

            if(index != -1)
            Boards.remove(index);

            Boards.add(board);



            writeFile(Boards, "Board.json");

            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            initBoardFile();
            saveBoard(boardID, boardState, turn);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            initBoardFile();
            saveBoard(boardID, boardState, turn);
        }

        return false;
    }


    private String transformMatToString(char [][] boardState){
        String result = "";
        for(int i = 0; i < 8; i++ ){

            for(int j = 0; j < 8 ; j++){
                result +=boardState[i][j];
            }

        }
        return result;
    }

    @Override
    public Board getBoard(int boardID) throws BoardDoesNotExists {

        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader("Board.json")){
            Object obj = jsonParser.parse(reader);
            JSONArray Boards = (JSONArray) obj;

            int index = findBoard(boardID, Boards);

            if(index == -1){
                throw new BoardDoesNotExists("Board does not exists");
            }else{
                return createBoard(Boards, index);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int findBoard(int boardID, JSONArray data){
        int index = -1;

        for(int i = 0; i < data.size(); i++){

            JSONObject board = (JSONObject) data.get(i);
            int boardDBID = Integer.parseInt(String.valueOf(board.get("boardID")));

            if(boardDBID == boardID){
                index = i;
                break;
            }
        }
        return index;
    }

    public boolean deleteBoard(int boardID){
        JSONParser jsonParser = new JSONParser();
        boolean deleted = false;

        try(FileReader reader = new FileReader("Board.json")){
            Object obj = jsonParser.parse(reader);
            JSONArray Boards = (JSONArray) obj;

            int index = findBoard(boardID, Boards);
            if(index != -1){

                Boards.remove(index);
                deleted = true;

            }

            writeFile(Boards,"Board.json");
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

    private void writeFile(JSONArray data, String fileName) throws IOException {
        FileWriter file = new FileWriter(fileName);
        file.write(data.toJSONString());
        file.flush();
    }

    private void initBoardFile(){
        FileWriter file = null;
        try {
            file = new FileWriter("Board.json");
            file.write("[]");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Board createBoard(JSONArray Boards, int index){
        JSONObject boardDB = (JSONObject) Boards.get(index);
        Board board = null;

        int boardID = Integer.parseInt(String.valueOf(boardDB.get("boardID")));
        boolean turn = Boolean.parseBoolean(String.valueOf(boardDB.get("turn")));
        String boardStateString = (String) boardDB.get("boardState");
        char [][] boardState = parseBoardStateStringToMatrix(boardStateString);

        board = new Board(boardID, boardState, turn);

        return board;

    }

    private char[][] parseBoardStateStringToMatrix(String boardStateString){
        char [][] boardState = new char[8][8];
        int index = 0;
        for(int i = 0; i < 8 ; i++){
            for(int j = 0; j < 8 ; j++){
                boardState[i][j] = boardStateString.charAt(index);
                index ++;
            }
        }
        return boardState;
    }

}
