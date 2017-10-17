package com.gildedgames.orbis.common.player;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.network.packets.PacketOrbisDeveloperMode;
import com.gildedgames.orbis.common.network.packets.PacketOrbisDeveloperReach;
import com.gildedgames.orbis.common.player.modules.GodPowerModule;
import com.gildedgames.orbis.common.util.OrbisRaytraceHelp;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.List;

public class PlayerOrbisModule extends PlayerAetherModule
{

	private final GodPowerModule godPowerModule;

	private double developerReach = 5.0D;

	private boolean reachSet;

	private boolean developerModeEnabled;

	public PlayerOrbisModule(final PlayerAether playerAether)
	{
		super(playerAether);

		this.godPowerModule = new GodPowerModule(playerAether);
	}

	public static PlayerOrbisModule get(final Entity player)
	{
		final PlayerAether playerAether = PlayerAether.getPlayer(player);

		return playerAether.getOrbisModule();
	}

	public GodPowerModule powers()
	{
		return this.godPowerModule;
	}

	public List<IWorldRenderer> getActiveRenderers()
	{
		if (!this.inDeveloperMode())
		{
			return Collections.emptyList();
		}

		return this.powers().getCurrentPower().getClientHandler().getActiveRenderers(this, this.getWorld());
	}

	public boolean canInteractWithItems()
	{
		return this.powers().getCurrentPower().canInteractWithItems(this);
	}

	public boolean inDeveloperMode()
	{
		return this.developerModeEnabled;
	}

	public IShape getSelectedRegion()
	{
		return OrbisRaytraceHelp.raytraceShapes(this.getEntity(), null, this.getReach(), 1, false);
	}

	public void setDeveloperMode(final boolean flag)
	{
		this.developerModeEnabled = flag;

		if (!this.getEntity().world.isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketOrbisDeveloperMode(flag), (EntityPlayerMP) this.getEntity());
		}
	}

	public BlockPos raytraceNoSnapping()
	{
		return OrbisRaytraceHelp.raytraceNoSnapping(this.getEntity());
	}

	public BlockPos raytraceWithRegionSnapping()
	{
		return OrbisRaytraceHelp.raytraceWithRegionSnapping(this.getEntity());
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

	public double getDeveloperReach()
	{
		return this.developerReach;
	}

	public void setDeveloperReach(final double reach)
	{
		this.developerReach = Math.max(1, reach);
		this.reachSet = true;

		if (!this.getEntity().world.isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketOrbisDeveloperReach(this.developerReach), (EntityPlayerMP) this.getEntity());
		}
		else
		{
			NetworkingAether.sendPacketToServer(new PacketOrbisDeveloperReach(this.developerReach));
		}
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
		output.setDouble("developerReach", this.developerReach);
	}

	@Override
	public void read(final NBTTagCompound input)
	{
		this.developerModeEnabled = input.getBoolean("developerModeEnabled");

		this.reachSet = input.getBoolean("reachSet");

		if (this.reachSet)
		{
			this.developerReach = input.getDouble("developerReach");
		}
	}

}