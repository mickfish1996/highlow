package HighLow;



import java.util.Random;

public class Dealer{
    private int card;
    private int numDraws;

    public Dealer(){
        this.numDraws = 0;
    }

    public boolean canDraw(int points){
        boolean canDo = false;
        if (points <= 0){
            canDo = true;
        }

        return canDo;
    }

    public int getPoints(String guess, int lastCard){
        int points = 0;
        if (guess == "h" && lastCard <= this.card){
            points = 100;
        }
        else if (guess == "l" && lastCard >= this.card){
            points = 100;
        }
        else if (guess == "h" && lastCard > this.card){
            points = -75;
        }
        else if (guess == "l" && lastCard < this.card){
            points = -75;
        }

        return points;
    }

    public void drawCard(){
        Random result = new Random();

        this.card = result.nextInt(13) + 1;
        this.numDraws++;
    }

    public int getNumDraws(){
        return this.numDraws;
    }

}