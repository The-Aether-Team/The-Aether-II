package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.chunk.IChunkAttachmentCapability;
import com.gildedgames.aether.api.capabilites.chunk.IPlacementFlagCapability;
import com.gildedgames.aether.common.blocks.util.ISkyrootMinable;
import com.gildedgames.aether.common.world.chunk.hooks.capabilities.ChunkAttachmentCapability;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
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
			IPlacementFlagCapability data = ChunkAttachmentCapability.get(world).getAttachment(new ChunkPos(pos), AetherCapabilities.CHUNK_PLACEMENT_FLAG);

			boolean wasPlaced = data.isMarked(pos);

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
