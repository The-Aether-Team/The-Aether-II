package com.gildedgames.aether.common.world.dungeon.util;

import java.awt.Rectangle;
import java.util.List;

import com.gildedgames.aether.common.world.dungeon.DungeonRoom;

public class DungeonLayer
{
	
	private List<DungeonRoom> rooms;
	
	private final int diameter;
	
	private int[][] tiles;
	
	private Rectangle rect;
	
	private int height = 60;
	
	private int minY;
	
	private int endX, endZ;
	
	private int desiredRoomCount = 73;

	private int desiredLargeRoomCount = 12;
	
	public DungeonLayer(int diameter)
	{
		this.diameter = diameter;
		this.rect = new Rectangle();
	}
	
	public List<DungeonRoom> getRooms()
	{
		return this.rooms;
	}

	public void setRooms(List<DungeonRoom> rooms)
	{
		this.rooms = rooms;
	}
	
	public int getDiameter()
	{
		return this.diameter;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public int getWidth()
	{
		return (int) this.rect.getWidth();
	}
	
	public int getLength()
	{
		return (int) this.rect.getHeight();
	}
	
	public int minY()
	{
		return this.minY;
	}
	
	public int maxY()
	{
		return this.minY + this.height;
	}
	
	public int minX()
	{
		return (int) this.rect.getMinX();
	}
	
	public int minZ()
	{
		return (int) this.rect.getMinY();
	}
	
	public int maxX()
	{
		return (int) this.rect.getMaxX();
	}
	
	public int maxZ()
	{
		return (int) this.rect.getMaxY();
	}
	
	public int endX()
	{
		return this.endX;
	}
	
	public int endZ()
	{
		return this.endZ;
	}
	
	public int[][] tiles()
	{
		return this.tiles;
	}
	
	public int desiredRoomCount()
	{
		return this.desiredRoomCount;
	}
	
	public int desiredLargeRoomCount()
	{
		return this.desiredLargeRoomCount;
	}
	
	protected void defineEnd(int x, int z)
	{
		this.endX = x;
		this.endZ = z;
	}
	
	protected void defineWidth(int width)
	{
		this.rect.setSize(width, (int)this.rect.getHeight());
	}
	
	protected void defineLength(int length)
	{
		this.rect.setSize((int)this.rect.getWidth(), length);
	}
	
	protected void defineHeight(int height)
	{
		this.height = height;
	}
	
	protected void defineMinY(int minY)
	{
		this.minY = minY;
	}
	
	protected void defineTiles(int[][] tiles)
	{
		this.tiles = tiles;
	}
	
}