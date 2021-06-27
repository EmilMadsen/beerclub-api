package dk.thebeerclub.brewhub.controller;

import dk.thebeerclub.brewhub.model.Brew;
import dk.thebeerclub.brewhub.model.TiltLog;
import dk.thebeerclub.brewhub.service.BrewService;
import dk.thebeerclub.brewhub.service.StepService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/brew")
public class BrewController {

    private final BrewService brewService;
    private final StepService stepService;

    public BrewController(BrewService brewService, StepService stepService) {
        this.brewService = brewService;
        this.stepService = stepService;
    }

    @GetMapping("/check")
    public String check() {
        return "check - " + new Date();
    }

    @GetMapping
    public List<Brew> getAll() {
        return brewService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brew> findById(@PathVariable Long id) {
        Optional<Brew> optional = brewService.findById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    @GetMapping("/{id}/tilt-logs")
    public ResponseEntity<List<TiltLog>> findTiltLogsById(@PathVariable Long id) {
        Optional<Brew> optional = brewService.findById(id);
        return optional.map(brew -> ResponseEntity.ok(brew.getTiltLogList()))
                .orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    @PostMapping
    public ResponseEntity<Brew> save(@RequestBody Brew brew) {
        if (null == brew.getId()) {
            brewService.save(brew);
            stepService.initializeSteps(brew);
            return ResponseEntity.status(HttpStatus.CREATED).body(brew);
        } else {
            return ResponseEntity.ok().body(brewService.save(brew));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        try {
            stepService.deleteByParentId(id);
            brewService.deleteById(id);
            return ResponseEntity.ok("Successfully deleted brew");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getClass() + " - message: " + e.getMessage());
        }
    }
}
