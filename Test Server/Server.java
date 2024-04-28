package com.team_a;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @apiNote This program is the Servers main entry and configuration point. 
 * @author Carl Hartry jr
 */

class Server
{
  protected static Lock lock =  new ReentrantLock();
  private static AtomicInteger index = new AtomicInteger(1);//used to keep a index on all threads
  private static Server server;
  private Connection conn;
  private final  String HOST = "localhost";
  // private final static String HOST = "10.90.123.90";
  
 private Server(){}

 public static Server getInstance()
 {
   server =  server ==  null ? new Server() : server;
  return server;
 }

/**
 *  @apiNote Used to handle activity on ports
 * @param port
 */
  protected void getPortHandler(int port)
  {
    new Thread()
    { 
      @Override
      public void run()
      {
        try
        {
          ServerSocket serverSocket = new ServerSocket(port,0,InetAddress.getByName(HOST));
         
          System.out.println("Server Starting"); 
          while (serverSocket.isBound())
          {
            System.out.println("listening for connection on port "+port);
            Socket clientSocketSession = serverSocket.accept();
            new Communication(clientSocketSession, index.getAndIncrement(),conn).start();
          }
          serverSocket.close();
          closeConnection();
        }
        catch(Exception e)
        {
          System.out.println("\n\n!!!!Critical Issuse Server running on port: "+port+" Shut Down!!!!\n\n");
          e.printStackTrace();
        }
      }
    }.start();
  }

  /**
   * Used to establish connectivity to sqlite database
   * @param dbLoc
   */
  protected  void getConnection(String dbLoc) 
  {
    Path cwd = Paths.get(System.getProperty("user.dir"));
    String path ="jdbc:sqlite:"+ cwd.resolve(dbLoc).toString();
    try 
    {
      conn = DriverManager.getConnection(path);
      System.out.println("Server Linked");
    
    } 
    catch (SQLException e) 
    {
      e.printStackTrace();
      System.out.println("Data Load failed");
      System.exit(1);
    }
    System.out.println("Data Initizilation Complete");
  }

  private  void closeConnection()
  {
    try 
    {
      conn.close();
    } 
    catch (SQLException e) 
    {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) 
  {
    System.out.println("Starting server Configuation");
    Server s = Server.getInstance();
    s.getConnection("server/src/main/java/com/team_a/data/auth.db");
    s.getPortHandler(5001);
  }
}//end of file