package com.gildedgames.aether.common.items.weapons.swords;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ItemSkyrootSword extends ItemAetherSword
{
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
