package earlybird.earlybird.email.address.save.controller.request;

import earlybird.earlybird.email.address.save.entity.MarketingEvent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SaveEmailAddressRequest {

    @NotBlank private String email;

    @NotNull private MarketingEvent sourceEvent;
}
