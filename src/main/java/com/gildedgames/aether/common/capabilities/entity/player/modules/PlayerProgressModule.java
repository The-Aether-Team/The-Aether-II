package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.client.gui.container.guidebook.discovery.DiscoveryTab;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketDiscoveryTabType;
import com.gildedgames.aether.common.network.packets.PacketProgressBooleanData;
import com.gildedgames.aether.common.network.packets.PacketTalkedTo;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map;

public class PlayerProgressModule extends PlayerAetherModule
{
	public boolean hasDiedInAether;

	private DiscoveryTab.DiscoveryTabType openedDiscoveryTabType = DiscoveryTab.DiscoveryTabType.BESTIARY;

	private Map<ResourceLocation, Boolean> hasTalkedTo = Maps.newHashMap();

	private Map<String, Boolean> booleanData = Maps.newHashMap();

	private boolean returnedToBed;

	private BlockPos beforeReturnToBed;

	public PlayerProgressModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public DiscoveryTab.DiscoveryTabType getOpenedDiscoveryTabType()
	{
		return this.openedDiscoveryTabType;
	}

	public void setOpenedDiscoveryTabType(final DiscoveryTab.DiscoveryTabType type)
	{
		this.openedDiscoveryTabType = type;

		if (this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToServer(new PacketDiscoveryTabType(type));
		}
	}

	public void setBoolean(final String key, final Boolean bool)
	{
		this.booleanData.put(key, bool);

		if (!this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketProgressBooleanData(key, bool), (EntityPlayerMP) this.getEntity());
		}
	}

	public boolean getBoolean(final String key)
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

	public void setHasDiedInAether(final boolean flag)
	{
		this.hasDiedInAether = flag;
	}

	public void setHasTalkedTo(final ResourceLocation speaker, final boolean flag)
	{
		this.hasTalkedTo.put(speaker, flag);

		if (!this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketTalkedTo(speaker, flag), (EntityPlayerMP) this.getEntity());
		}
	}

	public boolean hasTalkedTo(final ResourceLocation speaker)
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

	public void setHasReturnedToBed(final boolean flag)
	{
		this.returnedToBed = flag;
	}

	public BlockPos getBeforeReturnToBed()
	{
		return this.beforeReturnToBed;
	}

	public void setBeforeReturnToBed(final BlockPos blockPos)
	{
		this.beforeReturnToBed = blockPos;
	}

	@Override
	public void tickStart(final TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void tickEnd(final TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void onUpdate()
	{

	}

	@Override
	public void write(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		tag.setBoolean("hasDiedInAether", this.hasDiedInAether);
		tag.setBoolean("returnedToBed", this.returnedToBed);
		funnel.setMap("hasTalkedTo", this.hasTalkedTo, NBTFunnel.LOC_SETTER, NBTFunnel.BOOLEAN_SETTER);
		funnel.setPos("beforeReturnToBed", this.beforeReturnToBed);
		funnel.setMap("booleanData", this.booleanData, NBTFunnel.STRING_SETTER, NBTFunnel.BOOLEAN_SETTER);
		tag.setString("openedDiscoveryTabType", this.openedDiscoveryTabType.toString());
	}

	@Override
	public void read(final NBTTagCompound tag)
	{
		final NBTFunnel funnel = new NBTFunnel(tag);

		this.hasDiedInAether = tag.getBoolean("hasDiedInAether");
		this.returnedToBed = tag.getBoolean("returnedToBed");
		this.hasTalkedTo = funnel.getMap("hasTalkedTo", NBTFunnel.LOC_GETTER, NBTFunnel.BOOLEAN_GETTER);
		this.beforeReturnToBed = funnel.getPos("beforeReturnToBed");
		this.booleanData = funnel.getMap("booleanData", NBTFunnel.STRING_GETTER, NBTFunnel.BOOLEAN_GETTER);

		if (tag.hasKey("openedDiscoveryTabType"))
		{
			this.openedDiscoveryTabType = DiscoveryTab.DiscoveryTabType.valueOf(tag.getString("openedDiscoveryTabType"));
		}
	}
}
