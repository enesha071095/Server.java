package ObjStringConvertion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Tables.InterfaceTable;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ObjectStringConvertor {
    public static String serialiseObject(InterfaceTable interfaceTable) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(interfaceTable);
    }

    public static InterfaceTable deserialiseString(String stringMessage, Class<? extends InterfaceTable> thisInterface) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(stringMessage, thisInterface);
    }

    public static String serialiseObjects(ArrayList<? extends InterfaceTable> abstractTables) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.toJson(abstractTables);
    }


    public static ArrayList<InterfaceTable> deserialiseStrings(String stringMessage, Type type) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(stringMessage, type);
    }
}
