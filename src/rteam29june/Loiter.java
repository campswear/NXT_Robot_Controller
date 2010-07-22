package rteam29june;

import javaclient3.PlayerClient;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.SonarInterface;

public class Loiter
{
	PlayerClient robot = null;
	Position2DInterface p2di  = null;
	SonarInterface sonar  = null;
	RangerInterface	light = null;
	double[] lightValues = null;
	float[] sonarValues = null;
	boolean found;
	boolean myCurrentState;


	public Loiter(PlayerClient robot, Position2DInterface p2di, SonarInterface sonar, RangerInterface light)
	{

		this.p2di = p2di;
		this.robot = robot;
		this.sonar = sonar;
		this.light = light;
		found = false;
		myCurrentState = true;
		this.light.setRangerPower(100);
	}

	void Restart() throws InterruptedException
	{
		p2di.setSpeed(0, 0);
		Update();
	}

	void Stop()
	{
		p2di.setSpeed(0, 0);
	}

	void Update() throws InterruptedException
	{
		p2di.setSpeed(0, 70);
		Thread.sleep(2);
		p2di.setSpeed(0, 0);
		Thread.sleep(2);

		 

		//line boundary
		//p2di.setSpeed(65, 0);

		robot.requestData ();
		robot.readAll ();
		
		//light

		//while(!light.isDataReady()){
			//System.out.println("light not ready");

		//}
		/*
		if(light.isDataReady())
			{
			lightValues = light.getData().getRanges();
			System.out.println("#1 value1 = " + lightValues[0]);
	
			if(lightValues[0]>42) //if hit line, reverse & turn
			{
				p2di.setSpeed(-70,0);
				Thread.sleep(2);
				p2di.setSpeed(0,70);
				Thread.sleep(1);
			}
		}
*/



		//System.out.println("update in loiter");
		//LOITER BEHAVIOR
		//turn until you see something near
		//if see something, move to that object
		//if not then turn at random angle and move forward until you hit a boundary line
		//scan environment for something near again

		/*

		if (myCurrentState)
		{
			found = turnTillFound();
			if (found)
			{
				System.out.println("i found something, approaching it");
				p2di.setSpeed(70, 0);
				do
				{
					doReadValues();
				}
				while (sonarValues[2]>25);

				if (sonarValues[2]<=25)
				{
					System.out.println("IM NEAR OBJECT!");
					found=false;
					myCurrentState=false;
					Stop();
				}
		 */
		
	}
	//}


	//}
/*
	boolean turnTillFound()
	{

		p2di.setSpeed(0, 60);
		do
		{
			System.out.println("trying to find something");
			doReadValues();
		}
		while (sonarValues[2]>50);

		if (sonarValues[2]<=50)
		{
			p2di.setSpeed(0, 0);
			return true;
		}
		else
			return false;


	}

	void doReadValues()
	{
		robot.requestData ();
		robot.readAll ();

		while(!sonar.isDataReady()){
		}
		sonarValues = sonar.getData().getRanges();
		while(!light.isDataReady()){
		}
		lightValues = light.getData().getRanges();

		System.out.println("#1 value1 = " + sonarValues[0] + " value2 = " + sonarValues[1] + " value3 = " + sonarValues[2]);

		System.out.println("#1 value1 = " + lightValues[0]);
	}
	
	*/

}


