package earlybird.earlybird.email.address.save.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import earlybird.earlybird.email.address.check.CheckEmailAddressService;
import earlybird.earlybird.email.address.save.entity.MarketingEmailAddress;
import earlybird.earlybird.email.address.save.entity.MarketingEvent;
import earlybird.earlybird.email.address.save.repository.MarketingEmailAddressRepository;
import earlybird.earlybird.email.address.save.service.request.SaveEmailAddressServiceRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SaveEmailAddressServiceTest {

    @InjectMocks private SaveEmailAddressService saveEmailAddressService;

    @Mock private CheckEmailAddressService checkEmailAddressService;

    @Mock private MarketingEmailAddressRepository marketingEmailAddressRepository;

    @DisplayName("이메일 주소와 수집 출처를 저장한다.")
    @Test
    void save() {
        // given
        SaveEmailAddressServiceRequest request =
                SaveEmailAddressServiceRequest.builder()
                        .email("test@test.com")
                        .sourceEvent(MarketingEvent.WEB_MINI_GAME_1)
                        .build();

        when(checkEmailAddressService.checkEmailRegex(request.email())).thenReturn(true);

        // when
        saveEmailAddressService.save(request);

        // then
        verify(checkEmailAddressService).checkEmailRegex(request.email());
        verify(marketingEmailAddressRepository).save(any(MarketingEmailAddress.class));
    }

    @DisplayName("올바르지 않은 이메일 주소 저장을 요청하면 예외가 발생한다.")
    @Test
    void throwExceptionWhenInvalidEmail() {
        // given
        SaveEmailAddressServiceRequest request =
                SaveEmailAddressServiceRequest.builder()
                        .email("invalidAddress")
                        .sourceEvent(MarketingEvent.WEB_MINI_GAME_1)
                        .build();

        when(checkEmailAddressService.checkEmailRegex(request.email())).thenReturn(false);

        // when // then
        assertThatThrownBy(() -> saveEmailAddressService.save(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이메일 주소가 NULL로 주어지면 예외가 발생한다.")
    @Test
    void throwExceptionWhenNullEmail() {
        // given
        SaveEmailAddressServiceRequest request =
                SaveEmailAddressServiceRequest.builder()
                        .email(null)
                        .sourceEvent(MarketingEvent.WEB_MINI_GAME_1)
                        .build();

        // when // then
        assertThatThrownBy(() -> saveEmailAddressService.save(request))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
