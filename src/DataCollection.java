import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.Group;
import facebook4j.User;
import facebook4j.api.GroupMethods;
import facebook4j.auth.AccessToken;


public class DataCollection {
	Facebook facebook;
	public final static String appId = "849954301692852" ;
	public final static String appSecret = "314300e72b833d1868979d3a635e6bb5";
	public final static String  commaSeparetedPermissions= "public_profile, email, user_friends, user_groups";
	public final static String accessToken = "dfc3ca91703971e2c9ecdccdfbfb29a3";

	List<String> groupdIds;
	BufferedWriter bf;

	public static void main(String[] args) throws Exception{
		DataCollection dc = new DataCollection();
		dc.connectFB();
		dc.getAllGroups();
		dc.bf.close();
		//dc.getFBGroupFeed("297145383629241");

	}

	public DataCollection() throws IOException{
		groupdIds = new ArrayList();
		bf = new BufferedWriter(new FileWriter(new File("/home/niki/Desktop/Student Connect/Data Collection/FBData.txt")));

	}


	public void connectFB() throws FacebookException{
		facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(appId, appSecret);
		facebook.setOAuthPermissions(commaSeparetedPermissions);
		//facebook.setOAuthAccessToken(facebook.getOAuthAppAccessToken());
		facebook.setOAuthAccessToken(new AccessToken("CAAMFB2g6S7QBAEDgXq6ra4nQ6MbzzMJMNCGywQqx06nSfxVZCq0GAOg55a5ZBzUfdcZAkbMo8WS6F4gy5ZBampO2yZBKVI5Kg53jfZA3b1tcmvqcOxZANqXInV0yW0ZCvFUHjs4voMR880us31ndTIOSYlCoBmVNphPvBpVabVWERuu1vZCEwOdSKYpYs4t7ALAeAG2ZCbyqpjdAs7rA7PvTxS"));
		//facebook.setOAuthAccessToken(new AccessToken("CAAMFB2g6S7QBAEKDHfZBs17zuz20f5IofTDxoLEKlbZA9IR3gzdj0yMGoZBk9iamqROdCZCj4Jw8zgop0ZB1aXAZA254FPOV5ZA8RWjey6ZCD2PMDHGvbdDIHHp70uDyCZCQ0rKVYjy8jTv8ZCVJdovMaQo8EoCroEEQZCocWPVf2J9tZCyeShpq5zaY4Juoa98jyffnoGLfazYnSiH0EZBHYeZB30"));
	}


	public void getAllGroups(){
		try{
			if(facebook!=null){
				ResponseList<Group> results = facebook.searchGroups("Admits", new Reading().limit(100));
				//System.out.println("here");
				try{
					for(Group g: results){
						getFBGroupFeed(g.getId());
					}
				}catch(Exception e){
					System.out.println("Exception in group " );
				}
				try{	
					results = facebook.searchGroups("MS in US", new Reading().limit(100));
					//System.out.println("here");
					for(Group g: results){
						getFBGroupFeed(g.getId());
					}
				}catch(Exception e){
					System.out.println("Exception in group " );
				}

			}
		}catch(Exception e){
			System.out.println("Exception occurred in get groups " + e);
		}

	}

	public void getFBGroupFeed(String groupID) throws IOException{
		try{
			if(facebook!=null){
				ResponseList<Post> feed = facebook.getGroupFeed(groupID, new Reading().limit(2000));
				//System.out.println("here");
				for(Post post: feed){
					//System.out.println(post.toString().replaceAll("\n",""));
					bf.write(post.toString().replaceAll("\n", " ") + "\n");
				}
				System.out.println("Done " + groupID);
				bf.flush();
			}
		}catch(Exception e){
			System.out.println("Exception occurred " + e);
		}




	}
}
