package ki.agh.dyeti.service.generator.util;

import ki.agh.dyeti.model.Product;
import lombok.Data;

@Data
public class NutritionPerUnit {

    private static final double NUTRITION_LABEL_BASE_GRAMS = 100.0;

    private final double kcal;
    private final double protein;
    private final double carbs;
    private final double fats;

    public NutritionPerUnit(Product product) {
        final double gramsPerUnit = product.getGramsPerUnit();
        final double perUnitFactor = gramsPerUnit / NUTRITION_LABEL_BASE_GRAMS;
        this.kcal = perUnitFactor * product.getKcal100g();
        this.protein = perUnitFactor * product.getProtein100g();
        this.carbs = perUnitFactor * product.getCarbs100g();
        this.fats = perUnitFactor * product.getFat100g();
    }
}
