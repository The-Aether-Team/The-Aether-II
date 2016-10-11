package com.gildedgames.aether.common.tile_entities;

import com.gildedgames.aether.common.entities.projectiles.EntitySentryVaultbox;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.blocks.ItemBlockPresent;
import com.gildedgames.aether.common.items.misc.ItemWrappingPaper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPresent extends TileEntity
{

	private ItemBlockPresent.PresentData presentData = new ItemBlockPresent.PresentData();

	public void setPresentData(ItemBlockPresent.PresentData data)
	{
		this.presentData = data;
	}

	public ItemBlockPresent.PresentData getPresentData()
	{
		return this.presentData;
	}

	public void dropItem()
	{
		double radius = 0.7F;
		ItemStack stack = this.getPresentData().getStack();

		if (stack == null)
		{
			return;
		}

		this.getPresentData().setStack(null);

		double x2 = this.getPos().getX() + ((this.worldObj.rand.nextFloat() * radius) + (1.0F - radius) * 0.5D);
		double y2 = this.getPos().getY() + ((this.worldObj.rand.nextFloat() * radius) + (1.0F - radius) * 0.5D);
		double z2 = this.getPos().getZ() + ((this.worldObj.rand.nextFloat() * radius) + (1.0F - radius) * 0.5D);

		if (stack.getItem() instanceof ItemMonsterPlacer)
		{
			ItemMonsterPlacer.spawnCreature(this.worldObj, ItemMonsterPlacer.getEntityIdFromItem(stack), x2, y2, z2);
			return;
		}

		Entity entity;

		if (stack.getItem() == Item.getItemFromBlock(Blocks.TNT))
		{
			EntityTNTPrimed tnt = new EntityTNTPrimed(this.worldObj, this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D, null);
			tnt.setFuse(20);
			entity = tnt;
		}
		else if (stack.getItem() == ItemsAether.sentry_vaultbox)
		{
			entity = new EntitySentryVaultbox(this.worldObj, x2, y2, z2);
		}
		else
		{
			EntityItem item = new EntityItem(this.worldObj, x2, y2, z2, stack);
			item.setPickupDelay(10);
			entity = item;
		}

		this.worldObj.spawnEntityInWorld(entity);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("data", this.getPresentData().writeToNBT(new NBTTagCompound()));

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.presentData = ItemBlockPresent.PresentData.readFromNBT(compound.getCompoundTag("data"));
	}

	// Do not sync what items the present contains to the client!

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound tag = super.getUpdateTag();

		tag.setTag("dye", this.getPresentData().getDye().writeToNBT(new NBTTagCompound()));

		return tag;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound compound = this.getUpdateTag();

		return new SPacketUpdateTileEntity(this.pos, 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet)
	{
		NBTTagCompound nbt = packet.getNbtCompound();
		this.getPresentData().setDye(ItemWrappingPaper.PresentDyeData.readFromNBT(nbt.getCompoundTag("dye")));
	}

	public void sync()
	{
		IBlockState state = this.worldObj.getBlockState(this.pos);

		this.worldObj.notifyBlockUpdate(this.pos, state, state, 3);

		this.markDirty();
	}

}
