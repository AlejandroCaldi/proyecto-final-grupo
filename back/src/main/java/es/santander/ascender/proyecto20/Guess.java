package es.santander.ascender.proyecto20;

public class Guess {
    private long sessionID;
    private int guess;
    
    public long getSessionID() {
        return sessionID;
    }
    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }
    public int getGuess() {
        return guess;
    }
    public void setGuess(int guess) {
        this.guess = guess;
    }

}
