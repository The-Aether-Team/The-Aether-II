package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.common.items.blocks.ItemBlockPresent;
import com.gildedgames.aether.common.items.other.ItemWrappingPaper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPresent extends TileEntity
{

	private ItemBlockPresent.PresentData presentData = new ItemBlockPresent.PresentData();

	public ItemBlockPresent.PresentData getPresentData()
	{
		return this.presentData;
	}

	public void setPresentData(ItemBlockPresent.PresentData data)
	{
		this.presentData = data;
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

		double x2 = this.getPos().getX() + ((this.world.rand.nextFloat() * radius) + (1.0F - radius) * 0.5D);
		double y2 = this.getPos().getY() + ((this.world.rand.nextFloat() * radius) + (1.0F - radius) * 0.5D);
		double z2 = this.getPos().getZ() + ((this.world.rand.nextFloat() * radius) + (1.0F - radius) * 0.5D);

		if (stack.getItem() instanceof ItemMonsterPlacer)
		{
			ItemMonsterPlacer.spawnCreature(this.world, ItemMonsterPlacer.getNamedIdFrom(stack), x2, y2, z2);
			return;
		}

		Entity entity;

		if (stack.getItem() == Item.getItemFromBlock(Blocks.TNT))
		{
			EntityTNTPrimed tnt = new EntityTNTPrimed(this.world,
					this.getPos().getX() + 0.5D, this.getPos().getY() + 0.5D, this.getPos().getZ() + 0.5D, null);
			tnt.setFuse(20);

			entity = tnt;
		}
		else
		{
			ItemEntity item = new ItemEntity(this.world, x2, y2, z2, stack);
			item.setPickupDelay(10)
			;
			entity = item;
		}

		this.world.spawnEntity(entity);
	}

	@Override
	public CompoundNBT writeToNBT(CompoundNBT compound)
	{
		super.writeToNBT(compound);
		compound.put("data", this.getPresentData().writeToNBT(new CompoundNBT()));

		return compound;
	}

	@Override
	public void readFromNBT(CompoundNBT compound)
	{
		super.readFromNBT(compound);
		this.presentData = ItemBlockPresent.PresentData.readFromNBT(compound.getCompound("data"));
	}

	// Do not sync what items the present contains to the client!

	@Override
	public CompoundNBT getUpdateTag()
	{
		CompoundNBT tag = super.getUpdateTag();

		tag.put("dye", this.getPresentData().getDye().writeToNBT(new CompoundNBT()));

		return tag;
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		CompoundNBT compound = this.getUpdateTag();

		return new SUpdateTileEntityPacket(this.pos, 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, SUpdateTileEntityPacket packet)
	{
		CompoundNBT nbt = packet.getNbtCompound();
		this.getPresentData().setDye(ItemWrappingPaper.PresentDyeData.readFromNBT(nbt.getCompound("dye")));
	}

	public void sync()
	{
		BlockState state = this.world.getBlockState(this.pos);

		this.world.notifyBlockUpdate(this.pos, state, state, 3);

		this.markDirty();
	}

}
