package earlybird.earlybird.promotion.email.entity;

import lombok.Getter;

public enum PromotionEmailMessageType {
    BERKELEY_6_MONTH_FREE(
            "EarlyBird 6-month free trial for Berkeley students",
            """
            Hi, this is Young from the EarlyBird team.<br><br>
            We’re offering Berkeley students a 6-month free trial of EarlyBird:<br>
            <a href="%s">%s</a><br>
            It’s a self-care app that helps you stop procrastinating and do better in school.<br><br>
            Would love to hear your thoughts anytime.<br><br>
            Best,<br>
            Young<br>
            Founder, EarlyBird
            """),
    BERKELEY_30_DAY_FREE(
            "EarlyBird 30-day free trial for Berkeley students",
            """
            Hi, this is Young from the EarlyBird team.<br><br>
            We’re offering Berkeley students a 30-day free trial of EarlyBird:<br>
            <a href="%s">%s</a><br>
            It’s a self-care app that helps you stop procrastinating and do better in school.<br><br>
            Would love to hear your thoughts anytime.<br><br>
            Best,<br>
            Young<br>
            Founder, EarlyBird
            """);

    @Getter private final String title;

    @Getter private final String messageText;

    PromotionEmailMessageType(String title, String messageText) {
        this.title = title;
        this.messageText = messageText;
    }
}
