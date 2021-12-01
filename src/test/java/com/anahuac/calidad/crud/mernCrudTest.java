package com.anahuac.calidad.crud;


import java.util.regex.Pattern;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;
import java.io.File;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class mernCrudTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
   
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-setuid-sandbox");
    options.addArguments("--remote-debugging-port=9222");
    options.addArguments("--disable-dev-shm-using");
    options.addArguments("--disable-extensions"); 
    options.addArguments("--disable-gpu");
    options.addArguments("start-maximized");
    options.addArguments("disable-infobars");

    driver = new ChromeDriver(options);
    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void atestadd() throws Exception {
    driver.get("https://mern-crud.herokuapp.com/");
    driver.findElement(By.xpath("//div[@id='root']/div/div[2]/button")).click();
    driver.findElement(By.name("name")).click();
    driver.findElement(By.name("name")).clear();
    driver.findElement(By.name("name")).sendKeys("Marisol");
    driver.findElement(By.name("email")).click();
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys("hola2@jsdjkdsl.com");
    driver.findElement(By.name("age")).click();
    driver.findElement(By.name("age")).clear();
    driver.findElement(By.name("age")).sendKeys("20");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[1]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[1]/following::span[1]")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
    pause(5000);
    //WebElement texto = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[4]/div/p"));
    //String mensaje = texto.getText();
    String tag = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[4]/div/div")).getText();
    assertThat("Nice one!",is(tag));
  }


  @Test
  public void ctestUpdate() throws Exception {
	  driver.get("https://mern-crud.herokuapp.com/");
	  driver.findElement(By.xpath("//div[@id='root']/div/div[2]/table/tbody/tr/td[5]/button")).click();
	  driver.findElement(By.name("name")).click();
	  driver.findElement(By.name("name")).clear();
	  driver.findElement(By.name("name")).sendKeys("Marisol");
	  driver.findElement(By.xpath("//div[3]/div[2]/div/i")).click();
	  driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[2]/following::div[1]")).click();
	  driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
	  pause(5000);
	  String tag = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[4]/div/p")).getText();
	  assertThat("Successfully updated!",is(tag));
	  
  }
  
  @Test
  public void btestread() throws Exception {
	  driver.get("https://mern-crud.herokuapp.com/");
	  List<WebElement> elementsList = driver.findElements(By.xpath("//td"));
	  for (WebElement element: elementsList) {
		  System.out.println(element.getText());
	  }
	  
  }
  
  @Test
  public void dtestdelete() throws Exception {
	    driver.get("https://mern-crud.herokuapp.com/");
	    Boolean isPresentitem = driver.findElements(By.xpath("//button[@class='ui black button']")).size()>0;
	    if(!isPresentitem) {
	        driver.findElement(By.xpath("//div[@id='root']/div/div[2]/button")).click();
	        driver.findElement(By.name("name")).click();
	        driver.findElement(By.name("name")).clear();
	        driver.findElement(By.name("name")).sendKeys("Marisol");
	        driver.findElement(By.name("email")).click();
	        driver.findElement(By.name("email")).clear();
	        driver.findElement(By.name("email")).sendKeys("hola2@hola4marisol.com");
	        driver.findElement(By.name("age")).click();
	        driver.findElement(By.name("age")).clear();
	        driver.findElement(By.name("age")).sendKeys("20");
	        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[1]")).click();
	        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[1]/following::span[1]")).click();
	        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
	    }
	    pause(5000);
	    driver.findElement(By.xpath("//button[@class='ui black button']")).click();
	    driver.findElement(By.xpath("//button[@class='ui red button']")).click();
	    pause(5000);
	    Boolean isPresent = driver.findElements(By.xpath("//td[contains(text(),'Marisol')]")).size()>0;
	    assertFalse(isPresent);
	    
	 
	  } 

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
  
  private void pause(long mils) {
	  try {
		  Thread.sleep(mils);
		  }catch(Exception e){
			  e.printStackTrace();	
		  }
	  }
}

