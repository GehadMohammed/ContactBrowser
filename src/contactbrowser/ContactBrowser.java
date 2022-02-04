/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package contactbrowser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ContactBrowser extends Application {
    
    ContactDAO dbms = new ContactDAO();
    ContactPerson cp= new ContactPerson();
    List<ContactPerson> table_data;
    int index=0;
    Text title;
    List<Button> btnlist;
    List<labelXtextfiled> labelXtextfiled;
    String [] labletext={"ID","NAME","Nick Name","ADDRESS","HOME PHONE","WORK PHONE","CELL PHONE","EMAIL","WEBSITE","PROFESSION"};
    String [] buttonsname={"NEW","UPDATE","DELETE","FIRST","PREVIOUS","NEXT","LAST","CLEAR"};
    GridPane grid = new GridPane();
    FlowPane flow =new FlowPane(Orientation.VERTICAL);
    @Override
    public void init(){
        
        labelXtextfiled=creat_label_textfiled(labletext);
        btnlist=creat_buttons(buttonsname);    
        
        dbms.connect_DB();
        table_data = dbms.getContacts();
        title = new Text("Person Details ..");
        title.setId("title");

  
      for(int i=0;i<10;i++)
      {
           grid.add(labelXtextfiled.get(i).lable, 0, i+3);
           grid.add(labelXtextfiled.get(i).textfield, 1, i+3);
      } 
       
        labelXtextfiled.get(0).textfield.setEditable(false);
        
       
    }
    
    @Override
    public void start(Stage primaryStage) {
 
           
       //new    
        btnlist.get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                if(labelXtextfiled.get(1).textfield.getText().equals(""))
                    raiseAlert("you need to set name to add new data");
                else{
                    ContactPerson cp = new ContactPerson();
                    cp.setName(labelXtextfiled.get(1).textfield.getText());
                    cp.setNickName(labelXtextfiled.get(2).textfield.getText());
                    cp.setAddress(labelXtextfiled.get(3).textfield.getText());
                    cp.setHomePhone(labelXtextfiled.get(4).textfield.getText());
                    cp.setWorkPhone(labelXtextfiled.get(5).textfield.getText());
                    cp.setCellPhone(labelXtextfiled.get(6).textfield.getText());
                    cp.setEmail(labelXtextfiled.get(7).textfield.getText());
                    cp.setWebsite(labelXtextfiled.get(8).textfield.getText());
                    cp.setProfession(labelXtextfiled.get(9).textfield.getText());
                    
                    cp.setId(dbms.insert_record(cp));
                    table_data.add(cp);
                    raiseAlert("added successfully");
                    index=table_data.size()-1;
                    fill_data(index);
                    
                }
            }
        });

       //update
        btnlist.get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(labelXtextfiled.get(0).textfield.getText().equals(""))
                {
                    raiseAlert("please select record!");
                }
                else
                {
                table_data.get(index).setId(Integer.valueOf(labelXtextfiled.get(0).textfield.getText()));
                table_data.get(index).setName(labelXtextfiled.get(1).textfield.getText());
                table_data.get(index).setNickName(labelXtextfiled.get(2).textfield.getText());
                table_data.get(index).setAddress(labelXtextfiled.get(3).textfield.getText());
                table_data.get(index).setHomePhone(labelXtextfiled.get(4).textfield.getText());
                table_data.get(index).setWorkPhone(labelXtextfiled.get(5).textfield.getText());
                table_data.get(index).setCellPhone(labelXtextfiled.get(6).textfield.getText());
                table_data.get(index).setEmail(labelXtextfiled.get(7).textfield.getText());
                table_data.get(index).setWebsite(labelXtextfiled.get(8).textfield.getText());
                table_data.get(index).setProfession(labelXtextfiled.get(9).textfield.getText());
                
                int check=0;
                check = dbms.update_record(table_data.get(index));
                    
                if(check==0)
                 {
                     raiseAlert("try again!");

                 }
                else
                 {
                     raiseAlert("update record successfully");
                 }
                      }
            }
        });
        
        //delete 
        btnlist.get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               dbms.delete_record(table_data.get(index).getId());
               table_data.remove(index);
                raiseAlert("delete record successfully");
                if(index<table_data.size()-1)
                {
                    fill_data(index); 
                }
                else
                {
                    fill_data(--index);
                
                }       
        
                
               
               
            }
        });

        //first
        btnlist.get(3).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                index=0;
                fill_data(index);          
            }
        });

       //previous
        btnlist.get(4).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                 if(index>0)
                {
                    index--;
                    fill_data(index); 
                }
                 else
                    raiseAlert("this is first record");
        
      
            }
        });

        //next
        btnlist.get(5).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              
                if(index<table_data.size()-1)
                {
                    index++;
                    fill_data(index); 
                }
                else
                    raiseAlert("this is last record!!");             
        
            }
        });

       //last 
        btnlist.get(6).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               index=table_data.size()-1;    
               fill_data(index);
            }
        });
        
        //clear
        btnlist.get(7).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               clear_txtfield();
            }
        });

      
       
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(15);
        grid.setHgap(30);
        
        HBox hbBtn = new HBox(btnlist.get(0),btnlist.get(1),btnlist.get(2),btnlist.get(3),btnlist.get(4),btnlist.get(5),btnlist.get(6),btnlist.get(7));
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.setSpacing(20);
        grid.add(hbBtn, 1, 15);
        
        flow.setId("grid");
        flow.setAlignment(Pos.CENTER);
        flow.getChildren().addAll(title,grid);
        
        Scene scene = new Scene(flow, 1000, 700);
        scene.getStylesheets().clear();
        
        File f = new File("src/contactbrowser/style.css");
        scene.getStylesheets().add("file:///"+f.getAbsolutePath().replace("\\", "/")); 
        
        primaryStage.initStyle(StageStyle.DECORATED);          
        primaryStage.setTitle("Contact Browser");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    
     
    class labelXtextfiled
    {
        
     public Label lable= new Label(); 
     public TextField textfield= new TextField();
          
    }
          
   public List<labelXtextfiled> creat_label_textfiled(String [] names )
   {
        List<labelXtextfiled> ltlist=new ArrayList<labelXtextfiled>() ;
        int size =names.length;
        for (int index=0;index<size;index++)
        {        
            ltlist.add(new labelXtextfiled());
            ltlist.get(index).lable.setText(names[index]); 
            ltlist.get(index).lable.setId("lable");
             ltlist.get(index).textfield.setId("txtfield");
            
        }
        
   return ltlist;
   }
   
   public List<Button> creat_buttons(String [] names )
   {
        List<Button> btnlist=new ArrayList<Button>() ;
        int size =names.length;
        for (int index=0;index<size;index++)
        {        
            btnlist.add(new Button());
            btnlist.get(index).setText(names[index]);
            btnlist.get(index).setId("button");
        }
        
   return btnlist;
   }
    
   public void fill_data(int index)
   {
        labelXtextfiled.get(0).textfield.setText(String.valueOf(table_data.get(index).getId()));
        labelXtextfiled.get(1).textfield.setText(table_data.get(index).getName());
        labelXtextfiled.get(2).textfield.setText(table_data.get(index).getNickName());
        labelXtextfiled.get(3).textfield.setText(table_data.get(index).getAddress());
        labelXtextfiled.get(4).textfield.setText(table_data.get(index).getHomePhone());
        labelXtextfiled.get(5).textfield.setText(table_data.get(index).getWorkPhone());
        labelXtextfiled.get(6).textfield.setText(table_data.get(index).getCellPhone());
        labelXtextfiled.get(7).textfield.setText(table_data.get(index).getEmail());
        labelXtextfiled.get(8).textfield.setText(table_data.get(index).getWebsite());
        labelXtextfiled.get(9).textfield.setText(table_data.get(index).getProfession());
   }
   
 
    public void raiseAlert(String s)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setContentText(s);
        alert.showAndWait();
    }
    
    public void clear_txtfield()
    {
         for(int i=0;i<10;i++)
      {
          labelXtextfiled.get(i).textfield.setText("");
      } 
       
    
    }
   
    public static void main(String[] args) {
        launch(args);
    }
    
}
