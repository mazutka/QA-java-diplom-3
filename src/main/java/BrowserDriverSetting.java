import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserDriverSetting {
    private final static Browser CURRENT_BROWSER = Browser.YANDEX;

    public static WebDriver browserDriverSetUp(){
        WebDriver driver = null;
        switch (CURRENT_BROWSER) {
            case YANDEX:
                System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\WebDriver\\bin\\yandexdriver.exe");
                driver = new ChromeDriver();
                break;
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
        return driver;
    }
}
