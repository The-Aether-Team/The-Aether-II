package com.gildedgames.aether.common.capabilities.entity.player.modules;

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

	public PlayerSeparateInventoryModule(final PlayerAether playerAether)
	{
		super(playerAether);

		this.aetherInventory = new InventoryPlayer(this.getEntity());
	}

	public void switchToAetherInventory()
	{
		this.minecraftInventory = new InventoryPlayer(this.getEntity());
		this.minecraftInventory.copyInventory(this.getEntity().inventory);

		this.getEntity().inventory.copyInventory(this.aetherInventory);

		if (!this.getEntity().world.isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketSwitchToAetherInventory(), (EntityPlayerMP) this.getEntity());
		}
	}

	public void switchToMinecraftInventory(boolean saveAetherInv)
	{
		if (saveAetherInv)
		{
			this.aetherInventory = new InventoryPlayer(this.getEntity());
			this.aetherInventory.copyInventory(this.getEntity().inventory);
		}

		if (this.minecraftInventory != null)
		{
			this.getEntity().inventory.copyInventory(this.minecraftInventory);
		}

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
		
	}

	@Override
	public void write(final NBTTagCompound compound)
	{
		compound.setBoolean("WrittenInventory", true);
		compound.setTag("Inventory", this.aetherInventory.writeToNBT(new NBTTagList()));
		compound.setInteger("SelectedItemSlot", this.aetherInventory.currentItem);
	}

	@Override
	public void read(final NBTTagCompound compound)
	{
		if (compound.getBoolean("WrittenInventory"))
		{
			this.aetherInventory.readFromNBT(compound.getTagList("Inventory", 10));
			this.aetherInventory.currentItem = compound.getInteger("SelectedItemSlot");
		}
	}
}
