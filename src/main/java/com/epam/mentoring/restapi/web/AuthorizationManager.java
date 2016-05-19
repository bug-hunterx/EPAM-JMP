package com.epam.mentoring.restapi.web;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pengfrancis on 16/5/18.
 */
public class AuthorizationManager {
    /**
     * map of token -> UserToken
     */
    public static Map<String, UserToken> tokenMap= new ConcurrentHashMap<> ();
    /**
     * map of Role -> UserToken
     */
    public static Map<String, AccessControl> accessControlMap= new ConcurrentHashMap<> ();

    public static boolean authorize(String uri, String method, String token){
        if(token== null)
             return false;
        //todo check ACL
        //1.return true if uri is public

        //2. check if token is valid
        UserToken userToken= tokenMap.get(token);
        //todo

        //3. check role has AccessControl


        //4. further check, e.g. realm

            /*String[] uriParts = uri.split("[#?]");
            String path = uriParts[0];
            String rest = uri.substring(uriParts[0].length()); */

        return true;
    }
}

class UserToken{
    public long id;
    public String name;
    public String role;
    public long inChargeDepartmentId;
    public LocalDateTime expireTime;
}


class AccessControl{
    public String role;
    public String resource;
    public String operation;
    public boolean limitedRealm;
}