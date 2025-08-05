package earlybird.earlybird.promotion.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import earlybird.earlybird.promotion.entity.PromotionUrlUuid;
import earlybird.earlybird.promotion.repository.PromotionUrlUuidRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CreatePromotionUrlUuidServiceTest {

    @InjectMocks CreatePromotionUrlUuidService createPromotionUrlUuidService;

    @Mock PromotionUrlUuidRepository promotionUrlUuidRepository;

    @DisplayName("UUID를 생성하고 저장한다")
    @Test
    void create() {
        // given
        PromotionUrlUuid savedEntity = PromotionUrlUuid.builder().uuid(UUID.randomUUID()).build();

        when(promotionUrlUuidRepository.save(any(PromotionUrlUuid.class))).thenReturn(savedEntity);

        // when
        PromotionUrlUuid result = createPromotionUrlUuidService.create();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUuid()).isNotNull();
        verify(promotionUrlUuidRepository).save(any(PromotionUrlUuid.class));
    }
}
