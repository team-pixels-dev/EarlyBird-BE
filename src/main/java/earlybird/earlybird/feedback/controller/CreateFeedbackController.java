package earlybird.earlybird.feedback.controller;

import earlybird.earlybird.feedback.service.CreateAnonymousUserFeedbackService;
import earlybird.earlybird.feedback.service.CreateAuthUserFeedbackService;
import earlybird.earlybird.feedback.dto.FeedbackDTO;
import earlybird.earlybird.feedback.dto.FeedbackRequestDTO;
import earlybird.earlybird.security.authentication.oauth2.user.OAuth2UserDetails;
import earlybird.earlybird.user.dto.UserAccountInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@RequestMapping("/api/v1/feedbacks")
@RestController
public class CreateFeedbackController {

    private final CreateAuthUserFeedbackService createAuthUserFeedbackService;
    private final CreateAnonymousUserFeedbackService createAnonymousUserFeedbackService;

    @PostMapping
    public ResponseEntity<?> createFeedback(
            @AuthenticationPrincipal OAuth2UserDetails oAuth2UserDetails,
            @RequestBody FeedbackRequestDTO requestDTO) {

        if (oAuth2UserDetails != null) {
            createAuthUserFeedback(oAuth2UserDetails, requestDTO);
        }
        else {
            createAnonymousUserFeedback(requestDTO);
        }

        return ResponseEntity.ok().build();
    }

    private void createAuthUserFeedback(OAuth2UserDetails oAuth2UserDetails, FeedbackRequestDTO requestDTO) {
        UserAccountInfoDTO userAccountInfoDTO = oAuth2UserDetails.getUserAccountInfoDTO();
        String content = requestDTO.getContent();
        LocalDateTime createdAt = getCreatedTime(requestDTO);

        FeedbackDTO feedbackDTO = FeedbackDTO.builder()
                .content(content)
                .userAccountInfoDTO(userAccountInfoDTO)
                .createdAt(createdAt)
                .build();

        createAuthUserFeedbackService.create(feedbackDTO);
    }

    private void createAnonymousUserFeedback(FeedbackRequestDTO requestDTO) {
        String content = requestDTO.getContent();
        LocalDateTime createdAt = getCreatedTime(requestDTO);

        FeedbackDTO feedbackDTO = FeedbackDTO.builder()
                .content(content)
                .createdAt(createdAt)
                .userAccountInfoDTO(null)
                .build();

        createAnonymousUserFeedbackService.create(feedbackDTO);
    }

    private LocalDateTime getCreatedTime(FeedbackRequestDTO requestDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return LocalDateTime.parse(requestDTO.getCreatedAt(), formatter);
    }
}
