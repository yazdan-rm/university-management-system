package ir.ums.dto.sms;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SmsRequestDTO {
    private String apiKey;
    private String text;
    private String sender;
    private List<RecipientDTO> recipients;
}
