/**
 * Robot class
 *
 * gonzalez&sklar/28-june-2010
 *
 */
//http://www.copypastecode.com/31978/

package rteam29june;


import java.io.IOException;
import java.util.Vector;

import javaclient3.PlayerClient;
import javaclient3.PlayerException;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.SonarInterface;
import javaclient3.structures.PlayerConstants;


public class RobotMark {

	// Robot properties.
	private String mTypeID;
	private String mNameID;
	private Vector<String> mProvidesList;
	private int num_provides;
	private String[] cmd;


	// State properties.
	private int  mCurrentState;
	private int mSessionID;
	private boolean mPossessed;

	//player 
	PlayerClient robot = null;
	Position2DInterface p2di  = null;
	SonarInterface sonar  = null;
	RangerInterface	light = null;



	Loiter loiter;

	// Msg
	/*
	private String ibuf;
	private String obuf;
	private OutputStream out = null;
	private InputStream in = null;
	 */
	CommClient comm;

	// note that message queue in robot.cpp is not needed here because that's only for the server


	/**
	 * Robot constructor
	 * @throws IOException 
	 *
	 */
	public RobotMark(CommClient comm) throws IOException {

		this.comm = comm;
		// Initialize robot attributes.
		mTypeID = Cmd.SID_NXT;
		mNameID = Cmd.UID_NXT_IDK;

		// Initialize robot capabilities.
		init_provides();

		// Initialize default state.
		init_state();

		////this.provides = new Vector();
		//this.mypose = null;
	} // end of Robot constructor

	void init_state()
	{
		mCurrentState = State.STATE_INIT;
		mSessionID    = -1;
		mPossessed    = false;
	}

	void init_provides()
	{
		// Prepend function signature to error messages.
		final String signature = "init_provides()";

		try {
			robot = new PlayerClient ("localhost", 6665);
			p2di  = robot.requestInterfacePosition2D (0, PlayerConstants.PLAYER_OPEN_MODE);
			sonar  = robot.requestInterfaceSonar(0, PlayerConstants.PLAYER_OPEN_MODE);
			light = robot.requestInterfaceRanger (0,PlayerConstants.PLAYER_OPEN_MODE);
		} catch (PlayerException e) {
			System.err.println ("Javaclient test: > Error connecting to Player: ");
			System.err.println ("    [ " + e.toString() + " ]");
			System.exit (1);
		}
		mProvidesList = robot.provides;

		System.out.println(signature + mProvidesList.toString());

		String[] result = (this.mProvidesList.toString()).split("\\s");
		num_provides = result.length;
		System.out.println("num provides "+num_provides);
	}

	void do_state_action_init() throws IOException
	{
		// Prepend function signature to error messages.
		final String signature = "do_state_action_init()";

		// Send the INIT command.
		String ss = "";
		ss = ss + Cmd.CMD_INIT + " " + mTypeID + " " + mNameID + " " + mProvidesList.size();
		for (String s: mProvidesList)
		{
			ss+=" "+s;
		}
		System.out.println(ss);
		if (this.comm.sendMsg(ss)) {
			System.out.println( signature + " - success; next state: STATE_ACK" );
			do_state_change(State.STATE_ACK);

			//} else if (mStateTimer.elapsed() >= MAX_TIME_STATE) {
			//	System.out.println( signature + " - timeout; next state: STATE_QUIT" );
			//	do_state_change(State.STATE_QUIT);
		} else {
			System.out.println( signature + " - failure; next state: STATE_INIT" );
			do_state_change(State.STATE_INIT);
		}
	}


	void do_state_change(int state)
	{
		// Don't make false changes.
		if (mCurrentState != state) {
			if (state == State.STATE_INIT) {
				init_state();
			} else {
				mCurrentState = state;
			}
			// Update the state timer.
			//mStateTimer.start();
		}
	}


	void do_state_action_ack() throws IOException
	{
		// Prepend function signature to error messages.
		final String signature = "do_state_action_ack()";

		/*
		// Don't wait forever.
		if (mStateTimer.elapsed() >= MAX_TIME_STATE) {
			System.out.println( signature + " - timeout; next state: STATE_INIT" );
			do_state_change(State.STATE_INIT);
		}

		 */
		// Don't block while waiting for the command.
		if (!msg_waiting()) return;

		// process message
		String read = this.comm.readMsg();
		String[] parts = read.split(" ");
		int session_id = Integer.parseInt(parts[1]);

		if ( (!read.trim().equals("")) && (parts[0].equals(Cmd.CMD_ACK)) ) {
			if (session_id < 0) {
				mSessionID = -1;
				System.out.println( signature + " - rejected; next state: STATE_QUIT" );
				do_state_change(State.STATE_QUIT);
			} else {
				mSessionID = session_id;
				System.out.println( signature + " - accepted; next state: STATE_IDLE" );
				do_state_change(State.STATE_IDLE);
			}
		} else {
			System.out.println( signature + " - failure; next state: STATE_INIT" );
			do_state_change(State.STATE_INIT);
		}
	}



