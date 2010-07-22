/**
 * CommClient.java
 *
 * sklar/28-june-2010
 *
 */
package rteam29june;

import java.lang.*;
import java.io.*;
import java.net.*;


public class CommClient {

	private boolean debugging = false;

	private String host;
	private int port;
	private OutputStream out = null;
	Socket mySocket = null;
	InputStream in = null;
	DataOutputStream daout = null;

	/**
	 * CommClient constructor
	 *
	 */
	public CommClient( boolean debugging0, String host0, int port0 ) throws Exception {
		
		this.debugging = debugging0;
		this.host = host0;
		this.port = port0;
		if ( debugging ) {
			System.out.println( "attempting to open socket on [" + host + ":" + port + "]" );
		}
		this.mySocket = new Socket( host,port );
		this.out = mySocket.getOutputStream();
		this.in  = mySocket.getInputStream();
		daout = new DataOutputStream(this.out);
	} // end of CommClient constructor



	/**
	 * sendMsg()
	 *
	 * This function sends its argument "msg" over the socket.
	 * The format is as follows:
	 * First a byte is sent indicating the length of the message.
	 * Then the message is sent, one word (int) at a time.
	 *
	 */
	boolean sendMsg( String message ) throws IOException {
		byte i = (byte)message.length();
		if ( debugging ) {
			System.out.println( "CommClient: sending message [" + message + "] " + i );
		}
		 message = message + '\0';
		 try {
		 daout.writeByte(message.length());
		 daout.write(message.getBytes(), 0, message.length()); 
		 daout.flush();
		 } catch (IOException e) {
		 e.printStackTrace();
		 } 
		 
		
		return true;
	}

	/**
	 * readMsg()
	 *
	 * This function reads a message over the socket and returns the
	 * message read, formatted as a String.
	 * 
	 */
	String readMsg() throws IOException {
		String msg = null;
		int i;
		if (( i = in.read()) > 0 ) {
			msg = new String();
			int k;
			for ( int j=0; j<i; j++ ) {
				if (( k = in.read()) == -1 ) {
					msg = null;
					break;
				}
				msg += (char)k;
			}
		}
		if (( debugging ) && ( msg != null )) {
			System.out.println( "client: read message [" + msg + "]" );
		}
		return( msg );
	} // end of readMsg()


	/**
	 * close()
	 *
	 * This function closes the socket, input and output streams.
	 *
	 */
	public void close() throws Exception {
		this.out.close();
		this.in.close();
		this.mySocket.close();
		this.daout.close();
		this.daout = null;
		this.out = null;
		this.in = null;
		this.mySocket = null;
	} // end of close()



} // end of CommClient class
