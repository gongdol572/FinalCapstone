package com.example.capstone;

public class PersonalData {

    private String Lectnum;
    private String Lectname;
    private String StartTime;
    private String EndTime;
    private String Location;


    public String getLectnum(){
        return Lectnum;
    }

    public String getLectname(){
        return Lectname;
    }

    public String getLocation() {
        return Location;
    }
    public String getStartTime(){ return StartTime;}
    public String getEndTime(){ return EndTime;}

    public void setLectnum(String Lectnum){
        this.Lectnum= Lectnum;
    }

    public void setLectname(String Lectname){
        this.Lectname= Lectname;
    }
    public void setLocation(String Location){
        this.Location = Location;
    }
    public void setStartTime(String StartTime){ this.StartTime = StartTime;}
    public void setEndTime(String EndTime){ this.EndTime =EndTime;}

}
