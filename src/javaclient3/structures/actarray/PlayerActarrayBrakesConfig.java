/*
 *  Player Java Client 3 - PlayerActarrayBrakesConfig.java
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
 * $Id: PlayerActarrayBrakesConfig.java 90 2010-05-02 18:09:04Z corot $
 *
 */

package javaclient3.structures.actarray;

import javaclient3.structures.PlayerConstants;

/**
 * Request/reply: Brakes.
 * Send a PLAYER_ACTARRAY_BRAKES_REQ request to turn the brakes of all
 * actuators in the array that have them on or off. Null response.
 * @author Radu Bogdan Rusu
 * @version
 * <ul>
 *      <li>v2.0 - Player 2.0 supported
 * </ul>
 */
public class PlayerActarrayBrakesConfig implements PlayerConstants {

    // Brakes setting; 0 for off, 1 for on. 
    private byte value;


    /**
     * @return  Brakes setting; 0 for off, 1 for on. 
     **/
    public synchronized byte getValue () {
        return this.value;
    }

    /**
     * @param newValue  Brakes setting; 0 for off, 1 for on. 
     *
     */
    public synchronized void setValue (byte newValue) {
        this.value = newValue;
    }

}