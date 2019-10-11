package uk.co.nfl.repository;
import uk.co.nfl.domain.HealthObservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the HealthObservation entity.
 */
@Repository
public interface HealthObservationRepository extends MongoRepository<HealthObservation, String> {

    @Query("{}")
    Page<HealthObservation> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<HealthObservation> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<HealthObservation> findOneWithEagerRelationships(String id);

}
