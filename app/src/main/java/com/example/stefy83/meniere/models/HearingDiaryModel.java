package com.example.stefy83.meniere.models;

/**
 * Created by stefy83 on 22/06/2017.
 */

public class HearingDiaryModel {

    public String date;
    public String left05;
    public String left1;
    public String left2;
    public String left4;
    public String rigth05;
    public String rigth1;
    public String rigth2;
    public String rigth4;

   /* public String getnamesub()
    {
        return name;
    }
    public void setnamesub(String name)
    {
        this.name = name;
    }*/

    // Empty constructor
    public HearingDiaryModel(){

    }
    // constructor
    public HearingDiaryModel(String _date, String _left05, String _left1, String _left2,String _left4,String _rigth05,String _rigth1,String _rigth2,String _rigth4){
        this.date = _date;
        this.left05 = _left05;
        this.left1 = _left1;
        this.left2 = _left2;
        this.left4 = _left4;
        this.rigth05 = _rigth05;
        this.rigth1 = _rigth1;
        this.rigth2 = _rigth2;
        this.rigth4 = _rigth4;


    }


}


