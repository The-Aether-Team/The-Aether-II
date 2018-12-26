package com.gildedgames.aether.common.shop;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.shop.ICurrencyListener;
import com.gildedgames.aether.api.shop.IGuiCurrencyValue;
import com.gildedgames.aether.api.shop.IShopCurrency;
import com.gildedgames.aether.client.gui.dialog.GuiPumpkinCurrency;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.natural.plants.BlockPlumproot;
import com.gildedgames.orbis_api.client.rect.Rect;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class ShopCurrencyPlumproot implements IShopCurrency
{
	private List<ICurrencyListener> listeners = Lists.newArrayList();

	private long value;

	public ShopCurrencyPlumproot()
	{

	}

	@Override
	public long getValue(IPlayerAether playerAether)
	{
		this.update(playerAether);

		return this.value;
	}

	@Override
	public void addValue(long value, IPlayerAether playerAether)
	{
		long old = this.value;

		for (long i = value; i > 0; i -= 64)
		{
			playerAether.getEntity().addItemStackToInventory(new ItemStack(BlocksAether.plumproot, (int) Math.min(64, i)));
		}

		this.value += value;

		this.listeners.forEach((l -> l.onCurrencyChange(old, value)));
	}

	@Override
	public void removeValue(long value, IPlayerAether playerAether)
	{
		EntityPlayer player = playerAether.getEntity();

		long count = value;

		for (int i = 0; i < player.inventory.mainInventory.size(); i++)
		{
			if (count <= 0)
			{
				return;
			}

			ItemStack stack = player.inventory.mainInventory.get(i);

			if (stack.getItem() instanceof ItemBlock)
			{
				ItemBlock block = (ItemBlock) stack.getItem();

				if (block.getBlock() instanceof BlockPlumproot)
				{
					long reduce = Math.min(64, count);

					player.inventory.decrStackSize(i, (int) reduce);

					count -= reduce;
				}
			}
		}

		long old = this.value;
		this.value -= value;

		this.listeners.forEach((l -> l.onCurrencyChange(old, value)));
	}

	@Override
	public IGuiCurrencyValue createCurrencyValueGui(Rect rect)
	{
		return new GuiPumpkinCurrency(rect);
	}

	@Override
	public IGuiCurrencyValue createBuyItemCurrencyValueGui(Rect rect)
	{
		return new GuiPumpkinCurrency(rect);
	}

	@Override
	public IGuiCurrencyValue createSellItemCurrencyValueGui(Rect rect)
	{
		return new GuiPumpkinCurrency(rect);
	}

	@Override
	public void listenForCurrency(IPlayerAether playerAether, ICurrencyListener listener)
	{
		if (!this.listeners.contains(listener))
		{
			this.listeners.add(listener);
		}
	}

	@Override
	public boolean unlistenForCurrency(IPlayerAether playerAether, ICurrencyListener listener)
	{
		return this.listeners.remove(listener);
	}

	@Override
	public void update(IPlayerAether playerAether)
	{
		EntityPlayer player = playerAether.getEntity();

		long count = 0;

		for (int i = 0; i < player.inventory.mainInventory.size(); i++)
		{
			ItemStack stack = player.inventory.mainInventory.get(i);

			if (stack.getItem() instanceof ItemBlock)
			{
				ItemBlock block = (ItemBlock) stack.getItem();

				if (block.getBlock() instanceof BlockPlumproot)
				{
					count += stack.getCount();
				}
			}
		}

		if (Long.compare(count, this.value) != 0)
		{
			long finalCount = count;
			long old = this.value;

			this.value = count;

			this.listeners.forEach((l -> l.onCurrencyChange(old, finalCount)));
		}
	}

	@Override
	public void write(NBTTagCompound tag)
	{

	}

	@Override
	public void read(NBTTagCompound tag)
	{

	}
}
