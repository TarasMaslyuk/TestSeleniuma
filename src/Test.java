
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Test {

    public static WebDriver driver;

    public static void main(String[] args) {
        System.out.println("Тест:");
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        String url = "https://www.rgs.ru/";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(url);
        clickElement(By.xpath("//ol/li/a[contains(text(), 'Страхование')]"));
        //input[@data-test-name =  'FirstDepartureDate']
        //label[contains(text(), 'Не более 90 дней')]
        //div[@class = 'form-group']/input[@data-test-name = 'FullName']
        //input[@data-test-name = 'BirthDate']
        //div[@class='form-group width-xs-100pc']/adaptive-checkbox
        //div[@class='form-footer validation-group-has-error']/button[contains(text(),' Рассчитать ')]

        //div[@class='panel panel-default']/div/span[@class='text-uppercase text-semibold'] //застрахованый

        //*[contains(text(), 'активный отдых или спорт')]/ancestor::div[@class = 'calc-vzr-toggle-risk-group']//div[@class = 'toggle off toggle-rgs']
        clickElement(By.xpath("//a[contains(text(), 'Выезжающим за рубеж')]"));
        clickElement(By.xpath("//a[contains(text(), 'Рассчитать')]"));
        compareText(driver.findElement(By.xpath("//span[@class='h1']")).getText(), "Страхование выезжающих за рубеж");
        clickElement(By.xpath("//div/button[@data-test-value='Multiple']"));
        fillForm(By.xpath("//input[@id = 'Countries']"), "Шенген");

        driver.findElement(By.xpath("//pre[contains(text(),'Шенген')]")).click();
        driver.findElement(By.xpath("//input[@id = 'Countries']")).sendKeys(Keys.RETURN);
        new Select(driver.findElement(By.xpath("//select[@id =  'ArrivalCountryList']"))).selectByVisibleText("Испания");
        fillForm(By.xpath("//input[@data-test-name =  'FirstDepartureDate']"), fiksDate());
        fillForm(By.xpath("//input[@data-test-name =  'FirstDepartureDate']"), fiksDate());

    }


    public static void compareText(String actual, String expect) {
        if (actual.contains(expect)) {
            System.out.println("Искомый текст ЕСТЬ: " + expect);
        } else {
            System.err.println("Искомого теста НЕТ: " + expect + "  Вместо него: " + actual);
            driver.quit();
        }
    }

    public static void fillForm(By locator, String value){
        clickElement(locator);
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }

    public static void clickElement(By locator) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(locator));
        Wait<WebDriver> wait = new WebDriverWait(driver, 10, 1000);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
        driver.findElement(locator).click();
    }

    public static String fiksDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.DAY_OF_MONTH, 14);
        String newDate = dateFormat.format(instance.getTime());
        return newDate;
    }
}


