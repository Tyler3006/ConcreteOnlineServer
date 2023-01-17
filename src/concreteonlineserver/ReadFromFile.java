package concreteonlineserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tyler Costa
 */
public class ReadFromFile {
    String filePath;
    public String stringOut;
    String fileExtension = ".txt";
    

    public ReadFromFile(String filePath) {
        this.filePath = filePath;
        
        readFile();
    }

    //Generic File Reader from week 2
    public void readFile() {

          try {
            BufferedReader br;// = null;
            int i = 0;
            br = new BufferedReader(new FileReader(filePath));
            String line;// = null;
            try {
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    //stringOut = line;
                }
            } catch (IOException ex) {
                Logger.getLogger(ReadFromFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
          
          
    }
    
    public String stringOut(String stringOut){
        return stringOut;
    }
    
}
