package com.ron.ftp.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class FtpJob implements Job {
	private static Logger log = Logger.getLogger(FtpJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		log.info("Hello!  HelloJob is executing.");
	}

}
