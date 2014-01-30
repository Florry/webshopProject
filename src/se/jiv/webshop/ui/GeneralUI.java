package se.jiv.webshop.ui;

import java.util.Scanner;

public abstract class GeneralUI
{
	private Scanner scanner = null;
	public GeneralUI() {
		scanner = new Scanner(System.in);
	}
	protected String readString()
	{
		return scanner.nextLine();
	}

	protected int readInt()
	{
		while (true)
		{
			int value = -1;
			try
			{
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
