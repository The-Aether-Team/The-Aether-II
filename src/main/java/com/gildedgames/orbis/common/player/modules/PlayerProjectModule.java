package com.gildedgames.orbis.common.player.modules;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.exceptions.OrbisMissingProjectException;
import com.gildedgames.aether.api.orbis.management.IProject;
import com.gildedgames.aether.api.orbis.management.IProjectIdentifier;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.orbis.common.OrbisCore;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerProjectModule extends PlayerAetherModule
{

	private IProject currentProject;

	public PlayerProjectModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public IProject getCurrentProject()
	{
		return this.currentProject;
	}

	public void setCurrentProject(final IProject project)
	{
		this.currentProject = project;
	}

	@Override
	public void onUpdate()
	{

	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		if (this.currentProject != null)
		{
			funnel.set("projectId", this.currentProject.getProjectIdentifier());
		}
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = AetherCore.io().createFunnel(tag);

		final IProjectIdentifier id = funnel.get("projectId");

		if (id != null)
		{
			try
			{
				this.currentProject = OrbisCore.getProjectManager().findProject(id);
			}
			catch (final OrbisMissingProjectException e)
			{
				AetherCore.LOGGER.error(e);
			}
		}
	}
}
