package JUnitTests;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class JunitAutomation {
    WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headed");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }
    @Test
    public void getTitle(){
        driver.get("https://demoqa.com/");
        String title= driver.getTitle();
        Assert.assertEquals("ToolsQA",title);
        //Assert.assertTrue(title.contains("ToolsQA"));
    }
    @Test
    public void writeText(){
        driver.get("https://demoqa.com/text-box");
        //driver.findElement(By.id("userName")).sendKeys("Salman");
//        driver.findElement(By.cssSelector("[type=text]")).sendKeys("Salman");
        //driver.findElement(By.cssSelector("[id=userName]")).sendKeys("Salman");
        //driver.findElement(By.className("form-control")).sendKeys("Salman");
        WebElement txtFirstName= driver.findElement(By.id("userName"));
        txtFirstName.sendKeys("Salman");
//        WebElement txtEmail=driver.findElement(By.xpath("//input[@id='userEmail']"));
//        txtEmail.sendKeys("salman@test.com");
        WebElement txtEmail=driver.findElement(By.cssSelector("[type='email']"));
        txtEmail.sendKeys("salman@test.com");
        Actions action =new Actions(driver);
        List<WebElement> button=driver.findElements(By.tagName("button"));
        action.moveToElement(button.get(1)).click();
//        button.get(1).click();

        //driver.findElements(By.tagName("button")).get(0).click();
    }
    @Test
    public void handleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
//        driver.findElement(By.id("alertButton")).click();
//        Thread.sleep(3000);
//        driver.switchTo().alert().accept();
//        driver.findElement(By.cssSelector("[id=timerAlertButton]")).click();
//        Thread.sleep(9000);
//        driver.switchTo().alert().accept();
//        driver.findElement(By.id("confirmButton")).click();
//        Thread.sleep(2500);
//        driver.switchTo().alert().dismiss();
//        String text=driver.findElement(By.className("text-success")).getText();
//        Assert.assertTrue(text.contains("Cancel"));
        //prompt box testing
        driver.findElement(By.id("promtButton")).click();
        driver.switchTo().alert().sendKeys("You are life giving life");
        Thread.sleep(3500);
        driver.switchTo().alert().accept();
        String text = driver.findElement(By.id("promptResult")).getText();
        Assert.assertTrue(text.contains("life"));

    }
    @Test
    public void selectDropDown(){
        driver.get("https://demoqa.com/select-menu");
        Select select=new Select(driver.findElement(By.id("oldSelectMenu")));
        select.selectByValue("7");
        Select cars=new Select(driver.findElement(By.id("cars")));
        if(cars.isMultiple()){
            cars.selectByValue("audi");
            cars.selectByValue("volvo");
        }


    }
    //modal
    @Test
    public void modalDialog() throws InterruptedException {
        driver.get("https://demoqa.com/modal-dialogs");
        driver.findElement(By.id("showSmallModal")).click();
        Thread.sleep(3000);
        String text=driver.findElement(By.className("modal-body")).getText();
        System.out.println(text);
        driver.findElement(By.id("closeSmallModal")).click();


    }
    @Test
    public void mouseHover(){
        driver.get("https://green.edu.bd/");
        Actions action=new Actions(driver);
        //need to create custom xpath by ourself
        List<WebElement> list= driver.findElements(By.xpath("//a[contains(text(),\"About Us\")]"));
        action.moveToElement(list.get(2)).perform();
    }
    @Test
    public void datePicker() throws InterruptedException {
        driver.get("https://demoqa.com/date-picker");
        WebElement txtDate=driver.findElement(By.id("datePickerMonthYearInput"));
        Actions action =new Actions(driver);
//        action.moveToElement(txtDate).doubleClick().click().
//                keyDown(Keys.BACK_SPACE).keyUp(Keys.BACK_SPACE).
//                perform();
        txtDate.sendKeys(Keys.CONTROL+"a");
        txtDate.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(2000);
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys("03/18/1998");
        txtDate.sendKeys(Keys.ENTER);
    }
    @Test
    public void keyboardEvent() throws InterruptedException {
        driver.get("https://www.google.com/");
        Actions action =new Actions(driver);
        WebElement element=driver.findElement(By.name("q"));
        action.moveToElement(element).
                keyDown(Keys.SHIFT)
                .sendKeys("who am i?")
                .keyUp(Keys.SHIFT)
                .doubleClick()
                .click()
                .sendKeys(Keys.BACK_SPACE)
                .contextClick()
                .perform();
        Thread.sleep(5000);
    }
    @Test
    public void actionClick(){
        driver.get("https://demoqa.com/buttons");
        List<WebElement> buttons=driver.findElements(By.cssSelector("[type=button]"));
        Actions actions=new Actions(driver);
        actions.doubleClick(buttons.get(1)).perform();
        actions.contextClick(buttons.get(2)).perform();
    }
    @Test
    public void takeScreenShot() throws IOException {
        driver.get("https://www.google.com/");
        File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String time = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
        String fileWithPath = "./src/test/resources/screenshots/" + time + ".png";
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(screenshotFile, DestFile);
    }
    @Test
    public void uploadFile(){
        driver.get("https://demoqa.com/upload-download");
        driver.findElement(By.id("uploadFile")).sendKeys("C:\\Users\\NibrazKhan\\Pictures\\Saved Pictures\\random_Cv.jpg");
    }
    @Test
    public void downloadfile(){
        driver.get("https://demoqa.com/upload-download");
        driver.findElement(By.id("downloadButton")).click();
    }
    @Test
    public void handleTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(3000);
        ArrayList<String>wi=new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(wi.get(1));
        System.out.println("New tab title:"+driver.getTitle());
        String text=driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertTrue(text.contains("sample"));
        driver.close();
        driver.switchTo().window(wi.get(0));

    }
    @Test
    public void handleWindow()throws InterruptedException{
        driver.get("https://demoqa.com/browser-windows");
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("windowButton")));
        driver.findElement(By.id("windowButton")).click();
        String mainWindowHandle=driver.getWindowHandle();
        //set used to indicate unordered elements as the window will be the child window of the main window
        Set<String> allWindowHandles=driver.getWindowHandles();
        Iterator<String>iterator=allWindowHandles.iterator();
        while(iterator.hasNext()){
            String childWindow= iterator.next();
            if(!mainWindowHandle.equalsIgnoreCase(childWindow)){
                driver.switchTo().window(childWindow);
                String text=driver.findElement(By.id("sampleHeading")).getText();
                Assert.assertTrue(text.contains("sample"));
            }
        }
        driver.close();
    }
    @Test
    public void scrapTable(){
        driver.get("https://demoqa.com/webtables");
        WebElement table= driver.findElement(By.className("rt-tbody"));
        List<WebElement>allRows=table.findElements(By.className("rt-tr"));
        int i=0;
        for (WebElement row:
             allRows) {
            List<WebElement>cells=row.findElements(By.className("rt-td"));
            for (WebElement cell:cells){
                i++;
                System.out.println("num["+i+"] "+cell.getText());
            }
        }

    }
//    handling Iframe
    @Test
    public void handleIframe(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame2");
        String text=driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertTrue(text.contains("This is a sample page"));
        //switching back to the defaulted content
        driver.switchTo().defaultContent();

    }

//

    @After
    public void close() {
//        driver.close();
        driver.quit();
    }

}