package dk.thebeerclub.brewhub.repository;

import dk.thebeerclub.brewhub.model.BrewStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrewStepRepository extends JpaRepository<BrewStep, Long> {
}
