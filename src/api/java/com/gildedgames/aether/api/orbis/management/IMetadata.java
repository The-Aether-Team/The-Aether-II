package com.gildedgames.aether.api.orbis.management;

import com.gildedgames.aether.api.util.IText;
import com.gildedgames.aether.api.util.NBT;

import java.util.List;

public interface IMetadata extends NBT
{
	/**
	 * Used for displaying this metadata to users in the GUI.
	 * @return The text that will be displayed.
	 */
	List<IText> getMetadataDisplay();
}
