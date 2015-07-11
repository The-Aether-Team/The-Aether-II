package com.gildedgames.aether.common.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemSkyrootBucket extends Item
{
	private Block containedLiquid;

	public ItemSkyrootBucket(Block liquid)
	{
		this.containedLiquid = liquid;

		if (liquid != Blocks.air)
		{
			this.setMaxStackSize(1);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		boolean searchForLiquids = this.containedLiquid == Blocks.air;
		MovingObjectPosition raytrace = this.getMovingObjectPositionFromPlayer(world, player, searchForLiquids);

		if (raytrace == null)
		{
			return stack;
		}
		else
		{
			ItemStack eventOverride = net.minecraftforge.event.ForgeEventFactory.onBucketUse(player, world, stack, raytrace);

			if (eventOverride != null)
			{
				return eventOverride;
			}

			if (raytrace.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
			{
				BlockPos pos = raytrace.getBlockPos();

				if (!world.isBlockModifiable(player, pos))
				{
					return stack;
				}

				if (searchForLiquids)
				{
					if (!player.canPlayerEdit(pos.offset(raytrace.sideHit), raytrace.sideHit, stack))
					{
						return stack;
					}

					IBlockState state = world.getBlockState(pos);
					Material material = state.getBlock().getMaterial();

					if (material == Material.water && (Integer) state.getValue(BlockLiquid.LEVEL) == 0)
					{
						world.setBlockToAir(pos);
						player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
						return this.fillBucket(stack, player, ItemsAether.skyroot_water_bucket);
					}
				}
				else
				{
					if (this.containedLiquid == Blocks.air)
					{
						return new ItemStack(ItemsAether.skyroot_bucket);
					}

					BlockPos offsetPos = pos.offset(raytrace.sideHit);

					if (!player.canPlayerEdit(offsetPos, raytrace.sideHit, stack))
					{
						return stack;
					}

					if (this.tryPlaceContainedLiquid(world, offsetPos) && !player.capabilities.isCreativeMode)
					{
						player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

						return new ItemStack(ItemsAether.skyroot_bucket);
					}
				}
			}

			return stack;
		}
	}

	private ItemStack fillBucket(ItemStack stack, EntityPlayer player, Item fullBucket)
	{
		if (player.capabilities.isCreativeMode)
		{
			return stack;
		}
		else if (--stack.stackSize <= 0)
		{
			return new ItemStack(fullBucket);
		}
		else
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket)))
			{
				player.dropPlayerItemWithRandomChoice(new ItemStack(fullBucket, 1, 0), false);
			}

			return stack;
		}
	}

	public boolean tryPlaceContainedLiquid(World world, BlockPos pos)
	{
		if (this.containedLiquid == Blocks.air)
		{
			return false;
		}
		else
		{
			Material material = world.getBlockState(pos).getBlock().getMaterial();
			boolean isSolid = !material.isSolid();

			if (!world.isAirBlock(pos) && !isSolid)
			{
				return false;
			}
			else
			{
				if (world.provider.doesWaterVaporize() && this.containedLiquid == Blocks.flowing_water)
				{
					int x = pos.getX();
					int y = pos.getY();
					int z = pos.getZ();

					world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

					for (int count = 0; count < 8; ++count)
					{
						world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)x + Math.random(), (double)y + Math.random(), (double)z + Math.random(), 0.0D, 0.0D, 0.0D);
					}
				}
				else
				{
					if (!world.isRemote && isSolid && !material.isLiquid())
					{
						world.destroyBlock(pos, true);
					}

					world.setBlockState(pos, this.containedLiquid.getDefaultState(), 3);
				}

				return true;
			}
		}
	}
}
