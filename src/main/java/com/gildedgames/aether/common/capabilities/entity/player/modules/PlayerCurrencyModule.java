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
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class PlayerCurrencyModule extends PlayerAetherModule implements ICurrencyModule, IPlayerAetherModule.Serializable
{
	private static final int[] DENOMINATIONS = { CurrencyAetherInit.GILTAENI, CurrencyAetherInit.GILTAEN, CurrencyAetherInit.GILTAE, CurrencyAetherInit.GILT };

	private long currencyValue = 0;

	private int gilt, giltae, giltaen, giltaeni;

	private final List<ICurrencyListener> listeners = Lists.newArrayList();

	public PlayerCurrencyModule(PlayerAether playerAether)
	{
		super(playerAether);
	}

	public static int[] breakUpCurrency(long currencyValue)
	{
		int[] count = new int[DENOMINATIONS.length];

		for (int i = 0; i < DENOMINATIONS.length; i++)
		{
			while (currencyValue >= DENOMINATIONS[i])
			{
				count[i]++;
				currencyValue -= DENOMINATIONS[i];
			}
		}

		return count;
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

	private void refreshDenominators()
	{
		int[] brokenUp = breakUpCurrency(this.currencyValue);

		this.giltaeni = brokenUp[0];
		this.giltaen = brokenUp[1];
		this.giltae = brokenUp[2];
		this.gilt = brokenUp[3];
	}

	@Override
	public void add(long gilt)
	{
		long prevCurrency = this.currencyValue;

		this.currencyValue += gilt;

		this.currencyValue = Math.min(Math.max(0L, this.currencyValue), Long.MAX_VALUE);

		this.refreshDenominators();

		if (!this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketCurrencyModule(this), (ServerPlayerEntity) this.getEntity());
		}

		this.listeners.forEach((l) -> l.onCurrencyChange(prevCurrency, this.currencyValue));
	}

	@Override
	public void take(long gilt)
	{
		this.add(-gilt);
	}

	public int getGilt()
	{
		return this.gilt;
	}

	public int getGiltae()
	{
		return this.giltae;
	}

	public int getGiltaen()
	{
		return this.giltaen;
	}

	public int getGiltaeni()
	{
		return this.giltaeni;
	}

	@Override
	public void write(CompoundNBT tag)
	{
		tag.putLong("currencyValue", this.currencyValue);
	}

	@Override
	public void read(CompoundNBT tag)
	{
		long prevCurrency = this.currencyValue;

		this.currencyValue = tag.getLong("currencyValue");

		this.refreshDenominators();

		this.listeners.forEach((l) -> l.onCurrencyChange(prevCurrency, this.currencyValue));
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return AetherCore.getResource("currency");
	}
}
