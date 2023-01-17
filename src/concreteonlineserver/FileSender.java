/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concreteonlineserver;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 *
 * @author Tyler Costa
 *
 */
public class FileSender {

    String fileName;

        ServerSocket serverSocket = new ServerSocket(8000);

    public FileSender(String fileName) throws IOException {
        this.fileName = fileName;
        //FileOut();
        //newerFileOut();
        //newFileOut();
        fileOut();
    }

   
        public void fileOut() throws IOException{
            
            // Set up a server socket to listen for client connections

        while (true) {
            // Wait for a client to connect
            Socket clientSocket = serverSocket.accept();

            // Open the file to send
            File file = new File("jobs/"+fileName);
            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(bytes, 0, bytes.length);

            // Send the file name
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            out.writeUTF(file.getName());

            // Send the file
            OutputStream os = clientSocket.getOutputStream();
            os.write(bytes, 0, bytes.length);
            os.flush();

            // Close the socket
            clientSocket.close();
      }
    }
            
        
    
//      public void newFileOut() throws IOException{
//        int port = 1234; // specify a port number
//        ServerSocket serverSocket = new ServerSocket(port);
//
//        while (true) {
//            System.out.println("Waiting for a client to connect...");
//            Socket socket = serverSocket.accept();
//            System.out.println("Client connected from " + socket.getInetAddress());
//
//            File file = new File("jobs/"+fileName); // specify the file to be sent
//            byte[] bytes = new byte[(int) file.length()];
//            FileInputStream fis = new FileInputStream(file);
//            BufferedInputStream bis = new BufferedInputStream(fis);
//            bis.read(bytes, 0, bytes.length);
//
//            OutputStream os = socket.getOutputStream();
//            os.write(bytes, 0, bytes.length);
//            os.flush();
//            socket.close();
//
//            System.out.println("File sent to client.");
//        }
//    }
//    //ServerSocket serverSocket = new ServerSocket(900);

   
   
}
