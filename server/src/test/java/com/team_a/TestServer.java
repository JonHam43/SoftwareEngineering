package com.team_a;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.team_a.api.Delete;


public class TestServer 
{
  private  static TestClient tc ;
  protected  static Gson gson;
  private static String jsonString,result;
  private static boolean connected;
  private static HashMap<String,String> map;
   

  @BeforeAll
  public static  void start()
  {
  }

  @AfterAll
  public static void  end()
  {

  }

  @BeforeEach
  public  void beforeEach()
  {
    map = new HashMap<String,String>();
    result="";
    connected = false;
    jsonString="";
    tc = new TestClient("localhost",5001);
    connected = tc.connect();
    gson = new Gson();

  }

  @AfterEach
  public  void AfterEach()
  {

  }

  @Test
  public void testConnection()
  {
    assertTrue(connected);
  }


  @Test
  public void testReadMessage()
  {
    Object  myobj = Integer.parseInt(tc.readData().split(":")[1]);
    assertTrue(myobj instanceof Integer);
  }

  @Test
  public void testWriteMessage()
  {
    String message = "auth~post~hello";
    result = tc.readData();
    tc.writeData(message);
    assertTrue(tc.readData()!= null);
    tc.writeData("end");
  }

  @Test
  public void testGet()
  {
    tc.readData();
    jsonString = "{\"User_Name\":\"WOR5\", \"PASSWORD\":PASSCODE1}";
    tc.writeData(jsonString);
    result= null;
    result = tc.readData();
    assertNotNull(result);
  }

  @SuppressWarnings("unchecked")
  @Test 
  public void Gson_mapTest()
  {
    map.put("hero", "John");
    GsonBuilder builder = new GsonBuilder(); 
    builder.setPrettyPrinting(); 
    Gson gson = builder.create(); 
    map = gson.fromJson(jsonString, map.getClass()); 
    String json = gson.toJson(map);
    System.out.println(json);
  }

  @Test void failGet()
  {
    jsonString= String.format("{\"User_Name\": \"$2$s\", \"PASSWORD\":\"$1$s\",\"Trans_Code\":\"1001\"}",null,null,1001);
    tc.readData();
    tc.writeData(jsonString);
    assertEquals("400 Bad Request", tc.readData());
  }

