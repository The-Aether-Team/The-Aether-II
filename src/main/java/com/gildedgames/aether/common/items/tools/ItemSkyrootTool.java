package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.blocks.util.ISkyrootMinable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.List;

public class ItemSkyrootTool extends ItemAetherTool
{
	public ItemSkyrootTool(EnumToolType toolType)
	{
		super(ToolMaterial.WOOD, toolType);

		this.setHarvestLevel(toolType.getToolClass(), 0);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack heldStack, World world, Block block, BlockPos pos, EntityLivingBase living)
	{
		if (!world.isRemote)
		{
			IBlockState state = world.getBlockState(pos);

			if (!EnchantmentHelper.getSilkTouchModifier(living) && block instanceof ISkyrootMinable)
			{
				ISkyrootMinable doubleBlock = (ISkyrootMinable) block;

				if (block.isToolEffective(this.toolType.getToolClass(), state))
				{
					if (doubleBlock.canBlockDropDoubles(living, heldStack, state))
					{
						Collection<ItemStack> stacks = doubleBlock.getAdditionalDrops(world, pos, state, living);

						for (ItemStack stack : stacks)
						{
							Block.spawnAsEntity(world, pos, stack);
						}
					}
				}
			}
		}

		return super.onBlockDestroyed(heldStack, world, block, pos, living);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + "Doubles drops from blocks");
	}
}
