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
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class ProxyThread extends Thread {
    private Socket socket = null;
    ProxyCache webCache;
    boolean pageIsCached = false;
    private static final int BUFFER_SIZE = 32768;
    public ProxyThread(Socket socket,ProxyCache cache) {
        super("ProxyThread");
        this.socket = socket;
        this.webCache = cache;
    }
    
     public void run() {
        //get input from user
        //send request to server
        //get response from server
        //send response to user
        String response = null;
        //creating a log file to write HTTP messages
        File file=new File("log.txt");
           try {
            if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter wFile= new FileWriter(file.getAbsoluteFile(), true);
                               BufferedWriter bw = new BufferedWriter(wFile);
            DataOutputStream out =
		new DataOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(
		new InputStreamReader(socket.getInputStream()));

            String inputLine, outputLine;
            int cnt = 0;
            int length=0;
            String urlToCall = "";
            StringBuffer responsebuf1 = new StringBuffer();
            ///////////////////////////////////
            //begin get request from client
            while ((inputLine = in.readLine()) != null) {
                try {
                    System.out.println(inputLine);
                    responsebuf1.append(inputLine);
                    responsebuf1.append("\n");
                    
                    StringTokenizer tok = new StringTokenizer(inputLine);
                    tok.nextToken();
                } catch (Exception e) {
                    break;
                }
                //parse the first line of the request to find the url
                if (cnt == 0) {
                    String[] tokens = inputLine.split(" ");
                    urlToCall = tokens[1];  //taking the url part from request message
                    System.out.println("Request for : " + urlToCall);
                }

                cnt++;
            }
            //end get request from client
            ///////////////////////////////////
                  
            //writing the HTTP Request Message to log.txt
                        bw.write("HTTP Request Message:\n");
			bw.write(responsebuf1.toString());
                        System.out.println(responsebuf1.toString());

            //Checking the URL in cache message            
            if(webCache.get(urlToCall) != null)
            {
                System.out.println("Web page " + urlToCall + " found in cache.");
                pageIsCached = true;
            
            }
            else
            {
                System.out.println("Web page " + urlToCall + " not found in cache.");
                pageIsCached = false;
            
            }
            ///////////////////////////////////


            BufferedReader rd = null;
            InputStream is = null;
            try {
                
                ///////////////////////////////////
                //begin send request to server, get response from server
                URL currentUrl = new URL(urlToCall);
			        HttpURLConnection urlConn = (HttpURLConnection) currentUrl.openConnection();
			        urlConn.connect();
                                System.out.println(urlConn.getResponseCode()+":"+urlConn.getResponseMessage());
                                //Writing HTTP Response message to log.txt
                                bw.write("HTTP Response Meassage:\n");
                                Map<String, List<String>> map = urlConn.getHeaderFields();
                                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                                    String headername = entry.getKey();
                                  if (headername != null && !headername.equals("")) {
                                      bw.write(headername);
                                      bw.write(":");
                                  }
                                      
                                      List<String> headerValues = entry.getValue();
                                      for (String value : headerValues) {
                                      bw.write(value);
                                      bw.write("\n");
                                   }
                                      System.out.println("Key : " + entry.getKey() + " ,Value : " + entry.getValue());
                
	                         }
                                bw.write("\n");
                                bw.write("****************************************************\n");
                                bw.write("\n\n");
                                bw.close();
                
                                
                                //Error Handling for incorrect URL's
                    try {
                        if(urlConn.getResponseCode()== 404 || urlConn.getResponseCode()== 403 || urlConn.getResponseCode()== 400)
                        {
                            response="404:Not Found\n"
                                    + "*********************************************************************************\n"                                    
                                    + "The server has not found anything matching the Request-URI.";
                        }
                        else {
                            
                            
                            //Getting response from the main server
                        rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        String inputLine1;
					StringBuffer responsebuf = new StringBuffer();

					while ((inputLine1 = rd.readLine()) != null) {
						responsebuf.append(inputLine1);
					}
					
					response = responsebuf.toString();
                                        
                                        System.out.println(response);
                                     length = response.length();
                        }
                        
                                                
                    } catch (IOException ioe) {
                        System.out.println(
				"********* IO EXCEPTION **********: " + ioe);
                    }
                
                //end send request to server, get response from server
                
                
                //Update the webCache
                webCache.put(urlToCall, rd);
                ///////////////////////////////////
          
                  ///////////////////////////////////
                //begin send response to client
                out.write(response.getBytes());
                out.flush();

                //end send response to client
                ///////////////////////////////////
            }
             catch( java.lang.NullPointerException j)
            {
                System.out.println("File returned NULL");
            }
            
            
            catch (Exception e) {
                //can redirect this to error log
                System.err.println("Encountered exception: " + e);
                //encountered error - just send nothing back, so
                //processing can continue
                out.writeBytes("");
            }
        
           

            //close out all resources
          if (rd != null) {
                rd.close();
            }
            
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
