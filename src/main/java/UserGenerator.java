import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public static User getRandomUser(){
        return new User(UserGenerator.getRandomEmail(),UserGenerator.getRandomPassword(),UserGenerator.getRandomName());
    }

    public static String getRandomEmail(){
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }

    public static String getRandomPassword(){
        Faker faker = new Faker();
        return faker.internet().password(8,16,true,true,true);
    }

    public static String getRandomName(){
        Faker faker = new Faker();
        return faker.address().firstName();
    }

}
