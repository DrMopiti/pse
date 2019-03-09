package helmig.daniel.test;

import android.app.IntentService;
import android.content.Intent;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketService extends IntentService{

	public enum State {CONNECTED, DISCONNECTED}

	public static State state = State.DISCONNECTED;

	private static final String url = "ws://sdq-pse-gruppe1.ipd.kit.edu/server/";

	private WebSocketClient mWs;

	private final String user = "Testuser";

	public WebsocketService() {
		super("WSService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		while (true) {
			if (state == State.DISCONNECTED) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				connectToWS();
			}
		}
	}

	private static String getUrl() {
		return url;
	}

	public static void setState(State stateset) {
		state = stateset;
	}

	public void connectToWS() {
		try {
			System.out.println("Bitconnect");
			URI uri = new URI(getUrl()+"socket");
			System.out.println(uri);
			mWs = new WSClient(uri, new Draft_6455(), user);
			mWs.connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}



}
