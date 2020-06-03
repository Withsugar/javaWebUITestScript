package com.pku.testcases;

import com.pku.base.Retry;
import com.pku.base.TestBase;
import com.pku.data.LoginDataProvider;
import com.pku.page.HomePage;
import com.pku.page.LoginPage;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;


public class TestLogin extends TestBase {

    @Feature("登录反用例测试")
    @Test(priority = 0,dataProvider = "loginFailData",dataProviderClass = LoginDataProvider.class)
    public void testLoginFail(String userName,String password,String excepted){
        String actualMessage="";
        info("打开首页");
        HomePage homePage=open(config.getProperty("webSite"), HomePage.class);
        info("点击登录按钮，跳转至登录页面");
        homePage.loginBtn();

        info("开始测试登录反向用例");
        page(LoginPage.class).login(userName,password);
        delay(2);
        // 反向登录断言
        if("".equals(userName.trim())){
            actualMessage=page(LoginPage.class).getAccountErrorMessage();
        }else{
            actualMessage=page(LoginPage.class).getErrorMessage();
        }
        Assert.assertEquals(excepted,actualMessage);

    }

    @Feature("登录测试")
    @Test(priority = 1,dataProvider = "loginData",dataProviderClass = LoginDataProvider.class)
    public void testLogin(String userName,String password,String excepted){

        info("打开首页");
        HomePage homePage=open(config.getProperty("webSite"), HomePage.class);
        info("点击登录按钮，跳转至登录页面");
        homePage.loginBtn();

        info("开始登录");
        page(LoginPage.class).login(userName,password);
        Assert.assertEquals(excepted,page(LoginPage.class).getAccountMessage());

        info("退出登录");
        page(LoginPage.class).logout();
        //断言
        Assert.assertEquals("登1录",page(HomePage.class).getLoginBtnName());
        info("退出断言成功");
    }

//    下面的方式在jenkins上会导致构建不稳定，顾修改回上面的方式
//    @Feature("退出测试")
//    @Test(priority = 2,dependsOnMethods = {"testLogin"})
//    public void testLogout(String userName,String password,String excepted){
//
//        info("退出登录");
//        page(LoginPage.class).logout();
//        //断言
//        Assert.assertEquals("登录",page(HomePage.class).getLoginBtnName());
//        info("退出断言成功");
//    }


}
