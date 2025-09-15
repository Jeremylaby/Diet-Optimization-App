package ki.agh.dyeti.controller;

import ki.agh.dyeti.dto.request.PlanRequestDTO;
import ki.agh.dyeti.model.User;
import ki.agh.dyeti.security.CurrentUserProvider;
import ki.agh.dyeti.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plan")
public class PlanController {
    private final PlanService planService;
    private final CurrentUserProvider currentUserProvider;

    public PlanController(PlanService planService, CurrentUserProvider currentUserProvider) {
        this.planService = planService;
        this.currentUserProvider = currentUserProvider;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generatePlan(@RequestBody PlanRequestDTO planRequest) {
        User user =
                currentUserProvider.getCurrentUser().orElseThrow(() -> new RuntimeException("User not authenticated"));

        planService.startPlanGeneration(planRequest, user.getId());

        return ResponseEntity.ok("Plan generation started");
    }
}
