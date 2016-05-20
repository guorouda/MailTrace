package com.ron.ftp.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ron.weixin.ftp.FtpDownloadClienter;
import com.ron.weixin.ftp.FtpThread;
import com.ron.weixin.pereference.SystemGlobals;

public class FtpJob implements Job {
	private static Logger log = Logger.getLogger(FtpJob.class);
	
	FtpDownloadClienter ftp = new FtpDownloadClienter(SystemGlobals.getDefaultsValue("ftp.ip"), 
			Integer.parseInt(SystemGlobals.getDefaultsValue("ftp.port")), 
			SystemGlobals.getDefaultsValue("ftp.user"), 
			SystemGlobals.getDefaultsValue("ftp.passwd"));

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.debug("Ftp startting...");
		
		try{
			this.ftp.openConnection();
			FtpThread ftpThread = new FtpThread(this.ftp);
			for(int i = 0; i <= Integer.parseInt(SystemGlobals.getDefaultsValue("province.count")); i++){
				Thread t = new Thread(ftpThread);
				t.start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
//			this.ftp.closeConnection();
		}
	}

}
