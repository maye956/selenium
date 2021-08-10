package com.example.mystoredemo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Test_Usuarios {
    private WebDriver driver;
    static SoftAssert softAssert = new SoftAssert();

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Juliana.Sala\\IdeaProjects\\WebDrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://automationpractice.com/index.php");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();

    }

    @Test(dataProvider = "data")
    public void login(String mail,String pass) {
        WebElement webElement = driver.findElement(By.cssSelector(".login"));
        softAssert.assertEquals(webElement.getAttribute("innerText"), "Sign in");
        webElement.click();
        driver.findElement(By.cssSelector("#email")).sendKeys(mail);
        driver.findElement(By.cssSelector("#passwd")).sendKeys(pass);
        driver.findElement(By.cssSelector("#passwd")).sendKeys(Keys.ENTER);
        webElement = driver.findElement(By.cssSelector(".logout"));
        softAssert.assertEquals(webElement.getAttribute("innerText"), "Sign out");
        softAssert.assertTrue(webElement.isDisplayed(),"Usuario no logueado");
        webElement.click();
        //esto lo agrego en el branch
        //sigo escribiendo fuera del branch, pero hice un merge asi que veo el comentario del branch
//        pero el no puede ver el mio
    }

    @DataProvider (name="data")
    public Object[][] getData() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(new File("data.json").getAbsolutePath());
        //Read JSON file
        JSONArray jsonA = (JSONArray) jsonParser.parse(reader);
        Object[][] datos = new Object[jsonA.size()][2];
        for (int i=0; i<jsonA.size();i++) {
            JSONObject json =(JSONObject)jsonA.get(i);
            JSONObject usuario = (JSONObject) json.get("usuario");
            datos[i][0]=usuario.get("mail");
            datos[i][1]=usuario.get("pass");
        }
        return datos;
    }


}
