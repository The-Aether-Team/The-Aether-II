package com.gildedgames.aether.common.entities.effects.processors;

import com.gildedgames.aether.api.entities.effects.EntityEffectInstance;
import com.gildedgames.aether.api.entities.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.entities.effects.EntityEffectRule;
import com.gildedgames.aether.common.entities.effects.processors.FreezeBlocksEffect.Instance;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Freezes appropriate blocks arounds the player when wearing the attached Accessory.
 * Unfreezes frozen locations once the player has moved away from them.
 * @author Brandon Pearce
 */
public class FreezeBlocksEffect implements EntityEffectProcessor<Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		public Instance(int radius, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setInteger("radius", radius);
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getInteger("radius"), this.getRules());
		}

	}

	private List<BlockPos> frozenLocations = new ArrayList<>();

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
	public void apply(Entity source, Instance instance, List<Instance> all)
	{
	}

	@Override
	public void tick(Entity source, List<Instance> all)
	{
		World world = source.worldObj;
		IBlockState ice = all.size() > 1 ? Blocks.PACKED_ICE.getDefaultState() : Blocks.ICE.getDefaultState();

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
								if (block == Blocks.ICE)
								{
									world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState(), PLACEMENT_FLAG);
								}
								else if (block == Blocks.PACKED_ICE)
								{
									world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState(), PLACEMENT_FLAG);
								}
								else if (block == Blocks.OBSIDIAN)
								{
									world.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState(), PLACEMENT_FLAG);
								}

								this.frozenLocations.remove(pos);
							}

							continue;
						}

						if (block == Blocks.FLOWING_WATER)
						{
							world.setBlockState(pos, ice, PLACEMENT_FLAG);
							this.frozenLocations.add(pos);
						}
						else if (block == Blocks.WATER)
						{
							world.setBlockState(pos, ice, PLACEMENT_FLAG);
							this.frozenLocations.add(pos);
						}
						else if (block == Blocks.FLOWING_LAVA)
						{
							world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState(), PLACEMENT_FLAG);
							this.frozenLocations.add(pos);
						}
						else if (block == Blocks.LAVA)
						{
							world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState(), PLACEMENT_FLAG);
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

			if (block == Blocks.ICE)
			{
				world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState(), PLACEMENT_FLAG);
			}
			else if (block == Blocks.PACKED_ICE)
			{
				world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState(), PLACEMENT_FLAG);
			}
			else if (block == Blocks.OBSIDIAN)
			{
				world.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState(), PLACEMENT_FLAG);
			}
		}

		this.frozenLocations.clear();
	}

	@Override
	public void onKill(LivingDropsEvent event, Entity source, List<Instance> all)
	{
	}

	@Override
	public void onAttack(LivingHurtEvent event, Entity source, List<Instance> all)
	{
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		return new String[] {};
	}

}