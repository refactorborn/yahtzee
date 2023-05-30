/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.yahtzeegame;

import static com.mycompany.yahtzeegame.ScoreField.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Gebruiker
 */
public class ScoreCard {
    private Map<ScoreField, Integer> scores;
    private int totalScore;

    public ScoreCard() {
        scores = new HashMap<>();
        totalScore = 0;
    }
    
    public int getScore(ScoreField field) {
        return scores.getOrDefault(field, 0);
    }

    public void setScore(ScoreField field, int score) {
        scores.put(field, score);
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    
    public void chooseScore(ArrayList<Integer> finalDiceRound) {
        HashMap<Integer, Integer> ScoreFrequency = storeScoreFrequency(finalDiceRound);
        System.out.println(checkPossibleScorefields(ScoreFrequency).toString());
        ArrayList<String> possibleScorefields = checkPossibleScorefields(ScoreFrequency);
        String choice = makeChoice(finalDiceRound, possibleScorefields);
        appendScoreCard(choice);
        printScoreCard();
    }

    private HashMap<Integer, Integer> storeScoreFrequency(ArrayList<Integer> finalDiceRound) {
        HashMap<Integer, Integer> numberCount = new HashMap<>();
        for (int dice : finalDiceRound){
            if (numberCount.containsKey(dice)){
                numberCount.put(dice, numberCount.get(dice) + 1);
            } else {
                numberCount.put(dice, 1);
            }
        }
        return numberCount;
    }

    private ArrayList<String> checkPossibleScorefields(HashMap<Integer, Integer> scoreFrequency) {
        int throwTotal = 0;
        ArrayList<String> possibleScoreChoices = new ArrayList();
        int[][] littleStreets = {{1,2,3,4},{2,3,4,5},{3,4,5,6}};
        int[][] bigStreets = {{1,2,3,4,5},{2,3,4,5,6}};
        for (int key : scoreFrequency.keySet()){
            throwTotal += scoreFrequency.get(key) * key;             
            if (key == 1 && getScore(ONE) == 0){
                possibleScoreChoices.add("ONES:" + scoreFrequency.get(key) * key);
            }
            if (key == 2 && getScore(TWO) == 0){
                possibleScoreChoices.add("TWOS:" + scoreFrequency.get(key) * key);
            }
            if (key == 3 && getScore(THREE) == 0){
                possibleScoreChoices.add("THREES:" + scoreFrequency.get(key) * key);
            }
            if (key == 4 && getScore(FOUR) == 0){
                possibleScoreChoices.add("FOURS:" + scoreFrequency.get(key) * key);
            }
            if (key == 5 && getScore(FIVE) == 0){
                possibleScoreChoices.add("FIVES:" + scoreFrequency.get(key) * key);
            }
            if (key == 6 && getScore(SIX) == 0){
                possibleScoreChoices.add("SIXES:" + scoreFrequency.get(key) * key);
            }
        }
        for (int[] littleStreet : littleStreets) {
            if (containsAllNumbers(scoreFrequency, littleStreet) && getScore(LITTLE_STREET) == 0) {
                possibleScoreChoices.add("LS:30");
            }
        }
        for (int[] bigStreet : bigStreets){
            if (containsAllNumbers(scoreFrequency, bigStreet) && getScore(BIG_STREET) == 0){
                possibleScoreChoices.add("BS:40");
            }
        }    
        if (scoreFrequency.values().contains(3) && getScore(THREE_OF_A_KIND) == 0){
            possibleScoreChoices.add("ToaK:" + throwTotal);
        }
        if (scoreFrequency.values().contains(4) && getScore(FOUR_OF_A_KIND) == 0){
                possibleScoreChoices.add("FoaK:" + throwTotal);            
        }
        if (getScore(CHANCE) == 0) {
            possibleScoreChoices.add("Chance:" + throwTotal);
        }
        if (scoreFrequency.values().contains(2) && scoreFrequency.values().contains(3) && getScore(FULL_HOUSE) == 0){
            possibleScoreChoices.add("FH:25");
        }
        if (scoreFrequency.values().contains(5) && getScore(YAHTZEE) == 0){
            possibleScoreChoices.add("Yahtzee:50");
        }
        return possibleScoreChoices;
    }
    
    private boolean containsAllNumbers(HashMap<Integer, Integer> scoreFrequency, int[] keys) {
        for (int key : keys) {
            if (!scoreFrequency.containsKey(key)) {
                return false;
            }
        }
        return true;
    }
    
    private String inputChooseScoreGUI(ArrayList<Integer> finalDiceRound, ArrayList<String> possibleScorefields) {
        String s = JOptionPane.showInputDialog("U heeft gegooid: " + 
            finalDiceRound.toString() + ". U kunt de volgende opties kiezen om op uw scorekaart te zetten:" 
            + possibleScorefields
        );
        if (s == null) {
            return null;
        }  // Als op cancel gedrukt is: stoppen.
        else if (s.length() != possibleScorefields.size()){
            return inputChooseScoreGUI(finalDiceRound, possibleScorefields);
        }
        return s;
    }
    
    private String makeChoice(ArrayList<Integer> finalDiceRound, ArrayList<String> possibleScorefields){
        String s = inputChooseScoreGUI(finalDiceRound, possibleScorefields);
        char[] ca = s.toCharArray();
        for (int i = 0; i<ca.length;i++){
            if (ca[i] == '+'){
                return possibleScorefields.get(i);
            } 
        }
        return null;
    }

    private void appendScoreCard(String s) {
        System.out.println(s);
        String[] sSplit = s.strip().split(":");
        if (sSplit[0].contains("ONES")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(ONE,i);
        }
        else if (sSplit[0].contains("TWOS")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(TWO,i);
        }
        else if (sSplit[0].contains("THREES")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(THREE,i);
        }
        else if (sSplit[0].contains("FOURS")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(FOUR,i);
        }
        else if (sSplit[0].contains("FIVES")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(FIVE,i);
        }
        else if (sSplit[0].contains("SIXES")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(SIX,i);
        }
                else if (sSplit[0].contains("LS")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(LITTLE_STREET,i);
        }
        else if (sSplit[0].contains("BS")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(BIG_STREET,i);
        }
        else if (sSplit[0].contains("ToaK")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(THREE_OF_A_KIND,i);
        }
        else if (sSplit[0].contains("FoaK")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(FOUR_OF_A_KIND,i);
        }
        else if (sSplit[0].contains("FH")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(FULL_HOUSE,i);
        }
    else if (sSplit[0].contains("Chance")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(CHANCE,i);
        }
    else if (sSplit[0].contains("Yahtzee")){
            int i = Integer.parseInt(sSplit[1]);
            setScore(YAHTZEE,i);
        }
    }
    
    private void printScoreCard() {
        System.out.println(scores.toString());
    }
}
