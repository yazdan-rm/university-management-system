package ir.ums.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomException extends RuntimeException {

    private final String parameterValue;

    public CustomException(String message) {
        this(message, null);
    }

    public CustomException(String message, String parameterValue) {
        super(message);
        this.parameterValue = parameterValue;
    }
}
