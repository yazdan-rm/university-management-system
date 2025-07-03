package ir.ums.dto.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@ToString
@Setter
@Getter
public class SmsResponseDTO {
    @JsonProperty("Success")
    private Boolean Success;
    private Integer ErrorCode;
    private String Error;
    private List<SmsIdentifierDTO> Result;
}
//{"Success":true,"ErrorCode":null,"Error":null,"Result":[{"Id":43361284000,"UserTraceId":123}]}
