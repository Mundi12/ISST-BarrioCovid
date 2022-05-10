// Generated by Selenium IDE
package es.upm.dit.isst.barriocovid;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class FlujoVoluntarioTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    System.setProperty( "webdriver.chrome.driver", "/home/marinaaretaasanza/Documentos/chromedriver_linux64/chromedriver");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void flujoVoluntario() {
    driver.get("http://localhost:8080/login?logout");
    driver.manage().window().setSize(new Dimension(1853, 1053));
    driver.findElement(By.id("username")).sendKeys("Pepe Jiménez");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("voluntario1");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Todos los pedidos")).click();
    driver.findElement(By.linkText("Ver pedido")).click();
    driver.findElement(By.linkText("Confirmar pedido")).click();
    driver.findElement(By.linkText("Pedidos confirmados")).click();
    driver.findElement(By.linkText("Volver atrás")).click();
    driver.findElement(By.id("navbarDropdownMenuLink")).click();
    driver.findElement(By.linkText("Cerrar sesión")).click();
    driver.findElement(By.cssSelector(".btn")).click();
  }
}