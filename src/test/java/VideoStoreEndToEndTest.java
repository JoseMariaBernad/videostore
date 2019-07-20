import static java.util.Arrays.stream;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.stream.Collectors;
import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Test;

public class VideoStoreEndToEndTest {

    private Customer customer = new Customer("Fred", new PricePlan(new LoyaltyPlan(), new StandardTariff()));

    @Test
    public void basic_tariff() {
        Customer basic = new Customer("basic", new PricePlan(new LoyaltyPlan(), new StandardTariff()));
        basic.addRental(new Rental(children("childrens"), 3));
        basic.addRental(new Rental(regularMovie("regular"), 3));
        basic.addRental(new Rental(newRelease("new"), 3));

        assertThat(basic.generateStatement(new StatementRenderer()),
                hasStatementLines("Rental Record for basic",
                        "\tchildrens\t1.5",
                        "\tregular\t3.5",
                        "\tnew\t9.0",
                        "You owed 14.0",
                        "You earned 4 frequent renter points"
                ));
    }

    @Test
    public void vip_tariff() {
        Customer vip = new Customer("basic", new PricePlan(new LoyaltyPlan(), new VipTariff()));
        vip.addRental(new Rental(children("childrens"), 3));
        vip.addRental(new Rental(regularMovie("regular"), 3));
        vip.addRental(new Rental(newRelease("new"), 3));

        assertThat(vip.generateStatement(new StatementRenderer()),
                hasStatementLines("Rental Record for basic",
                        "\tchildrens\t1.0",
                        "\tregular\t3.0",
                        "\tnew\t6.0",
                        "You owed 10.0",
                        "You earned 4 frequent renter points"
                ));
    }

    @Test
    public void basic_tariff_with_discount() {
        Customer basic = new Customer("basic", new PricePlan(new LoyaltyPlan(), new StandardTariff()));
        basic.addRental(new Rental(children("childrens"), 3));
        basic.addRental(new Rental(regularMovie("regular"), 3));
        basic.addRental(new Rental(newRelease("new"), 3));
        basic.addRental(new Rental(newRelease("new1"), 3));
        basic.addRental(new Rental(newRelease("new2"), 3));

        assertThat(basic.generateStatement(new StatementRenderer()),
                hasStatementLines("Rental Record for basic",
                        "\tchildrens\t1.5",
                        "\tregular\t3.5",
                        "\tnew\t9.0",
                        "\tnew1\t9.0",
                        "\tnew2\t9.0",
                        "You owed 32.0",
                        "You got a discount of 1.6, so you now owe 30.4",
                        "You earned 8 frequent renter points"
                ));
    }

    private Matcher<String> hasStatementLines(String... lines) {
        return equalTo(stream(lines).collect(Collectors.joining("\n", "", "\n")));
    }

    private String generate(String generator) {
        return generator;
    }

    private Movie newRelease(String title) {
        return new Movie(title, Movie.NEW_RELEASE);
    }

    private Movie children(String title) {
        return new Movie(title, Movie.CHILDRENS);
    }

    private Movie regularMovie(String title) {
        return new Movie(title, Movie.REGULAR);
    }
}