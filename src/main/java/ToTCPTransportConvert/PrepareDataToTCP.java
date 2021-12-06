package ToTCPTransportConvert;

import ConnectionJDBC.QueryToJDBC;
import Tables.*;
import ObjStringConvertion.ObjectStringConvertor;

import java.util.ArrayList;

public class PrepareDataToTCP {
    private QueryToJDBC connectorJDBC = new QueryToJDBC();

    public String mainUserSearch(String data){
        OneUser user = (OneUser) ObjectStringConvertor.deserialiseString(data, OneUser.class);
        ArrayList<OneUser> users = connectorJDBC.select_users("",
                "`name` = '" + user.name + "' AND `password` = '" + user.password + "'");
        return users.size() > 0 ? ObjectStringConvertor.serialiseObject(users.get(0)) : "";

    }


    public String select_(String orderStringAndWhere, Class<? extends InterfaceTable> currentClass){
        String componentsQuery[] = new String[]{"", ""};
        if(!(orderStringAndWhere.equals("")))
            componentsQuery = orderStringAndWhere.split("~~~~~");
        return selectMainUser(componentsQuery[0], componentsQuery[1], currentClass);
    }

    private String selectMainUser(String table, String data, Class<? extends InterfaceTable> currentClass){
        switch (currentClass.getName()) {
            default:
            case "Tables.OneSession":      return ObjectStringConvertor.serialiseObjects(connectorJDBC.select_sessions(table, data));
            case "Tables.OneAnte":     return ObjectStringConvertor.serialiseObjects(connectorJDBC.select_antes(table, data));
            case "Tables.OneEmployer":     return ObjectStringConvertor.serialiseObjects(connectorJDBC.select_employers(table, data));
            case "Tables.OneUser":         return ObjectStringConvertor.serialiseObjects(connectorJDBC.select_users(table, data));
           }
    }


    public String delete(String data){
        String componentsQuery[] = data.split("~~~~~");
        if(connectorJDBC.delete(componentsQuery[0], Integer.valueOf(componentsQuery[1]))) return "ok";
        else  return "";
    }

    public String insert(String data, String table, Class<? extends InterfaceTable> thisInterface){
        if(connectorJDBC.insert(ObjectStringConvertor.deserialiseString(data, thisInterface), table)) return "ok";
        else return "";

    }

    public String update(String data, String table, Class<? extends InterfaceTable> thisInterface){
        String componentsQuery[] = data.split("~~~~~");
        if(connectorJDBC.update(ObjectStringConvertor.deserialiseString(componentsQuery[0], thisInterface)
                , table, Integer.valueOf(componentsQuery[1])))return "ok";
        else return "";
    }

}
