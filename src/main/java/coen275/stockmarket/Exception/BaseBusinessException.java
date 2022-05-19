package coen275.stockmarket.Exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.logging.LogLevel;

@Getter
@Setter
public abstract class BaseBusinessException extends RuntimeException {

    protected LogLevel Level = LogLevel.WARN;

    public BaseBusinessException(String message) {
        super(message);
    }

    public BaseBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getHttpErrorCode() {
        return 400;
    }

    public abstract String getErrorCode();
}