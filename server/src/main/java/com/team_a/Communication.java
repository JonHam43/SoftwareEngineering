package com.team_a;

import java.net.*;
import java.sql.Connection;
import java.io.*;
import java.util.Random;

import com.team_a.api.*;

/**
 * Class held to host communications with server on lan side.
 */
class Communication extends Thread
{
  private Socket clientSocket;
  private BufferedReader input;
  private BufferedWriter output;
  private String buffer = "";
  private int identifier,publicKey;
  private Random rand = new Random();
  private final long SLEEP_TIMER = 500;
  private Connection conn;

  protected Communication(Socket clientSocket, int identifier,Connection conn)
  {
    this.clientSocket = clientSocket;
    this.identifier = identifier;
    this.conn = conn;
  }

  
  public void run()// action done during thread
  {
    try
    {
      System.out.println(String.format("Session %d is held by (%s) ", identifier,clientSocket.getInetAddress().getHostAddress()));
      publicKey = rand.nextInt();
      //logic for query transfer
      writeTransaction("key:" + publicKey);
      sleep(SLEEP_TIMER);
      buffer = readTransaction();
      Server.lock.lock();
      buffer = handleData(buffer);//handleResource(buffer);
      Server.lock.unlock();
      sleep(SLEEP_TIMER);
      writeTransaction(encryption(buffer));
    }
    catch(Exception e)
    {
      e.printStackTrace();
      writeTransaction("500 Internal Server Error");
    }
  }

  /**
   * @apiNote Is used to parse the Json and get the correct API Calls
   * @param userInput
   * @return String 
   */
  private String handleData(String userInput)
  {
      String loader,result;
      String[] data = userInput.replaceAll("\\{|\\}|\"", "").split(",");

      for (String s : data) 
      {
        if(s.toLowerCase().contains("trans_code"))
        {
          try 
          {
            loader = s.split(":")[1].replace("\n", "").trim();
          }
          catch(IndexOutOfBoundsException e)
          {
            return  "400 Bad Request";
          }
          char c =loader.charAt((0));
          result = switch(c)
          {
            case '1' -> new Get(conn).handleApi(loader, userInput);
            case '2' -> new Put(conn).handleApi(loader, userInput);
            case '3' -> new Post(conn).handleApi(loader, userInput);
            case '4' -> new Delete(conn).handleApi(loader, userInput);
            default  -> "501 Not Implemented";
          };
          return !result.equals("{}") ? result : "400 Bad Request";
        }          
      }
    return "400 Bad Request";
  }
  
  /**
   * method  read data recieved from user.
   * @return String
   */
  private String readTransaction() 
  {
    String data ="";
    try 
    {
      input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      while(input.ready())
      {
        data += decryption(input.readLine());
      }
      return data;
    } 
    catch (Exception e) 
    {
      e.printStackTrace();
    }
    return data;
  }

  // method to write out messages
  private void writeTransaction(String data) 
  {
    try 
    {
      output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
      output.write(data);
      output.newLine();
      output.flush();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private String  encryption(String data)
  {
    char[] inputChars = data.toCharArray();
    char[] xoredChars = new char[inputChars.length];

    for (int i = 0; i < inputChars.length; i++) 
    {
      // XOR each character with the key
      xoredChars[i] = (char) (inputChars[i] ^ publicKey);
    }
    return new String(xoredChars);
  }

  private String  decryption(String data)
  {
    char[] inputChars = data.toCharArray();
    char[] xoredChars = new char[inputChars.length];

    for (int i = 0; i < inputChars.length; i++) 
    {
        // XOR each character with the key
        xoredChars[i] = (char) (inputChars[i] ^ publicKey);
    }
    return new String(xoredChars);
  }
}//end of comms