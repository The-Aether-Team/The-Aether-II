package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.genes.util.GeneUtil;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.tiles.TileEntityMoaEgg;
import com.gildedgames.aether.common.items.ItemsAether;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMoaEgg extends Item
{

	private final boolean creativeEgg;

	public ItemMoaEgg(final boolean creativeEgg)
	{
		super();

		this.creativeEgg = creativeEgg;

		this.setHasSubtypes(true);

		this.maxStackSize = 1;

		this.addPropertyOverride(new ResourceLocation("circles"), new ModelProperty("circles"));
		this.addPropertyOverride(new ResourceLocation("curves"), new ModelProperty("curves"));
		this.addPropertyOverride(new ResourceLocation("ladder"), new ModelProperty("ladder"));
		this.addPropertyOverride(new ResourceLocation("lines"), new ModelProperty("lines"));
		this.addPropertyOverride(new ResourceLocation("spots"), new ModelProperty("spots"));
	}

	public static void setGenePool(final ItemStack stack, final MoaGenePool pool)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}

		final NBTTagCompound poolTag = new NBTTagCompound();

		pool.write(poolTag);

		stack.getTagCompound().setTag("pool", poolTag);
	}

	public static MoaGenePool getGenePool(final ItemStack stack)
	{
		if (stack.getTagCompound() == null)
		{
			ItemMoaEgg.setGenePool(stack, new MoaGenePool());
		}

		final NBTTagCompound poolTag = stack.getTagCompound().getCompoundTag("pool");

		final MoaGenePool pool = new MoaGenePool();

		pool.read(poolTag);

		return pool;
	}

	@Override
	public void addInformation(final ItemStack stack, final World world, final List<String> creativeList, final ITooltipFlag flag)
	{
		final MoaGenePool genePool = ItemMoaEgg.getGenePool(stack);

		if (genePool.getFeathers() != null && stack.getItem() != ItemsAether.rainbow_moa_egg)
		{
			creativeList.add("\u2022 " + genePool.getFeathers().gene().localizedName() + " " + I18n.format("moa.feathers"));
			creativeList.add("\u2022 " + genePool.getKeratin().gene().localizedName() + " " + I18n.format("moa.keratin"));
			creativeList.add("\u2022 " + genePool.getEyes().gene().localizedName() + " " + I18n.format("moa.eyes"));

			creativeList.add("");

			creativeList.add(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + "" + genePool.getWingStrength().gene().data() + " "
					+ I18n.format("moa.jumps"));
		}
	}

	@Override
	public EnumActionResult onItemUse(final EntityPlayer player, final World world, final BlockPos pos, final EnumHand hand, final EnumFacing facing,
			final float hitX, final float hitY, final float hitZ)
	{
		final ItemStack stack = player.getHeldItem(hand);

		final IBlockState state = world.getBlockState(pos);

		final boolean replaceable = state.getBlock().isReplaceable(world, pos);

		final int yOffset = replaceable ? 0 : 1;

		if (stack.getCount() == 0)
		{
			return EnumActionResult.FAIL;
		}
		else if (!player.canPlayerEdit(pos, facing, stack))
		{
			return EnumActionResult.FAIL;
		}
		else if ((world.isAirBlock(pos.add(0, yOffset, 0)) || replaceable)
				&& BlocksAether.moa_egg.canPlaceBlockAt(world, pos.add(0, yOffset, 0)))
		{
			if (player.capabilities.isCreativeMode || this.creativeEgg)
			{
				if (!world.isRemote)
				{
					final EntityMoa moa = new EntityMoa(world, GeneUtil.getRandomSeed(world));
					moa.setPosition(pos.getX() + 0.5F, pos.getY() + (moa.height / 2), pos.getZ() + 0.5F);

					final MoaGenePool stackGenePool = ItemMoaEgg.getGenePool(stack);

					moa.setRaisedByPlayer(true);

					world.spawnEntity(moa);

					final MoaGenePool genePool = moa.getGenePool();

					if (this.creativeEgg)
					{
						genePool.transformFromSeed(GeneUtil.getRandomSeed(world));
					}
					else
					{
						genePool.transformFromParents(stackGenePool.getStorage().getSeed(), stackGenePool.getStorage().getFatherSeed(),
								stackGenePool.getStorage().getMotherSeed());
					}
				}

				stack.shrink(1);

				return EnumActionResult.SUCCESS;
			}
			else if (
					world.checkNoEntityCollision(
							BlocksAether.moa_egg.getCollisionBoundingBox(world.getBlockState(pos.add(0, yOffset, 0)), world, pos.add(0, yOffset, 0)))
							&& world.setBlockState(pos.add(0, yOffset, 0), BlocksAether.moa_egg.getDefaultState()))
			{
				final SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS,
						(soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

				final TileEntityMoaEgg egg = (TileEntityMoaEgg) world.getTileEntity(pos.add(0, yOffset, 0));

				if (egg != null)
				{
					final MoaGenePool stackGenes = ItemMoaEgg.getGenePool(stack);
					final MoaGenePool teGenes = egg.getGenePool();

					teGenes.transformFromParents(stackGenes.getStorage().getSeed(), stackGenes.getStorage().getFatherSeed(),
							stackGenes.getStorage().getMotherSeed());

					egg.setPlayerPlaced();
				}

				stack.shrink(1);

				return EnumActionResult.SUCCESS;
			}
		}

		return EnumActionResult.PASS;
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack)
	{
		return this.creativeEgg ? super.getItemStackDisplayName(stack) : super.getItemStackDisplayName(stack);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public void onUpdate(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected)
	{

	}

	private static class ModelProperty implements IItemPropertyGetter
	{

		private final String propertyName;

		public ModelProperty(final String propertyName)
		{
			this.propertyName = propertyName;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public float apply(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn)
		{
			final MoaGenePool genePool = ItemMoaEgg.getGenePool(stack);

			if (genePool.getMarks() != null)
			{
				final String mark = genePool.getMarks().gene().getResourceName();

				if (mark.equals(this.propertyName))
				{
					return 1.0F;
				}
			}

			return 0.0F;
		}

	}

}
