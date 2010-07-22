package sockets;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    
    OutputStream out;
    InputStream in;


    public static void main( String[] args ) throws IOException {

	Server server = new Server();
	String ibuf;
	boolean more;
	int i=-1, port = 0;
	
	// create socket to listen on specified port
	ServerSocket serverSocket = null;
	try {
	    serverSocket = new ServerSocket( 55555 );
	} 
	catch ( IOException iox ) {
	    System.err.println( "could not listen on port: " + port );
	    System.exit( 1 );
	}

	System.out.println( "host="+serverSocket.getInetAddress() );
	//System.out.println( "port="+serverSocket.getLocalPort() );
	System.out.println( "port=55555" );
	
	// accept connection from client
	Socket clientSocket = null;
	try {
	    clientSocket = serverSocket.accept();
	    // open streams on socket
	    server.out = clientSocket.getOutputStream();
	    server.in = clientSocket.getInputStream();
	} 
	catch ( IOException iox ) {
	    System.err.println( "accept failed." );
	    System.exit( 1 );
	}
	System.out.println( "accepted a client!" );
	
	// loop while reading/writing on the socket
	more = true;
	while ( more ) {
	    try {
		if (( ibuf = server.readMsg()) == null ) {
		    more = false;
		}
		else if ( ibuf.length() > 0 ) {
		    if ( ibuf.charAt( 0 ) == '\0' ) {
			more = false;
		    }
		    else {
			//server.sendMsg( "okay" );
		    server.sendMsg( "print" );
		    }
		}
	    }
	    catch ( IOException iox ) {
		System.out.println( "server: " + iox );
		more = false;
	    }
	}

	// close everything
	server.out.close();
	server.in.close();
	clientSocket.close();
	serverSocket.close();
  
    } /* end of main() */
  

    void sendMsg( String msg ) throws IOException {
	byte i = (byte)msg.length();
	System.out.println( "server: sending message [" + msg + "] " + i );
	out.write( i );
	for ( int j=0; j<i; j++ ) {
	    out.write( (int)msg.charAt( j ));
	}
    } /* end of sendMsg() */


    String readMsg() throws IOException {
	String msg = null;
	int i = -1;
	if (( i = in.read()) != -1 ) {
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
	if (( msg != null ) && ( i > 0 ) && ( msg.charAt(0) != '\0' )) {
	    System.out.println( "server: read message [" + msg + "]" );
	}
	return( msg );
    } /* end of readMsg() */




} /* end of Server class */
