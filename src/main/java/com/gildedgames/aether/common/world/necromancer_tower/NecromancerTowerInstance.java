package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.aether.common.registry.content.InstancesAether;
import com.gildedgames.orbis_api.core.CreationData;
import com.gildedgames.orbis_api.core.PlacedBlueprint;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import com.gildedgames.orbis_api.util.mc.BlockPosDimension;
import com.gildedgames.orbis_api.world.instances.IInstance;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class NecromancerTowerInstance implements IInstance
{

	private final List<EntityPlayer> players = Lists.newArrayList();

	private BlockPos insideEntrance;

	private int dimensionId;

	private PlacedBlueprint tower;

	@SuppressWarnings("unused")
	public NecromancerTowerInstance()
	{
	}

	@SuppressWarnings("unused")
	public NecromancerTowerInstance(World world)
	{

	}

	public NecromancerTowerInstance(final int id)
	{
		this.dimensionId = id;
	}

	private boolean initTower()
	{
		final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		final World world = server.getWorld(this.dimensionId);

		if (world == null)
		{
			return false;
		}

		this.tower = new PlacedBlueprint(world, GenerationAether.NECROMANCER_TOWER, new CreationData(world).pos(BlockPos.ORIGIN));

		final BlockPos spawn = this.tower.getScheduleFromTriggerID("spawn").getBounds().getMin();

		if (spawn != null)
		{
			this.insideEntrance = new BlockPosDimension(spawn.getX(), spawn.getY(), spawn.getZ(), this.dimensionId);
		}

		return true;
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
		final NBTFunnel funnel = new NBTFunnel(output);

		output.setTag("spawn", NBTUtil.createPosTag(this.insideEntrance));

		output.setInteger("dim", this.dimensionId);

		funnel.set("tower", this.tower);
	}

	@Override
	public void read(final NBTTagCompound input)
	{
		final NBTFunnel funnel = new NBTFunnel(input);

		this.insideEntrance = NBTUtil.getPosFromTag(input.getCompoundTag("spawn"));

		this.dimensionId = input.getInteger("dim");

		this.tower = funnel.get("tower");
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
				player.setEntityInvulnerable(true);
			}

			this.teleportPlayerToSpawn(player);
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
				player.setEntityInvulnerable(false);
			}
		}

		if (this.players.isEmpty())
		{
			InstancesAether.NECROMANCER_TOWER_HANDLER.handler.unregisterInstance(this);
		}
	}

	@Override
	public void onRespawn(EntityPlayer player)
	{
		this.teleportPlayerToSpawn(player);
	}

	private void teleportPlayerToSpawn(EntityPlayer player)
	{
		BlockPos spawn = this.getSpawnPosition();

		if (spawn != null)
		{
			((EntityPlayerMP) player).connection.setPlayerLocation(spawn.getX() + 0.5D, spawn.getY() + 0.5D, spawn.getZ() + 0.5D, 215, 0);
		}
	}

	public BlockPos getSpawnPosition()
	{
		return this.insideEntrance;
	}

	@Override
	public List<EntityPlayer> getPlayers()
	{
		return this.players;
	}

	@Override
	public DimensionType getDimensionType()
	{
		return DimensionsAether.NECROMANCER_TOWER;
	}

	@Override
	public int getDimensionId()
	{
		return this.dimensionId;
	}

	@Override
	public boolean isTemporary()
	{
		return true;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
		{
			return true;
		}

		if (obj instanceof NecromancerTowerInstance)
		{
			NecromancerTowerInstance instance = (NecromancerTowerInstance) obj;

			return instance.getDimensionId() == this.getDimensionId();
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.dimensionId);

		return builder.toHashCode();
	}

}
