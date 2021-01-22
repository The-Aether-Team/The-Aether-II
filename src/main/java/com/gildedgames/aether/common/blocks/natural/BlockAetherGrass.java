package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.IBlockRadiation;
import com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower;
import com.gildedgames.aether.common.blocks.natural.plants.BlockTallAetherGrass;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.world.biomes.forgotten_highlands.BiomeForgottenHighlands;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockAetherGrass extends Block implements IBlockMultiName, IGrowable, IBlockRadiation
{
	private int radiationDistance, radiationAmount, radiationRate;

	public static final BlockVariant AETHER = new BlockVariant(0, "normal"),
			ENCHANTED = new BlockVariant(1, "enchanted"),
			ARCTIC = new BlockVariant(2, "arctic"),
			MAGNETIC = new BlockVariant(3, "magnetic"),
			IRRADIATED = new BlockVariant(4, "irradiated");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", AETHER, ENCHANTED, ARCTIC, MAGNETIC, IRRADIATED);

	public static final PropertyBool SNOWY = PropertyBool.create("snowy");

	public BlockAetherGrass()
	{
		super(Material.GRASS);

		this.setSoundType(SoundType.PLANT);

		this.setHardness(0.6F);
		this.setTickRandomly(true);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, AETHER).withProperty(SNOWY, Boolean.FALSE));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final CreativeTabs tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos)
	{
		final IBlockState up = worldIn.getBlockState(pos.up());
		final Block block = up.getBlock();

		final Comparable<?> snowGrass = up.getProperties().get(BlockTallAetherGrass.TYPE);
		final Comparable<?> snowFlower = up.getProperties().get(BlockAetherFlower.PROPERTY_SNOWY);

		return state.withProperty(SNOWY,
				block == BlocksAether.highlands_snow || block == BlocksAether.highlands_snow_layer || (snowGrass != null && snowGrass
						.equals(BlockTallAetherGrass.Type.SNOWY)) || (
						snowFlower != null && snowFlower
								.equals(true)));
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		if (state.getValue(PROPERTY_VARIANT) == IRRADIATED)
		{
			worldIn.scheduleUpdate(pos, worldIn.getBlockState(pos).getBlock(), 1);
		}
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
	{
	}

	@Override
	public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand)
	{
		if (state.getValue(PROPERTY_VARIANT) == IRRADIATED)
		{
			this.tickRadiation(world, pos, this.getRadiationDistance(), this.getRadiationAmount(), this.getRadiationRate());

			world.scheduleUpdate(pos, world.getBlockState(pos).getBlock(), 1);
		}

		if (!world.isRemote && state.getValue(PROPERTY_VARIANT) != ENCHANTED)
		{
			BlockPos.PooledMutableBlockPos up = BlockPos.PooledMutableBlockPos.retain();
			up.setPos(pos.getX(), pos.getY() + 1, pos.getZ());

			if (world.getLightFromNeighbors(up) < 4 && world.getBlockState(up).getLightOpacity(world, up) > 2)
			{
				world.setBlockState(pos, BlocksAether.aether_dirt.getDefaultState());
			}
			else
			{
				if (world.getLightFromNeighbors(up) >= 9)
				{
					for (int i = 0; i < 4; ++i)
					{
						final BlockPos randomNeighbor = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

						if (randomNeighbor.getY() >= 0 && randomNeighbor.getY() < 256 && !world.isBlockLoaded(randomNeighbor))
						{
							return;
						}

						final IBlockState aboveState = world.getBlockState(randomNeighbor.up());
						final IBlockState neighborState = world.getBlockState(randomNeighbor);

						if (neighborState.getBlock() == BlocksAether.aether_dirt
								&& neighborState.getValue(BlockAetherDirt.PROPERTY_VARIANT) == BlockAetherDirt.DIRT &&
								world.getLightFromNeighbors(randomNeighbor.up()) >= 4
								&& aboveState.getLightOpacity(world, randomNeighbor.up()) <= 2)
						{
							final IBlockState grassState = this.getDefaultState().withProperty(PROPERTY_VARIANT, state.getValue(PROPERTY_VARIANT));

							world.setBlockState(randomNeighbor, grassState);
						}
					}
				}
			}

			up.release();
		}
	}

	@Override
	public BlockAetherGrass setRadiationDistance(int distance)
	{
		this.radiationDistance = distance;
		return this;
	}

	@Override
	public int getRadiationDistance()
	{
		return this.radiationDistance;
	}

	@Override
	public BlockAetherGrass setRadiationAmount(int amount)
	{
		this.radiationAmount = amount;
		return this;
	}

	@Override
	public int getRadiationAmount()
	{
		return this.radiationAmount;
	}

	@Override
	public Block setRadiationRate(int rate)
	{
		this.radiationRate = rate;
		return this;
	}

	@Override
	public int getRadiationRate()
	{
		return this.radiationRate;
	}

	private void tickRadiation(final World world, final BlockPos pos, int radiationAmount, int radiationDistance, int radiationRate)
	{
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		AxisAlignedBB axisalignedbb = (new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1)).grow(radiationDistance);
		List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);

		for (EntityPlayer player : list)
		{
			IAetherStatusEffectPool statusEffectPool = player.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

			if (!player.isCreative())
			{
				boolean tick = world.getTotalWorldTime() % radiationRate == 0;

				if (tick)
				{
					if (statusEffectPool != null)
					{
						if (!statusEffectPool.effectExists(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING))
						{
							statusEffectPool.applyStatusEffect(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING, radiationAmount);
						}
						else
						{
							statusEffectPool.modifyActiveEffectBuildup(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING,
									statusEffectPool.getBuildupFromEffect(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING) + radiationAmount);
						}
					}
				}
			}
		}
	}

	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune)
	{
		return Item.getItemFromBlock(BlocksAether.aether_dirt);
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		final BlockVariant variant = PROPERTY_VARIANT.fromMeta(meta);

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, variant);
	}

	@Override
	public ItemStack getPickBlock(final IBlockState state, final RayTraceResult target, final World world, final BlockPos pos, final EntityPlayer player)
	{
		return new ItemStack(this, 1, state.getValue(PROPERTY_VARIANT).getMeta());
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, SNOWY, PROPERTY_VARIANT);
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public boolean canGrow(final World worldIn, final BlockPos pos, final IBlockState state, final boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state)
	{
		return true;
	}

	@Override
	public void grow(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state)
	{
		int count = 0;

		while (count < 128)
        {
			BlockPos nextPos = pos.up();
			int grassCount = 0;

			while (true)
            {
				if (grassCount < count / 16)
				{
					nextPos = nextPos.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

					if (worldIn.getBlockState(nextPos.down()).getBlock() == BlocksAether.aether_grass &&
							!worldIn.getBlockState(nextPos).isNormalCube())
					{
						grassCount++;

						continue;
					}
				}
				else if (worldIn.isAirBlock(nextPos))
				{
					if (rand.nextInt(8) == 0)
					{
						flowersForGrass(worldIn, state, pos);
						plantFlower(worldIn, rand, nextPos);
						clearFlower();
					}
					else
					{
						int grassHeight = rand.nextInt(3);

						final IBlockState nextState1 = (grassHeight == 0)
								? BlocksAether.tall_aether_grass.getStateFromMeta(0)
								: ((grassHeight == 1)
								? BlocksAether.tall_aether_grass.getStateFromMeta(1)
								: BlocksAether.tall_aether_grass.getStateFromMeta(2)
						);

						final IBlockState nextState2 = BlocksAether.tall_aether_grass.getDefaultState();

						if (BlocksAether.tall_aether_grass.canPlaceBlockAt(worldIn, nextPos))
						{
							if (worldIn.getBiome(pos) instanceof BiomeForgottenHighlands && state.getValue(PROPERTY_VARIANT) == AETHER)
							{
								worldIn.setBlockState(nextPos, nextState1, 3);
							}
							else
							{
								worldIn.setBlockState(nextPos, nextState2, 3);
							}
						}
					}
				}

				++count;
				break;
			}
		}
	}

	private List<FlowerEntry> aetherFlowers = new java.util.ArrayList<>();

	public static class FlowerEntry extends WeightedRandom.Item
	{
		public final net.minecraft.block.state.IBlockState state;

		FlowerEntry(net.minecraft.block.state.IBlockState state, int weight)
        {
			super(weight);
			this.state = state;
		}
	}

	private void addFlower(IBlockState state, int weight)
	{
		this.aetherFlowers.add(new FlowerEntry(state, weight));
	}

	private void plantFlower(World world, Random rand, BlockPos pos)
	{
		if (aetherFlowers.isEmpty()) return;
		FlowerEntry flower = WeightedRandom.getRandomItem(rand, aetherFlowers);
		if (flower.state == null)
		{
			return;
		}

		world.setBlockState(pos, flower.state, 3);
	}

	private void clearFlower()
	{
		this.aetherFlowers.clear();
	}

	private void flowersForGrass(World world, IBlockState state, BlockPos pos)
	{
		defaultGrassFlowers();
		defaultBiomeFlowers(world, state, pos);
	}

	private void defaultGrassFlowers()
	{
		addFlower(BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.PURPLE_FLOWER), 15);
		addFlower(BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.WHITE_ROSE), 15);
		addFlower(BlocksAether.aether_flower.getDefaultState().withProperty(BlockAetherFlower.PROPERTY_VARIANT, BlockAetherFlower.BURSTBLOSSOM), 15);
		addFlower(BlocksAether.neverbloom.getDefaultState(), 10);
		addFlower(BlocksAether.quickshoot.getDefaultState(), 10);
		addFlower(BlocksAether.pink_swingtip.getDefaultState(), 10);
		addFlower(BlocksAether.green_swingtip.getDefaultState(), 10);
		addFlower(BlocksAether.blue_swingtip.getDefaultState(), 10);
	}

	private void defaultBiomeFlowers(World world, IBlockState state, BlockPos pos)
	{
		if (!world.isRemote)
		{
			if (state.getValue(PROPERTY_VARIANT) == ARCTIC)
			{
				addFlower(BlocksAether.arctic_spikespring.getDefaultState(), 12);
			}
			else if (state.getValue(PROPERTY_VARIANT) == IRRADIATED)
			{
				addFlower(BlocksAether.irradiated_flower.getDefaultState(), 12);
			}
			else if (state.getValue(PROPERTY_VARIANT) == AETHER)
			{
				if (world.getBiome(pos) instanceof BiomeForgottenHighlands)
				{
					addFlower(BlocksAether.forgotten_rose.getDefaultState(), 12);
				}
				else
				{
					addFlower(BlocksAether.highlands_tulips.getDefaultState(), 12);
				}
			}
		}
	}
}