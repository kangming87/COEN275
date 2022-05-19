package coen275.stockmarket.Exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.logging.LogLevel;

@Getter
@Setter
public class CommonException extends BaseBusinessException {

    protected LogLevel Level = LogLevel.ERROR;

    private Integer code = 500;

    private String error_code = "server_error";

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(String message, String error_code, Integer code) {
        super(message);
        this.code = code;
        this.error_code = error_code;
    }

    public String getErrorCode() {
        return this.error_code;
    }

    public int getHttpErrorCode() {
        return this.code;
    }

}
