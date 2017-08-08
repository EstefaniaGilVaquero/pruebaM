package com.symbel.meniere.models;

/**
 * Created by stefy83 on 22/06/2017.
 */

public class HearingDiaryModel {

    public String date;
    public String left05_a;
    public String left1_a;
    public String left2_a;
    public String left4_a;
    public String rigth05_a;
    public String rigth1_a;
    public String rigth2_a;
    public String rigth4_a;

    // Empty constructor
    public HearingDiaryModel(){

    }
    // constructor
    public HearingDiaryModel(String _date, String _left05_a, String _left1_a, String _left2_a,String _left4_a, String _rigth05_a, String _rigth1_a, String _rigth2_a, String _rigth4_a){
        this.date = _date;
        this.left05_a = _left05_a;
        this.left1_a = _left1_a;
        this.left2_a = _left2_a;
        this.left4_a = _left4_a;
        this.rigth05_a = _rigth05_a;
        this.rigth1_a = _rigth1_a;
        this.rigth2_a = _rigth2_a;
        this.rigth4_a = _rigth4_a;
    }
}


