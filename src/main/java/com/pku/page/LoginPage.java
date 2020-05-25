package com.pku.page;

import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public void login(String username, String password) {
        $(By.id("inputUserName")).clear();
        $(By.id("inputUserName")).setValue(username);
        $(By.id("inputPwd")).clear();
        $(By.id("inputPwd")).setValue(password);
        $(By.id("loginByUserName")).click();
    }


    public static String getErrorMessage(){
        return $(By.id("pwdTips")).getText();
    }

    //用户名为空，点击登录的错误提示信息
    public static String getAccountErrorMessage(){
        return $(By.id("userTips")).getText();
    }

    //登录成功后，展示用户名的信息
    public static String getAccountMessage(){
        return $(By.className("user-name")).getText();
    }

    public void logout() {
        $(By.className("exit")).click();
    }

}
