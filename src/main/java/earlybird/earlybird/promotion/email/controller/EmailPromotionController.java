package earlybird.earlybird.promotion.email.controller;

import earlybird.earlybird.promotion.apple.service.GetApplePromotionUrlService;
import earlybird.earlybird.promotion.apple.service.response.GetApplePromotionUrlServiceResponse;
import earlybird.earlybird.promotion.email.controller.request.AddEmailPromotionAddressDomainRequest;
import earlybird.earlybird.promotion.email.controller.request.UnivEmailPromotionVerificationRequest;
import earlybird.earlybird.promotion.email.service.AddEmailPromotionAddressDomainService;
import earlybird.earlybird.promotion.email.service.SendPromotionEmailService;
import earlybird.earlybird.promotion.email.service.request.AddEmailPromotionAddressDomainServiceRequest;
import earlybird.earlybird.promotion.email.service.request.SendVerificationEmailServiceRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class EmailPromotionController {

    private final SendPromotionEmailService sendPromotionEmailService;
    private final AddEmailPromotionAddressDomainService addEmailPromotionAddressDomainService;
    private final GetApplePromotionUrlService getApplePromotionUrlService;

    @PostMapping("/api/v1/promotion/apple/univ/email")
    public ResponseEntity<?> univPromotionVerifyEmail(@RequestBody UnivEmailPromotionVerificationRequest request) {

        SendVerificationEmailServiceRequest serviceRequest = SendVerificationEmailServiceRequest.from(request);
        sendPromotionEmailService.sendVerificationEmail(serviceRequest);

        return null;
    }

    @PostMapping("/api/v1/promotion/email/domain")
    public ResponseEntity<?> addEmailPromotionAddressDomain(
            @RequestBody AddEmailPromotionAddressDomainRequest request) {

        AddEmailPromotionAddressDomainServiceRequest serviceRequest =
                AddEmailPromotionAddressDomainServiceRequest.from(request);

        addEmailPromotionAddressDomainService.add(serviceRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/promotion/apple/univ/email")
    public void redirectToPromotionPage(
            @RequestParam(name = "code") String promotionCodeUuid,
            HttpServletResponse servletResponse) throws IOException {

        GetApplePromotionUrlServiceResponse serviceResponse =
                getApplePromotionUrlService.getPromotionUrlByUuid(promotionCodeUuid);

        String promotionUrl = serviceResponse.getPromotionUrl();
        servletResponse.sendRedirect(promotionUrl);
    }
}
