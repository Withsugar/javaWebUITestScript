package com.pku.testcases;

import com.pku.base.TestBase;
import com.pku.data.StaticProvider;
import com.pku.page.HomePage;
import com.pku.page.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;


public class TestLogin extends TestBase {

    @Test(description = "登录反用例测试",dataProvider = "loginFailData",dataProviderClass = StaticProvider.class)
    public void testLoginFail(String userName,String password,String excepted){
        String actualMessage="";
        info("===开始打印参数==============");
        info("userName="+userName+"==========="+"password="+password+"========="+"excepted="+excepted+"=========");
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
        info("actualMessage=="+actualMessage);
        Assert.assertEquals(excepted,actualMessage);

    }

    @Test(description = "登录退出测试",dataProvider = "loginData",dataProviderClass = StaticProvider.class)
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
        Assert.assertEquals("登录",homePage.getLoginBtnName());
        info("退出断言成功");


    }

}
