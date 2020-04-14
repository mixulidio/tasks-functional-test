package br.rs.mixulidio.tasks;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {

    public static WebDriver acessarApp() {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://localhost:8001/tasks/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void testAmbiente() {
        WebDriver driver = acessarApp();
        try{
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Test via Selenium");
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2021");
            driver.findElement(By.id("saveButton")).click();
            String mensagem = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", mensagem);
        }finally{
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSEmDescricao() {
        WebDriver driver = acessarApp();
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("dueDate")).sendKeys("10/10/2021");
        driver.findElement(By.id("saveButton")).click();
        String mensagem = driver.findElement(By.id("message")).getText();
        Assert.assertEquals("Fill the task description", mensagem);
        driver.quit();
    }

    @Test
    public void naoDeveSalvarTarefaSEmDatao() {
        WebDriver driver = acessarApp();
        driver.findElement(By.id("addTodo")).click();
        driver.findElement(By.id("task")).sendKeys("Test via Selenium");
        driver.findElement(By.id("saveButton")).click();
        String mensagem = driver.findElement(By.id("message")).getText();
        Assert.assertEquals("Fill the due date", mensagem);
        driver.quit();
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() {
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
