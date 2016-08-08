package com.gildedgames.aether.common.world.dungeon;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.core.nbt.NBTHelper;
import com.gildedgames.util.modules.instances.Instance;
import com.gildedgames.util.modules.instances.InstanceHandler;
import com.gildedgames.util.core.util.BlockPosDimension;
import com.google.common.collect.Lists;

public class DungeonInstance implements Instance
{
	
	private DungeonDefinition def;
	
	private DungeonGenerator generator;
	
	private DungeonRoomProvider roomProvider;
	
	private List<EntityPlayer> players = Lists.newArrayList();

	private BlockPosDimension outsideEntrance, insideEntrance;
	
	private boolean generated = false;
	
	private int dimIdInside;
	
	@SuppressWarnings("unused")
	private DungeonInstance()
	{
		
	}
	
	public DungeonInstance(int id, InstanceHandler<DungeonInstance> instanceHandler)
	{
		this.dimIdInside = id;
		
		this.def = DungeonDefinitions.SLIDERS_LABYRINTH;
	}

	public DungeonGenerator getGenerator()
	{
		if (this.generator == null)
		{
			this.generator = this.def.createGenerator();
		}
		
		return this.generator;
	}
	
	public DungeonRoomProvider getRoomProvider()
	{
		if (this.roomProvider == null)
		{
			this.roomProvider = this.def.createRoomProvider();
		}
		
		return this.roomProvider;
	}
	
	public DungeonDefinition getDefinition()
	{
		return this.def;
	}
	
	public void flagGenerated()
	{
		this.generated = true;
	}
	
	public boolean hasGenerated()
	{
		return this.generated;
	}
	
	public BlockPosDimension getOutsideEntrance()
	{
		return this.outsideEntrance;
	}
	
	public void setOutsideEntrance(BlockPosDimension entrance)
	{
		this.outsideEntrance = entrance;
	}
	
	public BlockPosDimension getInsideEntrance()
	{
		return this.insideEntrance;
	}
	
	public void setInsideEntrance(BlockPosDimension entrance)
	{
		this.insideEntrance = entrance;
	}
	
	@Override
	public void write(NBTTagCompound output)
	{
		output.setBoolean("generated", this.generated);
		
		output.setTag("outsideEntrance", NBTHelper.serializeBlockPosDimension(this.outsideEntrance));
		output.setTag("insideEntrance", NBTHelper.serializeBlockPosDimension(this.insideEntrance));
		
		output.setInteger("dimIdInside", this.dimIdInside);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.generated = input.getBoolean("generated");
		
		this.outsideEntrance = NBTHelper.getBlockPosDimension(input.getCompoundTag("outsideEntrance"));
		this.insideEntrance = NBTHelper.getBlockPosDimension(input.getCompoundTag("insideEntrance"));
		
		this.dimIdInside = input.getInteger("dimIdInside");
	}

	@Override
	public void onJoin(EntityPlayer player)
	{
		this.players.add(player);
	}

	@Override
	public void onLeave(EntityPlayer player)
	{
		this.players.remove(player);
	}

	@Override
	public List<EntityPlayer> getPlayers()
	{
		return this.players;
	}

	@Override
	public int getDimIdInside()
	{
		return this.dimIdInside;
	}

}
