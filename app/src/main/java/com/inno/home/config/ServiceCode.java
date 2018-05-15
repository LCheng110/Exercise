package com.inno.home.config;

/**
 * Created by liucheng on 2018/1/19.
 * 接口返回码和端口号管理类
 */

public interface ServiceCode {

    /**
     * 成功 插入 、删除 更新 修改
     */
    int SUCCEED_CODE_VALUE = 200;
    /**
     * 用户已点赞
     */
    int PRAISE_REPEAT = 202;
    /**
     * 参数列表不完整
     */
    int FAIL_CODE_PARAM_INSUFFICIENT = 300;
    /**
     * 失败 插入 、删除 更新 修改
     */
    int FAIL_CODE_VALUE = 400;
    /**
     * 用户登录TOKEN过期
     */
    int TOKEN_FAILED = 401;
    /**
     * 无权限访问
     */
    int PERM_CODE_VALUE = 403;
    /**
     * 操作太频繁
     */
    int OFFTEN_CODE_VALUE = 460;
    /**
     * 其他异常
     */
    int OTHER_CODE_VALUE = 500;
    /**
     * 连接超时
     */
    int SESSION_CODE_VALUE = 600;
    /**
     * 未登陆
     */
    int NO_LOGIN_CODE_VALUE = 601;
    /**
     * 连接异常（除请求超时）
     */
    int CLIENT_EXCEPTION_CODE_VALUE = 998;

    /**
     * 请求超时
     */
    int TIMEOUT_CODE_VALUE = 999;

    /**
     * 验签失败
     */
    int SIGN_FAIL = 99;
}
