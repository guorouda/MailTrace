package com.ron.weixin.ftp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        log.debug("flag：" + flag);
    }


    public boolean closeConnection() {
    	log.debug("Try disconnecting...");
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
    
    public  void download(int i, String remoteFolderPath, String remoteFileName, String localFolderPath) {
        String tmpRFN[] = remoteFileName.split("_|\\.");
        log.debug("已下载: " + SystemGlobals.getProvince(i, tmpRFN[1]));
        
        Date d=new Date();   
        SimpleDateFormat dfH=new SimpleDateFormat("yyyyMMddHH");   
        
        try {
            client.changeDirectory(remoteFolderPath);
            
        	List<String> fileNames = listFiles(".");
            String[] tempFileNames = new String[fileNames.size()];
            fileNames.toArray(tempFileNames);
            Arrays.sort(tempFileNames);
            Arrays.binarySearch(tempFileNames, tmpRFN[0] + "_" + tmpRFN[1] + "_" + dfH.format(new Date(d.getTime() + 1000 * 60 * 60)) + ".dat");
            log.debug("NEXT: " + tmpRFN[0] + "_" + tmpRFN[1] + "_" + dfH.format(new Date(d.getTime() + 1000 * 60 * 60)) + ".dat");
            
            File file = new File("d:\\temp\\" + remoteFileName);
            if(file.exists()){
            	log.debug(remoteFolderPath + "/" + remoteFileName +": " + client.fileSize(remoteFolderPath + "/" + remoteFileName));
  //          	file.delete();
            }
            int start = Integer.parseInt(SystemGlobals.getProvince(i, tmpRFN[1]));
            client.download(remoteFileName, new File( localFolderPath + File.separator + new File(remoteFileName).getName()), start, new MyTransferListener());
            SystemGlobals.setProvince(i, tmpRFN[1], Integer.toString((SystemGlobals.getFileLength() + start)));
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
    
	public List<String> listFiles(String path) {
		List<String> fileNameList = new ArrayList<String>();;
		
		try {
			client.changeDirectory(path);
			String[] fileNames = client.listNames();
			for(String fileName : fileNames){
				fileNameList.add(fileName);
			}
		} catch (Exception e) {
		}
		return fileNameList;
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
			//FtpThread ftpThread = new FtpThread(myFtp);
			//for(int i = 0; i <= 0; i++){
			//	Thread t = new Thread(ftpThread);
			//	t.start();
			//}
			myFtp.listFiles("");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
//			myFtp.closeConnection();
		}
	}
    
}
