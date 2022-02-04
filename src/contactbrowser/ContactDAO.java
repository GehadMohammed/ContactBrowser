/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package contactbrowser;

import java.sql.*; 
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author amr
 */
public class ContactDAO {
    
    Statement stmt;
    Connection con;  
    PreparedStatement pst;
    

    public ContactDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int insert_record(ContactPerson cp){
        int i=0;
        try {
            String insert = "insert into contact " +
                    "(name,nick_name,address,home_phone,work_phone,cell_phone,email,web_site,profession)"+
                    "values (?,?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(insert);
            pst.setString(1, cp.getName());
            pst.setString(2, cp.getNickName());
            pst.setString(3, cp.getAddress());
            pst.setString(4, cp.getHomePhone());
            pst.setString(5, cp.getWorkPhone());
            pst.setString(6, cp.getCellPhone());
            pst.setString(7, cp.getEmail());
            pst.setString(8, cp.getWebsite());
            pst.setString(9, cp.getProfession());
            pst.executeUpdate();
            
            pst.close();
            ResultSet rs;
            stmt=con.createStatement();
            rs=stmt.executeQuery("SELECT id FROM contact ORDER BY id DESC LIMIT 1");
            rs.next();            
            i=rs.getInt("id");
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
     return i;
    }
    
    public int update_record (ContactPerson cp) 
    {
       int status=0;
        try {
            String updatequery= "UPDATE contact SET name = ?, nick_name= ?, address = ? , home_phone = ? ,work_phone=?"
                    + ",cell_phone=?,email=?,web_site=?,profession=? WHERE id = ?";
            pst=con.prepareStatement(updatequery);
            pst.setString(1, cp.getName());
            pst.setString(2, cp.getNickName());
            pst.setString(3, cp.getAddress());
            pst.setString(4, cp.getHomePhone());
            pst.setString(5, cp.getWorkPhone());
            pst.setString(6, cp.getCellPhone());
            pst.setString(7, cp.getEmail());
            pst.setString(8, cp.getWebsite());
            pst.setString(9, cp.getProfession());
            pst.setInt(10, cp.getId());
            status =pst.executeUpdate();
            pst.close();
         
        } catch (SQLException ex) {
            Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
           return status;
    }
    public void delete_record(int id)
    {
        try {
            String deletequery="DELETE FROM contact WHERE id=?";           
            pst=con.prepareStatement(deletequery);
            pst.setInt(1, id);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    public void connect_DB(){
        try {
            con=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/addressbook","gehad","2801");
            stmt=con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<ContactPerson> getContacts(){
        try {
            ArrayList<ContactPerson> mylist = new ArrayList<ContactPerson>();
            String query = "select * from contact";
            ResultSet rs = stmt.executeQuery(query);
            ContactPerson cp;
           
            while(rs.next())
            {
                cp = new ContactPerson();
                cp.setId(rs.getInt("id"));
                cp.setName(rs.getString("name"));
                cp.setNickName(rs.getString("nick_name"));
                cp.setAddress(rs.getString("address"));
                cp.setHomePhone(rs.getString("home_phone"));
                cp.setWorkPhone(rs.getString("work_phone"));
                cp.setCellPhone(rs.getString("cell_phone"));
                cp.setEmail(rs.getString("email"));
                cp.setWebsite(rs.getString("web_site"));
                cp.setProfession(rs.getString("profession"));
                mylist.add(cp);
            }
            
            stmt.close();
            return mylist;
        } catch (SQLException ex) {
            Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void close(){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void create_DB(){
        String DB_query,table_query,insert_query;
        DB_query = "CREATE database addressbook";
        table_query = "CREATE TABLE contact " +
                    "(id INT UNSIGNED not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(100) not NULL, " + 
                    " nick_name VARCHAR(20), " +
                    " address VARCHAR(128), " +
                    " home_phone VARCHAR(10), " +
                    " work_phone VARCHAR(10), " +
                    " cell_phone VARCHAR(10), " +
                    " email VARCHAR(100), " +
                    " birthday date, " +
                    " web_site VARCHAR(100), " +
                    " profession VARCHAR(100), " +
                    " PRIMARY KEY ( id ))";
        insert_query = "insert into contact " +
                "(name,nick_name,address,home_phone,work_phone,cell_phone,email,birthday,web_site,profession)"+
                "values ('Bruce wyne','batman','XYZ batcave','987654210','627828','0115554448','batman@gmali.com','1976/02/03','batlog.com','super hero')";
        try {
            con=DriverManager.getConnection
                        ("jdbc:mysql://localhost/?user=gehad&password=2801");
            //here sonoo is database name, root is username and password
            stmt=con.createStatement();
            stmt.executeUpdate(DB_query);
            //Executing the query
            con.close();
            connect_DB();
            stmt.executeUpdate(table_query);
            stmt.executeUpdate(insert_query);
        } catch (SQLException ex) {
            Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


 /*      public static void main(String[] args) {
           ContactDAO c=new ContactDAO();
           c.create_DB();
     
    }*/
 
}
