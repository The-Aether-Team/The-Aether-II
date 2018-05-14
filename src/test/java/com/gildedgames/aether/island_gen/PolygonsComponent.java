package com.gildedgames.aether.island_gen;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class PolygonsComponent extends JComponent
{

	private final LinkedList<Poly> polys = new LinkedList<>();

	public void addPolygon(Polygon poly)
	{
		this.addPolygon(poly, Color.BLACK);
	}

	public void addPolygon(Polygon poly, Color color)
	{
		this.polys.add(new Poly(poly, color));
		this.repaint();
	}

	public void clear()
	{
		this.polys.clear();
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		for (Poly poly : this.polys)
		{
			g.setColor(poly.color);
			g.fillPolygon(poly.data);
		}
	}

	private static class Poly
	{
		final Polygon data;

		final Color color;

		public Poly(Polygon data, Color color)
		{
			this.data = data;
			this.color = color;
		}
	}

}
