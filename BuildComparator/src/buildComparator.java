import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class buildComparator {
	
	static WebDriver driver ;
	
    static ArrayList<String> env1 = new ArrayList<String>();
    
    //Name of the file to open.
    static String fileName = "C:\\temp\\Builds.txt";
    // static String bamboo = "http://192.168.22.175:8085";
    static String bamboo = "http://172.21.92.175:8085";
    
    //Environments to be compared
  	static String[] environment = {"AWS_Test01","AWS_Test02","AWS_Test03","AWS SprintDemo","AWS PQA","PQA Dev"};
    
    /**
	 * Extracts all the required build numbers of the given environment. A text file pops up once the execution is done.
	 * 
	 * @author - Varadharaju 
	 *  
	 */
	public static void main(String[] args) throws IOException, InterruptedException  {
		
/*		System.setProperty("webdriver.chrome.driver", "D:\\Softwares\\chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();*/
		
		System.setProperty("webdriver.chrome.driver", "D:\\Softwares\\chromedriver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu","--ignore-certificate-errors");  
		driver = new ChromeDriver(options);
			
		driver.get(bamboo +"/userlogin!default.action?os_destination=%2FallPlans.action");
		driver.manage().window().maximize();
		
		long startTime = System.currentTimeMillis();
		
		driver.findElement(By.id("loginForm_os_username")).sendKeys("vsoundararajan");
		driver.findElement(By.id("loginForm_os_password")).sendKeys("Welcome123");
		driver.findElement(By.id("loginForm_save")).click();
		
		//clear the file first
		PrintWriter writer = new PrintWriter(fileName);
		writer.print("");
		writer.close();
		
		/*//Environments to be compared
		String[] environment = {"AWS_Test01","AWS_Test02","AWS_Test03","AWS Sprint Demo","AWS PQA","PQA Dev"};*/
		
		//UI Main
		driver.get(bamboo + "/browse/NEW-SB/deployments");		
		//buildNumber(driver, "UI Main", environment);
		buildNumber(driver, "UI Main");
		
		Thread.sleep(1000);
		
		//UI Admin
		driver.get(bamboo + "/browse/NEW-SBA/deployments");		
		//buildNumber(driver, "UI Admin", environment);
		buildNumber(driver, "UI Admin");
		
	/*	//Config App
		driver.get(bamboo + "/browse/NEW-TCO/deployments");		
		buildNumber(driver, "Config App", environment);
		
		//Identity App
		driver.get(bamboo + "/browse/NEW-TES/deployments");		
		buildNumber(driver, "Identity App", environment);
		
		//Personalisation App
		driver.get(bamboo + "/browse/NEW-TPE/deployments");		
		buildNumber(driver, "Personalisation App", environment);

		//Reporting App
		driver.get(bamboo + "/browse/NEW-TREP/deployments");		
		buildNumber(driver, "Reporting App", environment);

		//Config DB
		driver.get(bamboo + "/browse/NEW-AACD/deployments");		
		buildNumber(driver, "Config DB", environment);

		//Identity DB
		driver.get(bamboo + "/browse/NEW-AMID/deployments");		
		buildNumber(driver,"Identity DB", environment);

		//Personalisation DB
		driver.get(bamboo + "/browse/NEW-AMPD/deployments");		
		buildNumber(driver,"Personalisation DB", environment);

		//Reporting DB
		driver.get(bamboo + "/browse/NEW-AMRED/deployments");		
		buildNumber(driver,"Reporting DB", environment);

		//Refdata DB
		driver.get(bamboo + "/browse/NEW-AMRD/deployments");		
		buildNumber(driver, "Refdata DB", environment);*/

		long endTime   = System.currentTimeMillis();
	
		driver.quit();
		
     // Create a ProcessBuilder object
        ProcessBuilder pb=new ProcessBuilder("notepad",fileName);
     // Start opening the file
        pb.start();
	}
    
	public static void buildNumber(WebDriver driver, String project)
	{
		String buildNum;	
		
        try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(fileName,true);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

			for(int i=0; i<environment.length ; i ++)
			{
				buildNum = driver.findElement(
						By.xpath("//a[text()= '"+ environment[i] +"']/parent::td/following-sibling::td[1]")).getText();
				env1.add("| " + environment[i] + " | "+ buildNum +" |");
			}
			
			bufferedWriter.newLine();
			bufferedWriter.write("|****** "+ project + " ******|");
			bufferedWriter.newLine();
			bufferedWriter.write("| Environment | Build #|");
			for(String s : env1)
			{
				bufferedWriter.newLine();
	            bufferedWriter.write(s);
	            
			}
			bufferedWriter.newLine();
			bufferedWriter.write(" ");
			
			env1.clear();
            
            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
        }
		buildNum = "";
	}
}
