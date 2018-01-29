package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.items.ItemAbilityType;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.MaterialsAether;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

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
	public static void dropLoot(final LivingDropsEvent event)
	{
		if (event.getSource().getTrueSource() instanceof EntityPlayer)
		{
			final EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();

			final ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);

			boolean providesDrops = false;

			if (held.getItem() == ItemsAether.skyroot_sword)
			{
				providesDrops = true;
			}
			else if (held.getItem() instanceof ItemTool)
			{
				final String material = ((ItemTool) held.getItem()).getToolMaterialName();

				providesDrops = Objects.equals(material, MaterialsAether.SKYROOT_TOOL.name());
			}

			if (providesDrops)
			{
				final List<ItemStack> stacks = new ArrayList<>();

				for (final EntityItem item : event.getDrops())
				{
					stacks.add(item.getItem());
				}

				for (final ItemStack stack : stacks)
				{
					final EntityItem item = new EntityItem(player.getEntityWorld(), player.posX, player.posY, player.posZ, stack);

					player.getEntityWorld().spawnEntity(item);
				}
			}
		}
	}

}
