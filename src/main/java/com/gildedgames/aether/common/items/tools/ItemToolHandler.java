package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.items.tools.handlers.*;
import com.gildedgames.aether.common.registry.content.MaterialsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class ItemToolHandler
{
	private static Map<Item.ToolMaterial, IToolEventHandler> handlers = new HashMap<>();

	static
	{
		handlers.put(MaterialsAether.SKYROOT_TOOL, new ItemSkyrootToolHandler());
		handlers.put(MaterialsAether.HOLYSTONE_TOOL, new ItemHolystoneToolHandler());
		handlers.put(MaterialsAether.ARKENIUM_TOOL, new ItemArkeniumToolHandler());
		handlers.put(MaterialsAether.ZANITE_TOOL, new ItemZaniteToolHandler());
		handlers.put(MaterialsAether.GRAVITITE_TOOL, new ItemGravititeToolHandler());
	}

	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent event)
	{
		if (event.getItemStack().getItem() instanceof ItemTool)
		{
			Item.ToolMaterial material = ((ItemTool) event.getItemStack().getItem()).getToolMaterial();

			IToolEventHandler handler = handlers.get(material);

			if (handler == null)
			{
				return;
			}

			handler.addInformation(event.getItemStack(), event.getToolTip());
		}
	}

	@SubscribeEvent
	public static void onBlockActivated(PlayerInteractEvent.RightClickItem event)
	{
		if (event.getItemStack().getItem() instanceof ItemTool)
		{
			Item.ToolMaterial material = ((ItemTool) event.getItemStack().getItem()).getToolMaterial();

			IToolEventHandler handler = handlers.get(material);

			if (handler == null)
			{
				return;
			}

			handler.onRightClickItem(event.getEntityPlayer(), event.getHand());
		}
	}

	@SubscribeEvent
	public static void onBlockActivated(PlayerInteractEvent.RightClickBlock event)
	{
		if (event.getItemStack().getItem() instanceof ItemTool)
		{
			Item.ToolMaterial material = ((ItemTool) event.getItemStack().getItem()).getToolMaterial();

			IToolEventHandler handler = handlers.get(material);

			if (handler == null)
			{
				return;
			}

			handler.onRightClickBlock(event.getWorld(), event.getPos(), event.getEntityPlayer(), event.getHand(), event.getFace());
		}
	}

	@SubscribeEvent
	public static void onBlockHarvested(BlockEvent.HarvestDropsEvent event)
	{
		if (event.getHarvester() == null)
		{
			return;
		}

		ItemStack stack = event.getHarvester().getHeldItem(EnumHand.MAIN_HAND);

		if (stack.getItem() instanceof ItemTool)
		{
			Item.ToolMaterial material = ((ItemTool) stack.getItem()).getToolMaterial();

			IToolEventHandler handler = handlers.get(material);

			if (handler == null)
			{
				return;
			}

			handler.onHarvestBlock(stack, event.getWorld(), event.getState(), event.getPos(), event.getHarvester(), event.getDrops());
		}
	}

	@SubscribeEvent
	public static void onCalculateBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);

		if (stack.getItem() instanceof ItemTool)
		{
			Item.ToolMaterial material = ((ItemTool) stack.getItem()).getToolMaterial();

			IToolEventHandler handler = handlers.get(material);

			if (handler == null)
			{
				return;
			}

			event.setNewSpeed(handler.getBreakSpeed(stack, event.getEntityPlayer().getEntityWorld(), event.getState(), event.getPos(), event.getEntityPlayer(), event.getOriginalSpeed()));
		}
	}

	@SubscribeEvent
	public static void onEntityHit(AttackEntityEvent event)
	{
		ItemStack stack = event.getEntityLiving().getHeldItem(EnumHand.MAIN_HAND);

		if (stack.getItem() instanceof ItemTool)
		{
			Item.ToolMaterial material = ((ItemTool) stack.getItem()).getToolMaterial();

			IToolEventHandler handler = handlers.get(material);

			if (handler == null)
			{
				return;
			}

			handler.onEntityHit(stack, event.getTarget(), event.getEntityLiving());
		}
	}

	public static boolean onEntityHit(ItemStack stack, Item.ToolMaterial material, EntityLivingBase target, EntityLivingBase attacker)
	{
		IToolEventHandler handler = handlers.get(material);

		if (handler != null)
		{
			handler.onEntityHit(stack, target, attacker);
		}

		return true;
	}
}
