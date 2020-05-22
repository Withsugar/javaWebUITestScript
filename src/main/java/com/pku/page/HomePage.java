package com.pku.page;


import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class HomePage {

    public void loginBtn() {
        $(By.id("newloginbtn")).click();
    }

    public void registerBtn() {

        $(By.xpath("//a[text()='注册']")).click();
    }

    public String getAccount() {

        return $(By.className("user-name")).getText();
    }

    public String getLoginBtnName() {

        return $(By.id("newloginbtn")).getText();
    }

    public void LogOut() {
        if ($(By.xpath("//span[@id='logOut']")).isDisplayed()) {
            $(By.xpath("//span[@id='logOut']")).click();
            $(By.xpath("//button[text()='确定']")).click();
        }
    }


}