  @SuppressWarnings("unchecked")
  @Test 
  public void getTestLoginInfo()
  {
    jsonString = "{ \"Trans_Code\": \"1001\",\"User_Name\":\"617000009\", \"PASSWORD\":PASSCODE1}"; 
    GsonBuilder builder = new GsonBuilder(); 
    builder.setPrettyPrinting(); 
    Gson gson = builder.create(); 
    map = gson.fromJson(jsonString,map.getClass()); 
    //creates class from json^^^
    System.out.println(map.toString());  
    jsonString = gson.toJson(map); 
    System.out.println();  
    //object to json
    tc.readData();
    tc.writeData(jsonString);
    map = gson.fromJson(tc.readData(), map.getClass());
    jsonString = gson.toJson(map);
    System.out.println(result);
    result="{\n"+
      "  \"SUFFIX\": \"null\",\n" +
      "  \"PASSWORD\": \"PASSCODE1\",\n"+
      "  \"DOB\": \"1/23/1777\",\n"+
      "  \"ADDRESS\": \"100 WORKISHOME CT.,WORK,GA 10011\",\n"+
      "  \"AUTHORIZATION_LEVEL\": \"WORKER\",\n"+
      "  \"USER_NAME\": \"617000009\",\n"+
      "  \"EMAIL\": \"617000009@example.com\",\n"+
      "  \"PHONE_NUMBER\": \"721-111-2222\",\n"+
      "  \"NAME\": \"WORK ERMAN\"\n"+
    "}";
    System.out.println(jsonString);
    assertEquals(result, jsonString);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testPost()
  {
    jsonString = "{ \"Trans_Code\": \"3000\",\"FIRST_NAME\":\"FRANK\", \"MIDDLE_INIT\":\"L\", \"LAST_NAME\":\"Richardson\",\"SUFFIX\":\"JR\", \"DOB\":\"01/01/1920\",\"STREET\":\"123 frank rd\",\"CITY\":\"Franky Bottom\",\"STATE\":\"FR\",\"ZIP\":\"12345\",\"PHONE_NUMBER\":\"123-456-7890\"}";
    GsonBuilder builder = new GsonBuilder(); 
    builder.setPrettyPrinting(); 
    Gson gson = builder.create(); 
    map = gson.fromJson(jsonString,map.getClass()); 
    //creates class from json^^^
    System.out.println(map.toString());  
    jsonString = gson.toJson(map); 
    System.out.println();  
    //object to json
    tc.readData();
    tc.writeData(jsonString);
    //map = gson.fromJson(tc.readData(), map.getClass());   //error line
    jsonString = gson.toJson(map);
    System.out.println(jsonString);
  }

  @Test
  public void getSearch()
  {
    jsonString = "{ \"Trans_Code\": \"1000\",\"User_Name\":\"617\", \"PASSWORD\":PASSCODE1,\"limit\":\"1\",\"table\":Customers}"; 
    GsonBuilder builder = new GsonBuilder(); 
    builder.setPrettyPrinting(); 
    Gson gson = builder.create(); 
    map = gson.fromJson(jsonString,map.getClass()); 
    //creates class from json^^^
    System.out.println(map.toString());  
    jsonString = gson.toJson(map); 
    System.out.println();  
    //object to json
    tc.readData();
    tc.writeData(jsonString);
    map = gson.fromJson(tc.readData(), map.getClass());
    jsonString = gson.toJson(map);
    System.out.println(result);
    result="{\n"+
      "  \"SUFFIX\": \"null\",\n" +
      "  \"PASSWORD\": \"PASSCODE1\",\n"+
      "  \"DOB\": \"1/23/1777\",\n"+
      "  \"ADDRESS\": \"100 WORKISHOME CT.,WORK,GA 10011\",\n"+
      "  \"AUTHORIZATION_LEVEL\": \"WORKER\",\n"+
      "  \"USER_NAME\": \"617000009\",\n"+
      "  \"EMAIL\": \"617000009@example.com\",\n"+
      "  \"PHONE_NUMBER\": \"721-111-2222\",\n"+
      "  \"NAME\": \"WORK ERMAN\"\n"+
    "}";
    System.out.println(jsonString);
    assertEquals(result, jsonString);

  }

  @Test
  public void testPut() 
  {
      
      String jsonString = "{ \"Trans_Code\": \"2000\",\"FIRST_NAME\":\"Gongbing\", \"MIDDLE_INIT\":\"L\", \"LAST_NAME\":\"Hong\",\"SUFFIX\":\"JR\",\"ADDRESS\":\"123 yao rd\",\"PHONE_NUMBER\":\"123-456-7890\",\"DOB\":\"01/01/1920\"}";        
      tc.connect();
      tc.writeData(jsonString);

      String result = tc.readData();

      try {
        assertEquals("200 OK", result);
      } catch (Exception e) {
        e.printStackTrace();
      }
  }

  @Test
  public void testPostCreateOrUpdate() 
  {
    String jsonString = "{\"Trans_Code\": \"3100\",\"PHONE_NUMBER\":\"478-446-7890\",\"TABLE\":\"USER\",\"identifiers\": {\"FIRST_NAME\":\"WORKLIN\",\"LAST_NAME\":\"QUIERMAN\"}}";      
    tc.connect();
    tc.readData();
    tc.writeData(jsonString);
    String result = tc.readData();
    assertEquals("200 OK", result);
  }

  @Test
  public void testDelete() 
  {
      // Prepare test data
      String json = "{\"FIRST_NAME\":\"John\",\"MIDDLE_INIT\":\"D\",\"LAST_NAME\":\"Doe\",\"SUFFIX\":\"Jr\",\"DOB\":\"1990-01-01\",\"ADDRESS\":\"123 Main St\",\"PHONE_NUMBER\":\"123-456-7890\"}";

      // Create a mock database connection
      try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database_name", "your_username", "your_password")) {
          // Instantiate Delete class
          Delete delete = new Delete(conn);

          // Call the deleteUserInfo method
          String result = delete.deleteUserInfo(json);

          // Check if the result is as expected
          assertEquals("200 OK", result);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

/*
 * 
 * END OF TESTS!!!!!!!
 * 
 * 
 */
  static class TestClient 
  {
    protected String currentUser,host;
    private int publicKey,port;
    private Socket sock;
    private boolean connected = false;
    private LocalDateTime currentDateTime;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:SS");
    private final long SLEEP_TIMER=1000;

    /**
     * @param host
     * @param port
     */
    public TestClient(String host, int port) {
      this.host = host;
      this.port = port;
    }

    protected String  getCurrentDateTime()
    {
      currentDateTime = LocalDateTime.now();
      return currentDateTime.format(dateFormat);
    } 

    /**
      * this will be used to read data provide from the server
      * @implNote Must Use Connect before usage of this method
      * @param data
      * @throws ConnectException
      */
    protected String readData()
    {
      if(connected == false)
          connect();

      String data = null;
      try 
      {
        
        BufferedReader input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        data = input.readLine();
        if(data.contains("key:"))
        {
          publicKey = Integer.parseInt(data.split(":")[1]);
        }
        else
        {
          data = decryption(data);
        }

        return data;
      } catch (Exception e) 
      {
        e.printStackTrace();
      }
      return data;
    }

      /**
       * this will be used to write to the server
       * @implNote Must Use Connect before usage of this method
       * @param data
       * @throws ConnectException
       */
      protected void writeData(String data)
    {
      data = encryption(data);
      if(connected == false)
          connect();
      
      try 
      {
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        output.write(data);
        output.newLine();
        output.flush();
      } catch (Exception e)
      {
        e.printStackTrace();
      }
    }  

  /**
   * used to establish a connection to server must be used first
   * @return boolean
   */
    private boolean connect()
    {
      try
      {
        this.sock = (new Socket(InetAddress.getByName(host),port));
        connected = true;
        return true;
      }
      catch (UnknownHostException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      return false;
    }
    
  /**
   *  This encyription uses ceasar cyfer to encyrpt messages
   * @param data
   * @return
   */
    private String  encryption(String data)
    {
      char[] inputChars = data.toCharArray();
          char[] xoredChars = new char[inputChars.length];

          for (int i = 0; i < inputChars.length; i++) {
              // XOR each character with the key
              xoredChars[i] = (char) (inputChars[i] ^ publicKey);
          }

          return new String(xoredChars);

    }

    private String  decryption(String data)
    {
      char[] inputChars = data.toCharArray();
          char[] xoredChars = new char[inputChars.length];

          for (int i = 0; i < inputChars.length; i++) {
              // XOR each character with the key
              xoredChars[i] = (char) (inputChars[i] ^ publicKey);
          }
          return new String(xoredChars);
    }

    protected void sleep(long time){
      try {
        Thread.sleep(SLEEP_TIMER);
      } catch (InterruptedException e) {
      
        e.printStackTrace();
      }
    }
  }

}
