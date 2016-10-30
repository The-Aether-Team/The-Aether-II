package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemAetherDeveloperWand extends Item
{

	public ItemAetherDeveloperWand()
	{
		super();
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		entity.setDead();

		return true;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			worldIn.playSound(playerIn, playerIn.getPosition(), SoundsAether.tempest_electric_shock, SoundCategory.NEUTRAL, 1.0F, 0.8F + (worldIn.rand.nextFloat() * 0.5F));

			return EnumActionResult.SUCCESS;
		}
		else if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			IBlockState iblockstate = worldIn.getBlockState(pos);

			if (playerIn.isSneaking())
			{
				worldIn.removeTileEntity(pos);
				worldIn.setBlockToAir(pos);
				return EnumActionResult.SUCCESS;
			}

			pos = pos.offset(facing);
			double d0 = 0.0D;

			if (facing == EnumFacing.UP && iblockstate.getBlock() instanceof BlockFence) //Forge: Fix Vanilla bug comparing state instead of block
			{
				d0 = 0.5D;
			}

			ItemAetherDeveloperWand.spawnCreature(worldIn, "aether.generator", (double)pos.getX() + hitX, (double)pos.getY() + d0, (double)pos.getZ() + hitZ);

			return EnumActionResult.SUCCESS;
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World world, EntityPlayer playerIn, EnumHand hand)
	{
		return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
	}

	@Nullable
	public static Entity spawnCreature(World worldIn, @Nullable String entityID, double x, double y, double z)
	{
		if (entityID != null && EntityList.NAME_TO_CLASS.containsKey(entityID))
		{
			Entity entity = null;

			for (int i = 0; i < 1; ++i)
			{
				entity = EntityList.createEntityByIDFromName(entityID, worldIn);

				if (entity instanceof EntityLivingBase)
				{
					EntityLiving entityliving = (EntityLiving)entity;
					entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
					entityliving.rotationYawHead = entityliving.rotationYaw;
					entityliving.renderYawOffset = entityliving.rotationYaw;
					entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), null);
					worldIn.spawnEntityInWorld(entity);
					entityliving.playLivingSound();
				}
			}

			return entity;
		}
		else
		{
			return null;
		}
	}

}
