package rteam29june;

import javaclient3.PlayerClient;
import javaclient3.PlayerException;
import javaclient3.Position2DInterface;
import javaclient3.RangerInterface;
import javaclient3.SonarInterface;
import javaclient3.structures.PlayerConstants;

public class testLoiter {

	PlayerClient robot = null;
	Position2DInterface p2di  = null;
	SonarInterface sonar  = null;
	RangerInterface	light = null;
	double[] lightValues = null;
	float[] sonarValues = null;
	boolean found;
	boolean myCurrentState;

	public testLoiter()
	{

		try {
			robot = new PlayerClient                 ("localhost", 6665);
			light = robot.requestInterfaceRanger (0,PlayerConstants.PLAYER_OPEN_MODE);
			sonar  = robot.requestInterfaceSonar      (0, PlayerConstants.PLAYER_OPEN_MODE);

		} catch (PlayerException e) {
			System.err.println ("Javaclient test: > Error connecting to Player: ");
			System.err.println ("    [ " + e.toString() + " ]");
			System.exit (1);
		}
		robot.runThreaded (-1, -1);

		found = false;
		myCurrentState = true;
		light.setRangerPower(100);
	}

void Update() throws InterruptedException
{
	System.out.println("update in loiter");
	//LOITER BEHAVIOR
	//turn until you see something near
	//if see something, move to that object
	//if not then turn at random angle and move forward until you hit a boundary line
	//scan environment for something near again




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


		}
	}


}
boolean turnTillFound()
{

	p2di.setSpeed(0, 70);
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



void Restart() throws InterruptedException
{
	p2di.setSpeed(0, 0);
	Update();
}

void Stop()
{
	p2di.setSpeed(0, 0);
}

public static void main(String[] args) throws InterruptedException {
	//System.setProperty ("PlayerClient.debug", "true");
	testLoiter loiter = new testLoiter();
	loiter.Update();

	//robot.close ();
}
}
