package com.ron.weixin.ftp;

import java.io.File;

import org.apache.log4j.Logger;

import com.ron.weixin.pereference.SystemGlobals;

import  it.sauronsoftware.ftp4j.*;

public class FtpDownloadClienter{
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
    	log.info("Try disconnecting...");
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
        String tmpRFN[] = remoteFileName.split("_");
        log.debug(SystemGlobals.getProvince(tmpRFN[1]));
        
        try {
            client.changeDirectory(remoteFolderPath);
            File file = new File(remoteFolderPath + "/" + remoteFileName);
            if(file.exists()){
            	log.info(remoteFolderPath + "/" + remoteFileName +": " + client.fileSize(remoteFolderPath + "/" + remoteFileName));
            	file.delete();
            }
            int start = Integer.parseInt(SystemGlobals.getProvince(tmpRFN[1]));
            client.download(remoteFileName, new File( localFolderPath + File.separator + new File(remoteFileName).getName()), start, new MyTransferListener());
            SystemGlobals.setProvince(tmpRFN[1], Integer.toString((SystemGlobals.getFileLength() + start)));
        } catch (FTPException e) {
            e.printStackTrace();
        	if(e.getCode() == 550){
        		System.out.println("File not found : " + remoteFileName  );
        	}
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        	log.info("Downloaded: " + remoteFolderPath + "/" + remoteFileName);
        }
    }
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		log.debug("Ftp startting...");
		
		String ftpHost = "192.168.202.13";       
		int ftpPort = 21;
		String ftpUser = "jiangsu";
		String ftpPasswd = "password";
		
		FtpDownloadClienter myFtp = new FtpDownloadClienter(ftpHost, ftpPort, ftpUser, ftpPasswd);
		try{
			myFtp.openConnection();
			FtpThread ftpThread = new FtpThread(myFtp);
			for(int i = 0; i <= 0; i++){
				Thread t = new Thread(ftpThread);
				t.start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
//			myFtp.closeConnection();
		}
	}
    
}
