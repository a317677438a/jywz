package xft.workbench.backstage.base.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class UserLogsFilter implements Filter {
    protected static Logger log = Logger.getLogger(UserLogsFilter.class);
    private static ThreadLocal<String> requestParamsThreadLocal = new ThreadLocal<String>();
    private static List<String> requestmappingList = null;

    public static String getRequestParams() {
        try {
            return UserLogsFilter.requestParamsThreadLocal.get();
        } catch (Exception e) {
            return null;
        }

    }

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
       chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }


    /**
     * 获取客户端IP地址
     *
     * @param request
     * @return
     */
    private static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress != null && ipAddress.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                if (ipAddress != null) {
                    ipAddress = inet.getHostAddress();
                }
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
            // = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


    /**
     * 得到请求参数
     */
    @SuppressWarnings("rawtypes")
    protected String getRequestParams(HttpServletRequest localRequest) throws Exception {

        Map<String, Object> map = null;
        String contentType = localRequest.getContentType();
        String requestURI = localRequest.getRequestURI();
        boolean isAPI = requestURI.contains("/api/");

        if (contentType != null && contentType.contains("application/json")&&!isAPI) {

            BufferedReader reader = localRequest.getReader();
            StringBuffer buffer = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            return buffer.toString();

        }

        return "";

    }


}
