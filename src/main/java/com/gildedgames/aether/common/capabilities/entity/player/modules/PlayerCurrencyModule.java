package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.player.ICurrencyModule;
import com.gildedgames.aether.api.player.IPlayerAetherModule;
import com.gildedgames.aether.api.shop.ICurrencyListener;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.init.CurrencyAetherInit;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketCurrencyModule;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class PlayerCurrencyModule extends PlayerAetherModule implements ICurrencyModule, IPlayerAetherModule.Serializable
{
	private long currencyValue = 0;

	private final List<ICurrencyListener> listeners = Lists.newArrayList();

	public PlayerCurrencyModule(PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public long getCurrencyValue()
	{
		return this.currencyValue;
	}

	@Override
	public void listen(ICurrencyListener listener)
	{
		if (!this.listeners.contains(listener))
		{
			this.listeners.add(listener);
		}
	}

	@Override
	public boolean unlisten(ICurrencyListener listener)
	{
		return this.listeners.remove(listener);
	}

	@Override
	public void add(long gilt)
	{
		long prevCurrency = this.currencyValue;

		this.currencyValue += gilt;

		this.currencyValue = Math.min(Math.max(0L, this.currencyValue), Long.MAX_VALUE);

		if (!this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketCurrencyModule(this), (EntityPlayerMP) this.getEntity());
		}

		this.listeners.forEach((l) -> l.onCurrencyChange(prevCurrency, this.currencyValue));
	}

	@Override
	public void take(long gilt)
	{
		this.add(-gilt);
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		tag.setLong("currencyValue", this.currencyValue);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		long prevCurrency = this.currencyValue;

		this.currencyValue = tag.getLong("currencyValue");

		this.listeners.forEach((l) -> l.onCurrencyChange(prevCurrency, this.currencyValue));
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return AetherCore.getResource("currency");
	}
}
