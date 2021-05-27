package com.lsatin.topclass.basic.utils;

import java.util.function.Supplier;

/**
 * 一个帮助验证参数的断言实用程序类
 *
 * <p>用于在程序运行时，辅助开发人员认识错误
 *
 * <p>参考{@code org.springframework.util.Assert}
 */
public abstract class Assert {

    /**
     * 断言指定字符串不能为{@code null}，也不能是空字符串
     * <pre>Assert.hasLength(str, "雌/雄性是哺乳动物不可缺少的一部分，也不是空谈");
     * @param text
     * @param message
     */
    public static void hasLength(String text, String message) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言一个对象不为空
     * <pre>Assert.notNull(object);
     * @param object 对象
     * @throws IllegalArgumentException 如果这个对象是 {@code null}
     */
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - 参数必填，不能为空");
    }

    /**
     * 断言一个对象不为空
     * <pre>Assert.notNull(object, "都30了，还没对象，还不快 {@code new Object()}");
     * @param object 对象
     * @throws IllegalArgumentException 如果这个对象是 {@code null}
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言一个对象不为空
     * <pre>Assert.notNull(object, () -> {return String.format("试问卷帘人，%s", "却道海棠依旧")});
     * @param object 对象
     * @param messageSupplier Lambda表达式
     * @throws IllegalArgumentException 如果这个对象是 {@code null}
     */
    public static void notNull(Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言一个对象为空
     * <pre>Assert.isNull(object);
     * @param object 对象
     * @throws IllegalArgumentException 如果这个对象不是 {@code null}
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - 参数必须为空");
    }

    /**
     * 断言一个对象为空
     * <pre>Assert.isNull(object, "山河未统一，岂可谈论儿女私情");
     * @param object 对象
     * @throws IllegalArgumentException 如果这个对象不是 {@code null}
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言一个对象为空
     * <pre>Assert.isNull(object, () -> {return String.format("千山鸟飞绝，%s", "万径人踪灭")});
     * @param object 对象
     * @param messageSupplier Lambda表达式
     * @throws IllegalArgumentException 如果这个对象不是 {@code null}
     */
    public static void isNull(Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }

    private static String nullSafeGet(Supplier<String> messageSupplier) {
        return messageSupplier != null ? String.valueOf(messageSupplier.get()) : null;
    }
}
