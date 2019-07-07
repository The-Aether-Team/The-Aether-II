package com.gildedgames.aether.common.events.listeners.items;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.init.MaterialsAether;
import com.gildedgames.aether.common.items.tools.handlers.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class ItemToolListener
{
	private static final Map<String, IToolEventHandler> handlers = new HashMap<>();

	static
	{
		handlers.put(MaterialsAether.SKYROOT_TOOL.name(), new ItemSkyrootToolHandler());
		handlers.put(MaterialsAether.HOLYSTONE_TOOL.name(), new ItemHolystoneToolHandler());
		handlers.put(MaterialsAether.ARKENIUM_TOOL.name(), new ItemArkeniumToolHandler());
		handlers.put(MaterialsAether.ZANITE_TOOL.name(), new ItemZaniteToolHandler());
		handlers.put(MaterialsAether.GRAVITITE_TOOL.name(), new ItemGravititeToolHandler());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onTooltip(final ItemTooltipEvent event)
	{
		if (event.getItemStack().getItem() instanceof ItemTool)
		{
			final String material = ((ItemTool) event.getItemStack().getItem()).getToolMaterialName();

			final IToolEventHandler handler = handlers.get(material);

			if (handler == null)
			{
				return;
			}

			handler.addInformation(event.getItemStack(), event.getToolTip());
		}
	}

	@SubscribeEvent
	public static void onRightClickItem(final PlayerInteractEvent.RightClickItem event)
	{
		if (event.getItemStack().getItem() instanceof ItemTool)
		{
			final String material = ((ItemTool) event.getItemStack().getItem()).getToolMaterialName();

			final IToolEventHandler handler = handlers.get(material);

			if (handler == null)
			{
				return;
			}

			handler.onRightClickItem(event.getEntityPlayer(), event.getHand());
		}
	}

	@SubscribeEvent
	public static void onBlockActivated(final PlayerInteractEvent.RightClickBlock event)
	{
		if (event.getItemStack().getItem() instanceof ItemTool)
		{
			final String material = ((ItemTool) event.getItemStack().getItem()).getToolMaterialName();

			final IToolEventHandler handler = handlers.get(material);

			if (handler == null)
			{
				return;
			}

			boolean result = handler.onRightClickBlock(event.getWorld(), event.getPos(), event.getEntityPlayer(), event.getHand(), event.getFace());

			if (result)
			{
				event.setCanceled(true);
				event.setCancellationResult(EnumActionResult.SUCCESS);
			}
		}
	}

	@SubscribeEvent
	public static void onBlockHarvested(final BlockEvent.HarvestDropsEvent event)
	{
		if (event.getHarvester() == null)
		{
			return;
		}

		PlayerAether playerAether = PlayerAether.getPlayer(event.getHarvester());

		ItemStack stack = event.getHarvester().getHeldItem(EnumHand.MAIN_HAND);

		if (stack.isEmpty() && playerAether.getLastDestroyedStack() != null)
		{
			stack = playerAether.getLastDestroyedStack();
			playerAether.setLastDestroyedStack(null);
		}

		if (stack.getItem() instanceof ItemTool)
		{
			final String material = ((ItemTool) stack.getItem()).getToolMaterialName();

			final IToolEventHandler handler = handlers.get(material);

			if (handler == null)
			{
				return;
			}

			handler.onHarvestBlock(stack, event.getWorld(), event.getState(), event.getPos(), event.getHarvester(), event.getDrops());
		}
	}

	@SubscribeEvent
	public static void onItemDestroyed(final PlayerDestroyItemEvent event)
	{
		if (event.getEntityPlayer() == null)
		{
			return;
		}

		PlayerAether playerAether = PlayerAether.getPlayer(event.getEntityPlayer());

		if (event.getOriginal().getItem() instanceof ItemTool)
		{
			playerAether.setLastDestroyedStack(event.getOriginal());
		}
	}

	@SubscribeEvent
	public static void onCalculateBreakSpeed(final PlayerEvent.BreakSpeed event)
	{
		final ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);

		if (stack.getItem() instanceof ItemTool)
		{
			final String material = ((ItemTool) stack.getItem()).getToolMaterialName();

			final IToolEventHandler handler = handlers.get(material);

			if (handler == null)
			{
				return;
			}

			event.setNewSpeed(handler.getBreakSpeed(stack, event.getEntityPlayer().getEntityWorld(), event.getState(), event.getPos(), event.getEntityPlayer(),
					event.getOriginalSpeed()));
		}
	}

	@SubscribeEvent
	public static void onEntityHit(final AttackEntityEvent event)
	{
		final ItemStack stack = event.getEntityLiving().getHeldItem(EnumHand.MAIN_HAND);

		if (stack.getItem() instanceof ItemTool)
		{
			final String material = ((ItemTool) stack.getItem()).getToolMaterialName();

			final IToolEventHandler handler = handlers.get(material);

			if (handler == null)
			{
				return;
			}

			handler.onEntityHit(stack, event.getTarget(), event.getEntityLiving());
		}
	}

	public static boolean onEntityHit(final ItemStack stack, final Item.ToolMaterial material, final EntityLivingBase target, final EntityLivingBase attacker)
	{
		final IToolEventHandler handler = handlers.get(material.name());

		if (handler != null)
		{
			handler.onEntityHit(stack, target, attacker);
		}

		return true;
	}
}