	void do_state_action_idle() throws IOException
	{
		// Prepend function signature to error messages.
		final String signature = "do_state_action_idle()";

		// Keep an eye out for new commands.
		if (msg_waiting()) {
			System.out.println( signature + " - received command; next state: STATE_CMD_PROC" );
			do_state_change(State.STATE_CMD_PROC);
			//} else if (mSilenceTimer.elapsed() >= MAX_TIME_SILENCE) {
			// Don't let the connection die.
			//System.out.println( signature + " - max silence exceeded; next state: STATE_PING_SEND" );
			//do_state_change(State.STATE_PING_SEND);
		}
	}



	boolean msg_waiting() throws IOException
	{
		// Prepend function signature to error messages.
		final String signature = "msg_waiting()";

		if (comm.in.available() > 0) {
			System.out.println( signature + " - msg waiting" );
			return true;
		} else {
			return false;
		}

	}


	void do_state_action_cmd_proc() throws IOException, InterruptedException
	{
		// Prepend function signature to error messages.
		final String signature = "do_state_action_cmd_proc()";

		// Don't block while waiting for the command.
		if (!msg_waiting()) return;

		// Prepare to read the command.
		String ss = this.comm.readMsg();
		this.cmd = ss.split(" ");

		if (!ss.trim().equals("")) {

			// Process the command.
			/*
			if (cmd.find(Cmd.CMD_PING) != String::npos)

			{
				System.out.println( signature + " - PING; next state: STATE_PONG_SEND" );
				do_state_change(State.STATE_PONG_SEND);
			}
			else
			 */
			if (this.cmd[0].equals(Cmd.CMD_LOCK))
			{
				mPossessed = true;
				// Cease behavior.
				if (loiter!=null) {
					loiter.Stop();
				}
				System.out.println( signature + " - LOCK; next state: STATE_IDLE" );
				do_state_change(State.STATE_IDLE);
			}
			else
				if (this.cmd[0].equals(Cmd.CMD_UNLOCK))
				{
					mPossessed = false;
					// Continue behavior.
					if (loiter!=null) {
						loiter.Restart();
					}
					System.out.println( signature + " - UNLOCK; next state: STATE_IDLE" );
					do_state_change(State.STATE_IDLE);
				}


				else
					if (this.cmd[0].equals(Cmd.CMD_ASKPOSE))

					{
						System.out.println( signature + " - ASKPOSE; next state: STATE_POSE" );
						do_state_change(State.STATE_POSE);
					}

					else
						if (this.cmd[0].equals(Cmd.CMD_MOVE))
						{
							System.out.println( signature + " - MOVE; next state: STATE_MOVING" );
							do_state_change(State.STATE_MOVING);
						}


						else
							if (this.cmd[0].equals(Cmd.CMD_QUIT))
							{
								System.out.println( signature + " - QUIT; next state: STATE_QUIT" );
								do_state_change(State.STATE_QUIT);
							}
							else
							{
								System.out.println( signature + " - unrecognized command; next state: STATE_IDLE" );
								do_state_change(State.STATE_IDLE);
							}
		} else {
			System.out.println( signature + " - failure; next state: STATE_IDLE" );
			do_state_change(State.STATE_IDLE);
		}
	}

