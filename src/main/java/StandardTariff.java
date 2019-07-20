import static java.lang.Float.max;

public class StandardTariff implements Tariff {
    @Override
    public double calculatePrice(Rental rental) {
        int daysRented = rental.getDaysRented();
        switch (rental.getMovie().getPriceCode()) {
            case Movie.REGULAR:
                return 2.0 + max(0, daysRented - 2) * 1.5;
            case Movie.NEW_RELEASE:
                return 3.0 * daysRented;
            case Movie.CHILDRENS:
                return 1.5 + max(0, daysRented - 3) * 1.5;
            default:
                throw new IllegalArgumentException("Unhandled price code" + rental.getMovie().getPriceCode());
        }
    }
}
