package com.gildedgames.aether.common.entities.effects.processors;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.EffectInstance;
import com.gildedgames.aether.common.entities.effects.EffectProcessor;
import com.gildedgames.aether.common.entities.effects.EffectRule;
import com.gildedgames.aether.common.entities.effects.processors.FreezeBlocksEffect.Instance;

/**
 * Freezes appropriate blocks arounds the player when wearing the attached Accessory.
 * Unfreezes frozen locations once the player has moved away from them.
 * @author Brandon Pearce
 */
public class FreezeBlocksEffect implements EffectProcessor<Instance>
{

	public static class Instance extends EffectInstance
	{

		public Instance(int radius, EffectRule... rules)
		{
			super(rules);
			
			this.getAttributes().setInteger("radius", radius);
		}
		
		@Override
		public EffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getInteger("radius"), this.getRules());
		}
		
	}
	
	private List<BlockPos> frozenLocations = new ArrayList<BlockPos>();
	
	private static final int PLACEMENT_FLAG = 7;
	
	@Override
	public String getUnlocalizedName(Entity source, Instance instance)
	{
		return "ability.freezeBlocks.name";
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, Instance instance)
	{
		return new String[] { "ability.freezeBlocks.desc" };
	}

	@Override
	public void apply(Entity source, Instance instance, List<Instance> all) {}

	@Override
	public void tick(Entity source, List<Instance> all)
	{
		World world = source.worldObj;
		IBlockState ice = all.size() > 1 ? Blocks.packed_ice.getDefaultState() : Blocks.ice.getDefaultState();
		
		int maxRadius = 0;
		
		for (Instance instance : all)
		{
			int radius = instance.getAttributes().getInteger("radius");
			
			if (radius > maxRadius)
			{
				maxRadius = radius;
			}
		}
		
		if (!world.isRemote)
		{
			int x1 = MathHelper.floor_double(source.posX);
			int y1 = MathHelper.floor_double(source.getEntityBoundingBox().minY);
			int z1 = MathHelper.floor_double(source.posZ);

			for (int x = x1 - maxRadius; x <= x1 + maxRadius; x++)
			{
				for (int y = y1 - maxRadius; y <= y1 + maxRadius; y++)
				{
					for (int z = z1 - maxRadius; z <= z1 + maxRadius; z++)
					{
						BlockPos pos = new BlockPos(x, y, z);
						
						IBlockState state = world.getBlockState(pos);
						Block block = state.getBlock();
						
						if (block.getMetaFromState(state) != 0)
						{
							continue;
						}
						
						if (x == x1 - maxRadius || y == y1 - maxRadius || z == z1 - maxRadius || x == x1 + maxRadius || y == y1 + maxRadius || z == z1 + maxRadius)
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
	public void cancel(Entity source, Instance instance, List<Instance> all)
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
	public void onKill(LivingDropsEvent event, Entity source, List<Instance> all) {}

	@Override
	public void onAttack(LivingHurtEvent event, Entity source, List<Instance> all) {}

	@Override
	public void formatLocalizedDesc(List<String> localizedDesc, Entity source, Instance instance) {}

}