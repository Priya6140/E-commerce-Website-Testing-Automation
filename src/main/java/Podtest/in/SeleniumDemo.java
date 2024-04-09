package Podtest.in;

import org.openqa.selenium.By;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;


public class SeleniumDemo {
    static WebDriver wd;
    public static void main(String[] args) throws InterruptedException {
        SeleniumDemo d1 = new SeleniumDemo();
        wd=d1.createDriver();
        wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        d1.openUrlUsingNavigate("https://demo.evershop.io/account/login");
        d1.fillText("//input[@name='email']","priyakanwar6140@gmail.com");
        d1.fillText("//input[@name='password']","Priya@123");
        d1.click("//button[@type='submit']");
        d1.click("//a/span[text()='Swift run x shoes']");
        String itemCode=d1.getText("//div[contains(@class,'product-single-sku')]");
        System.out.println("Item code is:"+itemCode);
        Thread.sleep(2000);
        d1.click("//ul[contains(@class,'variant-option-list')][1]//a[text()='S']");
        Thread.sleep(2000);
        d1.click("(//ul[contains(@class,'variant-option-list')])[2]//a[text()='Red']");
        Thread.sleep(2000);

        d1.click("//button[@type='button']/span[text()='ADD TO CART']");
        Thread.sleep(1000);
//        d1.click("//a[@class='mini-cart-icon']");
        d1.openUrlUsingNavigate("https://demo.evershop.io/cart");
        List<WebElement> rows= wd.findElements(By.xpath("//tbody/tr"));
        for(WebElement row: rows){
            System.out.println(row.findElement(By.xpath("td[1]//div[@class='cart-tem-info']/a")).getText());
            System.out.println(row.findElement(By.xpath("td[2]/div/span")).getText());
            System.out.println(row.findElement(By.xpath("td[3]/span")).getText());
            System.out.println(row.findElement(By.xpath("td[4]/span")).getText());
        }
        int colIndex=d1.getColumnIndex("Quantity");
        System.out.println("col Position is:"+colIndex);
        int rowIndex=d1.getRowIndex("Swift run x shoes","Red");
        System.out.println("row Position is:"+rowIndex);
        d1.deletItem(rowIndex);
        String qty=d1.getQuantity(

                d1.getRowIndex("Swift run x shoes","Black"),d1.getColumnIndex("Quantity")
        );
        System.out.println(qty);
        d1.click("//a[@class='button primary']/span[text()='CHECKOUT']");
//approch 1 for dropdown

//        Select countryDD=new Select(wd.findElement(By.xpath("//select[contains(@id,'country')]")));
//        countryDD.selectByValue("IN");
        //approch 2:
        d1.click("//select[contains(@id,'country')]");
        List<WebElement> ddValues=wd.findElements(By.xpath("//select[contains(@id,'country')]/option"));
        String valueTobeSlected="KR";
        for(WebElement ele:ddValues){
            if(ele.getAttribute("value").equalsIgnoreCase(valueTobeSlected)){
                ele.click();
                break;
            }
        }
        d1.click("//select[contains(@id,'country')]");




    }
    public void deletItem(int rowPosition){
        wd.findElement(By.xpath("//tbody/tr["+rowPosition+"]/td[1]//a[contains(@class,'text-textSubdued')]")).click();
    }
    public int getColumnIndex(String ColumnName){
        int tolColumnCount=wd.findElements(By.xpath("//thead/tr/td")).size();
        int columnPosition=-1;
        for(int i=1;i<=tolColumnCount;i++) {
            if (wd.findElement(By.xpath("//thead/tr/td[" + i + "]/span")).getText().equalsIgnoreCase(ColumnName)) {

                columnPosition=i;
                break;
            }
        }
        return columnPosition;
    }
    public int getRowIndex(String itemName, String color) {
        int rowPosition = 1;
        List<WebElement> rows = wd.findElements(By.xpath("//tbody/tr"));
        for (int i = 1; i <= rows.size(); i++) {
            if (wd.findElement(By.xpath("//tbody/tr[" + i + "]/td[1]//div[contains(@class,'cart-tem-info')]/a")).getText().equalsIgnoreCase(itemName)
                    &&
                    wd.findElement(By.xpath("//tbody/tr[" + i + "]/td[1]//div[contains(@class,'cart-item-variant-options')]//li[2]/span[2]")).getText().equalsIgnoreCase(color))
            {
                rowPosition = i;
                break;
            }


        }
        return rowPosition;

    }
    public String getQuantity(int rowpos, int colpos){
        return wd.findElement(By.xpath(
                "//tbody/tr["+rowpos+"]/td["+colpos+"]"
        )).getText();

    }
    public String getText(String locator){
        return wd.findElement(By.xpath(locator)).getText();
    }

    public static void fillText(String locator, String text) {
        wd.findElement(By.xpath(locator)).sendKeys(text);

    }
    public void click(String locator){
        wd.findElement(By.xpath(locator)).click();

    }
    public WebDriver createDriver() {
        WebDriver wd1 =new ChromeDriver();
        return wd1;

    }
    public void openUrlUsingNavigate(String URL){
        wd.navigate().to(URL);
    }

}
