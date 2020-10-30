package com.lsatin.topclass.web.base.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Enumeration;

/**
 * 基类Servlet
 */
public class BaseServlet extends HttpServlet {

    /**
     * logger
     */
    protected final Logger logger = LogManager.getLogger(getClass());

    /**
     * 视图前缀
     */
    protected static final String VIEW_PREFIX = "/views/";

    /**
     * 视图后缀
     */
    protected static final String VIEW_SUFFIX = ".jsp";

    /**
     * 返回JSON串
     */
    protected void writerJson(HttpServletResponse resp, Object data) throws Exception {
        if (logger.isDebugEnabled())
            logger.info(data.toString());
        resp.setContentType("text/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        writer.print(mapper.writeValueAsString(data));
        writer.close();
    }

    protected <T> T getRequestParameter(HttpServletRequest request, Class<?> clazz) throws Exception {
        T result = null;
        Enumeration<String> requestNames = request.getParameterNames();
        result = (T) clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        while(requestNames.hasMoreElements()) {
            String name = requestNames.nextElement(), value = request.getParameter(name);
            for (Field field : fields) {
                if (name.equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(result, value);
                    break;
                }
            }
        }
        return result;
    }

}
