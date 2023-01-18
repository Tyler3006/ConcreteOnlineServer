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
    int maxFilesToSend;

        ServerSocket serverSocket = new ServerSocket(8000);

    public FileSender() throws IOException {
        //this.fileName = fileName;
        maxFilesToSend = 0;
        //FileOut();
        //newerFileOut();
        //newFileOut();
        //fileOut();
        multiFileOut();
    }
    //maxFilesToSend < 1
    public void multiFileOut() throws IOException{
        
        ServerSocket serverSocket = new ServerSocket(5000); // create server socket on port 5000
        Socket clientSocket = serverSocket.accept(); // wait for client to connect
        OutputStream outputStream = clientSocket.getOutputStream(); // get output stream to send data to client
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        // directory containing the files to send
        File directory = new File("jobs/");

        // get list of files in the directory
        File[] files = directory.listFiles();

        // send number of files to be sent
        dataOutputStream.writeInt(files.length);

        for (File file : files) {
            // send file name and file size
            dataOutputStream.writeUTF(file.getName());
            dataOutputStream.writeLong(file.length());

            // send file contents
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();
        }

        outputStream.close();
        clientSocket.close();
        serverSocket.close();
        
        
        
//         while (maxFilesToSend < 1) {
//            Socket socket = serverSocket.accept();
//            OutputStream outputStream = socket.getOutputStream();
//            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
//
//            File[] files = new File("jobs/").listFiles();
//            dataOutputStream.writeInt(files.length);
//            for (File file : files) {
//                dataOutputStream.writeUTF(file.getName());
//                FileInputStream fileInputStream = new FileInputStream(file);
//                byte[] buffer = new byte[4096];
//                int bytesRead;
//                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//                fileInputStream.close();
//            }
//            dataOutputStream.flush();
//            outputStream.flush();
//            outputStream.close();
//            socket.close();
//                        maxFilesToSend = 1;
//
//        }
    }
    
   
//        public void fileOut() throws IOException{
//            
//            // Set up a server socket to listen for client connections
//
//        while (maxFilesToSend < 1) {
//            // Wait for a client to connect
//            Socket clientSocket = serverSocket.accept();
//
//            // Open the file to send
//            File file = new File("jobs/"+fileName);
//            byte[] bytes = new byte[(int) file.length()];
//            FileInputStream fis = new FileInputStream(file);
//            BufferedInputStream bis = new BufferedInputStream(fis);
//            bis.read(bytes, 0, bytes.length);
//
//            // Send the file name
//            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
//            out.writeUTF(file.getName());
//
//            // Send the file
//            OutputStream os = clientSocket.getOutputStream();
//            os.write(bytes, 0, bytes.length);
//            os.flush();
//
//            // Close the socket
//            clientSocket.close();
//            maxFilesToSend = 1;
//      }
//    }
            
        
    
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
