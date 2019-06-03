package com.gildedgames.aether.api.travellers_guidebook.entries;

import com.gildedgames.aether.api.cache.IEntityStats;
import com.gildedgames.aether.api.player.IPlayerAether;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public interface ITGEntryBestiaryPage extends ITGEntryEntity
{
	ResourceLocation getSilhouetteTexture();

	ResourceLocation getDiscoveredTexture();

	IEntityStats getEntityStats();

	ITextComponent getDescription();

	String getEntityName();

	boolean hasUnlockedStats(IPlayerAether playerAether);

	/**
	 * The complete overview of the bestiary page includes
	 * the description of the creature as well as a visible
	 * pixel art portrait of the creature.
	 *
	 * @return Whether or not the complete overview has been unlocked.
	 */
	boolean hasUnlockedCompleteOverview(IPlayerAether playerAether);

	/**
	 * @return Whether or not the name has been unlocked.
	 */
	boolean hasUnlockedName(IPlayerAether playerAether);
}
