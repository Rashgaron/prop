package Domain.Models.Usuario;

import Domain.Models.Game.*;
import org.javatuples.Pair;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Classe Bot
 * @Author Arnau Llobet Massallé
 */

public class Bot extends Player{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private Game game;
    private int maxDepth;

    public Bot(int ID, int BotLvl, Game game){
        super(ID);
        maxDepth = BotLvl;
        this.game = game;
    }

    /**
     * Funció bestMove
     *  Retorna un pair del millor moviment possible segons l'algorisme de MiniMax i la profunditat del Bot.
     */
    public Pair<Integer,Integer> bestMove(Game g) {
        return maxValue(g, 0).getValue1();
    }
    /**
     * Funció maxValue
     *  Funció que forma part de l'algorisme MiniMax. Consisteix en maximitzar l'heurístic a partir
     *  de tots els moviments moviments possibles.
     */
    private Pair <Pair <Integer, Integer>, Pair <Integer, Integer>> maxValue(Game g, int depth) {
        if (depth == maxDepth || g.esTerminal()) {
            return new Pair <> (g.getUltimMov(), g.getMovimentPare());
        }
        char col = 'W';
        if (g.getBoard().getTurn()) col = 'B';
        LinkedList<Game> successors;
        if (col == 'W') successors = g.getSuccessors(g.getWhitePlayer(), depth);
        else successors = g.getSuccessors(g.getBlackPlayer(), depth);
        int maxValue = Integer.MIN_VALUE;
        Pair<Integer,Integer> move;
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> res = new Pair<> (new Pair <> (-1, -1), new Pair <> (-1, -1));
        for (Game fill : successors) {
            move = minValue(fill, depth + 1).getValue0();
            Game nouJoc = new Game(fill);
            if (col == 'W') nouJoc.move(g.getBlackPlayer(), move.getValue0(), move.getValue1());
            else nouJoc.move(g.getWhitePlayer(), move.getValue0(), move.getValue1());
            int h = nouJoc.heuristic(col);
            if (h >= maxValue) {
                maxValue = h;
                res =  new Pair<> (move, nouJoc.getMovimentPare());
            }
        }
        if (successors.size() == 0) return new Pair<> (g.getUltimMov(), g.getMovimentPare());
        return res;
    }
    /**
     * Funció minValue
     *  Funció que forma part de l'algorisme MiniMax. Consisteix en minimitzar l'heurístic a partir
     *  de tots els moviments moviments possibles.
     */
    private Pair <Pair <Integer, Integer>, Pair <Integer, Integer>> minValue(Game g, int depth) {
        if (depth == maxDepth || g.esTerminal()) {
            return new Pair <> (g.getUltimMov(), g.getMovimentPare());
        }
        char col = 'B';
        if (!g.getBoard().getTurn()) col = 'W';
        LinkedList<Game> successors;
        if (col == 'W') successors = g.getSuccessors(g.getWhitePlayer(), depth);
        else successors = g.getSuccessors(g.getBlackPlayer(), depth);
        int minValue = Integer.MAX_VALUE;
        Pair<Integer,Integer> move;
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> res = new Pair<> (new Pair <> (-1, -1), new Pair <> (-1, -1));
        for (Game fill : successors) {
            move = maxValue(fill, depth + 1).getValue0();
            Game nouJoc = new Game(fill);
                if (col == 'W') nouJoc.move(g.getWhitePlayer(), move.getValue0(), move.getValue1());
            else nouJoc.move(g.getBlackPlayer(), move.getValue0(), move.getValue1());
            int h = nouJoc.heuristic(col);
            if (h <= minValue) {
                minValue = h;
                res =  new Pair<> (move, nouJoc.getMovimentPare());
            }

        }
        if (successors.size() == 0) return new Pair<> (g.getUltimMov(), g.getMovimentPare());
        return res;
    }

    public Pair<Integer,Integer> bestMovePoda(Game g) {
        return maxValuePoda(g, Integer.MIN_VALUE, Integer.MAX_VALUE, 0).getValue1();
    }

