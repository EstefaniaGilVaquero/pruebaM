package com.asmes.meniere.models;

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
    public String crisis;

    // Empty constructor
    public HearingDiaryModel(){

    }

    public HearingDiaryModel(String date, String left05_a, String left1_a, String left2_a, String left4_a, String rigth05_a, String rigth1_a, String rigth2_a, String rigth4_a, String crisis) {
        this.date = date;
        this.left05_a = left05_a;
        this.left1_a = left1_a;
        this.left2_a = left2_a;
        this.left4_a = left4_a;
        this.rigth05_a = rigth05_a;
        this.rigth1_a = rigth1_a;
        this.rigth2_a = rigth2_a;
        this.rigth4_a = rigth4_a;
        this.crisis = crisis;
    }
}


