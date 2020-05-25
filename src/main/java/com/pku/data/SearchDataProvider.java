package com.pku.data;

import org.testng.annotations.DataProvider;

public class SearchDataProvider {
    @DataProvider(name = "loginFailData")
    public static Object[][] loginFailData() {
        return new Object[][]{
                {"wgd", "","请输入密码！"},
                {"","xiaochao11520","请输入用户名！"},
                {"linux","xiaochao","用户名或密码错误。"},
        };
    }

    @DataProvider(name = "loginData")
    public static Object[][] loginData() {
        return new Object[][]{
                {"wgd","zhfb2019","wgd"},
        };
    }

}