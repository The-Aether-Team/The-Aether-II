package com.gildedgames.aether.client.renderer.entities.living;

import com.gildedgames.aether.common.entities.player.PlayerAetherBase;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RenderPlayerHelper
{
	public static void renderFirstPersonHand(PlayerAetherBase player, float partialTicks)
	{
		ItemStack gloveStack = player.getEquipmentInventory().getStackInSlot(2);

		if (gloveStack != null && gloveStack.getItem() instanceof ItemAetherGloves)
		{
			RenderPlayerHelper.renderGloves(player.getPlayer(), (ItemAetherGloves) gloveStack.getItem(), partialTicks);
		}
	}

	private static final ModelBiped modelBiped = new ModelBiped();

	private static void renderGloves(EntityPlayer player, ItemAetherGloves glove, float partialTicks)
	{
		RenderManager manager = Minecraft.getMinecraft().getRenderManager();
	}
}
