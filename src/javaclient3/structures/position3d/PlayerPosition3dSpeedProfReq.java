/*
 *  Player Java Client 3 - PlayerPosition3dSpeedProfReq.java
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
 * $Id: PlayerPosition3dSpeedProfReq.java 90 2010-05-02 18:09:04Z corot $
 *
 */

package javaclient3.structures.position3d;

import javaclient3.structures.PlayerConstants;

/**
 * Request/reply: Set speed profile parameters.
 *  
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v2.0 - Player 2.0 supported
 * </ul>
 */
public class PlayerPosition3dSpeedProfReq implements PlayerConstants {

    // max speed [rad/s] 
    private float speed;
    // max acceleration [rad/s^2] 
    private float acc;


    /**
     * @return  max speed [rad/s] 
     **/
    public synchronized float getSpeed () {
        return this.speed;
    }

    /**
     * @param newSpeed  max speed [rad/s] 
     *
     */
    public synchronized void setSpeed (float newSpeed) {
        this.speed = newSpeed;
    }
    /**
     * @return  max acceleration [rad/s^2] 
     **/
    public synchronized float getAcc () {
        return this.acc;
    }

    /**
     * @param newAcc  max acceleration [rad/s^2] 
     *
     */
    public synchronized void setAcc (float newAcc) {
        this.acc = newAcc;
    }

}