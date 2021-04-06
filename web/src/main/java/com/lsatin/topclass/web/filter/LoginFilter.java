package com.lsatin.topclass.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(LoginFilter.class);

    protected static List<Pattern> patterns = new ArrayList<Pattern>(10);

    static {
        patterns.add(Pattern.compile("/"));
        patterns.add(Pattern.compile("/index"));
        patterns.add(Pattern.compile("/login"));
        patterns.add(Pattern.compile("/signIn"));
        patterns.add(Pattern.compile("/register"));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("--------------------------------------login filter--------------------------------------------");
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();

        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if (isInclude(url)) {
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        }
        if (session.getAttribute("username") != null) {
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        } else {
            httpRequest.getRequestDispatcher("/login").forward(httpRequest, httpResponse);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isInclude(String url) {
        if (url.endsWith("png") || url.endsWith("jsp") || url.endsWith("css") || url.endsWith("js") || url.endsWith("woff2"))
            return true;
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
}
