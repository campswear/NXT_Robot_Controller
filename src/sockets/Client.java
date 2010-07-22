package sockets;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

	OutputStream out      = null;
	InputStream  in       = null;


	public static void main( String[] args ) throws IOException {

		Client client = new Client();
		
		Foo robot = new Foo();
		System.out.print( "FooFooFooFoo " );

		String ibuf, obuf;
		boolean more;
		int i=-1;
		String host = "0.0.0.0";
		int port = 55555;
		//String host = args[0];
		//int port = ( Integer.valueOf( args[1] )).intValue();
		//String host = "146.245.176.139"; //Joel's server  
		//int port = 6667;
		Socket mySocket = null;

		try {
			// make connection to server socket
			mySocket = new Socket( host,port );
			client.out = mySocket.getOutputStream();
			client.in  = mySocket.getInputStream();
		}
		catch ( UnknownHostException uhx ) {
			System.err.println( uhx );
			System.exit( 1 );
		} 
		catch ( IOException iox ) {
			System.err.println( iox );
			System.exit( 1 );
		}

		// open stream to read user's input
		DataInputStream stdin = new DataInputStream( System.in );

		// loop while reading/writing on the socket
		more = true;
		while ( more ) {
			System.out.print( "enter message to send (q to quit): " );
			obuf = stdin.readLine();
			if ( obuf.charAt( 0 ) == 'q' ) {
				more = false;
			}	    		
			else {
				client.sendMsg( obuf );
				System.out.println( "client: waiting for msg from server" );
				if (( ibuf = client.readMsg()) == null ) {
					more = false;
				}
				else
				{
					if (ibuf.equals("print"))
						System.out.println( "client got msg: print" );
						robot.print_ident();
				}
			}
		}

		// close everything
		client.out.close();
		client.in.close();
		stdin.close();
		mySocket.close();

	} /* end of main() */



	void sendMsg( String msg ) throws IOException {
		byte i = (byte)msg.length();
		System.out.println( "client: sending message [" + msg + "] " + i );
		out.write( i );
		for ( int j=0; j<i; j++ ) {
			out.write( (int)msg.charAt( j ));
		}
	} /* end of sendMsg() */


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
		if ( msg != null ) {
			System.out.println( "client: read message [" + msg + "]" );
		}
		return( msg );
	} /* end of readMsg() */


} /* end of Client class */
