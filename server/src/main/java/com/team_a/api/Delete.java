package com.team_a.api;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @apiNote This module deletes data only
 * @implNote Module used to implement the HTTP DELETE response
 */
public class Delete extends Module 
{
    private static final String String = null;
    String result;

    public Delete(Connection conn) 
    {
        super(conn);
    }

    public String deleteUserInfo(String json) 
    {
        try
        {
            map = dm.getMapping(json);
            String temp = "FIRST_NAME = '"+map.get("FIRST_NAME")+"'";
            for(String s: map.keySet())
            {
                if(!s.equals("FIRST_NAME"))
                {
                    if(!map.get(s).equals("null"))
                    {
                        if(!s.equals("Trans_Code"))
                            temp += " AND "+s+" = '"+map.get(s)+"'";
                    }
                    
                }
                
            }  
            temp+=";";
            String query = String.format("DELETE FROM USER WHERE %s",temp);
            result = handleQuery(query);
        }
         catch (Exception e) {
            e.printStackTrace();
            return "400 Bad Request";
        }
        return result;
    }
    public String deleteServices(String json) 
    {
        try
        {
            map = dm.getMapping(json);
            String temp = "SERVICE_NAME = '"+map.get("SERVICE_NAME")+"'";
            for(String s: map.keySet())
            {
                if(!s.equals("SERVICE_NAME"))
                {
                    if(!map.get(s).equals("null"))
                    {
                        if(!s.equals("Trans_Code"))
                            temp += " AND "+s+" = '"+map.get(s)+"'";
                    }
                    
                }
                
            }  
            temp+=";";
            String query = String.format("DELETE FROM SERVICE WHERE %s",temp);
            result = handleQuery(query);
            
            /* 
            map = dm.getMapping(json);  
            String query = String.format("DELETE FROM SERVICE WHERE SERVICE_NAME = '%1$s' AND PRICE = '%2$s' AND TIME = '%3$s' AND DATE_OFFERED = '%4$s'", map.get("SERVICE_NAME"), map.get("PRICE"), map.get("TIME"),map.get("DATE_OFFERED"));
            result = handleQuery(query);
            */
        }
         catch (Exception e) {
            e.printStackTrace();
            return "400 Bad Request";
        }
        return result;
    }

    @Override
    public String handleApi(String code, String json) {
        result = switch (code) {
            case "4000" -> this.deleteUserInfo(json);
            case "4001" -> this.deleteServices(json);
            default -> "501 Not Implemented";
        };
        return result;
    }
    @Override
    public String handleQuery(String query) 
    {
        int deleteCount = 0;
        String diditfuckup;
        try(PreparedStatement stmt = conn.prepareStatement(query))
        {
            deleteCount += stmt.executeUpdate(); 
        }
        catch(Exception e )
        {
            e.printStackTrace();
            return "404  Bad Request";
        }
        return "200 OK";
    }
}
