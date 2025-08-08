package earlybird.earlybird.email.address.save.entity;

import lombok.Getter;

public enum MarketingEvent {
    WEB_MINI_GAME_1("홍보용 웹 미니 게임 - 1");

    @Getter private final String description;

    MarketingEvent(String description) {
        this.description = description;
    }
}
