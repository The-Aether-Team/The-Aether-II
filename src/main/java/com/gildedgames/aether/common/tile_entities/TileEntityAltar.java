package com.gildedgames.aether.common.tile_entities;

import com.gildedgames.aether.api.registry.altar.IAltarRecipe;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.construction.BlockAltar;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityAltar extends TileEntity implements ITickable
{
	@SideOnly(Side.CLIENT)
	public double animationTicks, prevAnimationTicks;

	private ItemStack stackOnAltar;

	private int ambrosiumCount;

	@Override
	public void update()
	{
		if (this.worldObj.isRemote)
		{
			this.prevAnimationTicks = this.animationTicks;

			this.animationTicks++;
		}
	}

	public ItemStack getStackOnAltar()
	{
		return this.stackOnAltar;
	}

	public void setStackOnAltar(ItemStack stack)
	{
		this.stackOnAltar = stack;

		this.sendUpdates();
	}

	public int getAmbrosiumCount()
	{
		return this.ambrosiumCount;
	}

	public void setAmbrosiumCount(int count)
	{
		this.ambrosiumCount = count;

		this.sendUpdates();
	}

	public void removeAmbrosiumShard()
	{
		this.setAmbrosiumCount(this.getAmbrosiumCount() - 1);
	}

	public void addAmbrosiumShard()
	{
		this.setAmbrosiumCount(this.getAmbrosiumCount() + 1);
	}

	public void attemptCrafting()
	{
		if (this.getStackOnAltar() != null && this.getAmbrosiumCount() > 0)
		{
			IAltarRecipe recipe = AetherCore.PROXY.getRecipeManager().getAltarRegistry().getMatchingRecipe(this.getStackOnAltar());

			if (recipe != null)
			{
				int cost = recipe.getAmbrosiumCost(this.getStackOnAltar());

				if (this.getAmbrosiumCount() >= cost)
				{
					ItemStack stack = recipe.getOutput(this.getStackOnAltar());

					this.getWorld().spawnEntityInWorld(this.createEntityItemAboveAltar(stack));

					this.ambrosiumCount -= cost;
					this.setStackOnAltar(null);
				}
			}
		}
	}

	public void dropContents()
	{
		if (this.getAmbrosiumCount() > 0)
		{
			ItemStack stack = new ItemStack(ItemsAether.ambrosium_shard, this.getAmbrosiumCount());

			this.getWorld().spawnEntityInWorld(this.createEntityItemAboveAltar(stack));
		}

		if (this.getStackOnAltar() != null)
		{
			this.getWorld().spawnEntityInWorld(this.createEntityItemAboveAltar(this.getStackOnAltar()));
		}

		this.setAmbrosiumCount(0);
		this.setStackOnAltar(null);
	}

	public EntityItem createEntityItemAboveAltar(ItemStack stack)
	{
		return new EntityItem(this.getWorld(), this.getPos().getX() + 0.5D, this.getPos().getY() + 1.1D, this.getPos().getZ() + 0.5D, stack);
	}

	public EnumFacing getFacing()
	{
		return this.worldObj.getBlockState(this.pos).getValue(BlockAltar.PROPERTY_FACING);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagCompound itemCompound = new NBTTagCompound();

		if (this.stackOnAltar != null)
		{
			this.stackOnAltar.writeToNBT(itemCompound);
		}

		compound.setTag("StackOnAltar", itemCompound);
		compound.setInteger("AmbrosiumCount", this.ambrosiumCount);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		NBTTagCompound itemCompound = compound.getCompoundTag("StackOnAltar");
		this.stackOnAltar = itemCompound.hasNoTags() ? null : ItemStack.loadItemStackFromNBT(itemCompound);

		this.ambrosiumCount = compound.getInteger("AmbrosiumCount");
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);

		return new SPacketUpdateTileEntity(this.pos, 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}

	private void sendUpdates()
	{
		IBlockState state = this.worldObj.getBlockState(this.pos);

		this.worldObj.notifyBlockUpdate(this.pos, state, state, 3);

		this.markDirty();
	}
}
