package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.aether.api.util.BlockPosDimension;
import com.gildedgames.aether.api.world.instances.IInstance;
import com.gildedgames.aether.api.world.instances.IInstanceHandler;
import com.gildedgames.orbis.api.util.mc.NBTHelper;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class NecromancerTowerInstance implements IInstance
{

	private final List<EntityPlayer> players = Lists.newArrayList();

	private BlockPosDimension outsideEntrance, insideEntrance;

	private int dimIdInside;

	@SuppressWarnings("unused")
	private NecromancerTowerInstance()
	{

	}

	public NecromancerTowerInstance(final int id, final IInstanceHandler instanceHandler)
	{
		this.dimIdInside = id;
		this.insideEntrance = new BlockPosDimension(0, 0, 0, id);
	}

	public BlockPosDimension getOutsideEntrance()
	{
		return this.outsideEntrance;
	}

	public void setOutsideEntrance(final BlockPosDimension entrance)
	{
		this.outsideEntrance = entrance;
	}

	public BlockPosDimension getInsideEntrance()
	{
		return this.insideEntrance;
	}

	public void setInsideEntrance(final BlockPosDimension entrance)
	{
		this.insideEntrance = entrance;
	}

	@Override
	public void write(final NBTTagCompound output)
	{
		output.setTag("outsideEntrance", NBTHelper.write(this.outsideEntrance));
		output.setTag("insideEntrance", NBTHelper.write(this.insideEntrance));

		output.setInteger("dimIdInside", this.dimIdInside);
	}

	@Override
	public void read(final NBTTagCompound input)
	{
		this.outsideEntrance = NBTHelper.read(input.getCompoundTag("outsideEntrance"));
		this.insideEntrance = NBTHelper.read(input.getCompoundTag("insideEntrance"));

		this.dimIdInside = input.getInteger("dimIdInside");
	}

	@Override
	public void onJoin(final EntityPlayer player)
	{
		this.players.add(player);
	}

	@Override
	public void onLeave(final EntityPlayer player)
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
