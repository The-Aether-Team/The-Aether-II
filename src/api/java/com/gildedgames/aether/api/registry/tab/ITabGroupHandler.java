package com.gildedgames.aether.api.registry.tab;

public interface ITabGroupHandler
{
	void registerServerTab(ITab tab);

	void registerClientTab(ITabClient tab);

	int getDiscriminant(ITab tab);

	ITabGroup<ITabClient> getClientGroup();

	ITabGroup<ITab> getServerGroup();
}
