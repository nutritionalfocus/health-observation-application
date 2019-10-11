package uk.co.nfl.repository;
import uk.co.nfl.domain.HealthIndicatorObservation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the HealthIndicatorObservation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HealthIndicatorObservationRepository extends MongoRepository<HealthIndicatorObservation, String> {

}
