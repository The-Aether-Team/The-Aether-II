package com.gildedgames.aether.common.entities.effects.abilities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.Ability;
import com.gildedgames.aether.common.entities.effects.EntityEffect;

/**
 * Freezes appropriate blocks arounds the player when wearing the attached Accessory.
 * Unfreezes frozen locations once the player has moved away from them.
 * @author Brandon Pearce
 */
public class FreezeBlocksAbility<S extends Entity> implements Ability<S>
{
	
	private List<BlockPos> frozenLocations = new ArrayList<BlockPos>();
	
	private static final int PLACEMENT_FLAG = 7;
	
	@Override
	public String getUnlocalizedName(S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		return "ability.freezeBlocks.name";
	}

	@Override
	public String[] getUnlocalizedDesc(S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		return new String[] { "ability.freezeBlocks.desc" };
	}
	
	@Override
	public void initAttributes(S source, NBTTagCompound attributes)
	{
		
	}

	@Override
	public void apply(S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

	@Override
	public void tick(S source, EntityEffect<S> holder, NBTTagCompound attributes)
	{
		World world = source.worldObj;
		IBlockState ice = attributes.getInteger("modifier") > 1 ? Blocks.packed_ice.getDefaultState() : Blocks.ice.getDefaultState();
		
		if (!world.isRemote)
		{
			int x1 = MathHelper.floor_double(source.posX);
			int y1 = MathHelper.floor_double(source.getEntityBoundingBox().minY);
			int z1 = MathHelper.floor_double(source.posZ);
			
			final int RADIUS = 2;

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
						
						if (x == x1 - RADIUS || y == y1 - RADIUS || z == z1 - RADIUS || x == x1 + RADIUS || y == y1 + RADIUS || z == z1 + RADIUS)
						{
							if (this.frozenLocations.contains(pos))
							{
								if (block == Blocks.ice)
								{
									world.setBlockState(pos, Blocks.flowing_water.getDefaultState(), PLACEMENT_FLAG);
								}
								else if (block == Blocks.packed_ice)
								{
									world.setBlockState(pos, Blocks.flowing_water.getDefaultState(), PLACEMENT_FLAG);
								}
								else if (block == Blocks.obsidian)
								{
									world.setBlockState(pos, Blocks.flowing_lava.getDefaultState(), PLACEMENT_FLAG);
								}
								
								this.frozenLocations.remove(pos);
							}
							
							continue;
						}
						
						if (block == Blocks.flowing_water)
						{
							world.setBlockState(pos, ice, PLACEMENT_FLAG);
							this.frozenLocations.add(pos);
						}
						else if (block == Blocks.water)
						{
							world.setBlockState(pos, ice, PLACEMENT_FLAG);
							this.frozenLocations.add(pos);
						}
						else if (block == Blocks.flowing_lava)
						{
							world.setBlockState(pos, Blocks.obsidian.getDefaultState(), PLACEMENT_FLAG);
							this.frozenLocations.add(pos);
						}
						else if (block == Blocks.lava)
						{
							world.setBlockState(pos, Blocks.obsidian.getDefaultState(), PLACEMENT_FLAG);
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
	public void cancel(S source, EntityEffect<S> holder, NBTTagCompound attributes)
	{
		World world = source.worldObj;
		
		for (BlockPos pos : this.frozenLocations)
		{
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			
			if (block == Blocks.ice)
			{
				world.setBlockState(pos, Blocks.flowing_water.getDefaultState(), PLACEMENT_FLAG);
			}
			else if (block == Blocks.packed_ice)
			{
				world.setBlockState(pos, Blocks.flowing_water.getDefaultState(), PLACEMENT_FLAG);
			}
			else if (block == Blocks.obsidian)
			{
				world.setBlockState(pos, Blocks.flowing_lava.getDefaultState(), PLACEMENT_FLAG);
			}
		}
		
		this.frozenLocations.clear();
	}

	@Override
	public void onKill(LivingDropsEvent event, S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

	@Override
	public void onAttack(LivingHurtEvent event, S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

	@Override
	public void formatLocalizedDesc(List<String> localizedDesc, S source, EntityEffect<S> instance, NBTTagCompound attributes) {}

}