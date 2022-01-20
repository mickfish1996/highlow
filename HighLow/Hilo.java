package HighLow;



public class Hilo{

    /****************************************************************************
     * Main
     * This function calls the start game method from director and will run
     * the game.
     ****************************************************************************/
    public static void main(String[] args){
        Director director = new Director();
        director.startGame();
    }
}