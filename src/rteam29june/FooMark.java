/**
 * Foo class
 *
 * foo client skeleton in Java
 *
 * sklar/28-june-2010
 *
 */
package rteam29june;



public class FooMark {

	CommClient comm;


	/**
	 * main()
	 *
	 *
	 * This function handles the main processing of the skeleton foo client.
	 * The program follows this state machine:
	 *                                                    +---------------------------------------+
	 *                                                    |                                       |
	 *                                                    v                         /--read-pong--+
	 * --start-> [INIT] --send-init-> [ACK] --read-ack-> [PING] --send-ping-> [PONG]
	 *                                                    ^                         \--read-move--+
	 *                                                    |                                       |
	 *                                                    +--send-moving----- [MOVING] <----------+
	 * @throws Exception 
	 *
	 *
	 */
	public static void main( String[] args ) throws Exception {

		// instantiate this class
		FooMark foo = new FooMark();

		/*
	// parse command-line arguments
	if ( args.length < 2 ) {
	    System.out.println( "usage: ./java Foo <host> <port> <owner> <type> <name> [max_ping]\n" );
	    System.exit( 1 );
	}
	String host = args[0];
	int port = ( Integer.valueOf( args[1] )).intValue();
	String owner = args[2];
	String type = args[3];
	String name = args[4];
	int max_ping = -1;
	if ( args.length > 5 ) {
	    max_ping = ( Integer.valueOf( args[5] )).intValue();
	}
		 */
		// create client socket on port...
		//String host = "146.245.176.108"; //mark
		//String host = "146.245.176.144"; //mark
		//String host = "localhost";
		//String host = "192.168.2.2"; //mark Westin
		String host = "192.168.1.3"; //mark Westin

		int port = 6667;
		try {
			foo.comm = new CommClient( true, host, port );
		}
		catch( Exception x ) {
			System.out.println( "CommClient exception: " + x );
			System.exit( 1 );
		}

		RobotMark robot = new RobotMark(foo.comm);
		Loiter loiter = null;
		robot.setBehavior(loiter);
		
		while (robot.GetState() != State.STATE_QUIT) {
			// Update Player interfaces.
			//pc.ReadIfWaiting();
			//if (robot.IsRegistered() && !robot.IsLocked()) {
			// TODO: add autonomous behavior here.
			//}

			// Update the robot.
			robot.Update();

			// Take a quick breath.
			//sleep(1);
		}
		
		if (robot.GetState() == State.STATE_QUIT)
		{
			foo.comm.close();
		}

	} // end of main()


} // end of Foo class
