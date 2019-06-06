package com.gildedgames.aether.common.network;

import com.gildedgames.aether.api.dialog.IDialogSlide;
import com.gildedgames.aether.api.dialog.IDialogSlideRenderer;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.client.gui.container.GuiIcestoneCooler;
import com.gildedgames.aether.client.gui.container.GuiIncubator;
import com.gildedgames.aether.client.gui.container.guidebook.GuiGuidebookDiscovery;
import com.gildedgames.aether.client.gui.container.guidebook.GuiGuidebookInventory;
import com.gildedgames.aether.client.gui.container.guidebook.GuiGuidebookInventory;
import com.gildedgames.aether.client.gui.container.guidebook.GuiGuidebookLoreTome;
import com.gildedgames.aether.client.gui.container.guidebook.GuiGuidebookStatus;
import com.gildedgames.aether.client.gui.container.simple_crafting.ContainerMasonryBench;
import com.gildedgames.aether.client.gui.dialog.ContainerShop;
import com.gildedgames.aether.client.gui.container.guidebook.discovery.GuiGuidebookDiscoveryBestiary;
import com.gildedgames.aether.client.gui.container.guidebook.discovery.GuiGuidebookDiscoveryBiomes;
import com.gildedgames.aether.client.gui.container.guidebook.discovery.GuiGuidebookDiscoveryCharacters;
import com.gildedgames.aether.client.gui.container.guidebook.discovery.GuiGuidebookDiscoveryStructures;
import com.gildedgames.aether.client.gui.container.simple_crafting.GuiMasonryBench;
import com.gildedgames.aether.client.gui.dialog.GuiDialogViewer;
import com.gildedgames.aether.client.gui.dialog.GuiTrade;
import com.gildedgames.aether.client.gui.misc.GuiAetherLoading;
import com.gildedgames.aether.client.gui.misc.GuiAetherTeleporterNotice;
import com.gildedgames.aether.client.gui.misc.GuiPatronRewards;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.containers.ContainerCustomWorkbench;
import com.gildedgames.aether.common.containers.ContainerDialogController;
import com.gildedgames.aether.common.containers.ContainerLoadingScreen;
import com.gildedgames.aether.common.containers.ContainerTrade;
import com.gildedgames.aether.common.containers.guidebook.ContainerGuidebookInventory;
import com.gildedgames.aether.common.containers.guidebook.EmptyContainer;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.containers.tiles.ContainerIncubator;
import com.gildedgames.aether.common.dialog.DialogUtil;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiViewer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AetherGuiHandler implements IGuiHandler
{

	public static final int CUSTOM_WORKBENCH_ID = 1;

	public static final int INVENTORY_ID = 2;

	public static final int STATUS_ID = 3;

	public static final int LORE_TOME_ID = 4;

	public static final int DISCOVERY_ID = 5;

	public static final int FROSTPINE_COOLER_ID = 6;

	public static final int INCUBATOR_ID = 7;

	public static final int MASONRY_BENCH_ID = 8;

	public static final int AETHER_LOADING_ID = 9;

	public static final int PATRON_REWARDS_ID = 10;

	public static final int TELEPORTER_NOTICE_ID = 11;

	public static final int DIALOG_SHOP_ID = 12;

	public static final int DIALOG_VIEWER_ID = 13;

	public static final int TRADE_ID = 14;

	@Override
	public Container getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z)
	{
		final BlockPos pos = new BlockPos(x, y, z);
		final PlayerAether playerAether = PlayerAether.getPlayer(player);

		switch (id)
		{
			case CUSTOM_WORKBENCH_ID:
				return new ContainerCustomWorkbench(player.inventory, world, pos);
			case INVENTORY_ID:
				return new ContainerGuidebookInventory(playerAether);
			case STATUS_ID:
				return new EmptyContainer();
			case LORE_TOME_ID:
				return new EmptyContainer();
			case DISCOVERY_ID:
				return new EmptyContainer();
			case FROSTPINE_COOLER_ID:
				return new ContainerIcestoneCooler(player.inventory, (IInventory) world.getTileEntity(pos));
			case INCUBATOR_ID:
				return new ContainerIncubator(player.inventory, (IInventory) world.getTileEntity(pos));
			case MASONRY_BENCH_ID:
				return new com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench(player, new BlockPos(x, y, z));
			case AETHER_LOADING_ID:
				return new ContainerLoadingScreen();
			case DIALOG_SHOP_ID:
			{
				PlayerDialogModule dialogModule = playerAether.getModule(PlayerDialogModule.class);

				if (dialogModule.getTalkingCharacter() == null)
				{
					return null;
				}

				IShopInstanceGroup group = dialogModule.getTalkingCharacter().getShopInstanceGroup();

				if (group == null)
				{
					return null;
				}

				final int shopIndex = pos.getX();

				final IShopInstance shopInstance = group.getShopInstance(shopIndex);

				if (shopInstance == null)
				{
					return null;
				}

				return new com.gildedgames.aether.common.containers.ContainerShop(player.inventory, shopInstance);
			}
			case DIALOG_VIEWER_ID:
				return new ContainerDialogController(player);
			case TRADE_ID:
				return new ContainerTrade(player.inventory);
			default:
				return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z)
	{
		final BlockPos pos = new BlockPos(x, y, z);
		final PlayerAether playerAether = PlayerAether.getPlayer(player);

		switch (id)
		{
			case CUSTOM_WORKBENCH_ID:
				return new GuiCrafting(player.inventory, world, pos);
			case INVENTORY_ID:
				return new GuiGuidebookInventory(null, playerAether);
			case STATUS_ID:
				return new GuiGuidebookStatus(null, playerAether);
			case LORE_TOME_ID:
				return new GuiGuidebookLoreTome(null, playerAether);
			case DISCOVERY_ID:
				switch (playerAether.getProgressModule().getOpenedDiscoveryTabType())
				{
					case BESTIARY:
					{
						return new GuiGuidebookDiscoveryBestiary(null, playerAether);
					}
					case CHARACTERS:
					{
						return new GuiGuidebookDiscoveryCharacters(null, playerAether);
					}
					case BIOMES:
					{
						return new GuiGuidebookDiscoveryBiomes(null, playerAether);
					}
					case STRUCTURES:
					{
						return new GuiGuidebookDiscoveryStructures(null, playerAether);
					}
				}

			case FROSTPINE_COOLER_ID:
				return new GuiIcestoneCooler(player.inventory, (IInventory) world.getTileEntity(pos));
			case INCUBATOR_ID:
				return new GuiIncubator(player.inventory, (IInventory) world.getTileEntity(pos), pos);
			case MASONRY_BENCH_ID:
				return new ContainerMasonryBench(player, new BlockPos(x, y, z));
			case AETHER_LOADING_ID:
				return new GuiAetherLoading();
			case PATRON_REWARDS_ID:
				return new GuiPatronRewards();
			case TELEPORTER_NOTICE_ID:
				return new GuiAetherTeleporterNotice();
			case DIALOG_SHOP_ID:
			{
				PlayerDialogModule dialogModule = playerAether.getModule(PlayerDialogModule.class);

				if (dialogModule.getTalkingCharacter() == null)
				{
					return null;
				}

				IDialogSlide slide = DialogUtil.getSlide(dialogModule);

				IShopInstanceGroup group = dialogModule.getTalkingCharacter().getShopInstanceGroup();

				if (group == null)
				{
					return null;
				}

				final int shopIndex = pos.getX();

				final IShopInstance shopInstance = group.getShopInstance(shopIndex);

				if (shopInstance == null || slide == null)
				{
					return null;
				}

				GuiViewer prevViewer = null;

				if (FMLClientHandler.instance().getClient().currentScreen instanceof GuiViewer)
				{
					prevViewer = (GuiViewer) FMLClientHandler.instance().getClient().currentScreen;
				}

				GuiDialogViewer.preventDialogControllerClose = true;

				if (slide.getRenderer().isPresent())
				{
					final IDialogSlideRenderer renderer = DialogUtil.getRenderer(slide);

					return new ContainerShop(prevViewer, player, slide, renderer, shopInstance, shopIndex);
				}
				else
				{
					return new ContainerShop(prevViewer, player, slide, null, shopInstance, shopIndex);
				}
			}
			case DIALOG_VIEWER_ID:
				PlayerDialogModule dialogModule = playerAether.getModule(PlayerDialogModule.class);

				return new GuiDialogViewer(player, dialogModule, dialogModule.getCurrentSceneInstance());
			case TRADE_ID:
				GuiViewer prevViewerA = null;

				if (FMLClientHandler.instance().getClient().currentScreen instanceof GuiViewer)
				{
					prevViewerA = (GuiViewer) FMLClientHandler.instance().getClient().currentScreen;
				}

				return new GuiTrade(prevViewerA, player);
			default:
				return null;
		}
	}
}
