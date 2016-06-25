package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.entities.blocks.EntityFloatingBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;

public class ItemGravititeTool extends ItemAetherTool
{
	public ItemGravititeTool(EnumToolType toolType, float attackDamage, float attackSpeed)
	{
		super(ToolMaterial.DIAMOND, "gravitite", toolType, attackDamage, attackSpeed);

		this.setHarvestLevel(toolType.getToolClass(), 3);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			IBlockState state = world.getBlockState(pos);

			if (ForgeHooks.canHarvestBlock(state.getBlock(), player, world, pos))
			{
				if (!world.getBlockState(pos.up()).isOpaqueCube())
				{
					List<ItemStack> drops = state.getBlock().getDrops(world, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));

					EntityFloatingBlock floatingBlock = new EntityFloatingBlock(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, state, drops);
					world.spawnEntityInWorld(floatingBlock);

					stack.damageItem(5, player);
				}

				return EnumActionResult.SUCCESS;
			}
		}

		return EnumActionResult.FAIL;
	}

	@Override
	protected boolean isAbilityPassive()
	{
		return false;
	}
}
