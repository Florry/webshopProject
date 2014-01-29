package se.jiv.webshop.ui;

import java.util.Scanner;

public abstract class GeneralUI
{
	protected String readString()
	{
		Scanner scanner = new Scanner(System.in);
		return scanner.next();
	}

	protected int readInt()
	{
		while (true)
		{
			Scanner scanner = new Scanner(System.in);
			int value = -1;
			try
			{
				value = scanner.nextInt();
			}
			catch (Exception e){
				System.out.println("It have to be a number. Write it again:");
				continue;
			}
			
			return value;
		}
	}

}
