package ir.ums.dto.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Setter
@Getter
public class SmsIdentifierDTO {
    private Long Id;
    private String UserTraceId;
}