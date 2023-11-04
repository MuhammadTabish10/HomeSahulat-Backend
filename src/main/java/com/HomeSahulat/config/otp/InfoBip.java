package com.HomeSahulat.config.otp;

import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.ApiKey;
import com.infobip.BaseUrl;
import com.infobip.api.SmsApi;
import com.infobip.model.SmsAdvancedTextualRequest;
import com.infobip.model.SmsDestination;
import com.infobip.model.SmsTextualMessage;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class InfoBip {
    @Value("${infobip.apiKey}")
    private String apiKey;

    @Value("${infobip.baseUrl}")
    private String baseUrl;

    public boolean sendSMS(String from, String to, String messageText) {
        var client = ApiClient.forApiKey(ApiKey.from(apiKey))
                .withBaseUrl(BaseUrl.from(baseUrl))
                .build();

        var api = new SmsApi(client);

        var message = new SmsTextualMessage()
                .from(from)
                .destinations(Collections.singletonList(new SmsDestination().to(to)))
                .text(messageText);

        var request = new SmsAdvancedTextualRequest()
                .messages(Collections.singletonList(message));

        try {
            var response = api.sendSmsMessage(request).execute();
            System.out.println(response);
            return true;

        } catch (ApiException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
