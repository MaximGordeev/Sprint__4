import pom.MainPage;
import pom.OrderDataPage;
import pom.PersonalDataPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Parameterized.class)
public class TestScooterOrder {
    private final boolean headerButton;
    private final String name;
    private final String surname;
    private final String address;
    private final String metroStationPattern;
    private final String telephoneNumber;
    private final String deliveryDate;
    private final int rentDays;
    private final String courierComment;
    private WebDriver driver;

    //url сайта
    private final static String SITE_URL = "https://qa-scooter.praktikum-services.ru/";

    public TestScooterOrder(boolean headerButton, String name, String surname, String address, String metroStationPattern, String telephoneNumber, String deliveryDate, int rentDays, String courierComment) {
        this.headerButton = headerButton;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStationPattern = metroStationPattern;
        this.telephoneNumber = telephoneNumber;
        this.deliveryDate = deliveryDate;
        this.rentDays = rentDays;
        this.courierComment = courierComment;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2} {3} {4} {5} {6} {7} {8}")
    public static Object[][] getOrderData() {
        return new Object[][]{
                {true, "Макс", "Максимов", "переулок Максимов", "Сокольник", "12345678901", "23.03.2023", 2, "давай быстрее"},
                {false, "Гордей", "Гордеев", "улица Гордеевская", "Комсомольс", "10987654321", "04.04.2023", 4, "приедишь поговорим)"},
        };
    }

    @Before
    public void beforeSteps() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDrivers\\chrome\\chromedriver.exe");
        driver = new ChromeDriver();
        //System.setProperty("webdriver.gecko.driver","C:\\WebDrivers\\firefox\\geckodriver.exe");
        //driver = new FirefoxDriver();
        driver.get(SITE_URL);
        MainPage objMainPage = new MainPage(driver);
        objMainPage.clickOnCookieButton();
    }

    @Test
    public void testScooterOrderWithHeaderButton() {
        MainPage objMainPage = new MainPage(driver);
        if (headerButton) {
            objMainPage.clickOnOrderButton(objMainPage.getHeaderOrderButton());
        } else objMainPage.clickOnOrderButton(objMainPage.getMiddleOrderButton());
        PersonalDataPage objPersonalDataPage = new PersonalDataPage(driver);
        objPersonalDataPage.enterPersonalData(name, surname, address, metroStationPattern, telephoneNumber);
        OrderDataPage objOrderDataPage = new OrderDataPage(driver);
        objOrderDataPage.enterOrderData(deliveryDate, rentDays, courierComment);
        Assert.assertTrue(objOrderDataPage.isOrderConmfirmed());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}