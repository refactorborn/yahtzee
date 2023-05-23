/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.yahtzeespel;

import javax.swing.JOptionPane;

/**
 *
 * @author Gebruiker
 */

public class Yahtzeespel {

    public static void main(String[] args) {
        int players = getPlayers();
        //System.out.println(Arrays.toString(playerArray));
        //int[] results = 
        playGame(players);
    }

    private static int getPlayers() {
        String s = JOptionPane.showInputDialog("Voer het gewenst aantal spelers in.");
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) { // De Integer.parseInt(s) is mislukt.
            System.out.println("Helaas '" + s + "' is geen hoeveelheid spelers. sukkel!");
            return getPlayers();
        } 
    }

    public static int[] playGame(int amountPlayers) {
        int[] result = new int[amountPlayers];
        for (int i = 0; i<13; i++){
            System.out.println("Ronde " + (i + 1));
            for (int aPlayer =0; aPlayer < amountPlayers; aPlayer++) {
                doSpelerRonde(aPlayer);
            }
        } 
        return result;
    }

//    public static int[] doSpelerRonde(int aPlayer, int[] scorecard) {
//        System.out.println("De ronde van speler "+ (aPlayer + 1));
//        int[] diceFromRound = new int[5]; 
//        for (int gooi=0; gooi<3; gooi++){
//            int [] rollResult = roleDice(5);
//            if (gooi == 0){
//                diceFromRound = chooseDice(rollResult);
//            }
//            else if (gooi == 1){
//                diceFromRound = chooseDice(rollResult);
//                chooseDice2Reroll(diceFromRound);
//            }
//            else {
//                System.out.println(rollResult);
//                Scorecard = chooseScore();
//            }
//        }
//        return scorecard;
//    }
    
    public static int[] roleDice(int roleAmount){
        int min = 1;
        int max = 6;
        int[] roleArray = new int[roleAmount];
        for (int i = 0; i < roleAmount; i++){
            int role = (int) (Math.random() * (max - min + 1) + min);  
            roleArray[i] = role;
        }
        return roleArray;
    }  

    /*private static int chooseDice(int[] rollResult) {
        String s = JOptionPane.showInputDialog("U heeft gegooid: " + 
            Arrays.toString(rollResult) + " welke worp/worpen wilt u houden?" +
            "\n\nBijvoorbeeld -++-- houdt alleen dobbelsteen 2 en 3 vast, 1, 4 en 5 gooit dan opnieuw."
        );
        if (s == null) return null;  // Als op cancel gedrukt is: stoppen.
        }
    }*/
}

    
