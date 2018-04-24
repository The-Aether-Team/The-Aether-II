package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.registry.content.GenerationAether;
import com.gildedgames.orbis_api.core.CreationData;
import com.gildedgames.orbis_api.core.PlacedBlueprint;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import com.gildedgames.orbis_api.util.mc.BlockPosDimension;
import com.gildedgames.orbis_api.util.mc.NBTHelper;
import com.gildedgames.orbis_api.world.instances.IInstance;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
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

	private BlockPosDimension insideEntrance;

	private int dimensionId;

	private PlacedBlueprint tower;

	private World world;

	@SuppressWarnings("unused")
	private NecromancerTowerInstance()
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

	public BlockPosDimension getInsideEntrance()
	{
		return this.insideEntrance;
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

		output.setTag("insideEntrance", NBTHelper.write(this.insideEntrance));

		output.setInteger("dimensionId", this.dimensionId);

		funnel.set("tower", this.tower);
	}

	@Override
	public void read(final NBTTagCompound input)
	{
		final NBTFunnel funnel = new NBTFunnel(input);

		this.insideEntrance = NBTHelper.read(input.getCompoundTag("insideEntrance"));

		this.dimensionId = input.getInteger("dimensionId");

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
	public void setDimensionId(final int i)
	{
		this.dimensionId = i;
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
