package se.jiv.webshop.ui;

import java.util.Scanner;

public abstract class GeneralUI
{
	
	protected String readString()
	{
		Scanner scanner = new Scanner(System.in);
		String value = scanner.nextLine();
		
		return value;
	}

	protected int readInt()
	{
		while (true)
		{
			int value = -1;
			try
			{
				Scanner scanner = new Scanner(System.in);
				value = scanner.nextInt();
			}
			catch (Exception e)
			{
				System.out.println("It have to be a number. Write it again:");
				continue;
			}

			return value;
		}
	}

}
