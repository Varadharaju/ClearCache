import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import org.openqa.selenium.*;


public class clearCache {
	
	static String AWS_TEST01 = "52.56.239.38:81";
	static String AWS_TEST02 = "52.56.248.120:81";
	static String AWS_TEST03 = "35.178.79.150:81";
	static String AWS_SD = "35.177.186.200:81";	

			
	public static void main(String[] args) throws InterruptedException {
			
		System.setProperty("webdriver.chrome.driver", "D:\\Softwares\\chromedriver\\chromedriver.exe");
		String URL = null;		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu","--ignore-certificate-errors");
		WebDriver driver = new ChromeDriver(options);	
		//WebDriver driver = new ChromeDriver();
				
		//Options for the combo box dialog
		String[] choices = {"AWS TEST01", "AWS TEST02","AWS TEST03","AWS SD"};
		//Input dialog with a combo box
		String picked = (String)JOptionPane.showInputDialog(null, "Environment to Clear Cache:"
		, "Java Melody", JOptionPane.QUESTION_MESSAGE
		, null, choices, choices[0]);
		
		if(picked != null)
		{
			switch (picked) 
			{
				case "AWS TEST01":  				
					 URL = "http://aip_monitoring:0pen5ecret@" + AWS_TEST01;
					 break;
				
				case "AWS TEST02":  				
					 URL = "http://aip_monitoring:0pen5ecret@" + AWS_TEST02;
					 break;
					
				case "AWS TEST03":  				
					 URL = "http://aip_monitoring:0pen5ecret@" + AWS_TEST03;
					 break;
					
				case "AWS SD":  				
					 URL = "http://aip_monitoring:0pen5ecret@" + AWS_SD;
					 break;				
			}
			
			progressBar();
			
			long startTime = System.currentTimeMillis();
			
			//Passing the browser authentication with the URL itself
			/******************** Reporting Data **************/
			driver.get(URL + "/aip/reporting/monitoring?action=clear_caches");
			clearingCahce(20);
			dataclear(driver);
			clearingCahce(40);
			
			driver.get(URL + "/aip/config/monitoring?action=clear_caches");		
			clearingCahce(60);
			dataclear(driver);
			clearingCahce(80);
			
			driver.get(URL + "/aip/refdata/monitoring?action=clear_caches");		
			dataclear(driver);
			clearingCahce(100);
			
			long endTime   = System.currentTimeMillis();
		
			//Close the webdriver instance itself
			driver.quit();
			
			frame.setVisible(false);
			
			NumberFormat formatter = new DecimalFormat("#0.00");
			//Message box
			JOptionPane.showMessageDialog(null, "All Caches Cleared in " + formatter.format((endTime - startTime) / 1000d) + " seconds");
			
			System.exit(0);
			
		}else
		{
			driver.quit();
		}
	}
	
	public static void dataclear(WebDriver driver) throws InterruptedException
	{
		/***** Handle Browser Alert ****/
		try {
			Thread.sleep(500);
			//Alert after clearing the cache
			Alert altConfirm = driver.switchTo().alert();
			Thread.sleep(500);
			altConfirm.accept();	
		}catch(Exception e)
		{
			Thread.sleep(500);
			//Alert after clearing the cache
			/*Alert altConfirm1 = driver.switchTo().alert();
			Thread.sleep(1000);
			altConfirm1.accept();*/	
		}
	}
	
	static JFrame frame = new JFrame("Clearning Cache");
	final static JProgressBar pb = new JProgressBar();
	
	public static void progressBar()
	{
        pb.setMinimum(0);        
        pb.setStringPainted(true);
        
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(pb); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setVisible(true);
        //Center the frame
        frame.setLocationRelativeTo(null);
        
        pb.setValue(0);
	}
	
	public static void clearingCahce(int value)
	{
		pb.setValue(value);
	}
}
