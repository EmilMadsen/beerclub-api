package dk.thebeerclub.brewhub.controller;

import com.google.gson.Gson;
import dk.thebeerclub.brewhub.model.BrewStep;
import dk.thebeerclub.brewhub.service.StepService;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/step")
public class StepController {

    private final StepService stepService;
    private final Gson gson;

    public StepController(StepService stepService, Gson gson) {
        this.stepService = stepService;
        this.gson = gson;
    }

    @GetMapping("/parent/{parentId}")
    public List<BrewStep> getStepsByParentId(@PathVariable Long parentId) {
        return stepService.getStepsByParentId(parentId);
    }

    @GetMapping("/parent/{parentId}/active")
    public Optional<BrewStep> getCurrentActiveStep(@PathVariable Long parentId) {
        return stepService.getCurrentActiveStep(parentId);
    }


    @PostMapping("/parent/{parentId}/next/{timestamp}")
    public BrewStep goToNextStep(@PathVariable Long parentId, @PathVariable String timestamp) {
        ZonedDateTime zd = ZonedDateTime.parse(timestamp);
        return stepService.goToNextStep(parentId, zd);
    }

    @PostMapping
    public BrewStep save(@RequestBody BrewStep brewStep) {
        return stepService.save(brewStep);
    }
}
