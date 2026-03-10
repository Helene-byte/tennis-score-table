package org.example.util;

public class JspUtil {
    public static String getPath(String jspName) {
        return "/WEB-INF/jsp/" + jspName + ".jsp";
    }
}
