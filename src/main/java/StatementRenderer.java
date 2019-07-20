import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class StatementRenderer {
    public static class Statement {

        private String name;
        private List<PriceRental> priced;
        private double discountPercentage;

        private class Discount {
            public double discountAmount;
            public double discountedTotal;

            public Discount(double discountAmount, double discountedTotal) {
                this.discountAmount = discountAmount;
                this.discountedTotal = discountedTotal;
            }
        }

        public Statement(String name, List<PriceRental> priced, double discountPercentage) {

            this.name = name;
            this.priced = priced;
            this.discountPercentage = discountPercentage;
        }

        public Optional<Discount> discount() {
            if (discountPercentage == 0.0) {
                return Optional.empty();
            } else {
                double discountAmount = total() * discountPercentage;
                double discountedTotal = total() - discountAmount;
                return Optional.of(new Discount(discountAmount, discountedTotal));
            }
        }

        public void eachRental(Consumer<PriceRental> consumer) {
            priced.forEach(consumer);
        }

        public double total() {
            return priced.stream().collect(Collectors.summingDouble(pr -> pr.price));
        }

        public int points() {
            return priced.stream().collect(Collectors.summingInt(pr -> pr.points));
        }

        public String name() {
            return name;
        }
    }

    public String render(Statement statement) {

        StringBuilder result = new StringBuilder("Rental Record for " + statement.name() + "\n");


        statement.eachRental(priceRental ->
                result.append("\t").append(priceRental.title).append("\t").append(priceRental.price).append("\n")
        );

        result.append("You owed ").append(statement.total()).append("\n");

        statement.discount().ifPresent(discount -> result.append("You got a discount of " + String.valueOf(discount.discountAmount) +
                ", so you now owe " + String.valueOf(discount.discountedTotal) + "\n"));

        result.append("You earned ").append(statement.points()).append(" frequent renter points\n");


        return result.toString();
    }

}
