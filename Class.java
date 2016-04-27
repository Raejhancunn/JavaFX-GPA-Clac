package javafx;

/**
 *
 * @author Raejhan
 */
public class Class {
    String name;
    String grade;
    double hours;
    
    public Class(){}
    
    public Class(String n, String g, double h){
        name = n;
        grade = g;
        hours = h;
    }
    
    public void setName(String input){
        name = input;
    }
    public String getName(){
        return name;
    }
    
    public void setGrade(String input){
        grade = input;
    }
    public String getGrade(){
        return grade;
    }
    
    public void setHours(double input){
        hours = input;
    }
    public double getHours(){
        return hours;
    }
}
