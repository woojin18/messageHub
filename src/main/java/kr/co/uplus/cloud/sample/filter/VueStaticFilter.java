package kr.co.uplus.cloud.sample.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

// http://www.servletsuite.com/servlets/xssflt.htm
public class VueStaticFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (shouldExclude(request)) {
			chain.doFilter(request, response);
			// some logic so the request doesnt go to the servlet

			// maybe you could just forward
			// the request directly to the file getting accessed. not sure if that would
			// work
		} else {
			((HttpServletRequest) request).getRequestDispatcher("/").forward(request, response);
		}

		// file should be passed to the servlet; you can do some logic here
		// if you want
	}

	private boolean shouldExclude(ServletRequest req) {
		if (req instanceof HttpServletRequest) {
			HttpServletRequest hreq = (HttpServletRequest) req;
			return !(hreq.getRequestURI().startsWith("/view"));
		}
		return true;
	}

}