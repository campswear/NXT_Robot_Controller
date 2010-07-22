/**
 * State.java
 *
 * sklar/28-june-2010
 *
 */
package rteam29june;

public class State {
    
 // Client/Server States
    public static final int STATE_INIT           =   0;
    public static final int STATE_ACK            =   1;
    public static final int STATE_IDLE           =   2;
    public static final int STATE_CMD_PROC       =   3;
    public static final int STATE_CMD_BAD        =   4;
    public static final int STATE_PING_SEND      =   5;
    public static final int STATE_PING_READ      =   6;
    public static final int STATE_PONG_SEND      =   7;
    public static final int STATE_PONG_READ      =   8;
    public static final int STATE_QUIT           =   9;
    public static final int STATE_ERROR         =   10;
    public static final int STATE_MOVING        =   11;
    public static final int STATE_POSE          =   12;
    public static final int STATE_GUI_WAIT      =   13;


} // end of class State
