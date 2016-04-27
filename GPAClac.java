package javafx;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GPAClac extends Application {
    public static ObservableList<String> names = FXCollections.observableArrayList();
    public static ObservableList<Class> data = FXCollections.observableArrayList();
    static double tHours=0.0, tPoints=0.0;
    static int count=0;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GPA Calc");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(0,15,15,0));
        //top, right, bottom, left
        
        TextArea topText = new TextArea();
        topText.setPrefSize(300,150);
        grid.add(topText,1,0);
        
        TextArea result = new TextArea();
        grid.add(result,1,6);
        Button add = new Button("Add Class");
        grid.add(add,1,1);
        Button addS = new Button("Add Semeter");
        grid.add(addS,1,2);
        Button edit = new Button("Edit");
        grid.add(edit,1,3);
        Button remove = new Button("Remove");
        grid.add(remove,1,4);
        Button preset = new Button("Preset");
        grid.add(preset,1,5);
        
        ListView list = new ListView(names);
        list.setEditable(true);
        grid.add(list,0,0,1,7);
        
        data.addListener(new ListChangeListener<Class>() {
            @Override
            public void onChanged(Change<? extends Class> change){
                result.setText("");
                result.appendText("Total Quality Points: "+Double.toString(tPoints)+"\n");
                result.appendText("Total Hours: "+Double.toString(tHours)+"\n");
                result.appendText("GPA: "+Double.toString(tPoints/tHours));
            }
        });
        
        list.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                topText.setText("");
                try{
                int index = list.getSelectionModel().getSelectedIndex();
                Class selected = data.get(index);
                if(data.get(index) instanceof Semester){
                    Semester temp = (Semester)data.get(index);
                    topText.appendText("Quality Points: "+Double.toString(temp.getqPoints())+"\n");
                } else {
                    topText.appendText("Grade: "+data.get(index).getGrade()+"\n"); 
                }
                topText.appendText("Credit Hours: "+Double.toString(selected.getHours())+"\n");
                } catch(java.lang.ArrayIndexOutOfBoundsException e1){}
            }
        });
        
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
                TextInputDialog pop = new TextInputDialog();
                pop.setHeaderText("");
                pop.setContentText("Enter Class Name");
                pop.showAndWait();
                String inputName = pop.getEditor().getText();
                pop.getEditor().setText("");
                pop.setContentText("Enter Letter Grade");
                pop.showAndWait();
                String inputGrade = pop.getEditor().getText();
                pop.getEditor().setText("");
                pop.setContentText("Enter Credit Hours");
                pop.showAndWait();
                String inputHours = pop.getEditor().getText();
                Class temp = new Class(inputName, inputGrade, Double.parseDouble(inputHours));
                names.add(inputName);
                tHours+=Double.parseDouble(inputHours);
                tPoints+=getPoint(inputGrade)*Double.parseDouble(inputHours);
                data.add(temp);
                } catch(java.lang.NumberFormatException e1){
                Alert pop2 = new Alert(AlertType.ERROR);
                pop2.setContentText("Enter only letters for Grades and numbers for Credit Hours");
                pop2.show();
                } catch(java.lang.NullPointerException e2){}
            }
        });
        addS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
                TextInputDialog pop = new TextInputDialog();
                pop.setHeaderText("");
                pop.setContentText("Enter Semester Name");
                pop.showAndWait();
                String inputName = pop.getEditor().getText();
                pop.getEditor().setText("");
                pop.setContentText("Enter Total Quailty Points");
                pop.showAndWait();
                String inputPoints = pop.getEditor().getText();
                pop.getEditor().setText("");
                pop.setContentText("Enter Total Credit Hours");
                pop.showAndWait();
                String inputHours = pop.getEditor().getText();
                Semester temp = new Semester();
                temp.setName(inputName);
                temp.setqPoints(Double.parseDouble(inputPoints));
                temp.setHours(Double.parseDouble(inputHours));
                names.add(inputName);
                tPoints+=temp.getqPoints();
                tHours+=temp.getHours();
                data.add(temp);
                } catch(java.lang.NumberFormatException e1){
                Alert pop2 = new Alert(AlertType.ERROR);
                pop2.setContentText("Enter only numbers for Quailty Points and Credit Hours");
                pop2.show();
                } catch(java.lang.NullPointerException e2){}
            }
        });
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
                int index = list.getSelectionModel().getSelectedIndex();
                if(data.get(index) instanceof Semester){
                    Semester temp = (Semester)data.get(index);
                    topText.setText("");
                    topText.appendText("This is a Semester");
                    TextInputDialog pop = new TextInputDialog();
                    pop.setHeaderText("");
                    pop.setContentText("Enter New Quality Points");
                    pop.showAndWait();
                    double inputPoints = Double.parseDouble(pop.getEditor().getText());
                    pop.getEditor().setText("");
                    pop.setContentText("Enter New Credit Hours");
                    pop.showAndWait();
                    double inputHours = Double.parseDouble(pop.getEditor().getText());
                    tHours-=temp.getHours();
                    tPoints-=temp.getqPoints();
                    temp.setHours(inputHours);
                    temp.setqPoints(inputPoints);
                    data.add(index, temp);
                    tHours+=temp.getHours();
                    tPoints+=temp.getqPoints();
                    result.setText("");
                    result.appendText("Total Quality Points: "+Double.toString(tPoints)+"\n");
                    result.appendText("Total Hours: "+Double.toString(tHours)+"\n");
                    result.appendText("GPA: "+Double.toString(tPoints/tHours));
                } else {
                    topText.setText("");
                    topText.appendText("This is a Class");
                    TextInputDialog pop = new TextInputDialog();
                    pop.setHeaderText("");
                    pop.setContentText("Enter New Letter Grade");
                    pop.showAndWait();
                    String inputGrade = pop.getEditor().getText();
                    pop.getEditor().setText("");
                    pop.setContentText("Enter New Credit Hours");
                    pop.showAndWait();
                    double inputHours = Double.parseDouble(pop.getEditor().getText());
                    tHours-=data.get(index).getHours();
                    tPoints-=getPoint(data.get(index).getGrade())*data.get(index).getHours();
                    data.get(index).setHours(inputHours);
                    data.get(index).setGrade(inputGrade);
                    tHours+=inputHours;
                    tPoints+=getPoint(inputGrade)*inputHours;
                    result.setText("");
                    result.appendText("Total Quality Points: "+Double.toString(tPoints)+"\n");
                    result.appendText("Total Hours: "+Double.toString(tHours)+"\n");
                    result.appendText("GPA: "+Double.toString(tPoints/tHours));
                }
                } catch(java.lang.NumberFormatException e1){
                Alert pop2 = new Alert(AlertType.ERROR);
                pop2.setContentText("Enter only numbers for Quailty Points and Credit Hours");
                pop2.show();
                } catch(java.lang.NullPointerException e2){
                } catch(java.lang.ArrayIndexOutOfBoundsException e3){
                    Alert pop2 = new Alert(AlertType.ERROR);
                    pop2.setContentText("List is empty");
                    pop2.show();
                }
            }
        });
        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try{
                int index = list.getSelectionModel().getSelectedIndex();
                if(data.get(index) instanceof Semester){
                    Semester temp = (Semester)data.get(index);
                    tHours-=temp.getHours();
                    tPoints-=temp.getqPoints();
                    data.remove(index);
                    names.remove(index);
                    result.setText("");
                    result.appendText("Total Quality Points: "+Double.toString(tPoints)+"\n");
                    result.appendText("Total Hours: "+Double.toString(tHours)+"\n");
                    result.appendText("GPA: "+Double.toString(tPoints/tHours));
                } else {
                    tHours-=data.get(index).getHours();
                    tPoints-=getPoint(data.get(index).getGrade())*data.get(index).getHours();
                    data.remove(index);
                    names.remove(index);
                    result.setText("");
                    result.appendText("Total Quality Points: "+Double.toString(tPoints)+"\n");
                    result.appendText("Total Hours: "+Double.toString(tHours)+"\n");
                    result.appendText("GPA: "+Double.toString(tPoints/tHours));
                }
                } catch(java.lang.ArrayIndexOutOfBoundsException e3){
                    Alert pop2 = new Alert(AlertType.ERROR);
                    pop2.setContentText("List is empty");
                    pop2.show();
                }
            }
        });
        preset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String name;
                double points,hours;
                name = "Summer 2014";
                points = 12;
                hours = 4;
                Semester temp = new Semester(name,points,hours);
                names.add(name);
                tHours+=hours;
                tPoints+=points;
                data.add(temp);
                name = "Fall 2014";
                points = 31.4;
                hours = 11;
                temp.setName(name);
                temp.setqPoints(points);
                temp.setHours(hours);
                names.add(name);
                tHours+=hours;
                tPoints+=points;
                data.add(temp);
                name = "Spring 2015";
                points = 37;
                hours = 14;
                temp.setName(name);
                temp.setqPoints(points);
                temp.setHours(hours);
                names.add(name);
                tHours+=hours;
                tPoints+=points;
                data.add(temp);
                name = "Fall 2015";
                points = 46.9;
                hours = 17;
                temp.setName(name);
                temp.setqPoints(points);
                temp.setHours(hours);
                names.add(name);
                tHours+=hours;
                tPoints+=points;
                data.add(temp);
                result.setText("");
                result.appendText("Total Quality Points: "+Double.toString(tPoints)+"\n");
                result.appendText("Total Hours: "+Double.toString(tHours)+"\n");
                result.appendText("GPA: "+Double.toString(tPoints/tHours));
            }
        });
        
        Scene scene = new Scene(grid,500,440);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static double getPoint(String letter){
        if(letter.equalsIgnoreCase("A+"))
            return 4.0;
        if(letter.equalsIgnoreCase("A"))
            return 4.0;
        if(letter.equalsIgnoreCase("B+"))
            return 3.3;
        if(letter.equalsIgnoreCase("B"))
            return 3.0;
        if(letter.equalsIgnoreCase("B-"))
            return 2.7;
        if(letter.equalsIgnoreCase("C+"))
            return 2.3;
        if(letter.equalsIgnoreCase("C"))
            return 2.0;
        if(letter.equalsIgnoreCase("C-"))
            return 1.7;
        if(letter.equalsIgnoreCase("D+"))
            return 1.3;
        if(letter.equalsIgnoreCase("D"))
            return 1.0;
        return 0.0;
    }
}
