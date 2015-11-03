package com.gildedgames.aether.common.items.weapons.swords;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Doubles drops from mobs");
	}
}
