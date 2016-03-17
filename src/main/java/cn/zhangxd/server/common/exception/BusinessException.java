package cn.zhangxd.server.common.exception;

/**
 * 业务异常.
 * Created by zhangxd on 16/3/14.
 */
public class BusinessException extends Exception implements IBaseException {

    private static final long serialVersionUID = 1L;

    private String code;

    public BusinessException() {
    }

    public BusinessException(String code, String message) {
        this(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }
}