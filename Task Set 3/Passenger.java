public class Passenger implements Comparable
{
	String firstName,lastName,city;
	int timeDeparture, currentTime;
	
	public Passenger (String name, String givenCity, String givenTimeDeparture, String givenTime)
	{
		String[] nameArray=name.split(" ");
		firstName=nameArray[0];
		lastName=nameArray[1];
		city=givenCity;
		
		String[] deptTimeParse= givenTimeDeparture.split(" ");
		String[] deptTimeValueParse=deptTimeParse[0].split(":");
		int isPM=0;
		if (deptTimeParse[1].equals("PM"))
			isPM=1;
		timeDeparture=(Integer.valueOf(deptTimeValueParse[0])%12)*60+Integer.valueOf(deptTimeValueParse[1])+isPM*60*12;
		
		String[] currentTimeParse= givenTime.split(" ");
		String[] currentTimeValueParse=currentTimeParse[0].split(":");
		isPM=0;
		if (currentTimeParse[1].equals("PM"))
			isPM=1;
		currentTime=(Integer.valueOf(currentTimeValueParse[0])%12)*60+Integer.valueOf(currentTimeValueParse[1])+isPM*60*12;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public String flightCity()
	{
		return city;
	}
	public int flightTime()
	{
		return timeDeparture;
	}
	public int etdCalc()
	{
		if (timeDeparture>currentTime)
			return timeDeparture-currentTime;
		return 0;
	}
	public int compareTo(Object e)
	{
		if (e instanceof Passenger)
		{
			Passenger comparedPass = (Passenger)e;
			if (comparedPass.etdCalc()>60)
			{
				if (this.etdCalc()>60)
					return 0;
				else return -1;
			}
			return this.etdCalc()-comparedPass.etdCalc();
		}
		return 0;
	}
	public String toString()
	{
		String flightTimeString, etdCalcString;
		String[] suffix={"AM","PM"};
		int flightTimeInt, etdCalcInt;
		flightTimeInt=flightTime()%(24*60);
		etdCalcInt=etdCalc()%(24*60);
		if ((flightTimeInt%(12*60))/60!=0)
			flightTimeString=String.valueOf((flightTimeInt%(12*60))/60)+":"+String.valueOf(flightTimeInt%(60))+" "+suffix[flightTimeInt/(12*60)];
		else flightTimeString="12:"+String.valueOf(flightTimeInt%(60))+" "+suffix[flightTimeInt/(12*60)];
		etdCalcString=String.valueOf((etdCalcInt)/60)+" Hours, "+String.valueOf(etdCalcInt%(60))+" Minutes";
		return getLastName()+", "+getFirstName()+" - "+flightCity()+" - "+flightTimeString+" - "+etdCalcString;
	}
}
