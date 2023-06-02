import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static model.LoginPage.*;
import static model.MainPage.*;
import static model.FeedPage.*;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class MovingUserFromProfileTest {
    private WebDriver driver;
    private UserClient userClient;
    private String accessToken;
    private User user;
    private final By locator_menu;
    private final By locator_expected;

    public MovingUserFromProfileTest(By locator_menu, By locator_expected){
        this.locator_menu = locator_menu;
        this.locator_expected = locator_expected;
    }

    @Parameterized.Parameters(name = "{index}: Пункт меню - {0}, ожидаемый элемент на странице - {1}")
    public static Object[][] orderData() {
        return new Object[][]{
                {CONSTRUCTOR_LINK, SET_BURGER_HEADER},
                {FEED_LINK, FEED_HEADER},
                {LOGO_LINK, SET_BURGER_HEADER},
        };
    }

    @Before

    public void beforeTest() {
        driver = BrowserDriverSetting.browserDriverSetUp();
        //Сгенерировать данные пользователя
        user = UserGenerator.getRandomUser();
        //Создать пользователя
        userClient = new UserClient();
        ValidatableResponse createResponse = userClient.createUser(user);
        accessToken = createResponse.extract().path("accessToken");

    }

    @Test
    @DisplayName("Переход по пунктам меню из Личного кабинета")
    public void movingUserFromProfileTest() throws InterruptedException {
        //Открыть страницу входа и авторизоваться
        MainPage page = new MainPage(driver);
        page.openPage(LOGIN_PAGE_URL);
        page.findElementAndInputData(FIELD_EMAIL_LOGIN, user.getEmail());
        page.findElementAndInputData(FIELD_PASSWORD_LOGIN, user.getPassword());
        page.findAndClickElementWithWaitingAfter(LOGIN_BUTTON);
        //Перейти в личный кабинет
        page.findAndClickElement(PROFILE_LINK);
        //Перейти в пункт меню
        page.findAndClickElementWithWaitingAfter(locator_menu);
        //Проверить, что открыт пункт меню
        assertTrue(page.isElementDisplayed(page.findElement(locator_expected)));
    }

    @After
    public void afterTest() {
        driver.quit();
        //удалить пользователя
        userClient.deleteUser(accessToken);
    }
}
