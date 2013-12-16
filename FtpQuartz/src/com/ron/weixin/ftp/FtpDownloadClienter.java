package com.ron.weixin.ftp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ron.weixin.pereference.SystemGlobals;
import com.ron.weixin.pereference.Util;

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
        log.debug("已下载: " + SystemGlobals.getProvinceFile(i, tmpRFN[1]));
        
        Date d=new Date(); 
        SimpleDateFormat dfH = new SimpleDateFormat("HH");   
        SimpleDateFormat dfm = new SimpleDateFormat("mm");   
        
        try {
           	client.changeDirectory(remoteFolderPath + "/" + tmpRFN[2]);
           	log.info(remoteFolderPath + "/" + tmpRFN[2]);
           	
            if(dfm.format(d).equals("44")){
            	SystemGlobals.setProvinceGetFile(i, tmpRFN[1], "1");
            }
            
//            log.debug(SystemGlobals.getProvinceGetFile(i, tmpRFN[1]));
            int start = Integer.parseInt(SystemGlobals.getProvinceFile(i, tmpRFN[1]));
            if("1".equals(SystemGlobals.getProvinceGetFile(i, tmpRFN[1]))){
            	String HH = Util.TimeRound(dfH.format(d), dfm.format(d));            			
            	String ArchiveFile = tmpRFN[0] + "_" + tmpRFN[1] + "_" + tmpRFN[2] + HH + ".dat";
            	
	        	List<String> fileNames = listFiles(".");
	            String[] tempFileNames = new String[fileNames.size()];
	            fileNames.toArray(tempFileNames);
	            Arrays.sort(tempFileNames);
            	
	            if(Arrays.binarySearch(tempFileNames, ArchiveFile) >= 0){
	            	log.info("已检查, 切换了!");
		            client.download(ArchiveFile, new File( localFolderPath + File.separator + new File(remoteFileName).getName()), start, new MyTransferListener());
	            	File f =  new File("d:\\temp" + File.separator + remoteFileName);
	            	f.renameTo(new File("d:\\temp" + File.separator + ArchiveFile));
		            SystemGlobals.setProvinceFile(i, tmpRFN[1], "0");
	            	SystemGlobals.setProvinceGetFile(i, tmpRFN[1], "0");
	            }else{
	            	log.info("已检查,未切换!");
		            client.download(remoteFileName, new File( localFolderPath + File.separator + new File(remoteFileName).getName()), start, new MyTransferListener());
		            SystemGlobals.setProvinceFile(i, tmpRFN[1], Integer.toString((SystemGlobals.getFileLength() + start)));
	            }
	        }else{
            	log.info("不需检查!");
		        client.download(remoteFileName, new File( localFolderPath + File.separator + new File(remoteFileName).getName()), start, new MyTransferListener());
		        SystemGlobals.setProvinceFile(i, tmpRFN[1], Integer.toString((SystemGlobals.getFileLength() + start)));
	        }
            
            
            File file = new File("d:\\temp\\" + remoteFileName);
            if(file.exists()){
            	log.debug(remoteFolderPath + "/" + tmpRFN[2] + "/" + remoteFileName +": " + client.fileSize(remoteFolderPath + "/" + tmpRFN[2] + "/" + remoteFileName));
  //          	file.delete();
            }
            
        } catch (FTPException e) {
            e.printStackTrace();
        	if(e.getCode() == 550){
        		System.out.println("File not found : " + remoteFileName  );
        	}
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        	log.info("Downloaded: " + remoteFolderPath + "/" + tmpRFN[2] + "/" + remoteFileName);
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
