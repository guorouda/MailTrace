package com.ron.weixin.ftp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.ron.weixin.LoginWeixinWEB;
import com.ron.weixin.pereference.Util;
import com.ron.weixin.pereference.SystemGlobals;
import com.ron.weixin.utility.json.JSONException;

public class FtpThread implements Runnable {
	private static Logger log = Logger.getLogger(FtpThread.class);
	private int x = 0;
	
//	private String ftpIp = "192.168.202.13";       
//	private int ftpPort = 21;
//	private String ftpUser = "jiangsu";
//	private String ftpPasswd = "password";
	
//  interfaceCode 必须和con.ron.weixin.preference.SystemGlobals 中的完全相同
	private String interfaceCode[] = {"03003", "03004", "03005", "03016"};
//	private String province[][] = {{"anhui", "34"}, {"beijing", "11"}, {"chongqing", "50"}, {"fujian", "35"}, {"gansu", "62"}, {"guangdong", "44"}, {"guangxi", "45"}, {"guizhou", "52"}, {"hainan", "46"}, {"hebei", "13"}, {"heilongjiang", "23"}, {"henan", "41"}, {"hubei", "42"}, {"hunan", "43"}, {"jiangsu", "32"}, {"jiangxi", "36"}, {"jilin", "22"}, {"liaoning", "21"}, {"neimenggu", "15"}, {"ningxia", "64"}, {"qinghai", "63"}, {"shandong", "37"}, {"shang3xi", "61"}, {"shanghai", "31"}, {"shanxi", "14"}, {"sichuan", "51"}, {"tianjin", "12"}, {"xinjiang", "65"}, {"xizang", "54"}, {"yunnan", "53"}, {"zhejiang", "33"}};
//	private String province[][] = {{"beijing", "11"}, {"jiangsu", "32"}};
	private String province[][] = {{"jiangsu", "32"}};
	
	private FtpDownloadClienter ftp;

	public FtpThread(FtpDownloadClienter ftp) {
		super();
		this.ftp = ftp;
	}

	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
        Date d=new Date();   
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");   
        SimpleDateFormat dfH=new SimpleDateFormat("HH");   
        SimpleDateFormat dfm=new SimpleDateFormat("mm");   
       
       // FtpDownloadClienter ftp = new FtpDownloadClienter( ftpIp, ftpPort, ftpUser, ftpPasswd);
        String remoteFolderPath = "/home/" + province[x][0];
        for(int i=0; i <= interfaceCode.length - 1; i++){
	        String remoteFileName = null;    
           	log.info(dfH.format(d) + ":" +  SystemGlobals.getDaySwitch(i, province[x][1]));
           	if((Integer.parseInt(dfH.format(d)) - Integer.parseInt(SystemGlobals.getDaySwitch(i, province[x][1])) < 0)){
			       remoteFileName = interfaceCode[i] + "_" + province[x][1] + "_" + df.format(d.getTime() - 24 * 60 * 60 * 1000) + ".dat";    
			       ftp.download(i, remoteFolderPath, remoteFileName, "d:\\Temp", dfH.format(d.getTime() - 24 * 60 * 1000), dfm.format(d.getTime() - 24 * 60 * 1000));
			       SystemGlobals.setProvinceFile(i, province[x][1], "0");
            }
	        remoteFileName = interfaceCode[i] + "_" + province[x][1] + "_" +df.format(d)+".dat";    
	        ftp.download(i, remoteFolderPath, remoteFileName, "d:\\Temp", dfH.format(d), dfm.format(d));
  	        SystemGlobals.setDaySwitch(i,  province[x][1] , dfH.format(d));
	        SystemGlobals.resetFileLength();
//	        log.debug(x + ":" + remoteFolderPath + " : " + remoteFileName);
	        String content = Util.SearchFileByName(i, remoteFileName);
			if(  content != null){
				LoginWeixinWEB lww = new LoginWeixinWEB();
				try {
					lww.Login("sunrong@ymail.com", "aaaa1111");
					lww.SendMessage(content, "2386949502");
					lww.SendMessage(content, "970109862");
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        };
        }
        
        x++;
	}

}
