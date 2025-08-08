package earlybird.earlybird.email.address.save.controller;

import earlybird.earlybird.email.address.save.controller.request.SaveEmailAddressRequest;
import earlybird.earlybird.email.address.save.service.SaveEmailAddressService;
import earlybird.earlybird.email.address.save.service.request.SaveEmailAddressServiceRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/marketing/email/address")
@RestController
public class SaveEmailAddressController {

    private final SaveEmailAddressService saveEmailAddressService;

    @PostMapping
    public ResponseEntity<?> saveEmailAddress(@Valid @RequestBody SaveEmailAddressRequest request) {
        SaveEmailAddressServiceRequest serviceRequest = SaveEmailAddressServiceRequest.from(request);
        saveEmailAddressService.save(serviceRequest);
        return ResponseEntity.ok().build();
    }
}
