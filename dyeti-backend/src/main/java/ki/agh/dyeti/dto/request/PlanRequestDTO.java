package ki.agh.dyeti.dto.request;

public record PlanRequestDTO(
        String name,
        String description,
        Double caloriesTarget,
        Double proteinsTarget,
        Double carbsTarget,
        Double fatsTarget) {}
