package com.pku.base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class LogBase {
	
	/**
	 * 
	 * @param result HashMap 类型，key 为ID号，Value为 id,className,methodName,starTime,executionTime,logName 的数组 
	 * @param reportName 生成报告名称
	 * 
	 * */
	public void writeLog(HashMap<String, String[]> result,String reportName){
		try {
			//读取log模板
			File input = new File(System.getProperty("user.dir")+"\\log\\seleniumReportModel.html");
			Document doc = Jsoup.parse(input, "UTF-8");
			//设置title
			setTitle(doc,reportName);
			//插入总数信息
			writeTotalInfo(result,doc);
			//插入详细信息
			writeDetailInfo(result,doc);
			//生成文件
			SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
			String ip = java.net.InetAddress.getLocalHost().getHostAddress();
			String fileName = df.format(new Date()).toString();
			//生成log
			SimpleDateFormat log= new SimpleDateFormat("HHmmss");
			String logName = log.format(new Date()).toString();
//			fileName = System.getProperty("user.dir")+"\\log\\"+fileName+"\\"+ip+"\\"+reportName+logName+".html";
			fileName = System.getProperty("user.dir")+"\\reports\\"+reportName+".html";
			File tmp = new File(fileName);
			if(!tmp.getParentFile().exists()){tmp.getParentFile().mkdirs();}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(tmp),"UTF-8"));
			writer.write(doc.html());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param result HashMap 类型，key 为ID号，Value为 id,className,methodName,starTime,executionTime,logName 的数组 
	 * @param doc  Document
	 * */
	public void writeTotalInfo(HashMap<String, String[]> result,Document doc){
		Elements total = doc.select("#total");
		int passed= getAccountByType(result,"通过");
		int failed= getAccountByType(result,"失败");
		int skipped= getAccountByType(result,"跳过");
		int totalNumber = result.size();
		DecimalFormat df = new DecimalFormat("0.00");
		double pR=0;
		if(totalNumber!=0){
			pR= Double.valueOf(passed)/Double.valueOf(totalNumber)*100;
		}
		String passedRate=String.valueOf(df.format(pR));
		String element="<tr>"
				+"<td>"+String.valueOf(totalNumber)+"</td>"
				+"<td style='color:blue;'>"+String.valueOf(passed)+"</td>"
				+"<td style='color:red;'>"+String.valueOf(failed)+"</td>"
				+"<td style='color:green;'>"+String.valueOf(skipped)+"</td>"
				+"<td>"+passedRate+"%"+"</td>"
				+"</tr>";
		total.append(element);
	}
	
	
	/**
	 * 
	 * @param result HashMap 类型，key 为ID号，Value为id,className,methodName,starTime,executionTime,logName 数组 
	 * @param type  case 执行结果状态
	 * */
	public int getAccountByType(HashMap<String, String[]> result,String type){
		int account = 0;
		for (int i = 1; i <= result.size(); i++) {
			if(result.get(String.valueOf(i))[3].equals(type)){
				account ++;
			}
		}
		return account;
	}
	
	/**
	 * 
	 * @param result HashMap 类型，key 为ID号，Value为带id,className,methodName,starTime,executionTime,logName 数组 
	 * @param doc  Document
	 * */
	public void writeDetailInfo(HashMap<String, String[]> result,Document doc){
		Elements detail = doc.select("#detail");
		for (int i = 1; i <= result.size(); i++) {
			String[] al = result.get(String.valueOf(i));
			String element="<tr";
			if(result.get(String.valueOf(i))[3].equals("通过")){
				element = "<tr bgcolor='Azure'>";
			}else if(result.get(String.valueOf(i))[3].equals("失败")){
				element = "<tr bgcolor='#FFF0FF'>";
			}else{
				element = "<tr bgcolor=LightGreen>";
			}
			
			/*for (int j = 0; j <= al.length; j++) {
				if(j==al.length-1){
					String ip = "";
					try {
						ip = java.net.InetAddress.getLocalHost().getHostAddress();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					element = element + "<td>" + ip + "</td>";
				}else if(j==al.length){
					element = element + "<td><a href='.//"+al[j-1]+"'>" + al[j-1] + "</a></td>";
				}else{
					element = element + "<td>" + al[j] + "</td>";
				}
			}*/
			for (int j = 0; j <= al.length; j++) {
				if(j==al.length-2){
					String ip = "";
					try {
						ip = java.net.InetAddress.getLocalHost().getHostAddress();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					element = element + "<td>" + ip + "</td>";
				}else if(j==al.length-1){
					element = element + "<td><a href='.//"+al[j-1]+"'>" + al[j-1] + "</a></td>";
				}else if(j==al.length){
					element = element + "<td><a href='../../../screenshot/"+al[j-1]+ ".jpg"+"'>" + al[j-1] + "</a></td>";
				}else{
					element = element + "<td>" + al[j] + "</td>";
				}
			}
			element = element + "</tr>";
			detail.append(element);
		}
	}

	public void setTitle(Document doc,String reportName){
		Element title = doc.select("title").first();
		title.text(reportName);
		Element h1 = doc.select("h1").first();
		h1.text(reportName);
	}
}