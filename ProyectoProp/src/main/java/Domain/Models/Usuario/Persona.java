package Domain.Models.Usuario;

import Domain.Models.Game.Game;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Herencia de player
 * @author Daniel Rodriguez
 */

public class Persona extends Player{

    private String email;
    private String password;
    private Estadistiques estadistiques;
    private ArrayList <Game> games = new ArrayList<Game>();

    /**
     * Constructora
     * @param ID
     * @param email
     * @param password
     */
    public Persona(int ID, String email, String password) {
        super(ID);
        this.email = email;
        this.password = password;
        games = new ArrayList<Game>();
    }

    /**
     * Dado un indice, se retorna el game
     * @param gameSelected
     * @return
     */
    public Game getSelectedGame(int gameSelected){
        if(gameSelected > games.size()-1)
            return null;

        return games.get(gameSelected);
    }

    /**
     * Se retorna un game según su id
     * @param gameID
     * @return
     */
    public Game getGame(int gameID){
        for(int i = 0; i < games.size(); i++){
            Game game = games.get(i);
            if(game.getID() == gameID)
                return games.get(i);
        }
        return null;
    }

    /**
     * Reimplementación de makeYourMove en la clase Player
     * El jugador intenta hacer un movimiento y lo retorna
     * @param game
     * @return
     * @throws InputMismatchException
     */
    @Override
    public Pair<Integer, Integer> makeYourMove(Game game) throws InputMismatchException{

        Scanner scan = new Scanner(System.in);
        int exit = -1;

        System.out.println("Please, enter the pos of your play.");

        System.out.println("x: ");
        int xPos = scan.nextInt();
        if(checkIfExit(xPos)) return new Pair<>(exit, exit);

        System.out.println("y: ");
        int yPos = scan.nextInt();

        return new Pair<>(yPos, xPos);

    }

    /**
     * Se elimina el game con gameID == gameID
     * @param gameID
     * @return
     */
    public boolean deleteGame(int gameID){
        for(int i = 0; i < games.size(); i++){
            Game game = games.get(i);
            if(gameID == game.getID()){
                games.remove(i);
                return true;
            }
        }
        return false;
    }

    private boolean checkIfExit(int pos){
        return pos == -1;
    }


    public void setGames(ArrayList<Game> games){this.games = games;}
    public ArrayList<Game> getGames(){return games;}

    public void addGame(Game game){this.games.add(game);}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Estadistiques getEstadistiques() {
        return estadistiques;
    }

    public void setEstadistiques(Estadistiques estadistiques) {
        this.estadistiques = estadistiques;
    }

    public void winGame(){
        estadistiques.addPguanyat();
    }
    public void lostGame(){
        estadistiques.addPperdut();
    }
}
