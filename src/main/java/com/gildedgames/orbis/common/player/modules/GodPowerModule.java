package com.gildedgames.orbis.common.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.common.network.packets.PacketOrbisChangePower;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.*;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Collection;

public class GodPowerModule extends PlayerAetherModule
{
	private final IGodPower[] powers;

	private final GodPowerCreative creativePower;

	private final GodPowerBlueprint blueprintPower;

	private final GodPowerFill fillPower;

	private final GodPowerDelete deletePower;

	private final GodPowerReplace replacePower;

	private int currentPowerIndex;

	public GodPowerModule(final PlayerAether playerAether)
	{
		super(playerAether);

		this.creativePower = new GodPowerCreative();
		this.fillPower = new GodPowerFill();
		this.deletePower = new GodPowerDelete();
		this.replacePower = new GodPowerReplace();
		this.blueprintPower = new GodPowerBlueprint();

		final Collection<IGodPower> powers = new ArrayList<>();

		powers.add(this.creativePower);
		powers.add(this.fillPower);
		powers.add(this.deletePower);
		powers.add(this.replacePower);
		powers.add(this.blueprintPower);

		this.powers = powers.toArray(new IGodPower[powers.size()]);
	}

	public GodPowerReplace getReplacePower()
	{
		return this.replacePower;
	}

	public GodPowerCreative getCreativePower()
	{
		return this.creativePower;
	}

	public GodPowerFill getFillPower()
	{
		return this.fillPower;
	}

	public GodPowerDelete getDeletePower()
	{
		return this.deletePower;
	}

	public GodPowerBlueprint getBlueprintPower()
	{
		return this.blueprintPower;
	}

	public IGodPower getCurrentPower()
	{
		return this.powers[this.currentPowerIndex];
	}

	public void setCurrentPower(int powerIndex)
	{
		this.currentPowerIndex = powerIndex;

		if (this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToServer(new PacketOrbisChangePower(this.currentPowerIndex));
		}
	}

	public void setCurrentPower(final Class<? extends IGodPower> clazz)
	{
		int foundIndex = -1;

		for (int i = 0; i < this.powers.length; i++)
		{
			final IGodPower p = this.powers[i];

			if (clazz.isAssignableFrom(p.getClass()))
			{
				foundIndex = i;
				break;
			}
		}

		if (foundIndex != -1)
		{
			this.currentPowerIndex = foundIndex;

			if (this.getWorld().isRemote)
			{
				NetworkingAether.sendPacketToServer(new PacketOrbisChangePower(this.currentPowerIndex));
			}
		}
	}

	public int getPowerIndex(final Class<? extends IGodPower> clazz)
	{
		int foundIndex = -1;

		for (int i = 0; i < this.powers.length; i++)
		{
			final IGodPower p = this.powers[i];

			if (clazz.isAssignableFrom(p.getClass()))
			{
				foundIndex = i;
				break;
			}
		}

		return foundIndex;
	}

	public boolean isCurrentPower(final IGodPower power)
	{
		return this.getCurrentPower() == power;
	}

	public IGodPower[] getPowers()
	{
		return this.powers;
	}

	@Override
	public void onUpdate()
	{

	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTTagCompound modules = new NBTTagCompound();

		for (final IGodPower power : this.powers)
		{
			power.write(modules);
		}

		tag.setTag("powers", modules);
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTTagCompound modules = tag.getCompoundTag("powers");

		for (final IGodPower power : this.powers)
		{
			power.read(modules);
		}
	}
}
