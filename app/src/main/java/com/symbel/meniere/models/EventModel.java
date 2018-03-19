package com.symbel.meniere.models;

/**
 * Created by stefy83 on 25/02/2018.
 */

public class EventModel {

   public String duration;
   public String vertigoIntensity;
   public String limitation;
   public String stress;
   public Boolean hearingLoss;
   public Boolean tinnitus;
   public Boolean earFullness;
   public Boolean headache;
   public Boolean photophobia;
   public Boolean phonophobia;
   public Boolean visualSymptoms;
   public Boolean tumarkin;
   public Boolean menstruation;
   public Boolean nausea;
   public Boolean vomiting;
   public Boolean instability;
   public String instabilityIntensity;
   public String headacheProperties1;
   public String headacheProperties2;
   public String headacheProperties3;
   public String weather;
   public String sleep;
   public String physicalActivity;
   public String habitExcess;
   public String myNotes;
   public String dizzinessDis;
   public String inestabilityDis;
   public String visualBlurDis;
   public String headPressure;

    public EventModel() {
    }

    public EventModel(String duration, String vertigoIntensity, String limitation, String stress, Boolean hearingLoss, Boolean tinnitus, Boolean earFullness, Boolean headache, Boolean photophobia, Boolean phonophobia, Boolean visualSymptoms, Boolean tumarkin, Boolean menstruation, Boolean nausea, Boolean vomiting, Boolean instability, String instabilityIntensity, String headacheProperties1, String headacheProperties2, String headacheProperties3, String weather, String sleep, String physicalActivity, String habitExcess, String myNotes, String dizzinessDis, String inestabilityDis, String visualBlurDis, String headPressure) {
        this.duration = duration;
        this.vertigoIntensity = vertigoIntensity;
        this.limitation = limitation;
        this.stress = stress;
        this.hearingLoss = hearingLoss;
        this.tinnitus = tinnitus;
        this.earFullness = earFullness;
        this.headache = headache;
        this.photophobia = photophobia;
        this.phonophobia = phonophobia;
        this.visualSymptoms = visualSymptoms;
        this.tumarkin = tumarkin;
        this.menstruation = menstruation;
        this.nausea = nausea;
        this.vomiting = vomiting;
        this.instability = instability;
        this.instabilityIntensity = instabilityIntensity;
        this.headacheProperties1 = headacheProperties1;
        this.headacheProperties2 = headacheProperties2;
        this.headacheProperties3 = headacheProperties3;
        this.weather = weather;
        this.sleep = sleep;
        this.physicalActivity = physicalActivity;
        this.habitExcess = habitExcess;
        this.myNotes = myNotes;
        this.dizzinessDis = dizzinessDis;
        this.inestabilityDis = inestabilityDis;
        this.visualBlurDis = visualBlurDis;
        this.headPressure = headPressure;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getVertigoIntensity() {
        return vertigoIntensity;
    }

    public void setVertigoIntensity(String vertigoIntensity) {
        this.vertigoIntensity = vertigoIntensity;
    }

    public String getLimitation() {
        return limitation;
    }

    public void setLimitation(String limitation) {
        this.limitation = limitation;
    }

    public String getStress() {
        return stress;
    }

    public void setStress(String stress) {
        this.stress = stress;
    }

    public Boolean getHearingLoss() {
        return hearingLoss;
    }

    public void setHearingLoss(Boolean hearingLoss) {
        this.hearingLoss = hearingLoss;
    }

    public Boolean getTinnitus() {
        return tinnitus;
    }

    public void setTinnitus(Boolean tinnitus) {
        this.tinnitus = tinnitus;
    }

    public Boolean getEarFullness() {
        return earFullness;
    }

    public void setEarFullness(Boolean earFullness) {
        this.earFullness = earFullness;
    }

    public Boolean getHeadache() {
        return headache;
    }

    public void setHeadache(Boolean headache) {
        this.headache = headache;
    }

    public Boolean getPhotophobia() {
        return photophobia;
    }

    public void setPhotophobia(Boolean photophobia) {
        this.photophobia = photophobia;
    }

    public Boolean getPhonophobia() {
        return phonophobia;
    }

    public void setPhonophobia(Boolean phonophobia) {
        this.phonophobia = phonophobia;
    }

    public Boolean getVisualSymptoms() {
        return visualSymptoms;
    }

    public void setVisualSymptoms(Boolean visualSymptoms) {
        this.visualSymptoms = visualSymptoms;
    }

    public Boolean getTumarkin() {
        return tumarkin;
    }

    public void setTumarkin(Boolean tumarkin) {
        this.tumarkin = tumarkin;
    }

    public Boolean getMenstruation() {
        return menstruation;
    }

    public void setMenstruation(Boolean menstruation) {
        this.menstruation = menstruation;
    }

    public Boolean getNausea() {
        return nausea;
    }

    public void setNausea(Boolean nausea) {
        this.nausea = nausea;
    }

    public Boolean getVomiting() {
        return vomiting;
    }

    public void setVomiting(Boolean vomiting) {
        this.vomiting = vomiting;
    }

    public Boolean getInstability() {
        return instability;
    }

    public void setInstability(Boolean instability) {
        this.instability = instability;
    }

    public String getInstabilityIntensity() {
        return instabilityIntensity;
    }

    public void setInstabilityIntensity(String instabilityIntensity) {
        this.instabilityIntensity = instabilityIntensity;
    }

    public String getHeadacheProperties1() {
        return headacheProperties1;
    }

    public void setHeadacheProperties1(String headacheProperties1) {
        this.headacheProperties1 = headacheProperties1;
    }

    public String getHeadacheProperties2() {
        return headacheProperties2;
    }

    public void setHeadacheProperties2(String headacheProperties2) {
        this.headacheProperties2 = headacheProperties2;
    }

    public String getHeadacheProperties3() {
        return headacheProperties3;
    }

    public void setHeadacheProperties3(String headacheProperties3) {
        this.headacheProperties3 = headacheProperties3;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public String getPhysicalActivity() {
        return physicalActivity;
    }

    public void setPhysicalActivity(String physicalActivity) {
        this.physicalActivity = physicalActivity;
    }

    public String getHabitExcess() {
        return habitExcess;
    }

    public void setHabitExcess(String habitExcess) {
        this.habitExcess = habitExcess;
    }

    public String getMyNotes() {
        return myNotes;
    }

    public void setMyNotes(String myNotes) {
        this.myNotes = myNotes;
    }

    public String getDizzinessDis() {
        return dizzinessDis;
    }

    public void setDizzinessDis(String dizzinessDis) {
        this.dizzinessDis = dizzinessDis;
    }

    public String getInestabilityDis() {
        return inestabilityDis;
    }

    public void setInestabilityDis(String inestabilityDis) {
        this.inestabilityDis = inestabilityDis;
    }

    public String getVisualBlurDis() {
        return visualBlurDis;
    }

    public void setVisualBlurDis(String visualBlurDis) {
        this.visualBlurDis = visualBlurDis;
    }

    public String getHeadPressure() {
        return headPressure;
    }

    public void setHeadPressure(String headPressure) {
        this.headPressure = headPressure;
    }
}





