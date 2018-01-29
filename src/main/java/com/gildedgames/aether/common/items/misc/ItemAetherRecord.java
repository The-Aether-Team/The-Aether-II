package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAetherRecord extends ItemRecord
{
	public ItemAetherRecord(final String recordName, final SoundEvent sound)
	{
		super(recordName, sound);

		this.setCreativeTab(CreativeTabsAether.MISCELLANEOUS);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getRecordNameLocal()
	{
		return I18n.translateToLocal(this.getUnlocalizedName() + ".desc");
	}

	//TODO: Reimplement for 1.12.2?
	/*@Override
	public ResourceLocation getRecordResource(String name)
	{
		return AetherCore.getResource(name);
	}*/
}
