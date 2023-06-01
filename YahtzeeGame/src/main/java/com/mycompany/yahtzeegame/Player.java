/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.yahtzeegame;

/**
 *
 * @author Gebruiker
 */
public class Player {
    public String name;
    public ScoreCard scoreCard;

    public Player(String name) {
        this.name = name;
        this.scoreCard = new ScoreCard();
    }

}
