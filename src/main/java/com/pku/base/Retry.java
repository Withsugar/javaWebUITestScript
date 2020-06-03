package com.pku.base;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;


/*
 * 增加失败重跑机制
 */
public class Retry implements IRetryAnalyzer {

    private int retryCount = 0;
    //读取配置文件的重跑次数
    private static int maxRetryCount = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            TestBase.info("运行失败，第"+retryCount+"次，重试。。。");
            String message = "running retry for '" + result.getName() + "' on class " +

                    this.getClass().getName() + " Retrying " + retryCount + " times";
            TestBase.info(message);
            result.setStatus(ITestResult.FAILURE);
            Reporter.setCurrentTestResult(result);
            Reporter.log("RunCount=" + (retryCount + 1));
            TestBase.info("RunCount=" + (retryCount + 1));
            retryCount++;
            return true;
        }
        return false;
    }

}
