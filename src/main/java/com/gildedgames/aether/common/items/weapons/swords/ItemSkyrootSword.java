package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.items.ItemAbilityType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemSkyrootSword extends ItemAetherSword
{

	public static final Set<Item> blacklistedItems = new HashSet<>();

	static
	{
		blacklistedItems.add(Items.SADDLE);
		blacklistedItems.add(Items.SKULL);
		blacklistedItems.add(Item.getItemFromBlock(Blocks.CHEST));
	}

	public ItemSkyrootSword()
	{
		super(ToolMaterial.WOOD, ItemAbilityType.PASSIVE);
	}

	@SubscribeEvent
	public void dropLoot(LivingDropsEvent event)
	{
		if (event.getSource().getSourceOfDamage() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getSourceOfDamage();

			if (player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() == this)
			{
				List<ItemStack> stacks = new ArrayList<>();

				for (EntityItem item : event.getDrops())
				{
					stacks.add(item.getEntityItem());
				}

				for (ItemStack stack : stacks)
				{
					EntityItem item = new EntityItem(event.getEntity().getEntityWorld(), event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, stack);

					event.getEntity().getEntityWorld().spawnEntityInWorld(item);
				}
			}
		}
	}

}
