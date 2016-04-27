package javafx;

/**
 *
 * @author Raejhan
 */
public class Semester extends Class{
    double qPoints;
    
    public Semester(){}
    
    public Semester(String n, double q, double h){
        name = n;
        qPoints = q;
        hours = h;
    }
    
    public void setqPoints(double x){
        qPoints = x;
    }
    
    public double getqPoints(){
        return qPoints;
    }
}
