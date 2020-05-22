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



    public void logout() {
        $(By.className("exit")).click();
    }

}
