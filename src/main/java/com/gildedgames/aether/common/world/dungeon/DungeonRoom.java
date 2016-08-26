package com.gildedgames.aether.common.world.dungeon;

import net.minecraft.world.gen.structure.template.Template;

import java.awt.*;

public class DungeonRoom
{
	
	public Template template;

	public Rectangle rectangle;

	public boolean asleep; //used during the separation

	public boolean toRemove;

	public int[] position; //used during the triangulation
	
	private int height;
	
	public DungeonRoom(Template template)
	{
		this.template = template;
		this.rectangle = new Rectangle(0, 0, this.template.getSize().getX(), this.template.getSize().getZ());
	}

	public int[] getPositionArray()//Used during the triangulation
	{
		this.position = new int[3];
		this.position[0] = (int) this.rectangle.getCenterX();
		this.position[1] = (int) this.rectangle.getCenterY();
		this.position[2] = (int) (this.rectangle.getX() * this.rectangle.getX() + this.rectangle.getY() * this.rectangle.getY());
		return this.position;
	}
	
	public void setPos(int x, int z)
	{
		this.rectangle.setLocation(x, z);
	}

	public int getArea()
	{
		return (int) (this.rectangle.getWidth() * this.rectangle.getHeight());
	}

	public double getCenterX()
	{
		return this.rectangle.getX() + this.rectangle.getWidth() / 2;
	}

	public double getCenterZ()
	{
		return this.rectangle.getY() + this.rectangle.getHeight() / 2;
	}

	public void setPositionOffset(int x, int z)
	{
		this.rectangle.setLocation((int) (this.rectangle.getX() + x), (int) (this.rectangle.getY() + z));
	}

	public Rectangle fullRectangle()
	{
		return new Rectangle((int) this.rectangle.getX(), (int) this.rectangle.getY(), (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
	}

	public boolean contains(int[] position)
	{
		return this.rectangle.contains(position[0], position[1]);
	}

	public int getWidth()
	{
		return (int) this.rectangle.getWidth();
	}

	public int getLength()
	{
		return (int) this.rectangle.getHeight();
	}
	
	public int getHeight()
	{
		return this.template == null ? this.height : this.template.getSize().getY();
	}

	public int getMinX()
	{
		return (int) this.rectangle.getMinX();
	}

	public int getMaxX()
	{
		return (int) this.rectangle.getMaxX();
	}

	public int getMinZ()
	{
		return (int) this.rectangle.getMinY();
	}

	public int getMaxZ()
	{
		return (int) this.rectangle.getMaxY();
	}

}
