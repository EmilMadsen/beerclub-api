package dk.thebeerclub.brewhub.service;

import dk.thebeerclub.brewhub.model.Brew;
import dk.thebeerclub.brewhub.model.BrewStep;
import dk.thebeerclub.brewhub.repository.BrewStepRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StepService {

    private final BrewStepRepository brewStepRepository;
    private final BrewService brewService;

    public StepService(BrewStepRepository brewStepRepository, BrewService brewService) {
        this.brewStepRepository = brewStepRepository;
        this.brewService = brewService;
    }

    public List<BrewStep> getStepsByParentId(Long parentId) {
        return brewService.findById(parentId).get().getBrewSteps();
    }

    public Optional<BrewStep> getCurrentActiveStep(Long parentId) {
        List<BrewStep> steps = brewService.findById(parentId).get().getBrewSteps();
        return steps.stream()
                .filter(step -> step.getEnded() == null)
                .findFirst();
    }

    public BrewStep goToNextStep(Long parentId, ZonedDateTime timestamp) {
        Optional<BrewStep> optional = getCurrentActiveStep(parentId);
        if (optional.isPresent()) {
            BrewStep current = optional.get();
            if (current.getStarted() == null) {
                // first step (not started yet)
                current.setStarted(timestamp);
                return save(current);
            } else {
                // end current active & start next
                current.setEnded(timestamp);
                save(current);

                if (null != current.getNextStep()) {
                    BrewStep next = brewStepRepository.getById(current.getNextStep());
                    next.setStarted(timestamp);
                    return save(next);
                }

                return current;

            }
        }
        return null;
    }

    public BrewStep save(BrewStep brewStep) {
        return brewStepRepository.save(brewStep);
    }

    public void initializeSteps(Brew brew) {

        // link each step to the next.
        // todo: create some sort of step config?
        BrewStep step = new BrewStep(brew, "urtkøling",null, 6);
        save(step);
        step = new BrewStep(brew, "urtkogning", step.getId(), 5);
        save(step);
        step = new BrewStep(brew, "opkogning", step.getId(), 4);
        save(step);
        step = new BrewStep(brew, "eftergydning", step.getId(), 3);
        save(step);
        step = new BrewStep(brew, "mæskning", step.getId(), 2);
        save(step);
        step = new BrewStep(brew, "opvarmning", step.getId(), 1);
        save(step);

    }

    public void deleteByParentId(Long id) {
        List<BrewStep> steps = getStepsByParentId(id);
        for(BrewStep step : steps) {
            brewStepRepository.delete(step);
        }
    }
}
