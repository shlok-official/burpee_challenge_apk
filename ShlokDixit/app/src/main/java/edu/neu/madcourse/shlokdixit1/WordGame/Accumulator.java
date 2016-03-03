package edu.neu.madcourse.shlokdixit1.WordGame;

/**
 * Created by shlokdixit on 24/02/16.
 */

import java.util.ArrayList;


public class Accumulator {
    private static Accumulator dataObject = null;

    private Accumulator() {
        // left blank intentionally
    }

    public static Accumulator getInstance() {
        if (dataObject == null)
            dataObject = new Accumulator();
        return dataObject;
    }

    //**********new data variables******************

    private char[][] words;
    private boolean[][] correctClicks;
    private int points;
    private int bonusPoints;

    ArrayList<String> l1;
    ArrayList<String> l2;
    ArrayList<String> l02;
    ArrayList<String> l3;
    ArrayList<String> l4;
    ArrayList<String> l5;
    ArrayList<String> l6;
    ArrayList<String> l7;
    ArrayList<String> l8;
    ArrayList<String> l9;
    ArrayList<String> l10;
    ArrayList<String> l101;
    ArrayList<String> l11;
    ArrayList<String> l12;
    ArrayList<String> l13;
    ArrayList<String> l14;
    private ArrayList<ArrayList> masterData;

    //**********new data getters and setters*************


    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public char[][] getWords() {
        return words;
    }

    public void setWords(char[][] words) {
        this.words = words;
    }

    public boolean[][] getCorrectClicks() {
        return correctClicks;
    }

    public void setCorrectClicks(boolean[][] correctClicks) {
        this.correctClicks = correctClicks;
    }

    public ArrayList<String> getL1() {
        return l1;
    }

    public void setL1(ArrayList<String> l1) {
        this.l1 = l1;
    }

    //
    public ArrayList<String> getL2() {
        return l2;
    }

    public void l2(ArrayList<String> l2) {
        this.l2 = l2;
    }

    //
    public ArrayList<String> getL02() {
        return l02;
    }

    public void setLl02(ArrayList<String> l02) {
        this.l02 = l02;
    }

    //
    public ArrayList<String> getL3() {
        return l3;
    }

    public void l3(ArrayList<String> l3) {
        this.l3 = l3;
    }

    //
    public ArrayList<String> getL4() {
        return l4;
    }

    public void setL4(ArrayList<String> l4) {
        this.l4 = l4;
    }

    //
    public ArrayList<String> getL5() {
        return l5;
    }

    public void setL5(ArrayList<String> l5) {
        this.l5 = l5;
    }

    //
    public ArrayList<String> getL6() {
        return l6;
    }

    public void setL6(ArrayList<String> l6) {
        this.l6 = l6;
    }

    //
    public ArrayList<String> getL7() {
        return l7;
    }

    public void setL7(ArrayList<String> l7) {
        this.l7 = l7;
    }

    //
    public ArrayList<String> getL8() {
        return l8;
    }

    public void setL8(ArrayList<String> l8) {
        this.l8 = l8;
    }

    //
    public ArrayList<String> getL9() {
        return l9;
    }

    public void setL9(ArrayList<String> l9) {
        this.l9 = l9;
    }

    //
    public ArrayList<String> getL10() {
        return l10;
    }

    public void setL10(ArrayList<String> l10) {
        this.l10 = l10;
    }

    //
    public ArrayList<String> getL101() {
        return l101;
    }

    public void setL101(ArrayList<String> l101) {
        this.l101 = l101;
    }

    //
    public ArrayList<String> getL11() {
        return l11;
    }

    public void setL11(ArrayList<String> l11) {
        this.l1 = l11;
    }

    //
    public ArrayList<String> getL12() {
        return l12;
    }

    public void setL12(ArrayList<String> l12) {
        this.l12 = l12;
    }

    //
    public ArrayList<String> getL13() {
        return l13;
    }

    public void setL13(ArrayList<String> l13) {
        this.l13 = l13;
    }

    //
    public ArrayList<String> getL14() {
        return l14;
    }

    public void setL14(ArrayList<String> l14) {
        this.l14 = l14;
    }


    public ArrayList<ArrayList> getMasterData() {
        return masterData;
    }

    public void setMasterData(ArrayList<ArrayList> masterData) {
        this.masterData = masterData;
    }

    private ArrayList<String> arl;

    private String distributor_id;

    public ArrayList<String> getArl() {
        return arl;
    }

    public void setArl(ArrayList<String> arl) {
        this.arl = arl;
    }

    public String getDistributor_id() {
        return distributor_id;
    }

    public void setDistributor_id(String distributor_id) {
        this.distributor_id = distributor_id;
    }
}