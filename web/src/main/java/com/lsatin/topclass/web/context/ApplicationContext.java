package com.lsatin.topclass.web.context;

/**
 * 应用程序上下文
 * <p>参考{@code org.springframework.context.ApplicationContext}
 */
public interface ApplicationContext {

    /**
     * 返回此应用程序上下文唯一id
     * @return {@link String}
     */
    String getId();

    /**
     * 返回此应用名称
     * @return {@link String}
     */
    String getName();

}
