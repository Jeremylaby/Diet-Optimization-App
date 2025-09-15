package ki.agh.dyeti.dto;

import java.util.List;

public record PlanDTO(
        Long id,
        String name,
        String description,
        Double caloriesTarget,
        Double proteinsTarget,
        Double carbsTarget,
        Double fatsTarget,
        Double calories,
        Double proteins,
        Double carbs,
        Double fats,
        List<PlanProductDTO> products) {}
