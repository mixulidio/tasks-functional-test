package br.rs.mixulidio.tasks;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {

    public WebDriver acessarApp() throws MalformedURLException {
        // WebDriver driver = new ChromeDriver();
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), cap);
        driver.navigate().to("http://192.168.99.1:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void testAmbiente() throws MalformedURLException {
        WebDriver driver = acessarApp();
        try {
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Test via Selenium");
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2021");
            driver.findElement(By.id("saveButton")).click();
            String mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", mensagem);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSEmDescricao() throws MalformedURLException {
        WebDriver driver = acessarApp();
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("dueDate")).sendKeys("10/10/2021");
        driver.findElement(By.id("saveButton")).click();
        String mensagem = driver.findElement(By.id("message")).getText();
        Assert.assertEquals("Fill the task description", mensagem);
        driver.quit();
    }

    @Test
    public void naoDeveSalvarTarefaSEmDatao() throws MalformedURLException {
        WebDriver driver = acessarApp();
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("task")).sendKeys("Test via Selenium");
        driver.findElement(By.id("saveButton")).click();
        String mensagem = driver.findElement(By.id("message")).getText();
        Assert.assertEquals("Fill the due date", mensagem);
        driver.quit();
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
        WebDriver driver = acessarApp();
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("task")).sendKeys("Test via Selenium");
        driver.findElement(By.id("dueDate")).sendKeys("10/10/2019");
        driver.findElement(By.id("saveButton")).click();
        String mensagem = driver.findElement(By.id("message")).getText();
        Assert.assertEquals("Due date must not be in past", mensagem);
        driver.quit();
    }
}
