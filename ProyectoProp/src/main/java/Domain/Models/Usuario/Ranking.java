/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Models.Usuario;


public class Ranking {
    
    int position;
    String email;
    float wins, losses, winratio;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Ranking(int position, String email, float wins, float losses, float winratio) {
        this.position = position;
        this.email = email;
        this.wins = wins;
        this.losses = losses;
        this.winratio = winratio;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public float getWins() {
        return wins;
    }

    public void setWins(float wins) {
        this.wins = wins;
    }

    public float getLosses() {
        return losses;
    }

    public void setLosses(float losses) {
        this.losses = losses;
    }

    public float getWinratio() {
        return winratio;
    }

    public void setWinratio(float winratio) {
        this.winratio = winratio;
    }
    
}
