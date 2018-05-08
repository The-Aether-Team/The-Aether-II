package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.items.IDropOnDeath;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import com.google.common.collect.Lists;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class PlayerPreventDropsModule extends PlayerAetherModule
{

	public final NonNullList<ItemStack> mainInventory = NonNullList.withSize(36, ItemStack.EMPTY);

	public final NonNullList<ItemStack> armorInventory = NonNullList.withSize(4, ItemStack.EMPTY);

	public final NonNullList<ItemStack> offHandInventory = NonNullList.withSize(1, ItemStack.EMPTY);

	public PlayerPreventDropsModule(PlayerAether playerAether)
	{
		super(playerAether);
	}

	public static boolean shouldKeepOnDeath(ItemStack stack)
	{
		return !(stack.getItem() instanceof ItemBlock) && !(stack.getItem() instanceof ItemDoor) && !(stack.getItem() instanceof ItemFood) && !(stack
				.getItem() instanceof IDropOnDeath);
	}

	@Override
	public void onDeath(LivingDeathEvent event)
	{
		EntityPlayer player = (EntityPlayer) event.getEntity();

		if (player.world.provider.getDimensionType() == DimensionsAether.AETHER && !player.getEntityWorld().getGameRules().getBoolean("keepInventory"))
		{
			this.setIfShouldKeep(player.inventory.mainInventory, this.mainInventory, true);
			this.setIfShouldKeep(player.inventory.armorInventory, this.armorInventory, true);
			this.setIfShouldKeep(player.inventory.offHandInventory, this.offHandInventory, true);
		}
	}

	public void setIfShouldKeep(List<ItemStack> from, List<ItemStack> to, boolean shouldKeepCheck)
	{
		for (int i = 0; i < from.size(); i++)
		{
			ItemStack stack = from.get(i);

			if (!shouldKeepCheck || shouldKeepOnDeath(stack))
			{
				to.set(i, stack);
			}
		}
	}

	@Override
	public void onDrops(PlayerDropsEvent event)
	{
		if (event.getEntityPlayer().world.provider.getDimensionType() == DimensionsAether.AETHER && !event.getEntityPlayer().getEntityWorld().getGameRules().getBoolean("keepInventory"))
		{
			List<EntityItem> toRemove = Lists.newArrayList();

			for (EntityItem item : event.getDrops())
			{
				if (item != null && shouldKeepOnDeath(item.getItem()))
				{
					toRemove.add(item);
				}
			}

			event.getDrops().removeAll(toRemove);
		}
	}

	@Override
	public void onRespawn(PlayerEvent.PlayerRespawnEvent event)
	{
		EntityPlayer player = event.player;

		if (player.world.provider.getDimensionType() == DimensionsAether.AETHER && !player.getEntityWorld().getGameRules().getBoolean("keepInventory"))
		{
			this.setIfShouldKeep(this.mainInventory, player.inventory.mainInventory, false);
			this.setIfShouldKeep(this.armorInventory, player.inventory.armorInventory, false);
			this.setIfShouldKeep(this.offHandInventory, player.inventory.offHandInventory, false);

			this.mainInventory.clear();
			this.armorInventory.clear();
			this.offHandInventory.clear();
		}
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void onUpdate()
	{

	}

	@Override
	public void write(NBTTagCompound nbtTagCompound)
	{
		NBTFunnel funnel = new NBTFunnel(nbtTagCompound);

		funnel.setStackList("mainInventory", this.mainInventory);
		funnel.setStackList("armorInventory", this.armorInventory);
		funnel.setStackList("offHandInventory", this.offHandInventory);
	}

	@Override
	public void read(NBTTagCompound nbtTagCompound)
	{
		NBTFunnel funnel = new NBTFunnel(nbtTagCompound);

		List<ItemStack> mainInventory = funnel.getStackList("mainInventory");
		List<ItemStack> armorInventory = funnel.getStackList("armorInventory");
		List<ItemStack> offHandInventory = funnel.getStackList("offHandInventory");

		this.setIfShouldKeep(mainInventory, this.mainInventory, false);
		this.setIfShouldKeep(armorInventory, this.armorInventory, false);
		this.setIfShouldKeep(offHandInventory, this.offHandInventory, false);
	}
}
