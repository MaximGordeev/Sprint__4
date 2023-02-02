import pom.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Parameterized.class)
public class TestImportantQuestions {
    private final String expectedText;
    private final int stringIndex;
    private WebDriver driver;
    //кнопка использования Cookie
    private final static By COOKIE_BUTTON = By.xpath("//button[@id='rcc-confirm-button']");
    //url сайта
    private final static String SITE_URL = "https://qa-scooter.praktikum-services.ru/";
    //ожидаемые данные при раскрытии AccordionItems

    public TestImportantQuestions(String expectedText, int stringIndex) {
        this.expectedText = expectedText;
        this.stringIndex = stringIndex;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1}")
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"Сутки — 400 рублей. Оплата курьеру — наличными или картой.", 0},
                {"Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.", 1},
                {"Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.", 2},
                {"Только начиная с завтрашнего дня. Но скоро станем расторопнее.", 3},
                {"Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.", 4},
                {"Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.", 5},
                {"Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.", 6},
                {"Да, обязательно. Всем самокатов! И Москве, и Московской области.", 7},
        };
    }

    @Before
    public void beforeSteps() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDrivers\\chrome\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(SITE_URL);
        MainPage objMainPage = new MainPage(driver);
        objMainPage.clickOnCookieButton();
    }

    @Test
    public void testAnswersInImportantQuestions() {
        MainPage objMainPage = new MainPage(driver);
        objMainPage.actualAnswersTest(expectedText, stringIndex);

    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
