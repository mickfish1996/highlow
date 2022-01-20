package HighLow;



import java.util.Random;

public class Dealer{
    private int card;
    private int numDraws;

    /****************************************************************************
     * Dealer
     * This function is the function that is called whenever a new instance of
     * of dealer is created it will set the value of numDraws to 0.
     ****************************************************************************/
    public Dealer(){
        this.numDraws = 0;
    }

    /****************************************************************************
     * Can Draw
     * This function will tell the director whether or not it is allowed to draw
     * a card. If it is allowed it will return True, otherwise it will return 
     * a false value.
     ****************************************************************************/
    public boolean canDraw(int points){
        boolean canDo = false;
        if (points > 0){
            canDo = true;
        }

        return canDo;
    }

    /****************************************************************************
     * Get Points
     * This function will give out the points, if the guess is correct then
     * it gives out 100 points, if it is incorrect than it will take away 75
     * points and will return those values.
     ****************************************************************************/
    public int getPoints(String guess, int lastCard){
        int points = 0;
        if (guess.equalsIgnoreCase("h") && lastCard <= this.card){
            points = 100;
        }
        else if (guess.equalsIgnoreCase("l") && lastCard >= this.card){
            points = 100;
        }
        else if (guess.equalsIgnoreCase("h") && lastCard > this.card){
            points = -75;
        }
        else if (guess.equalsIgnoreCase("l") && lastCard < this.card){
            points = -75;
        }

        return points;
    }

    /****************************************************************************
     * Draw Card
     * This function will use the Random library to creat a random number between
     * 1 and 13, it will then set card to that value, and increment numDraws by 
     * one.
     ****************************************************************************/
    public void drawCard(){
        Random result = new Random();

        this.card = result.nextInt(13) + 1;
        this.numDraws++;
    }

    /****************************************************************************
     * Get Num Draws
     * This function will return the value of numDraws to the function that 
     * calls it. 
     ****************************************************************************/
    public int getNumDraws(){
        return this.numDraws;
    }
    
    /****************************************************************************
     * Get Card
     * This function will return the value of card to the function that calls it.
     ****************************************************************************/
    public int getCard(){
        return this.card;
    }

}