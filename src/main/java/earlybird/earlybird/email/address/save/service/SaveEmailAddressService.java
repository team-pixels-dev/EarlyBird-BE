package earlybird.earlybird.email.address.save.service;

import earlybird.earlybird.email.address.check.CheckEmailAddressService;
import earlybird.earlybird.email.address.save.entity.MarketingEmailAddress;
import earlybird.earlybird.email.address.save.repository.MarketingEmailAddressRepository;
import earlybird.earlybird.email.address.save.service.request.SaveEmailAddressServiceRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SaveEmailAddressService {

    private final CheckEmailAddressService checkEmailAddressService;
    private final MarketingEmailAddressRepository marketingEmailAddressRepository;

    @Transactional
    public void save(SaveEmailAddressServiceRequest request) {

        Optional.ofNullable(request.email())
                .filter(checkEmailAddressService::checkEmailRegex)
                .orElseThrow(
                        () ->
                                new IllegalArgumentException(
                                        "Invalid email address: " + request.email()));

        MarketingEmailAddress emailAddress =
                MarketingEmailAddress.builder()
                        .email(request.email())
                        .sourceEvent(request.sourceEvent())
                        .build();

        marketingEmailAddressRepository.save(emailAddress);
    }
}
