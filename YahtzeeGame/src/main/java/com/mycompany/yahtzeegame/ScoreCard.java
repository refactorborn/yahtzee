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
//        String possibleScorefields = checkPossibleScorefields(ScoreFrequency);
//        String input = inputChooseScoreGUI(finalDiceRound, possibleScorefields);
//        appendScoreCard(input);
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

    private ArrayList<String> checkPossibleScorefields(HashMap<Integer, Integer> ScoreFrequency) {
        int throwTotal = 0;
        ArrayList<String> possibleScoreChoices = new ArrayList();
        for (int key : ScoreFrequency.keySet()){
            possibleScoreChoices.add(""+ key + ":" + ScoreFrequency.get(key) * key);
            throwTotal += ScoreFrequency.get(key) * key; 
        }
        if (ScoreFrequency.values().contains(3) && getScore(THREE_OF_A_KIND) == 0){
            possibleScoreChoices.add("ToaK:" + throwTotal);
        }
        if (ScoreFrequency.values().contains(4) && getScore(FOUR_OF_A_KIND) == 0){
                possibleScoreChoices.add("FoaK:" + throwTotal);
        }
        if (getScore(CHANCE) == 0) {
            possibleScoreChoices.add("Chance:" + throwTotal);
        }
        if (ScoreFrequency.values().contains(2) && ScoreFrequency.values().contains(3) && getScore(FULL_HOUSE) == 0){
            possibleScoreChoices.add("FH:25");
        }
        if (ScoreFrequency.values().contains(5) && getScore(YAHTZEE) == 0){
            possibleScoreChoices.add("Yahtzee:50");
        }
        return possibleScoreChoices;
    }
    

    private String inputChooseScoreGUI(ArrayList<Integer> finalDiceRound, Enum possibleScorefields) {
        String s = JOptionPane.showInputDialog("U heeft gegooid: " + 
            finalDiceRound.toString() + ". U kunt de volgende opties kiezen om op uw scorekaart te zetten:"
        );
        if (s == null) return null;  // Als op cancel gedrukt is: stoppen.
        return s;
    }

    private void appendScoreCard(String s) {

    }
}
