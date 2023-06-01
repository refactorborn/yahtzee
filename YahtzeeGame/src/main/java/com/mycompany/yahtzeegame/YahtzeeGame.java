/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.yahtzeegame;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Gebruiker
 */

public class YahtzeeGame {
    
    public static void main(String[] args) {
        Player[] players = getPlayers();
        int amountRounds = getRoundAmount();
        playGame(players, amountRounds);
    }

    private static Player[] getPlayers() {
        int playerAmount = inputAmountPlayers();
        Player players[] = new Player[playerAmount];
        for (int i=0; i<playerAmount; i++){
            players[i] = new Player("" + (i + 1));
        }
        return players;
    }
    
    private static int getRoundAmount() {
            String s = JOptionPane.showInputDialog("Insert the amount of rounds. The maximum amount is 13.");
        if (s == null) return 0;  // Als op cancel gedrukt is: stoppen.
        try {
            int rounds = Integer.parseInt(s);
            if (rounds > 13){
                System.out.println("Sorry '" + s + "' is over the maximum of 12 rounds!");
                return getRoundAmount(); 
            }
            return rounds;
        } catch (NumberFormatException e) { // De Integer.parseInt(s) is mislukt.
            System.out.println("Sorry '" + s + "' is over the maximum of 12 rounds!");
            return getRoundAmount();
        } 
    }
    
    private static int inputAmountPlayers() {
        String s = JOptionPane.showInputDialog("Insert the amount of players.");
        if (s == null) return 0;  // Als op cancel gedrukt is: stoppen.
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) { // De Integer.parseInt(s) is mislukt.
            System.out.println("Sorry '" + s + "' is not an amount of players!");
            return inputAmountPlayers();
        } 
    }

    private static void playGame(Player[] players, int amountRounds) {
        System.out.println("Welcome to this game of Yahtzee!");
        for (int i = 0; i<amountRounds; i++){
            System.out.println("Ronde " + (i + 1));
            for (Player aPlayer: players) {
                doPlayerRound(aPlayer);
            }
        }
        determineWinner(players);
    }

    //find a way to remove the strings from here
    private static void doPlayerRound(Player aPlayer) {
        System.out.println("The round of player: "+ (aPlayer.name));
        ArrayList<Integer> savedDice = new ArrayList();
        int roleAmount = 5;
        for (int dice_throw=0; dice_throw<3; dice_throw++){
            ArrayList<Integer> rollResult = roleDice(roleAmount);
            
            switch (dice_throw) {
                case 0 -> {
                    String textGui = textGui(0, rollResult, savedDice);
                    savedDice.addAll(chooseDice(textGui,rollResult));
                    roleAmount -= savedDice.size();
                }
                case 1 -> {
                    if (roleAmount != 0){
                        String textGui = textGui(1, rollResult, savedDice);
                        savedDice.addAll(chooseDice(textGui, rollResult));
                        savedDice = chooseDice(textGui(2,rollResult,savedDice), savedDice);
                        roleAmount = 5 - savedDice.size();
                    }
                }
                default -> {
                    savedDice.addAll(rollResult);
                    aPlayer.scoreCard.chooseScore(savedDice);
                }
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

    private static ArrayList<Integer> chooseDice(String textGui, ArrayList<Integer> dice) {
        String s = diceChooserGUI(textGui, dice);
        char[] diceArray = s.toCharArray();
        ArrayList<Integer> roundResults = new ArrayList();
        for (int i = 0; i<dice.size();i++){
            if (diceArray[i] == '+'){
                roundResults.add(dice.get(i));
            } 
        }
        return roundResults;
    }
        private static String diceChooserGUI(String textGui, ArrayList<Integer> diceThrow){
        String answer = JOptionPane.showInputDialog(textGui);
        if (answer == null) return null;
        else if (answer.length() != diceThrow.size()) {
            System.out.println("length answer does not equal the amount of dice rolled");
            return diceChooserGUI(textGui,diceThrow);
        }
        return answer;
    }
    
    private static String textGui(int i, ArrayList<Integer> rollResult, ArrayList<Integer> savedDice) {
        String[] textsGui =  new String[3];
        textsGui[0] = "You have thrown: " + 
                rollResult.toString() + " which dice would you like to save?" +
                "\n\nExample -++-- only keeps dice 2 and 3 while it would thrown dice 1, 4 and 5 again.";
        textsGui[1] = "You have thrown: " + 
                rollResult.toString() + ".\nLast round you have thrown: " + savedDice.toString() + "."
                + "\n Which dice do you want to keep?" + "\n\nExample -++-- only keeps dice 2 and 3 while "
                + "it would thrown dice 1, 4 and 5 again.";
        textsGui[2] =  "You have saved these dice in round 1 and 2: " + 
                savedDice.toString() + " which dice would you like to role again?" +
                "\n\nExample -++-- only keeps dice 2 and 3 while it would thrown dice 1, 4 and 5 again.";       
        return textsGui[i];
    }

    //clean up method
    private static void determineWinner(Player[] players) {
        HashMap<String, Integer> playerScores = new HashMap<>();
        for (Player aPlayer : players){
            aPlayer.scoreCard.calculateTotalScores();
            int playerScore = aPlayer.scoreCard.getTotalScore();
            playerScores.put("" + aPlayer.name, playerScore);
        }
        int winnerScore = determineMaxScore(playerScores);
        
        System.out.println("This is the end of the game.");
        System.out.println("The winner(-s) is/are: ");
        for(String aPlayer : playerScores.keySet()) {
            if (playerScores.get(aPlayer) == winnerScore){
                System.out.println("Player " + aPlayer + "!");
            }
        }
        System.out.println("With " + winnerScore + " points :D");
        System.out.println("Thank you for playing.");
    }

        
    private static int determineMaxScore(HashMap<String, Integer> playerScores){
        String previousPlayer = null;
        int winnerScore = 0;
        for(String key : playerScores.keySet()) {
            int playerScore = playerScores.get(key);
            if (previousPlayer == null){
                previousPlayer = key;
                winnerScore = playerScore;
            }
            else if (playerScore > playerScores.get(previousPlayer)){
                previousPlayer = key;
                winnerScore = playerScore;
            }
        }
        return winnerScore;
    } 
}

    
