import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    static int MAX_DEPTH = 5;
    static int WAIT_TIME_SECONDS = 1;

    private static void wait_for_sub_element(WebElement element, By by) {
        int has_slept = 0;
        while (element.findElements(by).size() == 0 && has_slept <= WAIT_TIME_SECONDS * 5) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
                has_slept++;
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    private static void dive_into_menu(WebElement menu_item, int current_depth) {
        menu_item.findElement(By.tagName("a")).click();
        wait_for_sub_element(menu_item, By.className("zml"));
        List<WebElement> sub_menu_items =
                menu_item.findElements(By.xpath("./ul/li[not(contains(@class,'lxts'))]"));
        for (WebElement sub_menu_item : sub_menu_items) {
            System.out.println("---".repeat(current_depth).concat(sub_menu_item.getText()));
            if (sub_menu_item.getAttribute("class").equals("zml"))
                if (current_depth < MAX_DEPTH)
                    dive_into_menu(sub_menu_item, current_depth + 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello, world!");
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        WebDriver driver = new FirefoxDriver();

        driver.get("http://qzbltushu.ys168.com/");

        // Get all the top menu items
        wait_for_sub_element(driver.findElement(By.id("dzx")), By.className("gml"));
        List<WebElement> menu_items = driver.findElements(By.className("gml"));

        // dive into each item of menu and get sub_menu_items
        for (WebElement menu_item : menu_items) {
            System.out.println(menu_item.getText());
            dive_into_menu(menu_item, 1);
        }
    }
}
