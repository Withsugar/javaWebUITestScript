package com.pku.base;

import io.qameta.allure.Attachment;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


public class Listener extends TestListenerAdapter {
	public static String locator = null;
	public static String type = null;
	public static HashMap<String, String[]> result = new HashMap<String, String[]>();
	private int number;
	public String jpgName;

	@Override
	public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
		SimpleDateFormat df = new SimpleDateFormat("MMddHHmmss");
		jpgName = tr.getName()+"_"+df.format(new Date()).toString();
		//收集log
		setLog(tr,"失败");

		TestBase.info("=======================================================");
		TestBase.info("测试方法 "+tr.getName()+" 执行失败, 请参考");
		TestBase.info("失败截图参照 screenshot 文件夹中的 " +jpgName+".jpg ");
		TestBase.info("失败原因为："+tr.getThrowable().getMessage());
		TestBase.info("=======================================================");
	}

    /**
     * 打印测试步骤
     * @param tr
     */
    @Attachment(value = "操作步骤如下：")
    public String logCaseStep(ITestResult tr){
        String step = "1、打开浏览器  2、输入百度地址";
        return step;
    }

    /**
     * 打印测试步骤
     * @param tr
     */
    @Attachment(value = "期望结果如下：")
    public String exceptedResult(ITestResult tr){
        String result = "显示查询结果";
        return result;
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
    	super.onTestSkipped(tr);
    	//收集log
    	jpgName = null;
    	setLog(tr,"跳过");
		TestBase.info("=======================================================");
		TestBase.info("测试方法 "+tr.getName()+" 跳过");
		TestBase.info("=======================================================");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
    	super.onTestSuccess(tr);
    	//收集log
    	jpgName = null;
    	setLog(tr,"通过");
		TestBase.info("=======================================================");
		TestBase.info("测试方法 "+tr.getName()+" 执行成功！");
		TestBase.info("=======================================================");
    }

    @Override
	public void onFinish(ITestContext context) {
		Iterator<ITestResult> listOfFailedTests = context.getFailedTests().getAllResults().iterator();
        while (listOfFailedTests.hasNext()) {
            ITestResult failedTest = listOfFailedTests.next();
            ITestNGMethod method = failedTest.getMethod();
            if (context.getFailedTests().getResults(method).size() > 1) {
                listOfFailedTests.remove();
            } else {
                if (context.getPassedTests().getResults(method).size() > 0) {
                    listOfFailedTests.remove();
                }
            }
        }
	}
    public void setLog(ITestResult tr, String state){
    	String id = String.valueOf(++ number);
    	String className = tr.getTestClass().getName();
        String methodName = tr.getName();
        String startTime = getStartTime(tr);
        String totalTime = getTotalTime(tr);
        String screenshotName = jpgName;
        if (screenshotName == null) {
        	screenshotName = " ";
        }
    	//String[] array = new String[]{id,className,methodName,state,startTime,totalTime,TestBase.logName};
    	String[] array = new String[]{id,className,methodName,state,startTime,totalTime,TestBase.logName,screenshotName};
    	result.put(id, array);
    	//jpgName = null;
    }

    private String getTotalTime(ITestResult tr) {
        long start=0, stop=0;
            long tmp_start=tr.getStartMillis(), tmp_stop=tr.getEndMillis();
            if(start == 0)
                start=tmp_start;
            else {
                start=Math.min(start, tmp_start);
            }

            if(stop == 0)
                stop=tmp_stop;
            else {
                stop=Math.max(stop, tmp_stop);
            }
        return String.valueOf(stop - start);
    }

    private String getStartTime(ITestResult tr){
        Date date = new Date(tr.getStartMillis());
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        String startTime = sdformat.format(date);
        return startTime;
    }
}

