package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.recipes.altar.IAltarRecipe;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.containers.BlockAltar;
import com.gildedgames.aether.common.entities.tiles.util.TileEntitySynced;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class TileEntityAltar extends TileEntitySynced implements ITickable
{
	@SideOnly(Side.CLIENT)
	public double animationTicks, prevAnimationTicks;

	private ItemStack stackOnAltar = ItemStack.EMPTY;

	private int ambrosiumCount;

	@Override
	public void update()
	{
		if (this.world.isRemote)
		{
			this.prevAnimationTicks = this.animationTicks;

			this.animationTicks++;
		}
	}

	@Nonnull
	public ItemStack getStackOnAltar()
	{
		return this.stackOnAltar;
	}

	public void setStackOnAltar(@Nonnull ItemStack stack)
	{
		this.stackOnAltar = stack;

		this.sendUpdatesToClients();
	}

	public int getAmbrosiumCount()
	{
		return this.ambrosiumCount;
	}

	public void setAmbrosiumCount(int count)
	{
		this.ambrosiumCount = count;

		this.sendUpdatesToClients();
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
		if (!this.getStackOnAltar().isEmpty() && this.getAmbrosiumCount() > 0)
		{
			IAltarRecipe recipe = AetherAPI.content().altar().getMatchingRecipeFromInput(this.getStackOnAltar());

			if (recipe != null)
			{
				int cost = recipe.getAmbrosiumCost(this.getStackOnAltar());

				if (this.getAmbrosiumCount() >= cost)
				{
					ItemStack stack = recipe.getOutput(this.getStackOnAltar());

					this.getWorld().spawnEntity(this.createEntityItemAboveAltar(stack));

					this.ambrosiumCount -= cost;
					this.setStackOnAltar(ItemStack.EMPTY);
				}
			}
		}
	}

	public void dropContents()
	{
		if (this.getAmbrosiumCount() > 0)
		{
			ItemStack stack = new ItemStack(ItemsAether.ambrosium_shard, this.getAmbrosiumCount());

			this.getWorld().spawnEntity(this.createEntityItemAboveAltar(stack));
		}

		if (!this.getStackOnAltar().isEmpty())
		{
			this.getWorld().spawnEntity(this.createEntityItemAboveAltar(this.getStackOnAltar()));
		}

		this.setAmbrosiumCount(0);
		this.setStackOnAltar(ItemStack.EMPTY);
	}

	public EntityItem createEntityItemAboveAltar(ItemStack stack)
	{
		return new EntityItem(this.getWorld(),
				this.getPos().getX() + 0.5D, this.getPos().getY() + 1.1D, this.getPos().getZ() + 0.5D, stack);
	}

	public EnumFacing getFacing()
	{
		IBlockState state = this.world.getBlockState(this.pos);

		if (state.getBlock() == BlocksAether.altar)
		{
			return state.getValue(BlockAltar.PROPERTY_FACING);
		}

		return EnumFacing.NORTH;
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
		this.stackOnAltar = itemCompound.hasNoTags() ? null : new ItemStack(itemCompound);

		this.ambrosiumCount = compound.getInteger("AmbrosiumCount");
	}

}
