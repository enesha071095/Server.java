package ConnectionJDBC;

import Tables.*;

import java.util.ArrayList;

public class QueryToJDBC {
    private ConnectorJDBC connector =  ConnectorJDBC.getInstance();
    public boolean isHaveErrorInWork = false;

    public ArrayList<OneSession> select_sessions(String orderString, String where){
        try {
            ArrayList<InterfaceTable> interfaceTables;
            ArrayList<OneSession>  divisions = new ArrayList<>();
            interfaceTables = connector.select("sessions",   where, orderString);
            for (int i = 0; i < interfaceTables.size(); i++)
                divisions.add((OneSession) interfaceTables.get(i));
            return divisions;
        } catch (Exception e){
            isHaveErrorInWork = true;
            return null;
        }
    }



    public ArrayList<OneAnte> select_antes(String orderString, String where){
        try {
            ArrayList<InterfaceTable> interfaceTables;
            ArrayList<OneAnte>  firms = new ArrayList<>();
            interfaceTables = connector.select("antes",   where, orderString);
            for (int i = 0; i < interfaceTables.size(); i++) {
                firms.add((OneAnte) interfaceTables.get(i));
            }
            return firms;
        } catch (Exception e){
            isHaveErrorInWork = true;
            return null;
        }
    }

    public ArrayList<OneEmployer> select_employers(String orderString, String where){
        try {
            ArrayList<InterfaceTable> interfaceTables;
            ArrayList<OneEmployer>  employees = new ArrayList<>();
            interfaceTables = connector.select("employers",  where, orderString);
            for (int i = 0; i < interfaceTables.size(); i++)
                employees.add((OneEmployer) interfaceTables.get(i));
            return employees;
        } catch (Exception e){
            isHaveErrorInWork = true;
            return null;
        }
    }


    public ArrayList<OneUser> select_users(String orderString, String where){
        try {
            ArrayList<InterfaceTable> interfaceTables;
            ArrayList<OneUser>  users = new ArrayList<>();
            interfaceTables = connector.select("users", where, orderString);

            for (int i = 0; i < interfaceTables.size(); i++)
                users.add((OneUser) interfaceTables.get(i));
            return users;
        } catch (Exception e){
            isHaveErrorInWork = true;
            return null;
        }
    }


    public boolean delete(String table, int id) {
        try {
            if (connector.delete(table,id)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            isHaveErrorInWork = true;
            return false;
        }
    }

    public boolean insert(InterfaceTable interfaceTable, String table) {
        try {
            String SQLRequestComponents[] = traningSQLRequestComponents(interfaceTable);
            if (connector.insert(table, SQLRequestComponents[0], SQLRequestComponents[1])) {
                return true;
            } else {
                return false;
            }
        } catch (Exception  e){
            isHaveErrorInWork = true;
            return false;
        }
    }


    private String[] traningSQLRequestComponents(InterfaceTable interfaceTable){

        if(interfaceTable instanceof OneSession){
            return traningsessionsSQLRequest((OneSession) interfaceTable);
        }
        if(interfaceTable instanceof OneAnte){
            return traningantesSQLRequest((OneAnte) interfaceTable);
        }
        if(interfaceTable instanceof OneEmployer){
            return traningemployersSQLRequest((OneEmployer) interfaceTable);
        }
        if(interfaceTable instanceof OneUser){
            return traningusersSQLRequest((OneUser) interfaceTable);
        }
        return null;
    }

    private String traningColumns(String columns[]){
        String str = "";
        for (int i = 1; i < columns.length; i++) {
            str += columns[i];
            if(i != columns.length - 1) str +=",";
        }
        return str;
    }

    private String[] traningsessionsSQLRequest(OneSession abstractMod){
        String str[] = new String[]{"", ""};
        str[0] = traningColumns(OneSession.ThisRowAtribute);
        str[1] = "'" + abstractMod.name + "'," + abstractMod.price + "," + abstractMod.employer_id;
        return str;
    }

    private String[] traningantesSQLRequest(OneAnte abstractMod){
        String str[] = new String[]{"", ""};
        str[0] = traningColumns(OneAnte.ThisRowAtribute);
        str[1] = abstractMod.value + "," + abstractMod.employer_id;
        return str;
    }

    private String[] traningemployersSQLRequest(OneEmployer abstractMod){
        String str[] = new String[]{"", ""};
        str[0] = traningColumns(OneEmployer.ThisRowAtribute);
        str[1] = "'" + abstractMod.name + "'," + abstractMod.point + ",'" + abstractMod.lastname + "'";
        return str;
    }

    private String[] traningusersSQLRequest(OneUser abstractMod){
        String str[] = new String[]{"", ""};
        str[0] = traningColumns(OneUser.ThisRowAtribute);
        str[1] = "'" + abstractMod.name + "','" + abstractMod.password + "'," + abstractMod.user_privileges;
        return str;
    }


    public boolean update(InterfaceTable interfaceTable, String table, int id) {
        try {
            String values = traningSQLRequestValue(interfaceTable);
            if (connector.update(table, id, values)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            isHaveErrorInWork = true;
            return false;
        }
    }

    private String traningSQLRequestValue(InterfaceTable interfaceTable){

        if(interfaceTable instanceof OneSession){
            return traningsessionsValue((OneSession) interfaceTable);
        }
        if(interfaceTable instanceof OneAnte){
            return traningantesValue((OneAnte) interfaceTable);
        }
        if(interfaceTable instanceof OneEmployer){
            return traningemployersValue((OneEmployer) interfaceTable);
        }
        if(interfaceTable instanceof OneUser){
            return traningusersValue((OneUser) interfaceTable);
        }
        return "";
    }


    private String traningsessionsValue(OneSession abstractMod){
        return "name = '" + abstractMod.name + "', price = " + abstractMod.price
                + ", employer_id = " + abstractMod.employer_id;
    }

    private String traningantesValue(OneAnte abstractMod){
        return "value = " + abstractMod.value + ", employer_id = " + abstractMod.employer_id;
    }

    private String traningemployersValue(OneEmployer abstractMod){
        return "name = '" + abstractMod.name + "',point = " + abstractMod.point
                + ",lastname = '" + abstractMod.lastname + "'";

    }

    private String traningusersValue(OneUser abstractMod){
        return "name = '" + abstractMod.name + "',password = '" + abstractMod.password +
                "',user_privileges = " + abstractMod.user_privileges;
    }

}
