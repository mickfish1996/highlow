package HighLow;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


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
            
        }

        if (this.score > 0 && choice.equalsIgnoreCase("y")){
            this.keepPlaying = true;
        } else {
            this.keepPlaying = false;

            Scanner userIn = new Scanner(System.in);
            System.out.print("Would you like to save your game? ");
            String userAnswer = userIn.nextLine();

            if (userAnswer.equalsIgnoreCase("y")){
                System.out.print("Please Enter the name you would like to save as: ");
                String fileName = userIn.nextLine();
                writeFile(fileName);
            }

            

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



        return guess;
    }

    /****************************************************************************
     * 
     ****************************************************************************/
    private void readFile(){
        try {
            File file = new File("save.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                this.score = reader.nextInt();
            }
            reader.close();
        } catch (FileNotFoundException e){
            System.out.println("An Error occured.");
            e.printStackTrace();
        }
    }

    /****************************************************************************
     * 
     ****************************************************************************/
    private void writeFile(String fileName){
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(String.valueOf(this.score));
            writer.close();
            System.out.println("Save successful.");
        } catch (IOException e){
            System.out.println("An Error occurred.");
            e.printStackTrace();
        }
    }
}