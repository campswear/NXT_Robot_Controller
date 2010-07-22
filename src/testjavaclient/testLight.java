package testjavaclient;

import javaclient3.PlayerClient;
import javaclient3.PlayerException;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.SonarInterface;
import javaclient3.structures.PlayerConstants;


public class testLight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.setProperty ("PlayerClient.debug", "true");

		PlayerClient        robot = null;
		Position2DInterface p2di  = null;

		SonarInterface      soni  = null;

		RangerInterface	light = null;

		try {
			robot = new PlayerClient                 ("localhost", 6665);
			light = robot.requestInterfaceRanger (0,PlayerConstants.PLAYER_OPEN_MODE);
			soni  = robot.requestInterfaceSonar      (0, PlayerConstants.PLAYER_OPEN_MODE);

		} catch (PlayerException e) {
			System.err.println ("Javaclient test: > Error connecting to Player: ");
			System.err.println ("    [ " + e.toString() + " ]");
			System.exit (1);
		}

		robot.runThreaded (-1, -1);

		light.setRangerPower(100);
		double[] lightValues = null;
		float[] sonarValues;

		while (true)
		{
			robot.requestData ();
			robot.readAll ();
			//light

			while(!soni.isDataReady()){
			}
			sonarValues = soni.getData().getRanges();
			System.out.println("#1 value1 = " + sonarValues[0] + " value2 = " + sonarValues[1] + " value3 = " + sonarValues[2]);
			while(!light.isDataReady()){
			}
			lightValues = light.getData().getRanges();
			System.out.println("#1 value1 = " + lightValues[0]);
		}

		//robot.close ();
	}
}
