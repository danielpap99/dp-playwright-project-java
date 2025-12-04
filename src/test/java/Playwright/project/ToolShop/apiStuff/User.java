package Playwright.project.ToolShop.apiStuff;

/*
{
        "first_name": "John",
        "last_name": "Doe",
        "address": {
        "street": "Street 1",
        "city": "City",
        "state": "State",
        "country": "Country",
        "postal_code": "1234AA"
        },
        "phone": "0987654321",
        "dob": "1970-01-01",
        "password": "SuperSecure@123",
        "email": "john@doe.example"
        }
*/

import net.datafaker.Faker;

public record User(
        String first_name,
        String last_name,
        Address address,
        String phone,
        String dob,
        String password,
        String email) {
    public static User randomUser() {
        Faker fake = new Faker();

        return new User(
                fake.name().firstName(),
                fake.name().lastName(),
                new Address(
                        fake.address().streetName(),
                        fake.address().city(),
                        fake.address().state(),
                        fake.address().country(),
                        fake.address().postcode()
                ),
                fake.phoneNumber().phoneNumber(),
                "1999-01-01",
                "Az123!&xyz",
                fake.internet().emailAddress()
        );
    }

    public User withPassword(String password) {
        return new User(
                first_name,
                last_name,
                address,
                phone,
                dob,
                password,
                email);
    }

    public User withoutFirstName() {
        return new User(
                null,
                last_name,
                address,
                phone,
                dob,
                password,
                email);
    }
}
