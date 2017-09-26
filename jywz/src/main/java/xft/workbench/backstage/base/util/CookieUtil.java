package xft.workbench.backstage.base.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    private static String default_path = "/";
    private static int default_age = 30 * 24 * 3600;

    public static void addCookie(String name, String value,
                                 HttpServletResponse response, int age) {
        Cookie cookie1 = new Cookie(name, value);
        cookie1.setMaxAge(age);
       // cookie1.setSecure(true);
        //cookie1.setHttpOnly(true);
       // cookie1.setHttpOnly(true);
        cookie1.setPath(default_path);
        response.addCookie(cookie1);
        
        
        /*Cookie cookie2 = new Cookie(name, value+"222");
        cookie2.setMaxAge(age);
        cookie2.setPath(default_path);
        cookie2.setDomain("xxx.abs.com");
        response.addCookie(cookie2);*/
    }

    public static void addCookie(String name, String value,
                                 HttpServletResponse response) {
        addCookie(name, value, response, default_age);
    }

    public static String getCookie(String name,
                                   HttpServletRequest request) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }


    public static void deleteCookie(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setPath(default_path);
        response.addCookie(cookie);
    }
}
