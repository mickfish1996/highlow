package HighLow;

import java.util.*;
import java.io.*;

public class Director{
    private boolean keepPlaying;
    private int score;
    private Dealer dealer;
    private String guess;
    private int lastCard;
    private HashMap<String,Integer> saves = new HashMap<String,Integer>();

    /****************************************************************************
     * Director
     * This is the fuction that is called whenever an instance of this class is
     * created it sets keepPlaying to true, score to 300 and creats and instance
     * of Dealer.
     ****************************************************************************/
    public Director(){
        this.keepPlaying = true;
        this.score = 300;
        this.dealer = new Dealer();
    }
    /****************************************************************************
     * Start Game
     * This function will prompt the user and see if they would like to pull a 
     * previous score from a file, if they say yes than it prompts for the 
     * filename that they would like to use, and will then call readFile, then
     * after that it starts a while loop using keepPlaying and calls doOutputs.
     ****************************************************************************/
    public void startGame(){
        readFile();
        Scanner userIn = new Scanner(System.in);
        if (!saves.isEmpty()) {
            System.out.print("Would you like to load a save? [y/n]: ");
            String answer = userIn.nextLine();

            if (answer.equalsIgnoreCase("y")) {
                System.out.println();
                
                for (Map.Entry<String, Integer> key : saves.entrySet()){
                    System.out.println(key.getKey());
                }
                System.out.print("\nEnter the save name: ");
                String saveName = userIn.nextLine();
                try {
                    this.score = saves.get(saveName);
                } catch (Exception e) {}

                System.out.println("Yout score is: " + this.score);
                
            }
        }
        while (this.keepPlaying){
            doOutputs();
        }
    }
    /****************************************************************************
     * Do Outpus
     * This function will draw a card, then display it, it will prompt for a 
     * guess from the user, it will then set that value to guess, it will display 
     * your score and the new card. Then it checks if you can keep playing if you 
     * can it will ask if you want to keep playing if yes than the cycle repeats,
     * if no than it will ask if you want to save if yes it will prompt for file
     * name to save it to and then it will call writeFile.
     ****************************************************************************/
    private void doOutputs(){
        drawCard(); // draws card
        displayCard(); // displays card
        this.guess = getInput(); // receives the input
        this.lastCard = this.dealer.getCard(); // sets the current card to last card
        drawCard(); // draws a new card
        doUpdates(); // gets the score
        displayCard(); // displays the new card.

        System.out.println("Your score is: " + this.score + "\n");
        String choice = "";

        // This will check and see if the user can keep playing, if canDraw returns false
        // than it will set keepPlaying to False and print Game Over!
        if (!this.dealer.canDraw(this.score)){
            this.keepPlaying = false;
            System.out.println("Game Over!\n");

        } 
        // This else prompts the user to see if they would like to keep playing.
        else {
            Scanner userIn = new Scanner(System.in);
            System.out.print("Keep playing? [y/n]: ");

            choice = userIn.nextLine();        
            
        }

        // if the user enters y and they have a score larger than zero it will let them keep
        // playing.
        if (this.score > 0 && choice.equalsIgnoreCase("y")){
            this.keepPlaying = true;
        } 
        // This else will trigger if the score is less then zero of the user enter n.
        else {
            this.keepPlaying = false;

            Scanner userIn = new Scanner(System.in);

            // this if statement makes it so that only those who actually can save the
            // game are given the option too.
            if (this.score > 0){
                System.out.print("Would you like to save your game? [y/n]: ");
                String userAnswer = userIn.nextLine();

                // if the user wants to save than it will prompt for a file name from them.
                if (userAnswer.equalsIgnoreCase("y")){
                    System.out.print("Please Enter the name you would like to save as: ");
                    String saveName = userIn.nextLine();
                    saveFile(saveName);
                    writeFile();
                    System.out.println("\nYour game has been saved!\n");
                }
            }

            

        }
    }

    /****************************************************************************
     * Do Updates
     * This value will call getPoints from the dealer and will send guess and 
     * last card and will get the value of points back.
     ****************************************************************************/
    private void doUpdates(){
        int points = this.dealer.getPoints(this.guess, this.lastCard);

        this.score += points;
    }

    /****************************************************************************
     * Display Card
     * This function will determine what value to actually display to the screen
     * for the card. If the value is 10 or lower it will just show the number, if
     * the card is 11 or higher, it will display the value of the face card.
     ****************************************************************************/
    private void displayCard(){
        int card = dealer.getCard();

        if (card < 11){
            System.out.println("\nThe card is: " + card);
        }
        else if (card == 11){
            System.out.println("\nThe card is: Jack");
        }
        else if (card == 12){
            System.out.println("\nThe card is: Queen");
        }
        else if (card == 13){
            System.out.println("\nThe card is: King");
        }
    }
    /****************************************************************************
     * Draw card
     * This function will call the function draw card from the dealer.
     ****************************************************************************/
    private void drawCard(){
        this.dealer.drawCard();
    }

    /****************************************************************************
     * Get Input
     * This function will prompt the user if they think that the next card will
     * be higher or lower than the current card. it will then get that guess 
     * and return it to the function that calls it. 
     ****************************************************************************/
    private String getInput(){
        Scanner userIn = new Scanner(System.in);

        System.out.print("Higher or Lower? [h/l]: ");
        String guess = userIn.nextLine();



        return guess;
    }

    /****************************************************************************
     * Read File
     * This function will take the read from the file saves.txt, if there is data
     * in saves, it will load that data from the file and then store it in 
     * the hash map saves.
     ****************************************************************************/
    private void readFile(){
        String filePath = ".\\HighLow\\saves.txt";
        BufferedReader reader = null;
        try {
            File file = new File(filePath);
            
            reader = new BufferedReader(new FileReader(file));
            
            String line = null;

            while ((line = reader.readLine()) != null){
                // split the line up
                String[] splitLine = line.split(":");

                // first part is name of save, second it the score value
                String name = splitLine[0].trim();
                int score = Integer.valueOf(splitLine[1].trim());

                if (!name.equals("")) {
                    this.saves.put(name, score);
                }

            }
            

            
        } catch (Exception e){
            //e.printStackTrace();

        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (Exception e){};
            }
        }
    }

    /****************************************************************************
     * Write File
     * This function will write the value of saves to a txt file, in the format
     * of saveName:scoreValue so that it can be read later.
     ****************************************************************************/
    private void writeFile(){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(".\\HighLow\\saves.txt"));

            for (Map.Entry<String, Integer> entry : saves.entrySet())
            {
                writer.write(entry.getKey() + ":" + entry.getValue());

                writer.newLine();
            }

            writer.flush();

        } catch (IOException e){
            e.printStackTrace();      
        } finally {
            try {
                writer.close();
            } catch (Exception e) {}
        }
    }
    /****************************************************************************
     * Save File
     * What this function does is Add the key value pair to the hash map, it 
     * takes the name of the save that was enterd and it adds that and the 
     * current score to the hashmap.
     ****************************************************************************/
    private void saveFile(String saveName){
        this.saves.put(saveName,this.score);
    }
}