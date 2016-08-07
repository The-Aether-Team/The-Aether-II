package com.gildedgames.aether.client;

import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.client.models.blocks.AetherBlockModels;
import com.gildedgames.aether.client.models.items.AetherItemModels;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.client.renderer.ClientRenderHandler;
import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.util.modules.tab.TabModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	public static PlayerControllerAetherMP clientPlayerController;

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);

		AetherBlockModels.preInit();
		AetherItemModels.preInit();
		AetherRenderers.preInit();

		AetherCreativeTabs.registerTabIcons();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);

		AetherRenderers.init();

		MinecraftForge.EVENT_BUS.register(AetherMusicManager.INSTANCE);

		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		MinecraftForge.EVENT_BUS.register(new ClientRenderHandler());

		TabModule.api().getInventoryGroup().registerClientTab(new TabEquipment.Client());
	}

	@Override
	public boolean tryEquipEquipment(EntityPlayer player, ItemStack stack, EnumHand hand)
	{
		boolean result = super.tryEquipEquipment(player, stack, hand);

		if (result)
		{
			// Unfortunately we have to play the equip animation manually...
			if (player.worldObj.isRemote)
			{
				Minecraft.getMinecraft().getItemRenderer().resetEquippedProgress(EnumHand.MAIN_HAND);
			}

			player.worldObj.playSound(player, player.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.NEUTRAL, 1.0f, 1.0f);
		}

		return result;
	}

	@Override
	public void setExtendedReachDistance(EntityPlayer entity, float distance)
	{
		if (entity.worldObj instanceof WorldClient)
		{
			ClientProxy.clientPlayerController.setExtendedBlockReachDistance(distance);

			return;
		}

		super.setExtendedReachDistance(entity, distance);
	}
}
