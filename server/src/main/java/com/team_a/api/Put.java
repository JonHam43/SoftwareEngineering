package com.team_a.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
/**
 * @apiNote This module adds data only
 * @implNote Module used to implement the HTTP PUT response
 */
public class Put extends Module 
{
    String result;

    public Put(Connection conn) 
    {
        super(conn);
    }

    public String updateUser(String json) 
    {
        map = new HashMap<String,String>();
        // Use mapper to map JSON to HashMap
        map = dm.getMapping(json);

        try 
        {

        String userQuery = String.format("UPDATE USER SET FIRST_NAME = '%s', MIDDLE_INIT = '%s', LAST_NAME = '%s', SUFFIX = '%s', DOB = '%s', ADDRESS = '%s', PHONE_NUMBER = '%s' WHERE USER_NAME = '%s'",
        map.get("FIRST_NAME"), map.get("MIDDLE_INIT"), map.get("LAST_NAME"), map.get("SUFFIX"),
        map.get("DOB"), map.get("ADDRESS"), map.get("PHONE_NUMBER"));

        result = handleQuery(userQuery);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "400 Bad Request";
        }
        map =null;
        return "200 ok";
    }
    
    public String updateCus(String json) 
    {

        map = new HashMap<String,String>();
        // Use mapper to map JSON to HashMap
        map = dm.getMapping(json);

            try 
            {
            String userQuery = String.format("UPDATE USER SET FIRST_NAME = '%s', MIDDLE_INIT = '%s', LAST_NAME = '%s', SUFFIX = '%s', DOB = '%s', ADDRESS = '%s', PHONE_NUMBER = '%s' WHERE USER_NAME = '%s'",
            map.get("FIRST_NAME"), map.get("MIDDLE_INIT"), map.get("LAST_NAME"), map.get("SUFFIX"),
            map.get("DOB"), map.get("ADDRESS"), map.get("PHONE_NUMBER"));
            result = handleQuery(userQuery);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return "400 Bad Request";
        }
        map =null;
        return "200 ok";
    }
    
    public String updateAdmin(String json) 
    {
        map = new HashMap<String,String>();
        // Use mapper to map JSON to HashMap
        map = dm.getMapping(json);
        try 
        {
            String userQuery = String.format("UPDATE USER SET FIRST_NAME = '%s', MIDDLE_INIT = '%s', LAST_NAME = '%s', SUFFIX = '%s', DOB = '%s', ADDRESS = '%s', PHONE_NUMBER = '%s' WHERE USER_NAME = '%s'",
            map.get("FIRST_NAME"), map.get("MIDDLE_INIT"), map.get("LAST_NAME"), map.get("SUFFIX"),
            map.get("DOB"),map.get("ADDRESS"), map.get("PHONE_NUMBER"));
            result = handleQuery(userQuery);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return "400 Bad Request";
        }
        map =null;
        return "200 ok";
    }
    
    public String updateAuth(String json) 
    {
        map = new HashMap<String,String>();
        // Use mapper to map JSON to HashMap
        map = dm.getMapping(json);


            try {

            String userQuery = String.format("UPDATE USER SET FIRST_NAME = '%s', MIDDLE_INIT = '%s', LAST_NAME = '%s', SUFFIX = '%s', DOB = '%s', ADDRESS = '%s', PHONE_NUMBER = '%s' WHERE USER_NAME = '%s'",
            map.get("FIRST_NAME"), map.get("MIDDLE_INIT"), map.get("LAST_NAME"), map.get("SUFFIX"),
            map.get("DOB"), map.get("ADDRESS"), map.get("PHONE_NUMBER"));

            result = handleQuery(userQuery);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "400 Bad Request";
        }
        map =null;
        return "200 ok";
    }

     /**
     * @apiNote This method take a Json and will insert dat into database with given attubutes and table.
     * @implNote This is a fuction to insert data into data base by given table. Is a general Insert function
     * @param json
     * @return
     */
    protected String insert(String json)
    {
        String table = " ", attubutes = "",identifier = "";
        String[] value,data = dm.getArray(json);

        for(int i = 0; i < data.length;++i)
        {
            if(data[i].toLowerCase().contains("table"))
            {
                value = data[i].split(":");
                    if(value[1].isBlank())
                        return"400 Bad Request";
                table = value[1];
            }
            else
            {
                String []temp = data[i].replaceAll("\\s", "").split(":");
                if(temp[0].toLowerCase().equals("identifier"))
                    continue;
                attubutes += temp[0]+",";
                identifier+=temp[1]+",";
            }
        }
        value = null;
        attubutes = attubutes.substring(0, attubutes.length() - 1);
        identifier = identifier.substring(0, attubutes.length() - 1);
        query = String.format("INSERT INTO %1$s (%2$s) VALUES (%3$s);",table,attubutes,identifier).toUpperCase();
        result = handleQuery(query);
        return result ;
    }
 
    @Override
    public String handleQuery(String query) 
    {
        int result = 0;
        try(PreparedStatement stmt = conn.prepareStatement(query))
        {
            result += stmt.executeUpdate();  
        }
        catch(Exception e )
        {
            e.printStackTrace();
            return "400 Bad Request";
        }
        return "200 OK";
    }
   
    @Override
    public String handleApi(String code, String json) {
        result = switch (code) {
            case "2000" -> this.updateUser(json);
            case "2001" -> this.updateCus(json);
            case "2010" -> this.updateAuth(json);
            case "2011" -> this.updateAdmin(json);
            case "2100"-> this.insert(json);
            default -> "501 Not Implemented";
        };
        return result;
    } 
}
