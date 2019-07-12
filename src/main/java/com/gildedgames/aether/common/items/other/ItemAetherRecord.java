package com.gildedgames.aether.common.items.other;

import com.gildedgames.aether.common.init.CreativeTabsAether;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class ItemAetherRecord extends ItemRecord
{
	public ItemAetherRecord(final String recordName, final SoundEvent sound)
	{
		super(recordName, sound);

		this.setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getRecordNameLocal()
	{
		return new TranslationTextComponent(this.getTranslationKey() + ".desc").getFormattedText();
	}

	//TODO: Reimplement for 1.12.2?
	/*@Override
	public ResourceLocation getRecordResource(String name)
	{
		return AetherCore.getResource(name);
	}*/
}
