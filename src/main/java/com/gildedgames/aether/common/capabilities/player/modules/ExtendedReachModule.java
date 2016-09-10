package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.tools.ItemValkyrieTool;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

public class ExtendedReachModule extends PlayerAetherModule
{

	public ExtendedReachModule(PlayerAetherImpl playerAether)
	{
		super(playerAether);
	}

	@Override
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		AetherCore.PROXY.setExtendedReachDistance(this.getPlayer(), this.calculateExtendedReach());
	}

	private float calculateExtendedReach()
	{
		ItemStack stack = this.getPlayer().getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);

		if (stack != null)
		{
			Item item = stack.getItem();

			if (item instanceof ItemValkyrieTool || item == ItemsAether.valkyrie_lance)
			{
				return 3.5f;
			}
		}

		return 0.0f;
	}

}
