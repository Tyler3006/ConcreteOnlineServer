package concreteonlineserver;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tscos
 */
public class FileCreator {

    String fileInput, targetFile;
    boolean append;
    String txtExtension = ".txt";

    public FileCreator(String fileInput, String targetFile, boolean append) {
        this.fileInput = fileInput;
        this.targetFile = targetFile;
        this.append = append;
        writeFile();
    }
 public void writeFile() {
        try {
            PrintWriter pw = null;
            pw = new PrintWriter(new FileOutputStream("jobs/"+targetFile + txtExtension, append));
            pw.println(fileInput);
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
