package com.gildedgames.aether.common.items.weapons.swords;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
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
			
			if (player.getHeldItem().getItem() == this)
			{	
				BlockPos dyingPos = event.entityLiving.getPosition();				
				List<ItemStack> stacks = new ArrayList<ItemStack>();

				for (EntityItem item : event.drops)
				{
					stacks.add(item.getEntityItem());
				}
				
				for (ItemStack stack : stacks)
				{	
					EntityItem item = new EntityItem(event.entityLiving.getEntityWorld(), dyingPos.getX(), dyingPos.getY(), dyingPos.getZ(), stack);
					event.entityLiving.getEntityWorld().spawnEntityInWorld(item);
				}
			}
		}
		
	}
}
