package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.player.IPlayerAetherModule;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketProgressBooleanData;
import com.gildedgames.aether.common.network.packets.PacketTalkedTo;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class PlayerProgressModule extends PlayerAetherModule implements IPlayerAetherModule.Serializable
{

	public boolean hasDiedInAether;

	private Map<ResourceLocation, Boolean> hasTalkedTo = Maps.newHashMap();

	private Map<String, Boolean> booleanData = Maps.newHashMap();

	private boolean returnedToBed;

	private BlockPos beforeReturnToBed;

	public PlayerProgressModule(PlayerAether playerAether)
	{
		super(playerAether);
	}

	public void setBoolean(String key, Boolean bool)
	{
		this.booleanData.put(key, bool);

		if (!this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketProgressBooleanData(key, bool), (EntityPlayerMP) this.getEntity());
		}
	}

	public boolean getBoolean(String key)
	{
		if (!this.booleanData.containsKey(key))
		{
			return false;
		}

		return this.booleanData.get(key);
	}

	public boolean hasDiedInAether()
	{
		return this.hasDiedInAether;
	}

	public void setHasDiedInAether(boolean flag)
	{
		this.hasDiedInAether = flag;
	}

	public void setHasTalkedTo(ResourceLocation speaker, boolean flag)
	{
		this.hasTalkedTo.put(speaker, flag);

		if (!this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketTalkedTo(speaker, flag), (EntityPlayerMP) this.getEntity());
		}
	}

	public boolean hasTalkedTo(ResourceLocation speaker)
	{
		if (!this.hasTalkedTo.containsKey(speaker))
		{
			return false;
		}

		return this.hasTalkedTo.get(speaker);
	}

	public boolean hasReturnedToBed()
	{
		return this.returnedToBed;
	}

	public void setHasReturnedToBed(boolean flag)
	{
		this.returnedToBed = flag;
	}

	public BlockPos getBeforeReturnToBed()
	{
		return this.beforeReturnToBed;
	}

	public void setBeforeReturnToBed(BlockPos blockPos)
	{
		this.beforeReturnToBed = blockPos;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		tag.setBoolean("hasDiedInAether", this.hasDiedInAether);
		tag.setBoolean("returnedToBed", this.returnedToBed);
		funnel.setMap("hasTalkedTo", this.hasTalkedTo, NBTFunnel.LOC_SETTER, NBTFunnel.BOOLEAN_SETTER);
		funnel.setPos("beforeReturnToBed", this.beforeReturnToBed);
		funnel.setMap("booleanData", this.booleanData, NBTFunnel.STRING_SETTER, NBTFunnel.BOOLEAN_SETTER);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTFunnel funnel = new NBTFunnel(tag);

		this.hasDiedInAether = tag.getBoolean("hasDiedInAether");
		this.returnedToBed = tag.getBoolean("returnedToBed");
		this.hasTalkedTo = funnel.getMap("hasTalkedTo", NBTFunnel.LOC_GETTER, NBTFunnel.BOOLEAN_GETTER);
		this.beforeReturnToBed = funnel.getPos("beforeReturnToBed");
		this.booleanData = funnel.getMap("booleanData", NBTFunnel.STRING_GETTER, NBTFunnel.BOOLEAN_GETTER);
	}

	@Override
	public ResourceLocation getIdentifier()
	{
		return AetherCore.getResource("progress");
	}
}
