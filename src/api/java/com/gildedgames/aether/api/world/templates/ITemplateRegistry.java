package com.gildedgames.aether.api.world.templates;

public interface ITemplateRegistry
{
	int getID(TemplateDefinition def);

	TemplateDefinition get(int id);

	void register(int id, TemplateDefinition def);
}
