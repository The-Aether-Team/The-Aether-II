package com.gildedgames.orbis.common.player;

import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.network.packets.PacketOrbisDeveloperMode;
import com.gildedgames.orbis.common.network.packets.PacketOrbisExtendedReach;
import com.gildedgames.orbis.common.util.OrbisRaytraceHelp;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class PlayerOrbisModule extends PlayerAetherModule
{

	private double extendedReach = 5.0D;

	private boolean reachSet;

	private boolean developerModeEnabled;

	public PlayerOrbisModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public boolean canInteractWithItems()
	{
		return !this.inDeveloperMode();
	}

	public boolean inDeveloperMode()
	{
		return this.developerModeEnabled;
	}

	public IShape getSelectedRegion()
	{
		return OrbisRaytraceHelp.raytraceShapes(this.getEntity(), null, this.getFinalExtendedReach(), 1, false);
	}

	public void setDeveloperMode(final boolean flag)
	{
		this.developerModeEnabled = flag;

		if (!this.getEntity().world.isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketOrbisDeveloperMode(flag), (EntityPlayerMP) this.getEntity());
		}
	}

	public BlockPos getAirRaytracing()
	{
		return OrbisRaytraceHelp.getAirRaytracing(this.getEntity());
	}

	public BlockPos getRegionRaytracing()
	{
		return OrbisRaytraceHelp.getRegionRaytracing(this.getEntity());
	}

	public double getReach()
	{
		final boolean creativeMode = this.getEntity().capabilities.isCreativeMode;

		if (this.inDeveloperMode())
		{
			return OrbisRaytraceHelp.getFinalExtendedReach(this.getEntity());
		}
		else
		{
			return creativeMode ? 5.0F : 4.5F;
		}
	}

	public double getExtendedReach()
	{
		return this.extendedReach;
	}

	public void setExtendedReach(final double reach)
	{
		this.extendedReach = Math.max(1, reach);
		this.reachSet = true;

		if (!this.getEntity().world.isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketOrbisExtendedReach(this.extendedReach), (EntityPlayerMP) this.getEntity());
		}
		else
		{
			NetworkingAether.sendPacketToServer(new PacketOrbisExtendedReach(this.extendedReach));
		}
	}

	public double getFinalExtendedReach()
	{
		return OrbisRaytraceHelp.getFinalExtendedReach(this.getEntity());
	}

	@Override
	public void onUpdate()
	{

	}

	@Override
	public void write(final NBTTagCompound output)
	{
		output.setBoolean("developerModeEnabled", this.developerModeEnabled);

		output.setBoolean("reachSet", this.reachSet);
		output.setDouble("extendedReach", this.extendedReach);
	}

	@Override
	public void read(final NBTTagCompound input)
	{
		this.developerModeEnabled = input.getBoolean("developerModeEnabled");

		this.reachSet = input.getBoolean("reachSet");

		if (this.reachSet)
		{
			this.extendedReach = input.getDouble("extendedReach");
		}
	}

}