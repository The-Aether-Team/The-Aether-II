package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.init.CreativeTabsAether;
import com.gildedgames.aether.common.items.IDropOnDeath;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemSkyrootBucket extends BucketItem implements IDropOnDeath
{
	public ItemSkyrootBucket(Block liquid)
	{
		super(liquid);

		if (liquid == Blocks.AIR)
		{
			this.setMaxStackSize(16);
		}

		this.setCreativeTab(CreativeTabsAether.TAB_MISCELLANEOUS);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		boolean isEmpty = stack.getItem() == ItemsAether.skyroot_bucket;

		RayTraceResult traceResult = this.rayTrace(worldIn, player, isEmpty);

		ActionResult<ItemStack> ret = ForgeEventFactory.onBucketUse(player, worldIn, stack, traceResult);

		// when traceResult is null it means the player has right clicked on an air block.
		// Ignore intellij's warning that traceResult is never null.
		if (traceResult == null)
		{
			return new ActionResult<>(ActionResultType.PASS, stack);
		}

		if (ret != null)
		{
			return ret;
		}

		else if (traceResult.typeOfHit != RayTraceResult.Type.BLOCK)
		{
			return new ActionResult<>(ActionResultType.PASS, stack);
		}
		else
		{
			BlockPos pos = traceResult.getBlockPos();

			if (!worldIn.isBlockModifiable(player, pos))
			{
				return new ActionResult<>(ActionResultType.FAIL, stack);
			}
			else if (isEmpty)
			{
				if (!player.canPlayerEdit(pos.offset(traceResult.sideHit), traceResult.sideHit, stack))
				{
					return new ActionResult<>(ActionResultType.FAIL, stack);
				}
				else
				{
					BlockState state = worldIn.getBlockState(pos);

					if (state.getMaterial() == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0)
					{
						worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);

						player.addStat(StatList.getObjectUseStats(this));
						player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);

						return new ActionResult<>(ActionResultType.SUCCESS, this.fillBucket(stack, player, ItemsAether.skyroot_water_bucket));
					}

					return new ActionResult<>(ActionResultType.FAIL, stack);
				}
			}
			else
			{
				boolean canPlace = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);

				BlockPos placePos = canPlace && traceResult.sideHit == Direction.UP ? pos : pos.offset(traceResult.sideHit);

				if (!player.canPlayerEdit(placePos, traceResult.sideHit, stack))
				{
					return new ActionResult<>(ActionResultType.FAIL, stack);
				}
				else if (this.tryPlaceContainedLiquid(player, worldIn, placePos))
				{
					player.addStat(StatList.getObjectUseStats(this));

					return !player.isCreative() ?
							new ActionResult<>(ActionResultType.SUCCESS, new ItemStack(ItemsAether.skyroot_bucket)) :
							new ActionResult<>(ActionResultType.SUCCESS, stack);
				}
				else
				{
					return new ActionResult<>(ActionResultType.FAIL, stack);
				}
			}
		}
	}

	private ItemStack fillBucket(ItemStack emptyBucket, PlayerEntity player, Item fullBucket)
	{
		if (player.isCreative())
		{
			return emptyBucket;
		}

		emptyBucket.shrink(1);

		if (emptyBucket.isEmpty())
		{
			return new ItemStack(fullBucket);
		}
		else
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket)))
			{
				player.dropItem(new ItemStack(fullBucket), false);
			}

			return emptyBucket;
		}
	}
}
