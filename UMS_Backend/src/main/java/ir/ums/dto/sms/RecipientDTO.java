package ir.ums.dto.sms;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RecipientDTO {
    private String destination;
    private String userTraceId;
}
