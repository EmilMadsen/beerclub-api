package dk.thebeerclub.brewhub.service;

import dk.thebeerclub.brewhub.model.Brew;
import dk.thebeerclub.brewhub.repository.BrewRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrewService {

    private final BrewRepository brewRepository;

    public BrewService(BrewRepository brewRepository) {
        this.brewRepository = brewRepository;
    }

    public List<Brew> findAll() {
        return brewRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Optional<Brew> findById(Long id) {
        return brewRepository.findById(id);
    }

    public void deleteById(Long id) {
        brewRepository.deleteById(id);
    }

    public Brew save(Brew brew) {
        return brewRepository.save(brew);
    }
}
