package com.ron.weixin.ftp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ron.weixin.pereference.SystemGlobals;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class MyTransferListener implements FTPDataTransferListener {
	
	static Log log = LogFactory.getLog(MyTransferListener.class);
	 
	private int totalTransfered = 0;
	
	public void started() {
		// Transfer started
		log.debug("started");
	}
    
	public void transferred(int length) {
		// Yet other length bytes has been transferred since the last time this
		// method was called
		log.debug(length);
		totalTransfered += length;
	}
    
	public void completed() {
		// Transfer completed
		SystemGlobals.setFileLength(this.totalTransfered);
		log.info("completed: " + this.totalTransfered);
		log.info("completed: " + SystemGlobals.getFileLength());
	}
    
	public void aborted() {
		// Transfer aborted
		log.debug("aborted");
	}
    
	public void failed() {
		// Transfer failed
		 log.debug("failed");
	}

}