	void do_state_action_moving() throws IOException
	{
		// Prepend function signature to error messages.
		final String signature = "do_state_action_moving()";

		// Make sure that we're locked.
		if (!mPossessed) {
			String oss = Cmd.CMD_ERROR + " " + Cmd.CMD_MOVE + " failed: not locked";
			this.comm.sendMsg(oss);
			System.out.println( signature + " - failure; next state: STATE_IDLE" );
			do_state_change(State.STATE_IDLE);
		}

		// Make sure that we have the velocity arguments.
		double xv = 0.0, yv = 0.0, av = 0.0;
		xv = Double.parseDouble(cmd[2]);
		yv = Double.parseDouble(cmd[3]);
		av = Double.parseDouble(cmd[4]);

		double xvEdit = 0;
		double avEdit = 0;
		boolean xvNegative = false;
		boolean avNegative = false;

		/*
		0.12
		.24
		.36
		.48
		.60*/

		//go forward
		xvEdit = xv;
		avEdit = av;

		if (xv<0)
		{
			xv*=-1;
			xvNegative = true;
		}

		if (av<0)
		{
			av*=-1;
			avNegative = true;
		}

	
		if (xv > 0 && xv <= .13)
		{
			xv = 60;
		}
		else if (xv > .13 && xv <=.25)
		{
			xv =70;
		}
		else if (xv > .26 && xv <=.37)
		{
			xv =80;
		}
		else if (xv > .38 && xv <=.49)
		{
			xv =90;
		}
		else if (xv > .50)
		{
			xv =100;
		}


		if (av > 0 && av <= 1.2)
		{
			System.out.println("av <=1.2 " + av);

			av = 60;
		}
		else if (av > 1.2 && av <=2.4)
		{
			av =70;
		}
		else if (av > 2.4 && av <=3.6)
		{
			av =80;
		}
		else if (av > 3.6 && av <=4.8)
		{
			av =90;
		}
		else if (av > 4.8)
		{
			System.out.println("av > 4.8 " + av);

			av =100;
		}

		if (xvNegative)
		{
			xv*=-1;
		}

		if (!avNegative)
		{
			av*=-1;
		}

		// Send the command to Player.
		p2di.setSpeed(xv,av);
		System.out.println(" SPEEEEEEEEEEEEEEEEEEEEEEEEEEDddddddddddddddddddddddddddddddddddddddd****        xv "+xv+" av "+av);
		System.out.println(" SPEEEEEEEEEEEEEEEEEEEEEEEEEEDddddddddddddddddddddddddddddddddddddddd****        xv "+xv+" av "+av);
		System.out.println(" SPEEEEEEEEEEEEEEEEEEEEEEEEEEDddddddddddddddddddddddddddddddddddddddd****        xv "+xv+" av "+av);
		// Report success.
		//String oss = Cmd.CMD_MOVING;
		//if (comm.sendMsg(oss)) {
		System.out.println( signature + " - success; next state: STATE_IDLE" );
		//} else {
		//	System.out.println( signature + " - failure; next state: STATE_IDLE" );
		//}
		do_state_change(State.STATE_IDLE);
	}


	void do_state_action_pong_send() throws IOException
	{
		// Prepend function signature to error messages.
		final String signature = "do_state_action_pong_send()";

		// Send the PONG command.
		String oss = "";
		oss = oss + Cmd.CMD_PONG;
		if (comm.sendMsg(oss)) {
			System.out.println( signature + " - success; next state: STATE_IDLE" );
			do_state_change(State.STATE_IDLE);
			//} else if (mStateTimer.elapsed() >= MAX_TIME_STATE) {
			//	System.out.println( signature + " - timeout; next state: STATE_PING_SEND" );
			//	do_state_change(State.STATE_PING_SEND);
		} else {
			System.out.println( signature + " - failure; next state: STATE_PONG_SEND" );
			do_state_change(State.STATE_PONG_SEND);
		}
	}



	void do_state_action_pose() throws IOException
	{
		// Prepend function signature to error messages.
		final String signature = "Robot::do_state_action_pose()";


		// Grab the pose data from the Player server.
		double xp = 5, yp = 5, ap = 0, confidence = 0;

		// Send the pose data to the Central server.
		String oss = Cmd.CMD_POSE + " " + mSessionID + " " + xp + " " + yp + " " + ap + " " + confidence;
		if (this.comm.sendMsg(oss)) {
			System.out.println(" - success; next state: STATE_IDLE");
		} else {
			System.out.println(" - failure; next state: STATE_IDLE");
		}
		do_state_change(State.STATE_IDLE);
	}

	void Update() throws IOException, InterruptedException
	{
		// Prepend function signature to error messages.
		final String signature = "Update()";

		// Maintain the state machine.
		switch (mCurrentState) {
		case State.STATE_INIT: {
			do_state_action_init();
		} break;
		case State.STATE_ACK: {
			do_state_action_ack();
		} break;
		case State.STATE_IDLE: {
			do_state_action_idle();
		} break;
		case State.STATE_CMD_PROC: {
			do_state_action_cmd_proc();
		} break;
		case State.STATE_MOVING: {
			do_state_action_moving();
		} break;
		/*
		 case State.STATE_PING_SEND: {

			do_state_action_ping_send();
		} break;
		case State.STATE_PONG_READ: {
			do_state_action_pong_read();
		} break;
		 */
		case State.STATE_PONG_SEND: {
			do_state_action_pong_send();
		} break;

		case State.STATE_POSE: {
			do_state_action_pose();
		} break;

		default: {
			System.out.println ( signature + " - unrecognized state" );
			do_state_change(State.STATE_QUIT);
		} break;
		}
		// Update behavior.
		if (!mPossessed) {
			loiter.Update();
		}
	}

	void setBehavior(Loiter loiter)
	{
		this.loiter = loiter;
		this.loiter = new Loiter(robot,p2di,sonar,light);
	}


	public int  GetState() 
	{ return mCurrentState; }




} // end of Robot class
