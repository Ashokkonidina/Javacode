package org.joychou.security;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;


/**
 * Check referer for all GET requests with callback parameters.
 * If the check of referer fails, a 403 forbidden error page will be returned.
 *
 * Still need to add @ServletComponentScan annotation in Application.java.
 *
 */
@WebFilter(filterName = "referSecCheck", urlPatterns = "/*")
public class secFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String refer = request.getHeader("referer");
        String referWhitelist[] = {"joychou.org", "joychou.com"};

        // Check referer for all GET requests with callback parameters.
        if (request.getMethod().equals("GET") && StringUtils.isNotBlank(request.getParameter("callback")) ){
             // If the check of referer fails, a 403 forbidden error page will be returned.
            if (!SecurityUtil.checkURLbyEndsWith(refer, referWhitelist)){
                response.sendRedirect("https://test.joychou.org/error3.html");
                return;
            }
        }

        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
