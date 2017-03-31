/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse5344_project1_1001240453;

/**
 *
 * @author SreedharReddy
 */
import java.net.*;
import java.io.*;
public class ProxyServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        //Create a webpage cache object
        ProxyCache<InputStream> webCache = new ProxyCache<InputStream>();
        
        ServerSocket serverSocket = null;
        boolean listening = true;

        int port = 8080;	//default
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
        }

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Started on: " + port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + args[0]);
            System.exit(-1);
        }

        while (listening) {
            new ProxyThread(serverSocket.accept(), webCache).start();
        }
        serverSocket.close();
    }
    
}
