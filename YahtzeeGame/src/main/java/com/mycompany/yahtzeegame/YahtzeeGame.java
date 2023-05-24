/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.yahtzeegame;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Gebruiker
 */

public class YahtzeeGame {
    
    public static void main(String[] args) {
        Player[] players = getPlayers();
        //System.out.println(Arrays.toString(players));
        playGame(players);
    }

    private static Player[] getPlayers() {
        int playerAmount = inputAmountPlayers();
        Player players[] = new Player[playerAmount];
        for (int i=0; i<playerAmount; i++){
            players[i] = new Player(i);
        }
        return players;
    }
    
    private static int inputAmountPlayers() {
        String s = JOptionPane.showInputDialog("Insert the amount of players.");
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) { // De Integer.parseInt(s) is mislukt.
            System.out.println("Sorry '" + s + "' is not an amount of players!");
            return inputAmountPlayers();
        } 
    }

    public static void playGame(Player[] players) {
        for (int i = 0; i<2; i++){
            System.out.println("Ronde " + (i + 1));
            for (Player aPlayer: players) {
                doPlayerRound(aPlayer);
            }
        } 
    }

    public static void doPlayerRound(Player aPlayer) {
        System.out.println("The round of player: "+ (aPlayer.name));
        ArrayList<Integer> diceFromRound = new ArrayList();
        int roleAmount = 5;
        for (int dice_throw=0; dice_throw<3; dice_throw++){
            ArrayList<Integer> rollResult = roleDice(roleAmount);
            
            if (dice_throw == 0){
                diceFromRound.addAll(chooseDice(rollResult, diceFromRound));
                roleAmount -= diceFromRound.size();
                System.out.println(diceFromRound);
                System.out.println(roleAmount);
            }
            
            else if (dice_throw == 1){
                diceFromRound.addAll(chooseDice(rollResult, diceFromRound));
                System.out.println(diceFromRound);
                diceFromRound = chooseDice2Reroll(diceFromRound);
                roleAmount = 5 - diceFromRound.size();
                System.out.println(diceFromRound);
                System.out.println(roleAmount);
            }
            
            else {
                diceFromRound.addAll(rollResult);
                System.out.println(diceFromRound);
//                chooseScore();
            }
        }
    }
    
    public static ArrayList<Integer> roleDice(int roleAmount){
        int min = 1;
        int max = 6;
        ArrayList<Integer> roleArray = new ArrayList(roleAmount);
        for (int i = 0; i < roleAmount; i++){
            int role = (int) (Math.random() * (max - min + 1) + min);  
            roleArray.add(role);
        }
        return roleArray;
    }  

    private static ArrayList<Integer> chooseDice(ArrayList<Integer> rollResult, ArrayList<Integer> diceFromRound) {
        String s = inputChooseDice(rollResult, diceFromRound);
        char[] diceArray = s.toCharArray();
        ArrayList<Integer> roundResults = new ArrayList();
        for (int i = 0; i<rollResult.size();i++){
            if (diceArray[i] == '+'){
                roundResults.add(rollResult.get(i));
            } 
        }
        return roundResults;
    }

    private static String inputChooseDice(ArrayList<Integer> rollResult, ArrayList<Integer> diceFromRound) {
        if (diceFromRound.isEmpty()){
            String s = JOptionPane.showInputDialog("U heeft gegooid: " + 
                rollResult.toString() + " welke worp/worpen wilt u houden?" +
                "\n\nBijvoorbeeld -++-- houdt alleen dobbelsteen 2 en 3 vast, 1, 4 en 5 gooit dan opnieuw."
            );
            if (s == null) return null;  // Als op cancel gedrukt is: stoppen.
            return s;
        } else {
            String s = JOptionPane.showInputDialog("U heeft gegooid: " + 
                rollResult.toString() + ".\nVorige ronde heeft u gegooid: " + diceFromRound.toString() + ".\n Welke worp/worpen wilt u houden?" +
                "\n\nBijvoorbeeld -++-- houdt alleen dobbelsteen 2 en 3 vast, 1, 4 en 5 gooit dan opnieuw."
            );
            if (s == null) return null;  // Als op cancel gedrukt is: stoppen.
            return s;

        }
    }

    private static ArrayList<Integer> chooseDice2Reroll(ArrayList<Integer> diceFromRound) {
        String s = inputChooseDice2Reroll(diceFromRound);
                char[] diceArray = s.toCharArray();
        ArrayList<Integer> diceKept = new ArrayList();
        for (int i = 0; i<diceFromRound.size();i++){
            if (diceArray[i] == '+'){
                diceKept.add(diceFromRound.get(i));
            } 
        }
        return diceKept;

    }

    private static String inputChooseDice2Reroll(ArrayList<Integer> diceFromRound) {
        String s = JOptionPane.showInputDialog("U heeft deze dobbelstenen opzij gelegd: " + 
                diceFromRound.toString() + " welke worp/worpen wilt opnieuw rollen?" +
                "\n\nBijvoorbeeld -++-- houdt alleen dobbelsteen 2 en 3 vast, 1, 4 en 5 gooit dan opnieuw."
            );
            if (s == null) return null;  // Als op cancel gedrukt is: stoppen.
            return s;
    }
}

    
