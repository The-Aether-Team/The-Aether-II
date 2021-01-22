package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.IBlockRadiation;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockHolystone extends Block implements IBlockMultiName, IBlockRadiation
{
	private int radiationDistance, radiationAmount, radiationRate;

	public static final BlockVariant
			NORMAL_HOLYSTONE = new BlockVariant(0, "normal"),
			MOSSY_HOLYSTONE = new BlockVariant(1, "mossy"),
			BLOOD_MOSS_HOLYSTONE = new BlockVariant(2, "blood_moss"),
			IRRADIATED_HOLYSTONE = new BlockVariant(3, "irradiated");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", NORMAL_HOLYSTONE, MOSSY_HOLYSTONE, BLOOD_MOSS_HOLYSTONE, IRRADIATED_HOLYSTONE);

	public BlockHolystone()
	{
		super(Material.ROCK);

		this.setHardness(1.5F);
		this.setResistance(10.0F);

		this.setSoundType(SoundType.STONE);
		this.setTickRandomly(true);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, NORMAL_HOLYSTONE));
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		if (state.getValue(PROPERTY_VARIANT) == IRRADIATED_HOLYSTONE)
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
		if (state.getValue(PROPERTY_VARIANT) == IRRADIATED_HOLYSTONE)
		{
			this.tickRadiation(world, pos, this.getRadiationDistance(), this.getRadiationAmount(), this.getRadiationRate());

			world.scheduleUpdate(pos, world.getBlockState(pos).getBlock(), 1);
		}
	}

	@Override
	public BlockHolystone setRadiationDistance(int distance)
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
	public BlockHolystone setRadiationAmount(int amount)
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
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final CreativeTabs tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			if (variant != BLOOD_MOSS_HOLYSTONE)
			{
				list.add(new ItemStack(this, 1, variant.getMeta()));
			}
		}
	}

	@Override
	public float getBlockHardness(final IBlockState blockState, final World world, final BlockPos pos)
	{
		final IBlockState state = world.getBlockState(pos);

		return state.getBlock() == this && state.getValue(PROPERTY_VARIANT) == BLOOD_MOSS_HOLYSTONE ? -1.0f : this.blockHardness;
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta & 7));
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT);
	}

	@Override
	public int damageDropped(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

}
