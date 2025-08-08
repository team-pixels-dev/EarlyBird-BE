package earlybird.earlybird.promotion.controller;

import earlybird.earlybird.promotion.controller.request.CreatePromotionCampaignRequest;
import earlybird.earlybird.promotion.controller.response.CreatePromotionCampaignResponse;
import earlybird.earlybird.promotion.service.CreatePromotionCampaignService;
import earlybird.earlybird.promotion.service.request.CreatePromotionCampaignServiceRequest;
import earlybird.earlybird.promotion.service.response.CreatePromotionCampaignServiceResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/promotion/campaign")
@RestController
public class PromotionCampaignController {

    private final CreatePromotionCampaignService createPromotionCampaignService;

    @PostMapping
    public ResponseEntity<?> createPromotionCampaign(
            @Valid @RequestBody CreatePromotionCampaignRequest request) {
        CreatePromotionCampaignServiceRequest serviceRequest =
                CreatePromotionCampaignServiceRequest.from(request);
        CreatePromotionCampaignServiceResponse serviceResponse =
                createPromotionCampaignService.create(serviceRequest);
        CreatePromotionCampaignResponse response =
                CreatePromotionCampaignResponse.from(serviceResponse);
        return ResponseEntity.ok().body(response);
    }
}
