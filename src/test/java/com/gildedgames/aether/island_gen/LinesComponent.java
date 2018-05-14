package com.gildedgames.aether.island_gen;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class LinesComponent extends JComponent
{

	private final LinkedList<Line> lines = new LinkedList<>();

	public void addLine(int x1, int x2, int x3, int x4)
	{
		this.addLine(x1, x2, x3, x4, Color.black);
	}

	public void addLine(int x1, int x2, int x3, int x4, Color color)
	{
		this.lines.add(new Line(x1, x2, x3, x4, color));
		this.repaint();
	}

	public void clearLines()
	{
		this.lines.clear();
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		for (Line line : this.lines)
		{
			g.setColor(line.color);
			g.drawLine(line.x1, line.y1, line.x2, line.y2);
		}
	}

	private static class Line
	{
		final int x1;

		final int y1;

		final int x2;

		final int y2;

		final Color color;

		public Line(int x1, int y1, int x2, int y2, Color color)
		{
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.color = color;
		}
	}

}
