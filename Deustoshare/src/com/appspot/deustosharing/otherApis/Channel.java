package com.appspot.deustosharing.otherApis;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class Channel {
	
	public String getChannelKey(String userEmail){
		return userEmail;
	}
	
	public boolean sendUpdateToUser(String userEmail, String message){
		if(userEmail!=null){
			try{
				ChannelService channelServce= ChannelServiceFactory.getChannelService();
				String channelKey=getChannelKey(userEmail);
				channelServce.sendMessage(new ChannelMessage(channelKey, message));
				return true;
			}catch(Exception e){
				return false;
			}
			
		}else{
			return false;
		}
	}
	
	public String generateToken(String userEmail){
		String token=null;
		ChannelService channelService=ChannelServiceFactory.getChannelService();
		token=channelService.createChannel(this.getChannelKey(userEmail));
		return token;
	}
	

}
