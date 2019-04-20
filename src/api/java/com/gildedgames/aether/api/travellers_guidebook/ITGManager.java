package com.gildedgames.aether.api.travellers_guidebook;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Optional;

public interface ITGManager
{
	void registerEntry(ResourceLocation entry);

	void load();

	List<ITGEntryDefinition> getEntriesWithTag(String tag);

	<T extends ITGEntryDefinition> Optional<T> getEntry(String entryId, Class<T> clazzType);

	@SideOnly(Side.CLIENT)
	void attachReloadListener();
}
