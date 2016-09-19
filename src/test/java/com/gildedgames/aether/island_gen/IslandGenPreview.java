package com.gildedgames.aether.island_gen;

import javax.swing.*;
import java.awt.*;

public class IslandGenPreview
{

	public IslandGenPreview()
	{
		final JFrame frame = new JFrame();

		frame.setTitle("Island Generation");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String [] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				IslandGenPreview ex = new IslandGenPreview();
			}
		});
	}

}
