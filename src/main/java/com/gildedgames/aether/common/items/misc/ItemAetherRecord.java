package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemAetherRecord extends ItemRecord
{
	public ItemAetherRecord(final String recordName, final SoundEvent sound)
	{
		super(recordName, sound);

		this.setCreativeTab(CreativeTabsAether.MISCELLANEOUS);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getRecordNameLocal()
	{
		return new TextComponentTranslation(this.getTranslationKey() + ".desc").getFormattedText();
	}

	//TODO: Reimplement for 1.12.2?
	/*@Override
	public ResourceLocation getRecordResource(String name)
	{
		return AetherCore.getResource(name);
	}*/
}
