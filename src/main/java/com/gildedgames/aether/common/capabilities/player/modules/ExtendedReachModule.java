package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ExtendedReachModule extends PlayerAetherModule
{
	private static final Item[] ITEM_SET = new Item[] { ItemsAether.valkyrie_axe, ItemsAether.valkyrie_pickaxe, ItemsAether.valkyrie_shovel,
			ItemsAether.valkyrie_lance };

	public ExtendedReachModule(PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void onUpdate()
	{
		AetherCore.PROXY.setExtendedReachDistance(this.getEntity(), this.calculateExtendedReach());
	}

	@Override
	public void write(NBTTagCompound compound)
	{

	}

	@Override
	public void read(NBTTagCompound compound)
	{

	}

	private float calculateExtendedReach()
	{
		ItemStack stack = this.getEntity().getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);

		if (stack != null)
		{
			for (Item item : ITEM_SET)
			{
				if (item == stack.getItem())
				{
					return 3.5f;
				}
			}
		}

		return 0.0f;
	}

}
