package com.gildedgames.aether.common.tile_entities;

import com.gildedgames.aether.common.recipes.RecipesAether;
import com.gildedgames.aether.common.recipes.altar.IAltarRecipe;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAltar extends TileEntity
{
	private int ambrosiumCount;

	private ItemStack itemToEnchant;

	public int getAmbrosiumCount()
	{
		return this.ambrosiumCount;
	}

	public void setAmbrosiumCount(int count)
	{
		this.ambrosiumCount = count;

		this.worldObj.markBlockForUpdate(pos);
		this.markDirty();
	}

	public void setItemToEnchant(ItemStack stack)
	{
		this.itemToEnchant = stack;

		this.worldObj.markBlockForUpdate(pos);
		this.markDirty();
	}

	public ItemStack getItemToEnchant()
	{
		return this.itemToEnchant;
	}

	public void update()
	{
		IAltarRecipe recipe = RecipesAether.altarRegistry.getMatchingRecipe(this.itemToEnchant, this.ambrosiumCount);

		if (recipe != null)
		{
			this.ambrosiumCount -= recipe.getAmbrosiumNeeded();
			this.setItemToEnchant(null);

			EntityItem entity = new EntityItem(this.worldObj, this.pos.getX() + 0.5D, this.pos.getY() + 0.8D, this.pos.getZ() + 0.5D, recipe.getOutput().copy());
			this.worldObj.spawnEntityInWorld(entity);
		}
	}

	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		if (this.itemToEnchant != null)
		{
			NBTTagCompound itemToEnchantNBT = new NBTTagCompound();
			this.itemToEnchant.writeToNBT(itemToEnchantNBT);

			compound.setTag("ItemToEnchant", itemToEnchantNBT);
		}

		compound.setInteger("AmbrosiumCount", this.ambrosiumCount);
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		if (compound.hasKey("ItemToEnchant"))
		{
			NBTTagCompound itemToEnchantNBT = compound.getCompoundTag("ItemToEnchant");
			this.itemToEnchant = ItemStack.loadItemStackFromNBT(itemToEnchantNBT);
		}
		else
		{
			this.itemToEnchant = null;
		}

		this.ambrosiumCount = compound.getInteger("AmbrosiumCount");
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);

		return new S35PacketUpdateTileEntity(pos, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}
}
