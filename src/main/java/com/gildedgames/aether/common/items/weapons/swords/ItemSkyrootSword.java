package com.gildedgames.aether.common.items.weapons.swords;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemSkyrootSword extends ItemAetherSword
{
	
	public static final Set<Item> blacklistedItems = new HashSet<>();

	static
	{
		blacklistedItems.add(Items.saddle);
		blacklistedItems.add(Items.skull);
		blacklistedItems.add(Item.getItemFromBlock(Blocks.chest));
	}
	
	public ItemSkyrootSword()
	{
		super(ToolMaterial.WOOD);
	}
	
	@SubscribeEvent
	public void dropLoot(LivingDropsEvent event)
	{	
		if (event.source.getSourceOfDamage() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
			
			if (player.getHeldItem() != null && player.getHeldItem().getItem() == this)
			{	
				List<ItemStack> stacks = new ArrayList<>();

				for (EntityItem item : event.drops)
				{
					stacks.add(item.getEntityItem());
				}
				
				for (ItemStack stack : stacks)
				{	
					EntityItem item = new EntityItem(event.entityLiving.getEntityWorld(), event.entity.posX, event.entity.posY, event.entity.posZ, stack);

					event.entityLiving.getEntityWorld().spawnEntityInWorld(item);
				}
			}
		}
	}
	
}
