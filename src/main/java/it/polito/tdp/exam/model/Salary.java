package it.polito.tdp.exam.model;

public class Salary {

    private Integer iD;
    private Integer year;
    private String teamCode;
    private Integer teamID;
    private String playerID;
    private Double salary;


    public Integer getID(){
        return iD;
    }

    public void setID(Integer iD){
        this.iD=iD;
    }

    public Integer getYear(){
        return year;
    }

    public void setYear(Integer year){
        this.year=year;
    }

    public String getTeamCode(){
        return teamCode;
    }

    public void setTeamCode(String teamCode){
        this.teamCode=teamCode;
    }

    public Integer getTeamID(){
        return teamID;
    }

    public void setTeamID(Integer teamID){
        this.teamID=teamID;
    }

    public String getPlayerID(){
        return playerID;
    }

    public void setPlayerID(String playerID){
        this.playerID=playerID;
    }

    public Double getSalary(){
        return salary;
    }

    public void setSalary(Double salary){
        this.salary=salary;
    }

}


