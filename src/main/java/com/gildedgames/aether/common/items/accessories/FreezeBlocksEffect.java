package com.gildedgames.aether.common.items.accessories;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.gildedgames.aether.common.player.PlayerAether;

/**
 * Freezes appropriate blocks arounds the player when wearing the attached Accessory.
 * Unfreezes frozen locations once the player has moved away from them.
 * @author Brandon Pearce
 */
public class FreezeBlocksEffect implements AccessoryEffect
{
	
	private List<BlockPos> frozenLocations = new ArrayList<BlockPos>();

	@Override
	public void onEquipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}

	@Override
	public void onUnequipped(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}

	@Override
	public void onUpdate(PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		EntityPlayer player = aePlayer.getPlayer();
		World world = player.worldObj;
		
		if (!world.isRemote)
		{
			int x1 = MathHelper.floor_double(player.posX);
			int y1 = MathHelper.floor_double(player.getEntityBoundingBox().minY);
			int z1 = MathHelper.floor_double(player.posZ);
			
			final int RADIUS = 3;

			for (int x = x1 - RADIUS; x <= x1 + RADIUS; x++)
			{
				for (int y = y1 - RADIUS; y <= y1 + RADIUS; y++)
				{
					for (int z = z1 - RADIUS; z <= z1 + RADIUS; z++)
					{
						BlockPos pos = new BlockPos(x, y, z);
						
						IBlockState state = world.getBlockState(pos);
						Block block = state.getBlock();
						
						if (block.getMetaFromState(state) != 0)
						{
							continue;
						}
						
						if (x <= x1 - RADIUS || y <= y1 - RADIUS || z <= z1 - RADIUS || x >= x1 + RADIUS || y >= y1 + RADIUS || z >= z1 + RADIUS)
						{
							if (this.frozenLocations.contains(pos))
							{
								if (block == Blocks.ice)
								{
									world.setBlockState(pos, Blocks.water.getDefaultState());
								}
								else if (block == Blocks.obsidian)
								{
									world.setBlockState(pos, Blocks.lava.getDefaultState());
								}
								
								this.frozenLocations.remove(pos);
							}
							
							continue;
						}
						
						if (block == Blocks.flowing_water)
						{
							world.setBlockState(pos, Blocks.ice.getDefaultState());
							this.frozenLocations.add(pos);
						}
						else if (block == Blocks.water)
						{
							world.setBlockState(pos, Blocks.ice.getDefaultState());
							this.frozenLocations.add(pos);
						}
						else if (block == Blocks.flowing_lava)
						{
							world.setBlockState(pos, Blocks.obsidian.getDefaultState());
							this.frozenLocations.add(pos);
						}
						else if (block == Blocks.lava)
						{
							world.setBlockState(pos, Blocks.obsidian.getDefaultState());
							this.frozenLocations.add(pos);
						}
						else
						{
							continue;
						}
					}
				}
			}
		}
	}

	@Override
	public void onInteract(PlayerInteractEvent event, PlayerAether aePlayer, ItemStack stack, AccessoryType type)
	{
		
	}

}