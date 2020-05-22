package com.pku.testcases;

import com.codeborne.selenide.Configuration;
import com.pku.base.TestBase;
import com.pku.page.HomePage;
import com.pku.page.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;

public class TestLogin extends TestBase {

    @Test(description = "使用已存在用户登录")
    public void testLogin(){
        Configuration.browser="Chrome";
        Configuration.startMaximized=true;
        Configuration.screenshots=true;
        info("打开首页");
        HomePage homePage=open(config.getProperty("webSite"), HomePage.class);
        info("点击登录按钮，跳转至登录页面");
        homePage.loginBtn();
//        delay(3);
        page(LoginPage.class).login(config.getProperty("username"),config.getProperty("password"));
        // 断言
        info("断言登录成功");
        Assert.assertEquals(homePage.getAccount(),config.getProperty("username"));
        info("开始退出登录");
        page(LoginPage.class).logout();
        //断言
        Assert.assertEquals("登录",homePage.getLoginBtnName());

    }

}
