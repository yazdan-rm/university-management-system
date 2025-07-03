package ir.ums.events;

import ir.ums.service.sms.ISMS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationEvents {

    private final ISMS smsService;

    @EventListener
    public void onSuccessAuthentication(AuthenticationSuccessEvent successEvent) {
        String username = successEvent.getAuthentication().getName();
        if (successEvent.getAuthentication().getDetails() instanceof WebAuthenticationDetails webDetails) {
            //TODO : fetch user details with username and set full persian name for making sms and phone number of user and set UUID as UserTraceId
//            smsService.sendSMSToUser("%s %s".formatted(username, webDetails.getRemoteAddress()), "09033300462", "123");
            log.info("Authentication success {}", successEvent.getAuthentication().getName());
        }
    }

    @EventListener
    public void onFailureAuthentication(AbstractAuthenticationFailureEvent failureEvent) {
        String username = failureEvent.getAuthentication().getName();
        if (failureEvent.getAuthentication().getDetails() instanceof WebAuthenticationDetails webDetails) {
            //TODO : fetch user details with username and set full persian name for making sms and phone number of user and set UUID as UserTraceId
//            smsService.sendSMSToUser("%s %s".formatted(username, webDetails.getRemoteAddress()), "09033300462", "123");
            log.info("Authentication failure {}", failureEvent.getAuthentication().getName());
        }
    }
}