    private Pair <Pair <Integer, Integer>, Pair <Integer, Integer>> maxValuePoda(Game g, int a, int b, int depth) {
        if (depth == maxDepth || g.esTerminal()) {
            return new Pair <> (g.getUltimMov(), g.getMovimentPare());
        }
        char col = 'W';
        if (g.getBoard().getTurn()) col = 'B';
        LinkedList<Game> successors;
        if (col == 'W') successors = g.getSuccessors(g.getWhitePlayer(), depth);
        else successors = g.getSuccessors(g.getBlackPlayer(), depth);
        int maxValue = Integer.MIN_VALUE;
        Pair<Integer,Integer> move;
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> res = new Pair<> (new Pair <> (-1, -1), new Pair <> (-1, -1));
        for (Game fill : successors) {
            move = minValue(fill, depth + 1).getValue0();
            Game nouJoc = new Game(fill);
            if (col == 'W') nouJoc.move(g.getBlackPlayer(), move.getValue0(), move.getValue1());
            else nouJoc.move(g.getWhitePlayer(), move.getValue0(), move.getValue1());
            int h = nouJoc.heuristic(col);
            if (h >= maxValue) {
                maxValue = h;
                res =  new Pair<> (move, nouJoc.getMovimentPare());
                if (h >= b) return res;
            }
            a = Math.max(a, h);
        }
        if (successors.size() == 0) return new Pair<> (g.getUltimMov(), g.getMovimentPare());
        return res;
    }
    /**
     * Funció minValue
     *  Funció que forma part de l'algorisme MiniMax. Consisteix en minimitzar l'heurístic a partir
     *  de tots els moviments moviments possibles.
     */
    private Pair <Pair <Integer, Integer>, Pair <Integer, Integer>> minValuePoda(Game g, int a, int b, int depth) {
        if (depth == maxDepth || g.esTerminal()) {
            return new Pair <> (g.getUltimMov(), g.getMovimentPare());
        }
        char col = 'W';
        if (!g.getBoard().getTurn()) col = 'B';
        LinkedList<Game> successors;
        if (col == 'W') successors = g.getSuccessors(g.getBlackPlayer(), depth);
        else successors = g.getSuccessors(g.getWhitePlayer(), depth);
        int minValue = Integer.MAX_VALUE;
        Pair<Integer,Integer> move;
        Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> res = new Pair<> (new Pair <> (-1, -1), new Pair <> (-1, -1));
        for (Game fill : successors) {
            move = maxValue(fill, depth + 1).getValue0();
            Game nouJoc = new Game(fill);
            if (col == 'W') nouJoc.move(g.getWhitePlayer(), move.getValue0(), move.getValue1());
            else nouJoc.move(g.getBlackPlayer(), move.getValue0(), move.getValue1());
            int h = nouJoc.heuristic(col);
            if (h <= minValue) {
                minValue = h;
                res =  new Pair<> (move, nouJoc.getMovimentPare());
                if (h <= a) return res;
            }
            b = Math.min(b, h);
        }
        if (successors.size() == 0) return new Pair<> (g.getUltimMov(), g.getMovimentPare());
        return res;
    }

    private void showMessage(int random){
        System.out.println("");
        System.out.println(ANSI_RED);
        switch(random){
            case 1:
                System.out.println("Let me think ...");
                break;
            case 2:
                System.out.println("Awesome move !");
                break;
            case 3:
                System.out.println("Sure of that??");
                break;
            case 4:
                System.out.println("Ouch ....");
                break;
            case 5:
                System.out.println("That's all?");
                break;
            case 6:
                System.out.println("Bot always win HAHA");
                break;
            default:
                System.out.println("Cool ...");
                break;
        }
        System.out.println(ANSI_RESET);
    }

    @Override
    public Pair<Integer, Integer> makeYourMove(Game game) {

        Random r = new Random();
        int randomNumber = r.nextInt(6) + 1;
        showMessage(randomNumber);

        return bestMovePoda(game);

    }

    @Override
    public String getEmail(){
        return "Bot";
    }

    public int getBotLvl() {
        return maxDepth;
    }

    public Game getGame() {
        return game;
    }

    public void setBotLvl(int botLvl) {
        maxDepth = botLvl;
    }
}
