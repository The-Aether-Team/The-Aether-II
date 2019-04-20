package com.gildedgames.aether.api.travellers_guidebook.entries;

import com.gildedgames.aether.api.travellers_guidebook.ITGEntryDefinition;
import net.minecraft.util.text.ITextComponent;

public interface ITGEntryText extends ITGEntryDefinition
{
	ITextComponent getText();
}
