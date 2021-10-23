package utils;

import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;

public class Utility {

    public static String generatePayload(Map<String,String> map) {
        JSONObject requestParams = new JSONObject();
        for (String s : map.keySet()) {
            requestParams.put(s, map.get(s));
        }
        return requestParams.toString();
    }
    public  static  String generateUsername(String suffix)
    {
        return "username-"+suffix;
    }
    public static String generateUserId()
    {
        return UUID.randomUUID().toString();
    }
    public static String generateEmail(String prefix)
    {
        return prefix+"@gmail.com";
    }
}
