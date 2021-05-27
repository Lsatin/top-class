package com.lsatin.topclass.web.basic.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lsatin.topclass.basic.service.BasicService;
import com.lsatin.topclass.basic.utils.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * 基类Servlet
 */
public abstract class BasicServlet<T, S extends BasicService<T, Long>> extends HttpServlet {

    /** LOG4J */
    protected final Logger LOGGER = LogManager.getLogger(getClass());

    /** Object Mapper */
    protected final ObjectMapper OBJECT_MAPPER;

    protected BasicServlet() {
        this.OBJECT_MAPPER = new ObjectMapper();
    }

    protected abstract void setService(S service);

    protected abstract S getService();

    /**
     * 写入Json
     * @param resp http响应
     * @param data 响应数据
     * @throws Exception 异常
     */
    protected void writerJson(HttpServletResponse resp, Object data) throws Exception {
        Assert.notNull(data, "写入JSON数据不能为空");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(data.toString());
        }
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print(OBJECT_MAPPER.writeValueAsString(data));
        writer.close();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        super.service(req, resp);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        super.service(req, res);
    }

    protected <T> T mappingGetParameter(HttpServletRequest request, final Class<T> clazz) throws Exception {
        ObjectNode objectNode = OBJECT_MAPPER.createObjectNode();
        Enumeration<String> requestNames = request.getParameterNames();
        while(requestNames.hasMoreElements()) {
            String name = requestNames.nextElement(), value = request.getParameter(name);
            objectNode.put(name, value);
        }
        return OBJECT_MAPPER.readValue(objectNode.toString(), clazz);
    }

    protected <T> T mappingPostParameter(HttpServletRequest req, final Class<T> clazz) throws IOException {
        Assert.notNull(clazz, "映射请求参数，映射类型不能为空");
        BufferedReader reader = null;
        T result = null;
        try {
            reader = req.getReader();
            result = OBJECT_MAPPER.readValue(reader, clazz);
        } catch (IOException e) {
            LOGGER.error("映射POST请求参数错误: ", e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }

}
