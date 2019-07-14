package com.gildedgames.aether.common.entities.tiles;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.recipes.altar.IAltarRecipe;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.blocks.containers.BlockAltar;
import com.gildedgames.aether.common.entities.TileEntityTypesAether;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class TileEntityAltar extends TileEntitySynced implements ITickableTileEntity
{
	@OnlyIn(Dist.CLIENT)
	public double animationTicks, prevAnimationTicks;

	private ItemStack stackOnAltar = ItemStack.EMPTY;

	private int ambrosiumCount;

	public TileEntityAltar()
	{
		super(TileEntityTypesAether.ALTAR);
	}

	@Override
	public void tick()
	{
		if (this.world.isRemote())
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

					this.getWorld().addEntity(this.createEntityItemAboveAltar(stack));

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

			this.getWorld().addEntity(this.createEntityItemAboveAltar(stack));
		}

		if (!this.getStackOnAltar().isEmpty())
		{
			this.getWorld().addEntity(this.createEntityItemAboveAltar(this.getStackOnAltar()));
		}

		this.setAmbrosiumCount(0);
		this.setStackOnAltar(ItemStack.EMPTY);
	}

	public ItemEntity createEntityItemAboveAltar(ItemStack stack)
	{
		return new ItemEntity(this.getWorld(),
				this.getPos().getX() + 0.5D, this.getPos().getY() + 1.1D, this.getPos().getZ() + 0.5D, stack);
	}

	public Direction getFacing()
	{
		BlockState state = this.world.getBlockState(this.pos);

		if (state.getBlock() == BlocksAether.altar)
		{
			return state.get(BlockAltar.PROPERTY_FACING);
		}

		return Direction.NORTH;
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);

		CompoundNBT itemCompound = new CompoundNBT();

		if (this.stackOnAltar != null)
		{
			this.stackOnAltar.write(itemCompound);
		}

		compound.put("StackOnAltar", itemCompound);
		compound.putInt("AmbrosiumCount", this.ambrosiumCount);

		return compound;
	}

	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);

		CompoundNBT itemCompound = compound.getCompound("StackOnAltar");
		this.stackOnAltar = itemCompound.isEmpty() ? null : ItemStack.read(itemCompound);

		this.ambrosiumCount = compound.getInt("AmbrosiumCount");
	}

}
