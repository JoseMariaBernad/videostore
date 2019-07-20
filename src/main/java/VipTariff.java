import static java.lang.Float.max;

public class VipTariff implements Tariff {
    @Override
    public double calculatePrice(Rental rental) {
        int daysRented = rental.getDaysRented();
        switch (rental.getMovie().getPriceCode()) {
            case Movie.REGULAR:
                return 1.0 * daysRented;
            case Movie.NEW_RELEASE:
                return 2.0 * daysRented;
            case Movie.CHILDRENS:
                return max(0, daysRented - 2) * 1.0;
            default:
                throw new IllegalArgumentException("Unhandled price code" + rental.getMovie().getPriceCode());
        }
    }
}
