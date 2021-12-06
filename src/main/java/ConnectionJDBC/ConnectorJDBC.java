package ConnectionJDBC;

import Tables.*;

import java.sql.*;
import java.util.ArrayList;

public class ConnectorJDBC {
    private static ConnectorJDBC connectorJDBC = new ConnectorJDBC();
    private String MySQL_URL = "jdbc:mysql://localhost:3306/enesh_db?useLegacyDatetimeCode=false&serverTimezone=UTC";
    public boolean isHaveErrorInWork = false;

    public static ConnectorJDBC getInstance() {
        return connectorJDBC;
    }

    private ConnectorJDBC() {
        try {
             Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e){
            isHaveErrorInWork = true;
        }
    }

    private boolean queryExecuteUpdate(String SQLRequest){
        Connection connection = null;
        Statement statement = null;
        try{
            connection = DriverManager.getConnection(MySQL_URL, "root", "root");
            statement = connection.createStatement();
            statement.executeUpdate(SQLRequest);
            return true;
        } catch (Exception e){
            return !(isHaveErrorInWork = true);
        } finally {
            try{
                connection.close();
                statement.close();
            } catch (Exception e){
                return false;
            }
        }
    }

    public boolean insert (String table, String columns, String values){
        String SQLRequest = "insert into " + table + "(" + columns + ") values (" + values + ")";
        return queryExecuteUpdate(SQLRequest);
    }

    public boolean update (String table, int id, String values){
        String SQLRequest = "update " + table + " set " + values + " where id = " + id;
        return queryExecuteUpdate(SQLRequest);

    }

    public boolean delete (String table, int id){
        String SQLRequest = "delete from " + table + " where id =" + id;
        return queryExecuteUpdate(SQLRequest);
    }

    public ArrayList<InterfaceTable> select(String table, String whereString, String orderString) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(MySQL_URL, "root", "root");
            statement = connection.createStatement();
            if(!("".equals(orderString.trim()))) orderString = "order by " + orderString;
            if(!("".equals(whereString.trim()))) whereString = "where " + whereString;
            //System.out.println("select * from `" + table + "` " + whereString + " " + orderString);
            ResultSet myResult = statement.executeQuery("select * from `" + table + "` " + whereString + " " + orderString);

            ArrayList<InterfaceTable> cars = new ArrayList<>();
            while (myResult.next())cars.add(resToModel(myResult, table));
            return cars;
        } catch (Exception e){
            isHaveErrorInWork = true;
            return null;
        } finally {
            try{
                connection.close();
                statement.close();
            } catch (Exception e){
                return null;
            }
        }
    }


    private InterfaceTable resToModel(ResultSet myResult, String table){
        try {
            switch (table) {
                case "sessions":
                    return res_set_to_sessions(myResult);
                case "antes":
                    return res_set_to_antes(myResult);
                case "employers":
                    return res_set_to_employers(myResult);
                default:case "users":
                    return res_set_to_users(myResult);
            }
        } catch (Exception e){
            isHaveErrorInWork = true;
            return null;
        }
    }

    private OneSession res_set_to_sessions(ResultSet myResult) throws Exception  {
        OneSession division = new OneSession(
            myResult.getInt(1),
            myResult.getString(2),
            myResult.getInt(3),
            myResult.getInt(4)
        );
        return division;
    }

    private OneAnte res_set_to_antes(ResultSet myResult) throws Exception  {
        OneAnte employee = new OneAnte(
            myResult.getInt(1),
            myResult.getInt(2),
            myResult.getInt(3)
        );

        return employee;
    }

    private OneEmployer res_set_to_employers(ResultSet myResult) throws Exception  {
        OneEmployer firm = new OneEmployer(
            myResult.getInt(1),
            myResult.getString(2),
            myResult.getInt(3),
            myResult.getString(4)
        );
        return firm;
    }

    private OneUser res_set_to_users(ResultSet myResult) throws Exception  {
        OneUser user = new OneUser(
            myResult.getInt(1),
            myResult.getString(2),
            myResult.getString(3),
            myResult.getInt(4)
        );
        return user;
    }
}
