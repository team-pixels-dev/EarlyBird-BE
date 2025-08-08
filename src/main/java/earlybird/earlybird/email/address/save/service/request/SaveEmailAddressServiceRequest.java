package earlybird.earlybird.email.address.save.service.request;

import earlybird.earlybird.email.address.save.controller.request.SaveEmailAddressRequest;
import earlybird.earlybird.email.address.save.entity.MarketingEvent;

import lombok.*;

@Builder
public record SaveEmailAddressServiceRequest(String email, MarketingEvent sourceEvent) {

    public static SaveEmailAddressServiceRequest from(SaveEmailAddressRequest request) {
        return SaveEmailAddressServiceRequest.builder()
                .email(request.getEmail())
                .sourceEvent(request.getSourceEvent())
                .build();
    }
}
