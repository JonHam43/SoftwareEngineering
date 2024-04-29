package com.team_a.api;
import java.sql.Connection;
import java.sql.PreparedStatement;


//😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮😮

/**
 * @apiNote This module adds data or updates it
 * @implNote Module used to implement the HTTP POST response
 */
public class Post extends Module
{
    String result;

    public Post(Connection conn) 
    {
        super(conn); 
        result = "500 Internal Server Error";

    }
    
    private String addUser(String json)
    {
      
        try
        {
            map = dm.getMapping(json);  
            query = String.format("INSERT INTO USER (FIRST_NAME,MIDDLE_INIT,LAST_NAME,SUFFIX,DOB,STREET,CITY, STATE, ZIP, PHONE_NUMBER) VALUES ('%1$s', '%2$s', '%3$s', '%4$s', '%5$s', '%6$s', '%7$s', '%8$s', '%9$s', '%10$s')", map.get("FIRST_NAME"), map.get("MIDDLE_INIT"), map.get("LAST_NAME"), map.get("SUFFIX"), map.get("DOB"), map.get("STREET"), map.get("CITY"), map.get("STATE"), map.get("ZIP"), map.get("PHONE_NUMBER"));
            result = handleQuery(query);
        }
        catch(Exception e)
        {    
            e.printStackTrace();
            return"400 Bad Request";
        }
            return result;
    }

    private String addService(String json)
    {
        try
        {
            map = dm.getMapping(json); 
            query = String.format("INSERT INTO SERVICE (SERVICE_NAME,PRICE,TIME,DATE_OFFERED) VALUES ('%1$s', '%2$s', '%3$s', '%4$s')",map.get("SERVICE_NAME"), map.get("PRICE"),map.get("TIME"),map.get("DATE_OFFERED"));
            result = handleQuery(query);

        }
        catch(Exception e)
        {    
            e.printStackTrace();
            return"400 Bad Request";
        }
            return result;
    }
    
    private  String updateService(String json)
    {
        try
        {
            map = dm.getMapping(json); 
            query = String.format("UPDATE SERVICES SET PRICE = '%1$s', TIME = '%2$s', DATE_OFFERED = '%3$s' WHERE SERVICE_NAME = '%4$s'",map.get("PRICE"), map.get("TIME"),map.get("DATE_OFFERED"),map.get("SERVICE_NAME"));
            result = handleQuery(query);
        }
        catch(Exception e)
        {    
            e.printStackTrace();
            return"400 Bad Request";
        }
            return result;
    }


    private  String addJob(String json)
    {
        try
        {
            map = dm.getMapping(json); 
            query = String.format("INSERT INTO JOBS (SERVICE_NAME, DATE_OFFERED, REQUIREMENT) VALUES ('%1$s', '%2$s', '%3$s'", map.get("SERVICE_NAME"), map.get("DATE_OFFERED"), map.get("REQUIREMENT"));
            result = handleQuery(query);


        }
        catch(Exception e)
        {    
            e.printStackTrace();
            return"400 Bad Request";
        }
            return result;
    }

    /**
     * @apiNote This method will take in json that contains a identifer,table tag, and data to update from sed table and update or create  sed data.
     * @implNote is used as a gernal update.
     * @param json
     * @return
     */
    private String update(String json)
    {
        String table = " ", attubutes = "",identifiers = "",tempJson,whereto="";
        tempJson = json.toLowerCase();

        if(!tempJson.contains("table") || !tempJson.contains("identifiers"))
            return"400 Bad Request";
        
        int index = tempJson.indexOf("\"identifiers\"");
        identifiers = json.substring(index,json.indexOf("}",index)+1);
        tempJson = json.substring(0, index-1) + json.substring(index + identifiers.length());
        map = dm.getMapping(tempJson);
        
        if(identifiers.contains(","))
        {
            boolean firstLoop = true;
            identifiers =identifiers.replaceAll("\\{|\\}|\"", "");
            identifiers = identifiers.substring(12,identifiers.length());
            for(String v: identifiers.split(","))
            {
                String[]tempArr = v.split(":");
                try 
                {
                    whereto += !firstLoop ?" AND "+ String.format(" %s LIKE '%s' ",tempArr[0], tempArr[1]) : String.format(" %s LIKE '%s' ",tempArr[0], tempArr[1]);
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
                firstLoop = false;
            }
        }
        else
        {
            String[]tempArr = identifiers.replaceAll("\\{|\\}|\"", "").split(":");
            try 
            {
                whereto += String.format("  %s LIKE '%s' ",tempArr[0], "'"+tempArr[1])+"'";
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }

        for(String s: map.keySet())
        {
            if(s.toLowerCase().equals("table"))
            {
                table = map.get(s);
            }
            else
            {
                if(s.toLowerCase().equals("trans_code"))
                    continue;
                    // if passcode or name encypt
                attubutes += s+ " = '"+ map.get(s)+"',"; 
            }
        }
        map = null;
        attubutes = attubutes.substring(0, attubutes.length() - 1);
        query = String.format("UPDATE %1$s SET %2$s WHERE %3$s;",table,attubutes,whereto).toUpperCase();
        result = handleQuery(query);
        return result ;
    }

    private String updateLevel(String json)
    {
        if(!json.contains("table") || !json.contains("identifiers")||!json.contains("level") )
            return"400 Bad Request";
         String query ="";
         










        return handleQuery(json);
    }



    @Override
    public String handleQuery(String query) 
    {
        int updateCount = 0;
        try(PreparedStatement stmt = conn.prepareStatement(query))
        {
            updateCount += stmt.executeUpdate();  
        }
        catch(Exception e )
        {
            e.printStackTrace();
            return "400 Bad Request";
        }
        return "200 OK";
    } 

    @Override
    public String handleApi(String code, String json)
    {
        
        result = switch(code)
        {
            case "3000"-> this.addUser(json);
            case "3001"-> this.addService(json);
            case "3010"-> this.addJob(json);
            case "3011"-> this.updateService(json);
            case "3100"-> this.update(json);
            case "3101"-> this.updateLevel(json);
           
            default -> "501 Not Implemented";
        };
        return result;
    }
  
}