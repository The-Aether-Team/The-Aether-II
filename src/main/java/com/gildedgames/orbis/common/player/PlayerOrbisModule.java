package com.gildedgames.orbis.common.player;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.api.util.NBTHelper;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.network.packets.PacketOrbisDeveloperMode;
import com.gildedgames.orbis.common.network.packets.PacketOrbisDeveloperReach;
import com.gildedgames.orbis.common.player.godmode.IGodPower;
import com.gildedgames.orbis.common.player.modules.PlayerPowerModule;
import com.gildedgames.orbis.common.player.modules.PlayerProjectModule;
import com.gildedgames.orbis.common.player.modules.PlayerSelectionTypesModule;
import com.gildedgames.orbis.common.util.OrbisRaytraceHelp;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.List;

public class PlayerOrbisModule extends PlayerAetherModule
{

	private final PlayerPowerModule godPowerModule;

	private final PlayerSelectionTypesModule selectionTypeModule;

	private final PlayerProjectModule projectModule;

	private final List<PlayerAetherModule> modules = Lists.newArrayList();

	private double developerReach = 5.0D;

	private boolean reachSet;

	private boolean developerModeEnabled;

	public PlayerOrbisModule(final PlayerAether playerAether)
	{
		super(playerAether);

		this.godPowerModule = new PlayerPowerModule(playerAether);
		this.selectionTypeModule = new PlayerSelectionTypesModule(playerAether);
		this.projectModule = new PlayerProjectModule(playerAether);

		this.modules.add(this.godPowerModule);
		this.modules.add(this.selectionTypeModule);
		this.modules.add(this.projectModule);
	}

	public static PlayerOrbisModule get(final Entity player)
	{
		final PlayerAether playerAether = PlayerAether.getPlayer(player);

		return playerAether.getOrbisModule();
	}

	public PlayerProjectModule projects()
	{
		return this.projectModule;
	}

	public PlayerPowerModule powers()
	{
		return this.godPowerModule;
	}

	public PlayerSelectionTypesModule selectionTypes()
	{
		return this.selectionTypeModule;
	}

	public List<IWorldRenderer> getActiveRenderers()
	{
		if (!this.inDeveloperMode())
		{
			return Collections.emptyList();
		}

		final List<IWorldRenderer> renderers = Lists.newArrayList();
		final PlayerOrbisModule module = PlayerOrbisModule.get(this.getEntity());

		for (final IGodPower power : module.powers().array())
		{
			renderers.addAll(power.getClientHandler().getActiveRenderers(this, this.getWorld()));
		}

		return renderers;
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
	}

	@Override
	public void onUpdate()
	{
		this.godPowerModule.onUpdate();
	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		tag.setBoolean("developerModeEnabled", this.developerModeEnabled);

		tag.setBoolean("reachSet", this.reachSet);
		tag.setDouble("developerReach", this.developerReach);

		final NBTTagList modules = new NBTTagList();

		for (final PlayerAetherModule module : this.modules)
		{
			modules.appendTag(NBTHelper.write(module));
		}

		tag.setTag("modules", modules);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		this.developerModeEnabled = tag.getBoolean("developerModeEnabled");

		this.reachSet = tag.getBoolean("reachSet");

		if (this.reachSet)
		{
			this.developerReach = tag.getDouble("developerReach");
		}

		final NBTTagList modules = tag.getTagList("modules", 10);

		for (int i = 0; i < modules.tagCount(); i++)
		{
			final PlayerAetherModule module = this.modules.get(i);

			module.read(modules.getCompoundTagAt(i));
		}
	}

}