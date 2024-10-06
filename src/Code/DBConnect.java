
package Code;

import java.sql.*;
import javax.swing.JOptionPane;


public class DBConnect {
    public static Connection connect(){
        
        Connection conn = null;
        
        try{
           Class.forName("com.mysql.cj.jdbc.Driver");
           conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/medmanage","root","");
           
        }
        catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        return conn;
    }

    
}
