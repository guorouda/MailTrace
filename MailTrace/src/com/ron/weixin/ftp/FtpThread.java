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
	
//	private String provincefiletype[][] = {{"anhui", "0"}, {"beijing", "0"}, {"chongqing", "0"}, {"fujian", "1"}, {"gansu", "0"}, {"guangdong", "1"}, {"guangxi", "0"}, {"guizhou", "0"}, {"hainan", "0"}, {"hebei", "0"}, {"heilongjiang", "0"}, {"henan", "0"}, {"hubei", "0"}, {"hunan", "0"}, {"jiangsu", "1"}, {"jiangxi", "0"}, {"jilin", "0"}, {"liaoning", "0"}, {"neimenggu", "0"}, {"ningxia", "0"}, {"qinghai", "0"}, {"shandong", "0"}, {"shang3xi", "0"}, {"shanghai", "0"}, {"shanxi", "0"}, {"sichuan", "0"}, {"tianjin", "0"}, {"xinjiang", "0"}, {"xizang", "1"}, {"yunnan", "0"}, {"zhejiang", "0"}};
	
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
       
        String[] filecodes = SystemGlobals.getDefaultsValue("file.code").split(",");
        String[] provincecodes = SystemGlobals.getDefaultsValue("province.code").split(",");
        String[] provincecode = provincecodes[x].split(":");
        
        String remoteFolderPath = "/home/" + provincecode[0].trim();
        for(int i=0; i <= filecodes.length - 1; i++){
        	log.info( filecodes.length + ":" + "filecode:" + filecodes[i]);
	        String remoteFileName = null;    
           	log.info(dfH.format(d) + ":" +  SystemGlobals.getDaySwitch(filecodes[i], provincecode[1].trim()));
           	if((Integer.parseInt(dfH.format(d)) - Integer.parseInt(SystemGlobals.getDaySwitch(filecodes[i], provincecode[1])) < 0)){
			       remoteFileName = filecodes[i].trim() + "_" + provincecode[1] + "_" + df.format(d.getTime() - 24 * 60 * 60 * 1000) + ".dat";    
			       ftp.download(remoteFolderPath, remoteFileName, SystemGlobals.getDefaultsValue("download.path"), dfH.format(d.getTime() - 24 * 60 * 1000), dfm.format(d.getTime() - 24 * 60 * 1000));
			       SystemGlobals.setProvinceFile(filecodes[i], provincecode[1], "0");
            }
	        remoteFileName = filecodes[i].trim() + "_" + provincecode[1] + "_" +df.format(d)+".dat";    
	        ftp.download(remoteFolderPath, remoteFileName, SystemGlobals.getDefaultsValue("download.path"), dfH.format(d), dfm.format(d));
  	        SystemGlobals.setDaySwitch(filecodes[i],  provincecode[1] , dfH.format(d));
	        SystemGlobals.resetFileLength();
//	        log.debug(x + ":" + remoteFolderPath + " : " + remoteFileName);
	        String content = Util.SearchFileByName(remoteFileName);
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
