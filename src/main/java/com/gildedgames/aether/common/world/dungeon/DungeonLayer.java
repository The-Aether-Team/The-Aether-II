package com.gildedgames.aether.common.world.dungeon;

import com.gildedgames.aether.common.world.dungeon.DungeonRoom;

import java.awt.*;
import java.util.List;

public class DungeonLayer
{

	private List<DungeonRoom> rooms;

	private final int diameter;

	private int[][] tiles;

	private Rectangle rect;

	private int height;

	private int minY;

	private int smallestRoomHeight;

	private DungeonRoom endRoom, entranceRoom;

	public DungeonLayer(int diameter)
	{
		this.diameter = diameter;
		this.rect = new Rectangle();
	}

	public int smallestRoomHeight() { return this.smallestRoomHeight; }

	public void setSmallestRoomHeight(int smallestRoomHeight) { this.smallestRoomHeight = smallestRoomHeight; }

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

	public int[][] tiles()
	{
		return this.tiles;
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

	protected void definePos(int minX, int minZ)
	{
		this.rect.setLocation(minX, minZ);

		for (DungeonRoom room : this.rooms)
		{
			room.setPositionOffset(minX, minZ);
		}
	}

	protected void defineTiles(int[][] tiles)
	{
		this.tiles = tiles;
	}

	protected void defineEndRoom(DungeonRoom room)
	{
		this.endRoom = room;
	}

	protected DungeonRoom endRoom()
	{
		return this.endRoom;
	}

	protected void defineEntranceRoom(DungeonRoom room)
	{
		this.entranceRoom = room;
	}

	protected DungeonRoom entranceRoom() { return this.entranceRoom; }

}
