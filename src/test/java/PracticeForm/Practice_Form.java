package PracticeForm;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Practice_Form {
    WebDriver driver;
    HashMap<String,String> hashMap=new HashMap<>();
    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headed");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }
    @Test
    public void wholeFormAutomation() throws InterruptedException, IOException, ParseException {
        driver.get("https://demoqa.com/automation-practice-form");
//    --------------------Header Text Match----------------------
        String text= driver.findElement(By.tagName("h5")).getText();
        Assert.assertEquals("Student Registration Form",text);
//    ------------------------Form Displayed Or Not-------------------
        WebElement displayed=driver.findElement(By.id("userForm"));
        Assert.assertTrue(displayed.isDisplayed());
//        --------------------Writing on Text Box Areas----------------
        driver.findElement(By.id("firstName")).sendKeys("Nibraj");
        driver.findElement(By.id("lastName")).sendKeys("Khan");
        Thread.sleep(500);
        driver.findElement(By.id("userEmail")).sendKeys("nibrajkhan123@gmail.com");
        Thread.sleep(500);
        driver.findElement(By.id("userNumber")).sendKeys("1780006900");
        Thread.sleep(1500);
        driver.findElement(By.id("currentAddress")).sendKeys("383,Rosa Vista,Modhubagh,Moghbazar");
//    ---------------------------Radio Button Test----------------------
        WebElement radioButtonFemale=driver.findElement(By.xpath("//label[contains(text(),'Female')]"));
        radioButtonFemale.click();
        //checking if the other radio button gets deselected or not after clicking another radio button.
        WebElement radioButtonMale=driver.findElement(By.xpath("//label[contains(text(),'Male')]"));
        Thread.sleep(1200);
        if(!radioButtonMale.isSelected()) {
            radioButtonMale.click();
        }
        System.out.println("Female radio button is deselected and Male radio button is selected");
//        ----------------------Automating Date Picker-------------------
        WebElement calendarInput= driver.findElement(By.id("dateOfBirthInput"));
        calendarInput.sendKeys(Keys.CONTROL+"a");
        Thread.sleep(1000);
        calendarInput.sendKeys("03/18/1999");
        Thread.sleep(1000);
        calendarInput.sendKeys(Keys.ENTER);
//        --------------------Subject Choose--------------
        WebElement subjectChoose= driver.findElement(By.id("subjectsInput"));
        subjectChoose.sendKeys("Chem");
        Thread.sleep(1000);
        //selecting the autocorrect value
        subjectChoose.sendKeys(Keys.ENTER);
        subjectChoose.sendKeys("Econom");
        Thread.sleep(1000);
        subjectChoose.sendKeys(Keys.ENTER);
// ----------------- Selecting the Checkbox ----------------
        WebElement sportsCheckBox= driver.findElement(By.id("hobbies-checkbox-1"));
        Actions action =new Actions(driver);
        //if first checkbox is not selected.
        if(!sportsCheckBox.isSelected()) {
            action.moveToElement(sportsCheckBox).click().perform();
        }
        WebElement readingCheckBox=driver.findElement(By.id("hobbies-checkbox-2"));
        //if the second checkbox is enabled click on it.
        Thread.sleep(1000);
        if(sportsCheckBox.isEnabled()){
            action.moveToElement(readingCheckBox).click().perform();
        }
//    --------------Uploading Pictures----------------
        Thread.sleep(1000);
        WebElement uploadPic= driver.findElement(By.id("uploadPicture"));
        uploadPic.sendKeys("C:\\Users\\NibrazKhan\\Pictures\\Saved Pictures\\myImg.jpeg");
//      ---------------State and City Dropdown value choose---------------
        WebElement state= driver.findElement(By.id("react-select-3-input"));
        WebElement city= driver.findElement(By.id("react-select-4-input"));
        //selecting the state as Haryana by writing Ha and enter
        //if the dropdown is enabled
//        if(state.isDisplayed()) {
//            state.sendKeys("Ha");
//              Thread.sleep(1000);
//            state.sendKeys(Keys.ENTER);
//        }
        state.sendKeys("Ha");
        Thread.sleep(1000);
        state.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        //if city is enabled
//            city.sendKeys("Pan");
//            Thread.sleep(1000);
//            state.sendKeys(Keys.ENTER);
            action.moveToElement(city).click().keyDown(Keys.ENTER).keyUp(Keys.ENTER).perform();

//        -------------- Clicking Submit Button --------------
        WebElement submitButton= driver.findElement(By.cssSelector("[type=submit]"));
        Thread.sleep(1000);
        //clicking the submit button
        action.moveToElement(submitButton).sendKeys(Keys.ENTER).perform();
        //checking whether submit button is visible or not
//        Assert.assertTrue(submit.isDisplayed());
//    -----------------keeping user info into json Array by extracting the info from the table-------------
        // checking the table header message
//        WebElement tableHeaderMsg= driver.findElement(By.className("modal-title h4"));
//        System.out.println(tableHeaderMsg.getText());
////        Assert.assertEquals("Thanks for submitting the form",tableHeaderMsg.getText());
        WebElement table= driver.findElement(By.tagName("tbody"));
        System.out.println(driver.findElement(By.tagName("tbody")).isDisplayed());
        List<WebElement> allRows=table.findElements(By.tagName("tr"));
        System.out.println(allRows);
        for (WebElement row:
                allRows) {
            List<WebElement>cells=row.findElements(By.tagName("td"));
//            System.out.println(cells.get(0).getText());
            hashMap.put(cells.get(0).getText(),cells.get(1).getText());
            }
        //printing values of form datas
//        for (String value:
//                hashMap.values()) {
//            System.out.println(value);
//
//        }
        jsonDataWrite(hashMap);

        }
    public void jsonDataWrite(HashMap<String,String>userData) throws IOException, ParseException {
        String fileName="./src/test/resources/students.json";
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader(fileName));
        JSONObject userObj = new JSONObject();
        for (Map.Entry<String, String> set : userData.entrySet()) {
            userObj.put(set.getKey(), set.getValue());

        }

        JSONArray jsonArray = (JSONArray) obj;
        jsonArray.add(userObj);
        FileWriter file = new FileWriter(fileName);
        file.write(jsonArray.toJSONString());
        file.flush();
        file.close();
        System.out.println("Saved!");
        System.out.print(jsonArray);

    }




    @After
    public void close() {
//        driver.close();
//        driver.quit();
    }

}
