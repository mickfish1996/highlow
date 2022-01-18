package HighLow;



public class Hilo{

    public static void main(String[] args){
        Dealer deal = new Dealer();

        boolean canDo = deal.canDraw(300);
        System.out.println(canDo);
    }
}