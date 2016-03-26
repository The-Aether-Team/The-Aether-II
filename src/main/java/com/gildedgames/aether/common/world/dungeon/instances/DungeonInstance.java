package com.gildedgames.aether.common.world.dungeon.instances;

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
	
	public DungeonInstance(int id, InstanceHandler<DungeonInstance> instanceHandler)
	{
		
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
		
	}

	@Override
	public void read(NBTTagCompound input)
	{
		
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
