package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.orbis.api.core.CreationData;
import com.gildedgames.orbis.api.core.PlacedBlueprint;
import com.gildedgames.orbis.api.util.mc.BlockPosDimension;
import com.gildedgames.orbis.api.util.mc.NBTHelper;
import com.gildedgames.orbis.api.world.instances.IInstance;
import com.gildedgames.orbis.api.world.instances.IInstanceHandler;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;

public class NecromancerTowerInstance implements IInstance
{

	private final List<EntityPlayer> players = Lists.newArrayList();

	private BlockPosDimension outsideEntrance, insideEntrance;

	private int dimIdInside;

	private PlacedBlueprint tower;

	@SuppressWarnings("unused")
	private NecromancerTowerInstance()
	{

	}

	public NecromancerTowerInstance(final int id, final IInstanceHandler instanceHandler)
	{
		this.dimIdInside = id;
	}

	private boolean initTower()
	{
		final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		final World world = server.getWorld(this.dimIdInside);

		if (world == null)
		{
			return false;
		}

		this.tower = new PlacedBlueprint(world, GenerationAether.NECROMANCER_TOWER, new CreationData(world).pos(BlockPos.ORIGIN));

		final BlockPos spawn = this.tower.getScheduleFromTriggerID("spawn").getBounds().getMin();

		if (spawn != null)
		{
			this.insideEntrance = new BlockPosDimension(spawn.getX(), spawn.getY(), spawn.getZ(), this.dimIdInside);
		}

		return true;
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

	public PlacedBlueprint getTower()
	{
		if (this.tower == null)
		{
			this.initTower();
		}

		return this.tower;
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

		if (player instanceof EntityPlayerMP)
		{
			final EntityPlayerMP playerMP = (EntityPlayerMP) player;

			if (playerMP.interactionManager.getGameType() == GameType.SURVIVAL)
			{
				player.setGameType(GameType.ADVENTURE);
			}
		}
	}

	@Override
	public void onLeave(final EntityPlayer player)
	{
		this.players.remove(player);

		if (player instanceof EntityPlayerMP)
		{
			final EntityPlayerMP playerMP = (EntityPlayerMP) player;

			if (playerMP.interactionManager.getGameType() == GameType.ADVENTURE)
			{
				player.setGameType(GameType.SURVIVAL);
			}
		}
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

	@Override
	public void setDimIdInside(final int dimIdInside)
	{
		this.dimIdInside = dimIdInside;
	}

}
