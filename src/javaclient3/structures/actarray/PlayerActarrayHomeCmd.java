/*
 *  Player Java Client 3 - PlayerActarrayHomeCmd.java
 *  Copyright (C) 2006 Radu Bogdan Rusu
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * $Id: PlayerActarrayHomeCmd.java 90 2010-05-02 18:09:04Z corot $
 *
 */

package javaclient3.structures.actarray;

import javaclient3.structures.PlayerConstants;

/**
 * Command: Joint home (PLAYER_ACTARRAY_HOME_CMD)
 * Tells a joint (or the whole array) to go to home position. 
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v2.0 - Player 2.0 supported
 * </ul>
 */
public class PlayerActarrayHomeCmd implements PlayerConstants {

    // The joint to command - set to -1 to command all. 
    private char joint;


    /**
     * @return  The joint to command - set to -1 to command all. 
     **/
    public synchronized char getJoint () {
        return this.joint;
    }

    /**
     * @param newJoint  The joint to command - set to -1 to command all. 
     *
     */
    public synchronized void setJoint (char newJoint) {
        this.joint = newJoint;
    }

}