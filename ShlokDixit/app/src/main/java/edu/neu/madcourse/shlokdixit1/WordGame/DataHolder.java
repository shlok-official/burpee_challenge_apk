package edu.neu.madcourse.shlokdixit1.WordGame;

/**
 * Created by shlokdixit on 24/02/16.
 */

import java.util.ArrayList;


public class DataHolder{
    private static DataHolder dataObject = null;

    private DataHolder() {
        // left blank intentionally
    }

    public static DataHolder getInstance() {
        if (dataObject == null)
            dataObject = new DataHolder();
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