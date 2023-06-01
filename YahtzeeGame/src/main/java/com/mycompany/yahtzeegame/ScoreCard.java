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
        return scores.getOrDefault(field, -1);
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
    
    public void printScoreCard() {
        System.out.println(scores.toString());
    }

    public void chooseScore(ArrayList<Integer> finalDiceRound) {
        HashMap<Integer, Integer> ScoreFrequency = storeScoreFrequency(finalDiceRound);
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

    //Little Street can appear twice when throw is like bigstreet
    private ArrayList<String> checkPossibleScorefields(HashMap<Integer, Integer> scoreFrequency) {
        int throwTotal = 0;
        ArrayList<String> possibleScoreChoices = new ArrayList();
        int[][] littleStreets = {{1,2,3,4},{2,3,4,5},{3,4,5,6}};
        int[][] bigStreets = {{1,2,3,4,5},{2,3,4,5,6}};
        ScoreField[] fields = {ONE, TWO, THREE, FOUR, FIVE, SIX};

        for (int key : scoreFrequency.keySet()) {
            throwTotal += scoreFrequency.get(key) * key;
            ScoreField correspondingField = fields[key - 1]; // Index of fields is 0-based
            if (getScore(correspondingField) == -1) {
                possibleScoreChoices.add(correspondingField + ":" + scoreFrequency.get(key) * key);
            }
        }
        for (int[] littleStreet : littleStreets) {
            if (containsAllNumbers(scoreFrequency, littleStreet) && getScore(LSTR) == -1 && !possibleScoreChoices.contains("LSTR:30")) {
                possibleScoreChoices.add("LSTR:30");
            }
        }
        for (int[] bigStreet : bigStreets){
            if (containsAllNumbers(scoreFrequency, bigStreet) && getScore(BSTR) == -1){
                possibleScoreChoices.add("BSTR:40");
            }
        }    
        if (scoreFrequency.values().contains(3) && getScore(TOAK) == -1){
            possibleScoreChoices.add("TOAK:" + throwTotal);
        }
        if (scoreFrequency.values().contains(4) && getScore(FOAK) == -1){
                possibleScoreChoices.add("FOAK:" + throwTotal);            
        }
        if (getScore(CHANCE) == -1) {
            possibleScoreChoices.add("CHANCE:" + throwTotal);
        }
        if (scoreFrequency.values().contains(2) && scoreFrequency.values().contains(3) && getScore(FULLH) == -1){
            possibleScoreChoices.add("FULLH:25");
        }
        if (scoreFrequency.values().contains(5) && getScore(YAHTZEE) == -1){
            possibleScoreChoices.add("YAHTZEE:50");
        }
        if (possibleScoreChoices.isEmpty()){
            for (ScoreField score : ScoreField.values()){
                if (getScore(score) == -1){
                    possibleScoreChoices.add("" + score + ":0");
                }
            } 
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
    
        private String makeChoice(ArrayList<Integer> finalDiceRound, ArrayList<String> possibleScorefields){
        String scoreInput = inputChooseScoreGUI(finalDiceRound, possibleScorefields);
        char[] ca = scoreInput.toCharArray();
        int numPlusses = 0;
        String fields = null;
        for (int i = 0; i<ca.length;i++){
            if (ca[i] == '+'){
                numPlusses += 1;
                if (numPlusses > 1){
                    System.out.println("You chose more then one scorefield.");
                    System.out.println("Please choose one.");
                    return makeChoice(finalDiceRound, possibleScorefields);
                }
                fields = possibleScorefields.get(i);
            } 
        }
        return fields;
    }

    //add sout for the else if and check +-'s
    private String inputChooseScoreGUI(ArrayList<Integer> finalDiceRound, ArrayList<String> possibleScorefields) {
        String s = JOptionPane.showInputDialog("You have saved: " + 
            finalDiceRound.toString() + " dice. You can choose from the following options using +-:" 
            + possibleScorefields
        );
        if (s == null) return null;  // Als op cancel gedrukt is: stoppen.
        else if (s.length() != possibleScorefields.size()){
            System.out.println("length answer does not equal the amount of dice rolled");
            return inputChooseScoreGUI(finalDiceRound, possibleScorefields);
        }
        return s;
    }
    
    private void appendScoreCard(String stringField) {
        String[] stringFieldSplit = stringField.strip().split(":");
        ScoreField field = getScoreFieldFromString(stringFieldSplit[0]);
        if (field != null) {
            int i = Integer.parseInt(stringFieldSplit[1]);
            setScore(field, i);
        }
    }

    private ScoreField getScoreFieldFromString(String fieldString) {
        fieldString = fieldString.toUpperCase();
        for (ScoreField field : ScoreField.values()) {
            if (fieldString.contains(field.toString())) {
                return field;
            }
        }
        return null;
    }
    
    public void calculateTotalScores() {
        int scoreTotal = 0;
        for(ScoreField field : ScoreField.values())
            if(getScore(field) != -1){
                scoreTotal += getScore(field);
                setTotalScore(scoreTotal);
                }
    }
}