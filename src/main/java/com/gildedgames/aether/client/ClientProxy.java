package com.gildedgames.aether.client;

import com.gildedgames.aether.client.gui.tab.TabEquipment;
import com.gildedgames.aether.client.main_menu.WorldAetherOptionsOverlay;
import com.gildedgames.aether.client.models.blocks.AetherBlockModels;
import com.gildedgames.aether.client.models.items.AetherItemModels;
import com.gildedgames.aether.client.renderer.AetherRenderers;
import com.gildedgames.aether.client.renderer.ClientRenderHandler;
import com.gildedgames.aether.client.renderer.items.ItemMoaEggColorHandler;
import com.gildedgames.aether.client.renderer.items.LeatherGlovesColorHandler;
import com.gildedgames.aether.client.sound.AetherMusicManager;
import com.gildedgames.aether.common.CommonProxy;
import com.gildedgames.aether.common.util.structure.StructureInjectionEvents;
import com.gildedgames.aether.common.registry.minecraft.CreativeTabsAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.util.core.gui.viewing.MinecraftGuiViewer;
import com.gildedgames.util.modules.tab.TabModule;
import com.gildedgames.util.modules.ui.UiModule;
import com.gildedgames.util.modules.ui.common.GuiFrame;
import com.gildedgames.util.modules.ui.util.factory.Factory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
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

	public static final String MAIN_MENU_OVERLAY_ID = "aetherMainMenuOverlay";

	@Override
	public EntityPlayer getPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);

		AetherBlockModels.preInit();
		AetherItemModels.preInit();
		AetherRenderers.preInit();

		CreativeTabsAether.registerTabIcons();

		UiModule.locate().registerOverlay("worldAetherOptionsOverlay", new Factory<GuiFrame>()
		{

			@Override
			public GuiFrame create()
			{
				return new WorldAetherOptionsOverlay();
			}

		}, MinecraftGuiViewer.instance());

		/*UiModule.locate().registerOverlay(MAIN_MENU_OVERLAY_ID, new Factory<GuiFrame>()
		{

			@Override
			public GuiFrame create()
			{
				return new MainMenuOverlay();
			}

		}, MinecraftGuiViewer.instance());*/
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);

		AetherRenderers.init();

		MinecraftForge.EVENT_BUS.register(AetherMusicManager.INSTANCE);

		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		MinecraftForge.EVENT_BUS.register(new ClientRenderHandler());
		MinecraftForge.EVENT_BUS.register(StructureInjectionEvents.class);

		TabModule.api().getInventoryGroup().registerClientTab(new TabEquipment.Client());

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemMoaEggColorHandler(), ItemsAether.moa_egg);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new LeatherGlovesColorHandler(), ItemsAether.leather_gloves);
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

	@Override
	public void displayDismountMessage()
	{
		Minecraft.getMinecraft().ingameGUI.setRecordPlaying(I18n.format("mount.onboard", new Object[] {Minecraft.getMinecraft().gameSettings.keyBindSneak.getDisplayName()}), false);
	}

}
