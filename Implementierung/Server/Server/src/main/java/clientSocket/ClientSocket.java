package clientSocket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientSocket {
	
	private String url = "127.0.0.1:370";
	private String user = "";
	
	ClientSocket(String user) {
		this.user = user;
	}
	
	private String getUser() {
		return this.user;
	}
	
	private String getUrl() {
		return this.url;
	}
	
	public void connectToWS() {
		
	}
	
	public String requestBoard() {
		String board = "";
		board = connect(getUrl() + "/board/" + getUser());
		return board;		
	}
	
	public String sendMove(String move) {
		String success = "";
		success = connect(getUrl() + "/move/" + getUser() + "/" + move);
		return success;
		
	}
	
	public String newGame(String opponent) {
		String success = "";
		success = connect(getUrl()  +"/newGame/" + getUser() + "/" + opponent);
		return success;
	}
	
	public String connect(String targetURL) {
		HttpURLConnection connection = null;
			try {
				
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("GET");	   	

		    //Send request
		    DataOutputStream wr = new DataOutputStream (
		        connection.getOutputStream());		   
		    wr.close();
	
		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); 
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    return response.toString();
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		  }
		}
		
}
