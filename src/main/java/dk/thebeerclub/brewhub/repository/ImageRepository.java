package dk.thebeerclub.brewhub.repository;

import dk.thebeerclub.brewhub.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
