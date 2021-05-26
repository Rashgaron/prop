
package UI.Vistas.Controllers.helpers;


import java.util.Date;

public class ContinueGame {

    int position, gameID;
    String white, black;
    Date data;

    public ContinueGame(int position, String white, String black, Date data, int gameID){
        this.position = position;
        this.white = white;
        this.black = black;
        this.data = data;
        this.gameID = gameID;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setWhite(String white) {
        this.white = white;
    }

    public String getWhite() {
        return white;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public String getBlack() {
        return black;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }
    
}
