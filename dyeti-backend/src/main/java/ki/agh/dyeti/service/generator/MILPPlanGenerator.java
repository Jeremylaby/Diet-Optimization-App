package ki.agh.dyeti.service.generator;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.*;
import java.util.*;
import ki.agh.dyeti.model.Plan;
import ki.agh.dyeti.model.PlanProduct;
import ki.agh.dyeti.model.Product;
import ki.agh.dyeti.model.util.PlanProductId;
import ki.agh.dyeti.service.generator.util.NutritionPerUnit;
import org.springframework.stereotype.Component;

@Component
public class MILPPlanGenerator implements PlanGenerator {

    //    The problem I had was that we cant use variable value in calculation of objective
    //    for example we can't do : objective.setCoefficient(variable, f(variable) * preference *
    // product.getGramsPerUnit())
    //    and I needed that to implement law of dimnishing value so I'm simulating it with those segments and max int,
    //    now each product will have multiple variables with differenc coeficients according to mentioned law

    private static final int MAX_INT_AMOUNT = 15;
    private static final int MAX_SEGMENTS_AMOUNT = 10;
    private static final double SEGMENT_SIZE = 50.0;
    private static final double K0_CONST = 0.8;
    private static final double CALORIE_DENSITY = 2.0;

    public double calculateVariableCoefficient(double calorieDensity, double preference, int segmentIndex) {
        // function : f(x) = 1 - e^(-k*x)

        double k = K0_CONST * (calorieDensity / CALORIE_DENSITY);
        double intervalStart = segmentIndex * SEGMENT_SIZE;
        double intervalEnd = intervalStart + SEGMENT_SIZE;

        double integralStartValue = 1 - Math.exp(-k * intervalStart);
        double integralEndValue = 1 - Math.exp(-k * intervalEnd);
        // robię tutaj nominalną wartość przypadającą na jeden gram produktu.
        double marginalUtilityPerUnit = (integralEndValue - integralStartValue) / SEGMENT_SIZE;
        return preference * marginalUtilityPerUnit;
    }

    @Override
    public Plan generate(Plan plan, Map<Product, Double> preferences) {
        Loader.loadNativeLibraries();
        MPSolver solver = MPSolver.createSolver("SCIP");
        if (solver == null) {
            throw new IllegalStateException("Could not create solver.");
        }

        Map<Product, List<MPVariable>> productVariables = new HashMap<>();
        for (Product product : preferences.keySet()) {
            List<MPVariable> variables = new ArrayList<>();
            if ("piece".equals(product.getUnit().getName())) {
                for (int i = 0; i < MAX_INT_AMOUNT; i++) {
                    MPVariable variable = solver.makeIntVar(0.0, 1.0, "x_" + product.getId() + "_" + i);
                    variables.add(variable);
                }
            } else {
                for (int i = 0; i < MAX_SEGMENTS_AMOUNT; i++) {
                    MPVariable variable = solver.makeNumVar(0.0, SEGMENT_SIZE, "x_" + product.getId() + "_" + i);
                    variables.add(variable);
                }
            }
            productVariables.put(product, variables);
        }

        MPConstraint calorieConstraint = solver.makeConstraint(0.0, plan.getCaloriesTarget());
        MPConstraint proteinConstraint = solver.makeConstraint(0.0, plan.getProteinsTarget());
        MPConstraint carbohydrateConstraint = solver.makeConstraint(0.0, plan.getCarbsTarget());
        MPConstraint fatConstraint = solver.makeConstraint(0.0, plan.getFatsTarget());

        MPObjective objective = solver.objective();

        for (Map.Entry<Product, List<MPVariable>> entry : productVariables.entrySet()) {
            Product product = entry.getKey();
            List<MPVariable> variables = entry.getValue();

            NutritionPerUnit nutritionPerUnit = new NutritionPerUnit(product);
            double gramsPerUnit = product.getGramsPerUnit();
            double calorieDensity = nutritionPerUnit.getKcal() / gramsPerUnit;

            for (int i = 0; i < variables.size(); i++) {
                MPVariable currVariable = variables.get(i);
                calorieConstraint.setCoefficient(currVariable, nutritionPerUnit.getKcal());
                proteinConstraint.setCoefficient(currVariable, nutritionPerUnit.getProtein());
                carbohydrateConstraint.setCoefficient(currVariable, nutritionPerUnit.getCarbs());
                fatConstraint.setCoefficient(currVariable, nutritionPerUnit.getFats());

                double variableCoef =
                        calculateVariableCoefficient(calorieDensity, preferences.getOrDefault(product, 1.0), i);
                objective.setCoefficient(currVariable, variableCoef);
            }
        }

        objective.setMaximization();

        if (solver.solve() != MPSolver.ResultStatus.OPTIMAL) {
            throw new IllegalStateException("No optimal solution found.");
        }

        double totalCalories = 0;
        double totalProteins = 0;
        double totalCarbs = 0;
        double totalFats = 0;

        List<PlanProduct> planProducts = new ArrayList<>();

        for (Map.Entry<Product, List<MPVariable>> entry : productVariables.entrySet()) {
            Product product = entry.getKey();
            double variableCount = 0;
            for (MPVariable variable : entry.getValue()) {
                double quantity = variable.solutionValue();
                if (quantity <= 0) {
                    break;
                }
                variableCount += quantity;
            }
            if (variableCount > 0) {
                NutritionPerUnit nutritionPerUnit = new NutritionPerUnit(product);

                totalCalories += variableCount * nutritionPerUnit.getKcal();
                totalProteins += variableCount * nutritionPerUnit.getProtein();
                totalCarbs += variableCount * nutritionPerUnit.getCarbs();
                totalFats += variableCount * nutritionPerUnit.getFats();

                PlanProduct planProduct = PlanProduct.builder()
                        .id(new PlanProductId(plan.getId(), product.getId()))
                        .plan(plan)
                        .product(product)
                        .quantity(variableCount)
                        .build();

                planProducts.add(planProduct);
            }
        }

        plan.setCalories(totalCalories);
        plan.setProteins(totalProteins);
        plan.setCarbs(totalCarbs);
        plan.setFats(totalFats);
        plan.setProducts(planProducts);

        return plan;
    }
}
