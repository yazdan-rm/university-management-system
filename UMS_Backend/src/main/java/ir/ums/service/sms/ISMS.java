package ir.ums.service.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.ums.dto.sms.SmsResponseDTO;

public interface ISMS {

    SmsResponseDTO sendSMSToUser(String messageText, String userPhoneNumber, String userTraceId);
}
