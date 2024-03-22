package com.springcloud.demo.server.core.result;

/**
 * 通用返回状态码枚举
 */
public enum CommonResultStatus {
    /**
     * 成功
     */
    SUCCESS("200", "OK"),

    /**
     * 参数错误
     */
    PARAMS_ERROR("400", "参数错误"),

    /**
     * 没有登录
     */
    NOT_LOGIN("401", "没有登录"),

    FORBIDDEN("403", "没有相关权限"),

    NOT_FOUND("404", "请求的地址不存在"),

    METHOD_NOT_ALLOWED("405", "请求方法不正确"),

    TOKEN_ERROR("431", "访问令牌不存在"),

    REMOTE_DATA_ERROR("600", "远程数据错误"),

    UPLOAD_FILE_ERROR("620", "上传文件失败"),

    UPLOAD_FILE_FORMAT_ERROR("621", "上传文件格式错误"),

    MYSQL_BACKUP_ERROR("630", "备份数据库失败"),
    MYSQL_RESTORE_ERROR("631", "恢复数据库失败"),

    /**
     * 未知的错误
     */
    SYS_EXCEPTION("999", "系统发生异常");


    CommonResultStatus(String value, String message) {
        this.value = value;
        this.message = message;
    }

    /**
     * 获取状态枚举对应值
     */
    public String value() {
        return value;
    }

    public Integer intValue() {
        return Integer.valueOf(value);
    }

    /**
     * 获取状态枚举对应信息
     */
    public String message() {
        return message;
    }

    private String value;
    private String message;
}
