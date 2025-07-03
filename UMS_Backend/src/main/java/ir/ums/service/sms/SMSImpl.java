package ir.ums.service.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import ir.ums.dto.sms.RecipientDTO;
import ir.ums.dto.sms.SmsRequestDTO;
import ir.ums.dto.sms.SmsResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class SMSImpl implements ISMS {

    public static final String SMS_SERVICE_ENDPOINT = "https://api.sms-webservice.com/api/V3/SendBulk";

//    @Value("${sms.api-key}")
//    private String smsApiKey;

    @Override
    public SmsResponseDTO sendSMSToUser(String messageText, String userPhoneNumber, String userTraceId) {
        RestTemplate restTemplate = new RestTemplate();

        SmsRequestDTO requestData = new SmsRequestDTO();
        requestData.setApiKey("270618-b65f91cd68254d8d998b2ea466a2046d");
        requestData.setText(messageText);
        requestData.setSender("50004075001161");

        RecipientDTO recipientDTO = new RecipientDTO();
        recipientDTO.setDestination(userPhoneNumber);
        recipientDTO.setUserTraceId(userTraceId);

        requestData.setRecipients(List.of(recipientDTO));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SmsRequestDTO> entity = new HttpEntity<>(requestData, headers);

        String response = restTemplate.exchange(SMS_SERVICE_ENDPOINT, HttpMethod.POST, entity, String.class).getBody();

        log.info("\u001B[34mSMS Response: {}\u001B[0m", response);
        JsonMapper jsonMapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .build();

        try {
            return jsonMapper.readValue(response, SmsResponseDTO.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
