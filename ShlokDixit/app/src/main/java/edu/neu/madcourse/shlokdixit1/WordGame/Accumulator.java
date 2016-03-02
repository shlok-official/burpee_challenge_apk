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

    private ArrayList<String> arl;
    private String distributor_id;
    private GameFragment_wg controlObj;

    public GameFragment_wg getControlObj() {
        return controlObj;
    }

    public void setControlObj(GameFragment_wg controlObj) {
        this.controlObj = controlObj;
    }

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