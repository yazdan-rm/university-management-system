package ir.ums.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {
    public static final String INTERNAL_SERVER_EXCEPTION = "internal.server.exception";
    public static final String INVALID_REQUEST_BODY_EXCEPTION = "invalid.request.body.exception";
    public static final String NOT_FOUND_EXCEPTION = "not.found.exception";
    public static final String SERVICE_NOT_FOUND_EXCEPTION = "service.not.found.exception";
    public static final String ACCESS_DENIED_EXCEPTION = "access.denied.exception";
    public static final String AUTHENTICATION_EXCEPTION = "authentication.exception";
    public static final String DATA_INTEGRITY_EXCEPTION = "data.integrity.exception";
}
