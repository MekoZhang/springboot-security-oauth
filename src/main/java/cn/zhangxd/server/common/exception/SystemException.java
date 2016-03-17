package cn.zhangxd.server.common.exception;


/**
 * 系统异常，从由Spring管理事务的函数中抛出时会触发事务回滚.
 * Created by zhangxd on 16/3/14.
 */
public class SystemException extends RuntimeException implements IBaseException {

    private static final long serialVersionUID = 1L;

    private String code;

    public SystemException() {
    }

    public SystemException(String code, String message) {
        this(message);
        this.code = code;
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }
}