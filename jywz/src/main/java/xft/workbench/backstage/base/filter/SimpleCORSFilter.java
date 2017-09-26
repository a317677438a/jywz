package xft.workbench.backstage.base.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter {
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:63342");//请求源  http://test.domain.cn  http://localhost:63342
		response.setHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS, DELETE");//请求方式POST, GET, OPTIONS
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"x-requested-with,content-type");//请求头类型
		response.setHeader("Access-Control-Allow-Credentials", "true");//是否支持cookie跨域
		
		
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}
}
