package com.team_a.api;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @apiNote This module retrives data only
 * @implNote Module used to implement the HTTP GET response
 * @author Carl Hartry Jr.
 */
public class Get extends Module
{
    private String result;    
    public Get(Connection conn)
    {
        super(conn);
        result ="";
    }
    

    private  String getLoginInfo(String json)
     {
      
        try
        {
            map = dm.getMapping(json);
            query = String.format("SELECT * FROM (SELECT * FROM EMPLOYEES  WHERE USER_NAME = '%1$s' AND PASSWORD = '%2$s' "+
            " UNION ALL SELECT * FROM CUSTOMERS WHERE USER_NAME = '%1$s' AND PASSWORD = '%2$s') AS DATA",map.get("User_Name"),map.get("PASSWORD")); 
            result = handleQuery(query);
        }
        catch(Exception e)
        {    
            e.printStackTrace();
            return"400 Bad Request";
        }
        return result;
    }

    private String getSearch(String json) 
    {
        String table = " ", attubutes = "WHERE ", limit = " ";
        String[] data = dm.getArray(json);
        boolean firstloop = true;

        for(int i = 0; i < data.length;++i)
        {
            if(data[i].toLowerCase().contains("table"))
            {
                table = data[i].split(":")[1];
            }
            else if(data[i].toLowerCase().contains("limit"))
            {
                limit = "Limit  "+ data[i].split(":")[1];
            }
            else
            {
                if(!firstloop)
                    attubutes+="AND";
                String []temp = data[i].replaceAll("\\s", "").split(":");
                temp[1] = "'%"+temp[1]+"%'";
                try {
                    attubutes += String.format(" %s LIKE  %s  ",temp[0],temp[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                firstloop = false;
            } 
        }
        query = String.format("SELECT * FROM %1$s %2$s %3$s;",table,attubutes,limit).toUpperCase();
        result = handleQuery(query);
        return  result;
    }

    @Override
    public String handleQuery(String query) 
    {
        try(Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query))
        {
            
            ArrayList<HashMap<String,String>>  resultList = new ArrayList<HashMap<String,String>>(); 
            ResultSetMetaData metaData = rs.getMetaData();

                while(rs.next())
                {
                    map = new HashMap<String,String>();
                    for (int i = 2; i <= metaData.getColumnCount(); i++)
                    {
                        String columnName = metaData.getColumnName(i);
                        Object value = rs.getObject(columnName);
                        map.put(columnName,""+value);// decrypt here
                    }
                    resultList.add(map);
                    map = null;
                }
                return resultList.isEmpty() ? "404: Not Found" :resultList.size()==1 ?  dm.getJson(resultList.get(0))  : dm.getJson(resultList);
        }
        catch(Exception e )
        {
            e.printStackTrace();
            return "400: Bad Request";
        }
    } 

    @Override
    public String handleApi(String code, String json)
    {
        result = switch(code)
        {
            case "1000"-> this.getSearch(json);
            case "1001"-> this.getLoginInfo(json);
            default -> "501 Not Implemented";
        };
        return result;
    }  
}