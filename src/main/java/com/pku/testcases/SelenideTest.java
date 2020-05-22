package com.pku.testcases;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SelenideTest {

    @Test
    public void TestBaidu() throws InterruptedException {
        Configuration.browser = "Chrome";
        Configuration.baseUrl="https://www.baidu.com";
        open("/");
        $("#kw").setValue("selenide");
        $("#su").click();
        Thread.sleep(3000);
        // 断言
        $$("h3 > a").shouldHave(size(9));
        $("h3 > a").setValue(String.valueOf(text("selenide_百度翻译")));
    }

}
