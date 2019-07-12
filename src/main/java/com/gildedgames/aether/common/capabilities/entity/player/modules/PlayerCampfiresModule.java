package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.player.IPlayerAetherModule;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.gildedgames.orbis.lib.util.mc.BlockPosDimension;
import com.google.common.collect.Sets;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class PlayerCampfiresModule extends PlayerAetherModule implements IPlayerAetherModule.Serializable
{

	public Set<BlockPosDimension> campfiresActivated = Sets.newHashSet();

	private boolean shouldRespawnAtCampfire;

	private BlockPosDimension deathPos;

	public PlayerCampfiresModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public BlockPosDimension getDeathPos()
	{
		return this.deathPos;
	}

	public void setDeathPos(BlockPosDimension deathPos)
	{
		this.deathPos = deathPos;
	}

	public boolean hasCampfire(BlockPosDimension campfire)
	{
		return this.campfiresActivated.contains(campfire);
	}

	public Set<BlockPosDimension> getCampfiresActivated()
	{
		return this.campfiresActivated;
	}

	public void setCampfiresActivated(Set<BlockPosDimension> campfiresActivated)
	{
		this.campfiresActivated = campfiresActivated;
	}

	public BlockPos getClosestCampfire()
	{
		BlockPos closest = null;

		int x = this.getDeathPos().getX();
		int y = this.getDeathPos().getY();
		int z = this.getDeathPos().getZ();

		for (BlockPos p : this.campfiresActivated)
		{
			if (closest == null)
			{
				closest = p;
				continue;
			}

			if (closest.getDistance(x, y, z) > p.getDistance(x, y, z))
			{
				closest = p;
			}
		}

		return closest;
	}

	public void addActivatedCampfire(BlockPosDimension campfire)
	{
		this.campfiresActivated.add(campfire);
	}

	public void setShouldRespawnAtCampfire(boolean flag)
	{
		this.shouldRespawnAtCampfire = flag;
	}

	public boolean shouldRespawnAtCampfire()
	{
		return this.shouldRespawnAtCampfire;
	}

	@Override
	public void write(final CompoundNBT compound)
	{
		NBTFunnel funnel = new NBTFunnel(compound);

		funnel.setSet("campfiresActivated", this.campfiresActivated);
		funnel.set("deathPos", this.deathPos);
		compound.putBoolean("shouldRespawnAtCampfire", this.shouldRespawnAtCampfire);
	}

	@Override
	public void read(final CompoundNBT compound)
	{
		NBTFunnel funnel = new NBTFunnel(compound);

		this.campfiresActivated = funnel.getSet("campfiresActivated");
		this.deathPos = funnel.get("deathPos");
		this.shouldRespawnAtCampfire = compound.getBoolean("shouldRespawnAtCampfire");
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return AetherCore.getResource("campfires");
	}
}
