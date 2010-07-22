package testjavaclient;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javaclient3.BumperInterface;
import javaclient3.PlayerClient;
import javaclient3.PlayerException;
import javaclient3.Position2DInterface;
import javaclient3.SonarInterface;
import javaclient3.structures.PlayerConstants;

import javax.imageio.ImageIO;


public class testLinda {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.setProperty ("PlayerClient.debug", "true");

		PlayerClient        robot = null;
		Position2DInterface p2di  = null;
		SonarInterface      soni  = null;
		BumperInterface	bumpie = null;

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

		int i = 0;
		int width = 200, height = 80;
		int x1=0, y1=0;
		int x2=0, y2=40;
		boolean start=false;

		float[] sonarValues;

		int motor;
		int turning;
		int cont=-1;
		Scanner scan = new Scanner(System.in);


		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D ig2 = bi.createGraphics();

		ig2.setPaint(Color.red);
		//try { Thread.sleep (2000); } catch (Exception e) { e.printStackTrace(); }

		robot.runThreaded (-1, -1);
		//		robot.setNotThreaded ();

		String filename="";
		String string="";
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input);

		//images are taken from CARPET IN LAB- speed 65
		//IMAGES IN GEORGIA CARPET, 62
		System.out.print("enter motor power: ");
		motor = scan.nextInt();
		System.out.print("enter turning power: ");
		turning = scan.nextInt();
		p2di.setSpeed(motor, turning);

		while (true)
		{
			robot.requestData ();
			robot.readAll ();
			//sonar
			while(!soni.isDataReady()){
				//	System.out.println("Sonar Not Ready1");
			}
			sonarValues = soni.getData().getRanges();
			//System.out.print("SONAR: length = " + sonarValues.length);
			System.out.println("#1 value1 = " + sonarValues[0] + " value2 = " + sonarValues[1]);

			if(sonarValues[0]<15 || sonarValues[1]<15)
				start=true;

			while(start==true)
			{
				robot.requestData ();
				robot.readAll ();
				while(!soni.isDataReady()){
					//System.out.println("Sonar Not Ready2");
				}
				sonarValues = soni.getData().getRanges();

				System.out.println("#inloop value1 = " + sonarValues[0] + " value2 = " + sonarValues[1]);
				//try { Thread.sleep (100); } catch (Exception e) { e.printStackTrace(); }


				if(sonarValues[1] < 15)
				{
					ig2.setPaint(Color.red);
					ig2.fill(new Rectangle2D.Double(x1, y1, 20, 40));
				}
				if (sonarValues[1]>15 && sonarValues[1]<30)
				{
					ig2.setPaint(Color.yellow);
					ig2.fill(new Rectangle2D.Double(x1, y1, 20, 40));
				}
				if(sonarValues[1]>30)
				{
					ig2.setPaint(Color.green);
					ig2.fill(new Rectangle2D.Double(x1, y1, 20, 40));
				}

				if(sonarValues[0] < 15)
				{
					ig2.setPaint(Color.blue);
					ig2.fill(new Rectangle2D.Double(x2, y2, 20, 40));
				}
				if (sonarValues[0]>15 && sonarValues[0]<30)
				{
					ig2.setPaint(Color.yellow);
					ig2.fill(new Rectangle2D.Double(x2, y2, 20, 40));
				}
				if(sonarValues[0]>30)
				{
					ig2.setPaint(Color.green);
					ig2.fill(new Rectangle2D.Double(x2, y2, 20, 40));
				}

				x1+=20;
				x2+=20;

				if(sonarValues[0]>60 && sonarValues[1]>60)
				{
					ig2.setPaint(Color.white);
					ig2.fill(new Rectangle2D.Double(x2, y2, 20, 40));
					
					p2di.setSpeed(0, 0);
					System.out.println(" end");

					System.out.println("Enter filename");
					string="";

					try
					{

						string = reader.readLine(); 

					}

					catch(Exception e){}
					// read in user input 
					filename="/home/linda/Desktop/images/testImage/";
					filename=filename.concat(string);
					filename=filename.concat(".JPG");

					try {
						ImageIO.write(bi, "JPEG", new File(filename));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.print("cont? 0=no, 1=yes ");
					cont = scan.nextInt();
					if(cont==0)
					{
						//p2di.setSpeed(0, 0);
						break;
					}
					else
					{
						System.out.print("enter motor power: ");
						motor = scan.nextInt();
						System.out.print("enter turning power: ");
						turning = scan.nextInt();
						p2di.setSpeed(motor, turning);
						x1=0;
						x2=0;
						start=false;
					}

				}
				//bumper
				/*
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
			}

			//heartbeat
			if(i % 10 == 1){
				p2di.setSpeed(0, 0);
				System.out.println("heartbeat");
			}
			try { Thread.sleep (100); } catch (Exception e) { e.printStackTrace(); }
			i++;

			if (i == 1000)	// modify -1 to a number if you wish to terminate
				// after X iterations
				break;
				 */

			}
			if (cont==0)
			{

				ImageRecognition recog = new ImageRecognition(filename);
				recog.recognize();

				break;
			}

		}

		robot.close ();
	}
}
