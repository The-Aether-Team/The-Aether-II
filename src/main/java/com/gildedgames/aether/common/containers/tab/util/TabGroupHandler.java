package com.gildedgames.aether.common.containers.tab.util;

import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabClient;
import com.gildedgames.aether.api.registry.tab.ITabGroup;
import com.gildedgames.aether.api.registry.tab.ITabGroupHandler;
import com.gildedgames.aether.common.AetherCore;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * This is what contains {@link ITab}s for a {@link GuiScreen}'s tab interface.
 * @author Brandon Pearce
 */
public class TabGroupHandler implements ITabGroupHandler
{
	public final BiMap<Integer, String> idMappings = HashBiMap.create();

	@OnlyIn(Dist.CLIENT)
	public TabGroup<ITabClient> clientGroup;

	public final TabGroup<ITab> serverGroup;

	public int discriminant;

	public TabGroupHandler()
	{
		if (AetherCore.isClient())
		{
			this.clientGroup = new TabGroup<>();
		}

		this.serverGroup = new TabGroup<>();
	}

	@Override
	public void registerServerTab(ITab tab)
	{
		if (!this.idMappings.inverse().containsKey(tab.getUnlocalizedName()))
		{
			this.idMappings.put(this.discriminant++, tab.getUnlocalizedName());
		}

		this.getServerGroup().add(tab);
	}

	@Override
	public void registerClientTab(ITabClient tab)
	{
		if (!this.idMappings.inverse().containsKey(tab.getUnlocalizedName()))
		{
			this.idMappings.put(this.discriminant++, tab.getUnlocalizedName());
		}

		this.getClientGroup().add(tab);
	}

	@Override
	public int getDiscriminant(ITab tab)
	{
		return this.idMappings.inverse().get(tab.getUnlocalizedName());
	}

	@Override
	public ITabGroup<ITabClient> getClientGroup()
	{
		return this.clientGroup;
	}

	@Override
	public ITabGroup<ITab> getServerGroup()
	{
		return this.serverGroup;
	}
}
