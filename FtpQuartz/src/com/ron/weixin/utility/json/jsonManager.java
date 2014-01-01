/**
 * 
 */
package com.ron.weixin.utility.json;



/**
 * @author suansuan
 *
 */
public class jsonManager {
	public jsonNode doParse(Object object){
		jsonNode node=new jsonNode() ;
		JSONObject json = (JSONObject) object;
		
		try{
			if(json.has("ErrMsg")){
				String[] tem=json.getString("ErrMsg").split("&token=");
				node.sz=tem[1];			
			}
		} catch(Exception e){
		}
		
		return node;
	}
	
}
