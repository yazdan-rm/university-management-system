package ir.ums.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Slf4j
@Setter
@Getter
public class ApiResponse<T> {
    private T data;
    private String message;

    ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponseBuilder<T> builder() {
        return new ApiResponseBuilder<T>();
    }

    public static class ApiResponseBuilder<T> {
        private T data;
        private String message;

        ApiResponseBuilder() {
        }

        public ApiResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder<T> message(String message) {
            this.message = createMessageSource().getMessage(message, null, LocaleContextHolder.getLocale());
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<T>(this.data, this.message);
        }

        public String toString() {
            return "ApiResponse.ApiResponseBuilder(data=" + this.data + ", message=" + this.message + ")";
        }

        private MessageSource createMessageSource() {
            ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
            messageSource.setBasename("classpath:messages");
            messageSource.setDefaultEncoding("UTF-8");
            return messageSource;
        }
    }
}
