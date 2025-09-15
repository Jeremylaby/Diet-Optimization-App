package ki.agh.dyeti.service;

import ki.agh.dyeti.dto.request.PlanRequestDTO;
import ki.agh.dyeti.repository.PlanRepository;
import ki.agh.dyeti.repository.PlansRecipesRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanService {
    private final PlanRepository planRepository;
    // private final PlansProductsRepository plansProductsRepository;
    private final PlansRecipesRepository plansRecipesRepository;

    public PlanService(
            PlanRepository planRepository,
            // PlansProductsRepository plansProductsRepository,
            PlansRecipesRepository plansRecipesRepository) {
        this.planRepository = planRepository;
        // this.plansProductsRepository = plansProductsRepository;
        this.plansRecipesRepository = plansRecipesRepository;
    }

    public void startPlanGeneration(PlanRequestDTO planRequestDTO, Long ownerId) {
        // Fetch user preferences (create the endpoint first)
        // Run optimization
        // Save results
    }
}
