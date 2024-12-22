package utilities;

import net.datafaker.Faker;

import java.time.Instant;
import java.util.Locale;

public class FakerConfigs {

    Faker faker = new Faker(Locale.ENGLISH);

    public static FakerConfigs getFaker() {
        return new FakerConfigs();
    }

    public String getEmailFake() {
        return faker.internet().emailAddress();
    }

    public String getPassword () {
        return faker.internet().password();
    }

    public String getFirstName () {
        return faker.name().firstName();
    }

    public String getLastName() {
        return faker.name().lastName();
    }

    public String getMiddleName() {
        return faker.name().name();
    }

}
