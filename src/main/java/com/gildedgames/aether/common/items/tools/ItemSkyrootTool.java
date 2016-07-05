package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.util.ISkyrootMinable;
import com.gildedgames.util.modules.chunk.ChunkModule;
import com.gildedgames.util.modules.chunk.api.hook.BlockBitFlagChunkHook;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;

public class ItemSkyrootTool extends ItemAetherTool
{
	public ItemSkyrootTool(EnumToolType toolType, float attackDamage, float attackSpeed)
	{
		super(ToolMaterial.WOOD, "skyroot", toolType, attackDamage, attackSpeed);

		this.setHarvestLevel(toolType.getToolClass(), 0);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity)
	{
		if (!world.isRemote)
		{
			BlockBitFlagChunkHook data = ChunkModule.api().getHook(world, pos, AetherCore.PROXY.getPlacementFlagProvider());

			boolean wasPlaced = data.isMarked(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15);

			if (!wasPlaced && state.getBlock().isToolEffective(this.toolType.getToolClass(), state))
			{
				int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);

				if (state.getBlock() instanceof ISkyrootMinable)
				{
					ISkyrootMinable doubleBlock = (ISkyrootMinable) state.getBlock();

					if (doubleBlock.canBlockDropDoubles(entity, stack, state))
					{
						Collection<ItemStack> drops = doubleBlock.getAdditionalDrops(world, pos, state, entity);

						for (ItemStack drop : drops)
						{
							Block.spawnAsEntity(world, pos, drop);
						}
					}
				}
				else
				{
					state.getBlock().dropBlockAsItem(world, pos, state, fortune);
				}
			}
		}

		return super.onBlockDestroyed(stack, world, state, pos, entity);
	}
}
