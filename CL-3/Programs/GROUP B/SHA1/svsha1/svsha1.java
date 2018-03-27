/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svsha1;

/**
 *
 * @author nakukuku
 */

import java.net.*;
import java.io.*;

public class svsha1 extends Thread {
    private ServerSocket serverSocket;
   
   public svsha1(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
   }

   public void run()
   {
      while(true)
      {
         try
         {
            System.out.println("Waiting for client on port " +
            serverSocket.getLocalPort() + "...");
            
            Socket server = serverSocket.accept();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            serverSocket.setSoTimeout(1000000);

            DataInputStream in =new DataInputStream(server.getInputStream());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            
            String upass=in.readUTF();
            System.out.println(upass);
            
            if(upass.equals("aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d")){         
            out.writeUTF("1");
            server.close();
            }
            else{
            out.writeUTF("2");    
            }
         }catch(Exception s)
         {
            s.printStackTrace();
             break;
         }
      }
   }
    
    public static void main(String args[])
    {
      try
      {
         Thread t = new svsha1(9890);
         t.start();
      }catch(Exception e)
      {
         e.printStackTrace();
      }
    }
}
