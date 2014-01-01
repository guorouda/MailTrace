package com.ron.ftp.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ron.weixin.ftp.FtpDownloadClienter;
import com.ron.weixin.ftp.FtpThread;
import com.ron.weixin.ftp.MyFtp;

public class FtpJob implements Job {
	private static Logger log = Logger.getLogger(FtpJob.class);
	
	private String ftpIp = "192.168.202.13";       
	private int ftpPort = 21;
	private String ftpUser = "jiangsu";
	private String ftpPasswd = "password";
	
	FtpDownloadClienter ftp = new FtpDownloadClienter(ftpIp, ftpPort, ftpUser, ftpPasswd);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		log.debug("Ftp startting...");
		
		MyFtp myFtp = new MyFtp();		
		try{
			myFtp.ftp.openConnection();
			FtpThread ftpThread = new FtpThread(myFtp.ftp);
			for(int i = 0; i <= 1; i++){
				Thread t = new Thread(ftpThread);
				t.start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
//			myFtp.ftp.closeConnection();
		}
	}

}
