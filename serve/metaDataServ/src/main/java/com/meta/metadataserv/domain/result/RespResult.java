package com.meta.metadataserv.domain.result;

import com.meta.metadataserv.enums.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(
        value = "返回结果",
        description = "结果对象"
)
public class RespResult<T> implements Serializable {
    @ApiModelProperty(
            value = "返回代码",
            position = 1,
            example = "200"
    )
    private String code;
    @ApiModelProperty(
            value = "提示信息",
            position = 2,
            example = "成功"
    )
    private String msg;
    @ApiModelProperty(
            value = "返回结果",
            position = 3
    )
    private T data;
    @ApiModelProperty(
            value = "异常信息",
            position = 4
    )
    private String exception;

    public RespResult(String code, String msg, T data, String exception) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.exception = exception;
    }

    public static <T> RespResult<T> ok() {
        return new RespResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), (Object)null, (String)null);
    }

    public static <T> RespResult<T> ok(T data) {
        return new RespResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data, (String)null);
    }

    public static <T> RespResult<T> error() {
        return new RespResult(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(), ResultCode.SYSTEM_EXECUTION_ERROR.getMsg(), (Object)null, (String)null);
    }

    public static <T> RespResult<T> error(String exception) {
        return new RespResult(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(), ResultCode.SYSTEM_EXECUTION_ERROR.getMsg(), (Object)null, exception);
    }

    public static <T> RespResult<T> error(String msg, String exception) {
        return new RespResult(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(), msg, (Object)null, exception);
    }

    public static <T> RespResult<T> error(String code, String msg, String exception) {
        return new RespResult(code, msg, (Object)null, exception);
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public String getException() {
        return this.exception;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RespResult)) {
            return false;
        } else {
            RespResult<?> other = (RespResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label59;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label59;
                    }

                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                Object this$exception = this.getException();
                Object other$exception = other.getException();
                if (this$exception == null) {
                    if (other$exception != null) {
                        return false;
                    }
                } else if (!this$exception.equals(other$exception)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof RespResult;
    }

    public int hashCode() {
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $exception = this.getException();
        result = result * 59 + ($exception == null ? 43 : $exception.hashCode());
        return result;
    }

    public String toString() {
        return "RespResult(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ", exception=" + this.getException() + ")";
    }
}
