package earlybird.earlybird.email.address.save.repository;

import earlybird.earlybird.email.address.save.entity.MarketingEmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketingEmailAddressRepository extends JpaRepository<MarketingEmailAddress,Long> {
}
