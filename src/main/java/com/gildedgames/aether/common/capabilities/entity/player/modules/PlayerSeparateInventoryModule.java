package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketSwitchToAetherInventory;
import com.gildedgames.aether.common.network.packets.PacketSwitchToMinecraftInventory;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerSeparateInventoryModule extends PlayerAetherModule
{
	public InventoryPlayer aetherInventory;

	public InventoryPlayer minecraftInventory;

	private boolean usingAetherInventory;

	private boolean turningOffSeparateInventories;

	public PlayerSeparateInventoryModule(final PlayerAether playerAether)
	{
		super(playerAether);

		this.aetherInventory = new InventoryPlayer(this.getEntity());
	}

	public void switchToAetherInventory()
	{
		if (!AetherCore.CONFIG.separateInventories())
		{
			return;
		}

		this.minecraftInventory = new InventoryPlayer(this.getEntity());
		this.minecraftInventory.copyInventory(this.getEntity().inventory);

		this.getEntity().inventory.copyInventory(this.aetherInventory);

		this.usingAetherInventory = true;

		if (!this.getEntity().world.isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketSwitchToAetherInventory(), (EntityPlayerMP) this.getEntity());
		}
	}

	public void switchToMinecraftInventory(boolean saveAetherInv)
	{
		if (!AetherCore.CONFIG.separateInventories() && !this.turningOffSeparateInventories)
		{
			return;
		}

		if (saveAetherInv)
		{
			this.aetherInventory = new InventoryPlayer(this.getEntity());
			this.aetherInventory.copyInventory(this.getEntity().inventory);
		}

		if (this.minecraftInventory != null)
		{
			this.getEntity().inventory.copyInventory(this.minecraftInventory);
		}

		this.usingAetherInventory = false;

		if (!this.getEntity().world.isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketSwitchToMinecraftInventory(saveAetherInv), (EntityPlayerMP) this.getEntity());
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
		if (!this.getWorld().isRemote && !AetherCore.CONFIG.separateInventories() && this.usingAetherInventory)
		{
			this.turningOffSeparateInventories = true;
			this.switchToMinecraftInventory(true);
			this.turningOffSeparateInventories = false;

			this.usingAetherInventory = false;
		}
	}

	@Override
	public void write(final NBTTagCompound compound)
	{
		compound.setBoolean("WrittenInventory", true);

		compound.setTag("Inventory", this.aetherInventory.writeToNBT(new NBTTagList()));
		compound.setInteger("SelectedItemSlot", this.aetherInventory.currentItem);

		if (this.minecraftInventory != null)
		{
			compound.setBoolean("MinecraftWrittenInventory", true);

			compound.setTag("MinecraftInventory", this.minecraftInventory.writeToNBT(new NBTTagList()));
			compound.setInteger("MinecraftSelectedItemSlot", this.minecraftInventory.currentItem);
		}

		compound.setBoolean("usingAetherInventory", this.usingAetherInventory);
	}

	@Override
	public void read(final NBTTagCompound compound)
	{
		if (compound.getBoolean("WrittenInventory"))
		{
			this.aetherInventory.readFromNBT(compound.getTagList("Inventory", 10));
			this.aetherInventory.currentItem = compound.getInteger("SelectedItemSlot");
		}

		if (compound.getBoolean("MinecraftWrittenInventory"))
		{
			this.minecraftInventory = new InventoryPlayer(this.getEntity());

			this.minecraftInventory.readFromNBT(compound.getTagList("MinecraftInventory", 10));
			this.minecraftInventory.currentItem = compound.getInteger("MinecraftSelectedItemSlot");
		}

		this.usingAetherInventory = compound.getBoolean("usingAetherInventory");
	}
}
