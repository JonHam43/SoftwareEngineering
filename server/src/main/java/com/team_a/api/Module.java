package com.team_a.api;
import java.sql.Connection;
import com.team_a.DataMapper;

import java.util.HashMap;

 abstract class Module
{
    protected Connection conn;
    protected String query;
    protected HashMap<String,String>map;
    protected DataMapper dm;

    public Module(Connection conn)
    {
        this.conn = conn;
        dm = DataMapper.getInstance();
    }

/**
 * @apiNote This method will be used to handle the query being sent to the DataBase each module handles Their query diffrently
 * @param query
 * @return
 */
    String handleQuery(String query)
    {
        throw new UnsupportedOperationException("Unimelemented operatin in module");
    }


    /**
     * @apiNote This class will be used to swap between the diffrent logic need to handle api calls
     */
    public String handleApi(String code, String json)
    {
        throw new UnsupportedOperationException("Unimelemented operatin in module");
    }
    
}
