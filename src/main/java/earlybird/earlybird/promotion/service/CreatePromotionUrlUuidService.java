package earlybird.earlybird.promotion.service;

import earlybird.earlybird.promotion.entity.PromotionUrlUuid;
import earlybird.earlybird.promotion.repository.PromotionUrlUuidRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CreatePromotionUrlUuidService {

    private final PromotionUrlUuidRepository urlUuidRepository;

    public PromotionUrlUuid create() {
        PromotionUrlUuid promotionUrlUuid =
                PromotionUrlUuid.builder().uuid(UUID.randomUUID()).build();
        return urlUuidRepository.save(promotionUrlUuid);
    }
}
