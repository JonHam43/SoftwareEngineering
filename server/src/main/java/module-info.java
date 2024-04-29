module com.team_a 
{
    requires java.sql;
    requires com.google.gson;
  
    opens com.team_a to com.google.gson;
    opens com.team_a.api to com.google.gson;
    exports com.team_a;
}
