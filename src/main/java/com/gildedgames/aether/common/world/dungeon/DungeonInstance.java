package com.gildedgames.aether.common.world.dungeon;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.modules.instances.Instance;
import com.gildedgames.util.modules.instances.InstanceHandler;
import com.gildedgames.util.modules.world.common.BlockPosDimension;
import com.google.common.collect.Lists;

public class DungeonInstance implements Instance
{
	
	private List<EntityPlayer> players = Lists.newArrayList();

	private BlockPosDimension entrance;
	
	private boolean generated = false;
	
	@SuppressWarnings("unused")
	private DungeonInstance()
	{
		
	}
	
	public DungeonInstance(int id, InstanceHandler<DungeonInstance> instanceHandler)
	{
		
	}
	
	public void flagGenerated()
	{
		this.generated = true;
	}
	
	public boolean hasGenerated()
	{
		return this.generated;
	}
	
	public BlockPosDimension getEntrance()
	{
		return this.entrance;
	}
	
	public void setEntrance(BlockPosDimension entrance)
	{
		this.entrance = entrance;
	}

	@Override
	public void write(NBTTagCompound output)
	{
		output.setBoolean("generated", this.generated);
	}

	@Override
	public void read(NBTTagCompound input)
	{
		this.generated = input.getBoolean("generated");
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

}
