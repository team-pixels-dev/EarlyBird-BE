package earlybird.earlybird.log.visit.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientIdRepository extends JpaRepository<ClientId, Long> {

    Optional<ClientId> findByClientId(String clientId);
}
