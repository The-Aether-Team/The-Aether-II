package com.gildedgames.aether.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.blocks.natural.BlockAetherDirt;

public class AetherCreativeTabs
{
    public static final CreativeTabs tabBlocks = new CreativeTabs("aetherBlocks")
    {
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return Item.getItemFromBlock(Aether.getBlocks().aether_dirt);
        }

        @SideOnly(Side.CLIENT)
        public int getIconItemDamage()
        {
            return BlockAetherDirt.AetherGrassVariant.GRASS.getMetadata();
        }
    };
}
