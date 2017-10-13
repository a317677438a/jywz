package xft.workbench.backstage.base.util;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FileUtil {

    public static String getBrowser(HttpServletRequest request) {
        String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (UserAgent.contains("msie") && !UserAgent.contains("chrome") || UserAgent.indexOf("like gecko") > 0 && !UserAgent.contains("chrome"))
            return "IE";
        if (UserAgent.contains("firefox"))
            return "FF";
        if (UserAgent.contains("safari"))
            return "SF";
        return null;
    }


    public static String encodeFileName(HttpServletRequest request, String fileName) {

        String userAgent = request.getHeader("User-Agent");
        String rtn = "";

        try {
            String new_filename = URLEncoder.encode(fileName, "UTF8");
            new_filename = new_filename.replaceAll("\\+", "%20");

            // 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
            rtn = "filename=\"" + new_filename + "\"";
            if (userAgent != null) {
                userAgent = userAgent.toLowerCase();

                // IE/Edge浏览器，只能采用URLEncoder编码
                if (userAgent.contains("msie") || userAgent.contains("edge")) {
                    rtn = "filename=\"" + new_filename + "\"";
                }

                // Opera浏览器只能采用filename*
                else if (userAgent.contains("opera")) {
                    rtn = "filename*=UTF-8''" + new_filename;
                }

                // Safari浏览器，只能采用ISO编码的中文输出
                else if (userAgent.contains("safari")) {
                    rtn = "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"";
                }

                // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
                else if (userAgent.contains("applewebkit")) {
                    new_filename = MimeUtility.encodeText(fileName, "UTF8", "B");
                    rtn = "filename=\"" + new_filename + "\"";
                }

                // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
                else if (userAgent.contains("mozilla")) {
                    rtn = "filename*=UTF-8''" + new_filename;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return rtn;
    }


}
