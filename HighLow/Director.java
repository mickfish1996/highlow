package HighLow;

import java.util.Scanner;

public class Director{
    private boolean keepPlaying;
    private int score;
    private Dealer dealer;
    private String guess;
    private int lastCard;

    /****************************************************************************
     * 
     ****************************************************************************/
    public Director(){
        this.keepPlaying = true;
        this.score = 300;
        this.dealer = new Dealer();
    }
    /****************************************************************************
     * 
     ****************************************************************************/
    public void startGame(){
        while (this.keepPlaying){
            doOutputs();
        }
    }
    /****************************************************************************
     * 
     ****************************************************************************/
    private void doOutputs(){
        drawCard();
        displayCard();
        this.guess = getInput();
        this.lastCard = this.dealer.getCard();
        drawCard();
        doUpdates();
        displayCard();

        System.out.println("Your score is: " + this.score + "\n");
        String choice = "";
        if (!this.dealer.canDraw(this.score)){
            this.keepPlaying = false;
            System.out.println("Game Over!\n");
        } else {
            Scanner userIn = new Scanner(System.in);
            System.out.print("Keep playing? [y/n]: ");

            choice = userIn.nextLine();        
            
            userIn.close();
        }

        if (this.score > 0 && choice.equalsIgnoreCase("y")){
            this.keepPlaying = true;
        } else {
            this.keepPlaying = false;
        }
    }

    /****************************************************************************
     * 
     ****************************************************************************/
    private void doUpdates(){
        int points = this.dealer.getPoints(this.guess, this.lastCard);

        this.score += points;
    }
    /****************************************************************************
     * 
     ****************************************************************************/
    private void displayCard(){
        int card = dealer.getCard();

        if (card < 11){
            System.out.println("The card is: " + card);
        }
        else if (card == 11){
            System.out.println("The card is: Jack");
        }
        else if (card == 12){
            System.out.println("The card is: Queen");
        }
        else if (card == 13){
            System.out.println("The card is: King");
        }
    }
    /****************************************************************************
     * 
     ****************************************************************************/
    private void drawCard(){
        this.dealer.drawCard();
    }

    /****************************************************************************
     * 
     ****************************************************************************/
    private String getInput(){
        Scanner userIn = new Scanner(System.in);

        System.out.print("Higher or Lower? [h/l]: ");
        String guess = userIn.nextLine();

        userIn.close();

        return guess;
    }
}