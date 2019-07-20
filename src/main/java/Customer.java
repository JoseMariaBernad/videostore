import java.util.ArrayList;
import java.util.List;

class Customer {
    private List<Rental> rentals = new ArrayList<>();
    private String name;
    private final PricePlan plan;

    String generateStatement(StatementRenderer renderer) {
        return renderer.render(plan.statement(name, rentals));
    }

    Customer(String name, PricePlan plan) {
        this.name = name;
        this.plan = plan;
    }

    void addRental(Rental rental) {
        rentals.add(rental);
    }

}