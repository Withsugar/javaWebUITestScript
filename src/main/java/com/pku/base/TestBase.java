package com.pku.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.ScreenShooter;
import com.pku.page.HomePage;
import com.pku.page.LoginPage;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.assertj.db.type.Source;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;

@Listeners({Listener.class})
public class TestBase {
    public static String logName;
    public static Properties config = null;
    public static Logger logger = null;
    public static String proId = null;
    public static Source source = null;
    public static ArrayList<String> proList = new ArrayList<String>();//存储所有测试过程中创建的项目Id，用于测试后删除项目


    @BeforeSuite
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src//main//resources//chromedriver.exe");
        System.setProperty("selenide.browser", "Chrome");
        Configuration.startMaximized=true;
        ScreenShooter.captureSuccessfulTests=false;//设为true时，不管用例成功失败都截图，false时，只有失败时才会截图
        Configuration.reportsFolder = "screenshot";

        initConfig();
        initLogger();
//        initProject();
//        initDB();
    }

    @AfterSuite(alwaysRun = true)
    public void log() {
        new LogBase().writeLog(Listener.result, config.getProperty("reportName"));
//        info("删除所有测试项目: " + proList);
//		ProjectService.getInstance().deleteAllProject(proList,config);
    }

    @BeforeClass
    public void beforeBaseClass() {
        initCaseLogger();
    }

    public void initCaseLogger() {
        String name = this.getClass().getName();
        String caseNumber = name.substring(name.lastIndexOf(".") + 1, name.length());
        SimpleDateFormat df = new SimpleDateFormat("HHmmss");
        name = caseNumber + "_" + df.format(new Date()).toString() + "Log";
        logName = name;
        logger = Logger.getLogger(this.getClass().getName());
        // BasicConfigurator replaced with PropertyConfigurator.
        PropertyConfigurator.configure("config//log4j.properties");
        initLogConfig(name);
        info("=======================================================");
        info("                    开始测试  " + caseNumber);
        info("=======================================================");

    }

    /**
     * initialize config
     */
    public void initConfig() {
        config = new Properties();
        try {
            InputStreamReader fn = new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "//config//CONFIG.properties"), "UTF-8");
            config.load(fn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initLogger() {
        logger = Logger.getLogger(this.getClass().getName());
        // BasicConfigurator replaced with PropertyConfigurator.
        PropertyConfigurator.configure("config//log4j.properties");
        initLogConfig("BaseLog");
        info("=======================================================");
        info("                    测试开始");
        info("=======================================================");
    }

    public void initProject() {
//        info("初始化自动化测试项目");
//		proId = ProjectService.getInstance().generateProject(config);
//		info("生成的项目ID为: " + proId);
//		proList.add(proId);
//		System.out.println(proList);
    }

    public void initDB() {
        info("初始化数据库连接");
        info("DBURL: " + config.getProperty("DBURL"));
        source = new Source(config.getProperty("DBURL"), config.getProperty("DBUSER"), config.getProperty("DBPASSWORD"));
    }

    /**
     * add logger info
     */
    public static void info(String info) {
        logger.info(info);
    }

    /**
     * initialize LogConfig
     */
    public void initLogConfig(String name) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
        String fileName = df.format(new Date()).toString();
        String ip;
        FileAppender append;
        try {
            ip = java.net.InetAddress.getLocalHost().getHostAddress();
            String lPath = System.getProperty("user.dir")
                    + "//log//" + fileName;
            File tmp = new File(lPath);
            if (!tmp.getAbsoluteFile().exists()) {
                tmp.getAbsoluteFile().mkdirs();
            }
            append = (FileAppender) logger.getRoot().getAppender("File");
            String logPath = lPath + "//" + name;
            append.setFile(logPath);
            append.activateOptions();
        } catch (Exception e) {
        }
    }

    /**
     * delay thread seconds
     */
    public static void delay(int time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 除登录测试外，其他测试前的登录操作
     */
    public static void login(){
        info("登录");
        HomePage homePage=open(config.getProperty("webSite"), HomePage.class);
        homePage.loginBtn();
        page(LoginPage.class).login(config.getProperty("username"),config.getProperty("password"));
    }
}
