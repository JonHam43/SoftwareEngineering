package com.team_a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * @implNote Is a singleton class used to convert data
 * @author Carl Hartry JR
 */
public class  DataMapper 
{
    private static DataMapper  dataMapper;
    private HashMap<String,String> map;
    private Gson gson;
    private GsonBuilder builder;

    private DataMapper(){}

    public static DataMapper  getInstance()
    {
        dataMapper = dataMapper == null ? new DataMapper(): dataMapper;
        return dataMapper;
    }

    @SuppressWarnings("unchecked")
    public HashMap<String,String> getMapping(String Json)
    {
        map = new HashMap<String,String>();
        builder = new GsonBuilder(); 
        builder.setPrettyPrinting(); 
        gson = builder.create(); 
        map = gson.fromJson(Json,map.getClass());
        return map;
    } 

    public String getJson(HashMap<String,String> map)
    {
        builder = new GsonBuilder(); 
        builder.setPrettyPrinting(); 
        gson = builder.create();
        return gson.toJson(map);
    }

    public String getJson(ArrayList<HashMap<String,String>> map)
    {
        builder = new GsonBuilder(); 
        builder.setPrettyPrinting(); 
        gson = builder.create();
        return gson.toJson(map);
    }

    public String[] getArray(String json)
    {
        String []data = json.replaceAll("\\{|\\}|\"", "").split(",");
        data = Arrays.stream(data).filter(element -> !element.contains("Trans_Code")).toArray(String[] :: new );
        return data;
    }

    
}
