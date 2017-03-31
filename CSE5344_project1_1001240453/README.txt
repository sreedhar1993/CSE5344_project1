CSE_5344_Project1_1001240453
*************************************************************************************************************
WEB PROXY CACHE SERVER

This project in done in JAVA using socket programming. Client request is sent from a browser to a server through proxy server.
If a client request is found in cache of proxy server, the response is sent directly from proxy server without contacting the main server.
You can find in server output window if a url is cached or not.
Eg. "URL ***** is found in cache" 
    is pinrted on server output window.

All the http request and reponse messages are automatically written into log.txt file.




**************************************************************************************************************

Requirements to Run this project:

1. JDK
2. NetBeans 8.1
3. Mozilla Firefox

**************************************************************************************************************

Steps to run the project:

1. Open the project in NetBeans 8.1.
2. Default port is 8080 and for a user input port run the project by passing the port number as argument.
3. After starting the server at specified port number, set your browser settings with HTTP proxy as 127.0.0.1 and specified port number.
   Proxy Settings for Mozilla Firefox  http://www.wikihow.com/Enter-Proxy-Settings-in-Firefox
4. Send your client request url from the browser after you set proxy settings.

***************************************************************************************************************
Worked well with www.google.com, www.uta.edu, www.w3c.org

Enter a wrong URL to check error handling.

****************************************************************************************************************

References:

1.http://www.java2s.com/Tutorials/Java/java.net/URLConnection/Java_URLConnection_getHeaderFields_.htm
2.http://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/
3.https://docs.oracle.com/javase/7/docs/api/java/net/HttpURLConnection.html
4.http://www.java2s.com/Code/Java/Network-Protocol/Asimpleproxyserver.htm
5.http://www.jtmelton.com/2007/11/27/a-simple-multi-threaded-java-http-proxy-server/
 
