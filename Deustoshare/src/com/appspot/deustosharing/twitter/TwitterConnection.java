package com.appspot.deustosharing.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConnection {
	private final static String CONSUMER_KEY="Ymp7OfwGzsoQb4bVdx5pCw";
	private final static String CONSUMER_SECRET="h9JhCMmTWnDQSsuy3dqLnS04Rpgcpu60LFMnuQoD80";
	private final static String ACCESS_TOKEN="325076908-kHkV92ulGEdVXc6Ak8nL6uivDcXfYFfxNvOPER90";
	private final static String ACCESS_TOKEN_SECRET="GjI3ESBfPIToxXXt4BOpjN1Z5LuiRHg6mkt0aA4K3nk5J";
	
	public static void sendTweet(String message){
		Twitter twitter;
        ConfigurationBuilder cb = new ConfigurationBuilder();
        //Aquí deberéis sustituir vuestras key, secret, token y tokenSecret.
        //Estas son las claves de mi cuenta @SuarezDeveloper
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(CONSUMER_KEY)
            .setOAuthConsumerSecret(CONSUMER_SECRET)
            .setOAuthAccessToken(ACCESS_TOKEN)
            .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        twitter = new TwitterFactory(cb.build()).getInstance();
      //Actualizar tu estado
        try {
			twitter.updateStatus(message);
		} catch (TwitterException e) {
			System.out.println("The tweet wasn't send. "+e.getMessage());
			e.printStackTrace();
		}
        
	}
}
