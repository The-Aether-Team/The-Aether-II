package com.gildedgames.aether.common.containers.tab.util;

import com.gildedgames.aether.api.registry.tab.ITab;
import com.gildedgames.aether.api.registry.tab.ITabGroup;

import java.util.ArrayList;
import java.util.List;

public class TabGroup<T extends ITab> implements ITabGroup<T>
{

	protected final ArrayList<T> tabs = new ArrayList<>();

	protected T selectedTab, rememberedTab;

	protected boolean rememberSelectedTab = true;

	@Override
	public boolean getRememberSelectedTab()
	{
		return this.rememberSelectedTab;
	}

	@Override
	public void setRememberSelectedTab(boolean rememberSelectedTab)
	{
		this.rememberSelectedTab = rememberSelectedTab;
	}

	@Override
	public T getSelectedTab()
	{
		return this.selectedTab;
	}

	@Override
	public void setSelectedTab(T tab)
	{
		this.selectedTab = tab;
	}

	@Override
	public T getRememberedTab()
	{
		return this.rememberedTab;
	}

	@Override
	public void setRememberedTab(T tab)
	{
		this.rememberedTab = tab;
	}

	@Override
	public void add(T tab)
	{
		this.tabs.add(tab);
	}

	@Override
	public int getTabAmount()
	{
		int amount = 0;

		for (T tabDescription : this.tabs)
		{
			if (tabDescription.isEnabled())
			{
				amount++;
			}
		}

		return amount;
	}

	@Override
	public List<T> getEnabledTabs()
	{
		List<T> enabledTabs = new ArrayList<>();

		for (T tabDescription : this.tabs)
		{
			if (tabDescription.isEnabled())
			{
				enabledTabs.add(tabDescription);
			}
		}

		return enabledTabs;
	}

	@Override
	public List<T> getTabs()
	{
		return this.tabs;
	}

}
