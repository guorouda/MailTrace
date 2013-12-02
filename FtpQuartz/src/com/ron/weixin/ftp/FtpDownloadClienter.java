package com.ron.weixin.ftp;

import java.io.File;

import org.apache.log4j.Logger;

import com.ron.weixin.pereference.SystemGlobals;

import  it.sauronsoftware.ftp4j.*;

public class FtpDownloadClienter{
//	static Log log = LogFactory.getLog(FtpDownloadClienter.class);
	private static Logger log = Logger.getLogger(FtpDownloadClienter.class);
	
	private FTPClient client = new FTPClient();
	   
    private String ftpHost;
    private int ftpPort = 21;
    private String ftpUser;
    private String ftpPasswd;

    public FtpDownloadClienter(String ftpHost, int ftpPort, String ftpUser, String ftpPasswd) {
        super();
        this.ftpHost = ftpHost;
        this.ftpPort = ftpPort;
        this.ftpUser = ftpUser;
        this.ftpPasswd = ftpPasswd;
    }

    public  void openConnection()throws Exception{
        client.connect(ftpHost, ftpPort);
        client.login(ftpUser, ftpPasswd);
        boolean flag =  client.isResumeSupported();
        log.info("flag：" + flag);
    }


    public boolean closeConnection() {
        if ( null != client && client.isConnected()) {
          try {
                client.disconnect(true);
                return true;
                } catch (Exception e) {
                   e.printStackTrace();
                   return false;
                }
        }
        return true;
    }
    
    public  void download(String remoteFolderPath, String remoteFileName, String localFolderPath) {
        try {
            log.info(remoteFolderPath + "/" + remoteFileName +": " + client.fileSize(remoteFolderPath + "/" + remoteFileName));
            client.changeDirectory(remoteFolderPath);
            client.download(remoteFileName, new File( localFolderPath + File.separator + new File(remoteFileName).getName()), SystemGlobals.getFileLength(), new MyTransferListener());
        } catch (FTPException e) {
            e.printStackTrace();
        	if(e.getCode() == 550){
        		System.out.println("File not found : " + remoteFileName  );
        	}
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        }
    }
    
}
