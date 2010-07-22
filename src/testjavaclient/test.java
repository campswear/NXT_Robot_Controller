package testjavaclient;

import java.util.Vector;

import javaclient3.PlayerClient;
import javaclient3.PlayerException;
import javaclient3.Position2DInterface;
import javaclient3.SonarInterface;
import javaclient3.structures.PlayerConstants;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.setProperty ("PlayerClient.debug", "true");
		
		PlayerClient        robot = null;
		Position2DInterface p2di  = null;
		SonarInterface      soni  = null;
		//BumperInterface	bumpie = null;
		//PlayerDevAddr devAddr = new PlayerDevAddr();
		//robot.requestDriverInfo(devAddr);
		//LogInterface        logi  = null;
		
		try {
			robot = new PlayerClient                 ("localhost", 6665);
			p2di  = robot.requestInterfacePosition2D (0, PlayerConstants.PLAYER_OPEN_MODE);
			soni  = robot.requestInterfaceSonar      (0, PlayerConstants.PLAYER_OPEN_MODE);
			//bumpie = robot.requestInterfaceBumper    (0,PlayerConstants.PLAYER_OPEN_MODE);
			
		} catch (PlayerException e) {
			System.err.println ("Javaclient test: > Error connecting to Player: ");
			System.err.println ("    [ " + e.toString() + " ]");
			System.exit (1);
		}
		
		Vector provides = new Vector();
		Vector index = new Vector();
		String name ="a";
		provides = robot.provides;
		index = robot.index;
		name = robot.name;
		System.out.println(" name ="+name);
		System.out.println("provides "+provides.toString());
		System.out.println("indexes "+index.toString());
		System.out.println("robot name ="+name);
		
		int i = 0;
		// --[ Test Log
		//logi.setFileName ("test.txt");
		// --]

		//try { Thread.sleep (2000); } catch (Exception e) { e.printStackTrace(); }
		
		robot.runThreaded (-1, -1);
//		robot.setNotThreaded ();
		
		// --[ Test Position2D
		p2di.setSpeed(0, 0);
		// --]
		
		while (true)
		{
			robot.requestData ();
			robot.readAll ();
			p2di.setSpeed(0, 0);
			
//sonar
			while(!soni.isDataReady()){
				//System.out.println("Sonar Not Ready");
			}
			float[] sonarValues = soni.getData().getRanges();
			System.out.print("SONAR: length = " + sonarValues.length);
			System.out.println(" value = " + sonarValues[0]);
			if(sonarValues[0] < 10){
				//p2di.setSpeed(100, 0);
			}/*
//bumper
			while(!bumpie.isDataReady()){
				//System.out.println("bumper Not Ready");
			}
			byte[] bumperValues = bumpie.getData().getBumpers();
			System.out.print("BUMPER: length = " + bumperValues.length);
			System.out.print(" value = ");
			for(int j = 0; j < bumperValues.length;j++){
				System.out.print(j+"="+bumperValues[j]+", ");
			}
			System.out.println();
			if(bumperValues[0] < 10){
				//p2di.setSpeed(100, 0);
			}*/
//heartbeat
			if(i % 10 == 1){
				//p2di.setSpeed(70, 0);
				//System.out.println("heartbeat");
			}
			try { Thread.sleep (100); } catch (Exception e) { e.printStackTrace(); }
			i++;

			if (i == 100)	// modify -1 to a number if you wish to terminate
			        	// after X iterations
				break;
		}
		robot.close ();

	}

}