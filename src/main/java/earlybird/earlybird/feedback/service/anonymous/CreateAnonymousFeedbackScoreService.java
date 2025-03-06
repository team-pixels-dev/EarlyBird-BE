package earlybird.earlybird.feedback.service.anonymous;

import earlybird.earlybird.common.util.LogUtil;
import earlybird.earlybird.feedback.domain.score.FeedbackScore;
import earlybird.earlybird.feedback.domain.score.FeedbackScoreDayInfo;
import earlybird.earlybird.feedback.domain.score.FeedbackScoreDayInfoRepository;
import earlybird.earlybird.feedback.domain.score.FeedbackScoreRepository;
import earlybird.earlybird.feedback.service.anonymous.request.CreateAnonymousFeedbackScoreServiceRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CreateAnonymousFeedbackScoreService {

    private final FeedbackScoreRepository feedbackScoreRepository;
    private final FeedbackScoreDayInfoRepository feedbackScoreDayInfoRepository;

    @Transactional
    public void create(CreateAnonymousFeedbackScoreServiceRequest request) {
        FeedbackScore feedbackScore =
                FeedbackScore.builder()
                        .score(request.getScore())
                        .clientId(request.getClientId())
                        .createdTimeAtClient(request.getCreatedAt())
                        .build();

        feedbackScoreRepository.save(feedbackScore);

        FeedbackScoreDayInfo dayInfo =
                FeedbackScoreDayInfo.builder()
                        .day(request.getDayCount())
                        .feedbackScore(feedbackScore)
                        .build();

        LogUtil.log(LogLevel.INFO, Map.of(), "feedbackScoreDayInfoRepository.save(dayInfo) 실행 직전");
        feedbackScoreDayInfoRepository.save(dayInfo);
        LogUtil.log(LogLevel.INFO, Map.of(), "feedbackScoreDayInfoRepository.save(dayInfo) 실행 직후");
    }
}
