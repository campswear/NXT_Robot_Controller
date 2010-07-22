/**
 * Cmd.java
 *
 * sklar/28-june-2010
 *
 */

package rteam29june;
public class Cmd {

	// Client/Server Commands
	public static final String CMD_ERROR	="ERROR";   // <--> ERROR <message>
	public static final String CMD_INIT	 	="INIT";    // ---> INIT <type> <name> <num-provides> [<provides-list>]
	public static final String CMD_ACK		="ACK";     // <--- ACK <id>
	public static final String CMD_PING		="PING";    // <--> PING
	public static final String CMD_PONG		="PONG";    // <--> PONG
	public static final String CMD_WAIT		="WAIT";    // <--> WAIT (maybe not used -- use ERROR instead?)
	public static final String CMD_QUIT	    ="QUIT";    // <--> QUIT
	public static final String CMD_MOVE	    ="MOVE";    // <--- MOVE <id> <x-vel> <y-vel> <a-vel>
	public static final String CMD_MOVING 	="MOVING";  // ---> MOVING
	public static final String CMD_STATE    ="STATE";   // <--- STATE <id> (currently replaced by ASKPOSE?)
	public static final String CMD_ASKPOSE  ="ASKPOSE"; // <--- ASKPOSE <id>
	public static final String CMD_POSE     ="POSE";    // ---> POSE <x-pos> <y-pos> <a-pos> <confidence>
	public static final String CMD_LOCK     ="LOCK";    // <--- LOCK
	public static final String CMD_UNLOCK   ="UNLOCK";  // <--- UNLOCK
	public static final String CMD_SNAP     ="SNAP";    // <--- SNAP <id> (request an image)
	public static final String CMD_IMAGE    ="IMAGE";   // ---> IMAGE <image-data>
	public static final String CMD_IDENT    ="IDENT";   // IDENT <num-robots> [<robot_id> <name> <type> <num-provides> <provides>]

	// Client Species Types
	public static final String SID_AIBO         ="aibo";
	public static final String SID_SURVEYOR     ="surveyor";
	public static final String SID_SCRIBBLER    ="scribbler";
	public static final String SID_NXT          ="nxt";
	public static final String SID_GUI          ="gui";
	

	// Unique Client Names
	public static final String UID_SURVEYOR_SRV10    ="srv10";
	public static final String UID_AIBO_GROWL        ="growl";
	public static final String UID_AIBO_BETSY        ="betsy";
	public static final String UID_GUI_PABLO         ="pablo";
	public static final String UID_NXT_IDK		     ="idk";

} // end of Cmd class
