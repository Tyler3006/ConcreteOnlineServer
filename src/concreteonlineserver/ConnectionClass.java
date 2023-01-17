
 
package concreteonlineserver;

//import java.sql.Connection;

import java.sql.Connection;
import java.sql.DriverManager;



/**
 *
 * @author Tyler Costa
 */
public class ConnectionClass {
    
    public Connection connection;
    
    public Connection getConnection(){
        String dbName = "ordersTestV1";
        String userName = "root";
        String password = "";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//.newInstance();0
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,userName,password); // switch commmas to + if there is an issue
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Connection Failed!");
        }
        
        return connection;
    }
    
}
