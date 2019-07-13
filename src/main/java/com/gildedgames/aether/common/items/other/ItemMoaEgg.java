package com.gildedgames.aether.common.items.other;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.blocks.natural.BlockMoaEgg;
import com.gildedgames.aether.common.entities.animals.EntityMoa;
import com.gildedgames.aether.common.entities.genes.GeneUtil;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.tiles.TileEntityMoaEgg;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMoaEgg extends Item
{

	private final boolean creativeEgg;

	public ItemMoaEgg(final boolean creativeEgg, Item.Properties properties)
	{
		super(properties);

		this.creativeEgg = creativeEgg;

		this.addPropertyOverride(new ResourceLocation("circles"), new ModelProperty("circles"));
		this.addPropertyOverride(new ResourceLocation("curves"), new ModelProperty("curves"));
		this.addPropertyOverride(new ResourceLocation("ladder"), new ModelProperty("ladder"));
		this.addPropertyOverride(new ResourceLocation("lines"), new ModelProperty("lines"));
		this.addPropertyOverride(new ResourceLocation("spots"), new ModelProperty("spots"));
	}

	public static void setGenePool(final ItemStack stack, final MoaGenePool pool)
	{
		if (stack.getTag() == null)
		{
			stack.setTag(new CompoundNBT());
		}

		final CompoundNBT poolTag = new CompoundNBT();

		pool.write(poolTag);

		stack.getTag().put("pool", poolTag);
	}

	public static MoaGenePool getGenePool(final ItemStack stack)
	{
		if (stack.getTag() == null)
		{
			ItemMoaEgg.setGenePool(stack, new MoaGenePool());
		}

		final CompoundNBT poolTag = stack.getTag().getCompound("pool");

		final MoaGenePool pool = new MoaGenePool();

		pool.read(poolTag);

		return pool;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(final ItemStack stack, final World world, final List<ITextComponent> creativeList, final ITooltipFlag flag)
	{
		final MoaGenePool genePool = ItemMoaEgg.getGenePool(stack);

		if (genePool.getFeathers() != null && stack.getItem() != ItemsAether.rainbow_moa_egg)
		{
			creativeList.add(new TranslationTextComponent("moa.feathers", genePool.getFeathers().gene().localizedName()));
			creativeList.add(new TranslationTextComponent("moa.keratin", genePool.getKeratin().gene().localizedName()));
			creativeList.add(new TranslationTextComponent("moa.eyes", genePool.getEyes().gene().localizedName()));
			creativeList.add(new TranslationTextComponent("moa.wing_strength", genePool.getWingStrength().gene().localizedName())
					.setStyle(new Style().setColor(TextFormatting.YELLOW).setItalic(true)));
		}
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context)
	{
		final PlayerEntity player = context.getPlayer();

		if (player == null)
		{
			return ActionResultType.FAIL;
		}

		final World world = context.getWorld();
		final ItemStack stack = context.getItem();
		final BlockPos pos = context.getPos();
		final Direction facing = context.getFace();

		final BlockState state = world.getBlockState(pos);

		final boolean replaceable = state.getMaterial().isReplaceable();

		final int yOffset = replaceable ? 0 : 1;

		if (stack.getCount() == 0)
		{
			return ActionResultType.FAIL;
		}
		else if (!player.canPlayerEdit(pos, facing, stack))
		{
			return ActionResultType.FAIL;
		}
		else if ((world.isAirBlock(pos.add(0, yOffset, 0)) || replaceable)
				&& BlocksAether.moa_egg.getDefaultState().isValidPosition(world, pos.add(0, yOffset, 0)))
		{
			if (player.isCreative() || this.creativeEgg)
			{
				if (!world.isRemote())
				{
					final EntityMoa moa = new EntityMoa(world, GeneUtil.getRandomSeed(world));
					moa.setPosition(pos.getX() + 0.5F, pos.getY() + (moa.getHeight() / 2), pos.getZ() + 0.5F);

					final MoaGenePool stackGenePool = ItemMoaEgg.getGenePool(stack);

					moa.setRaisedByPlayer(true);

					world.addEntity(moa);

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

				return ActionResultType.SUCCESS;
			}
			else if (world.checkBlockCollision(BlockMoaEgg.BOUNDING_BOX.withOffset(pos.getX(), pos.getY() + 1, pos.getZ()).getBoundingBox()) &&
					world.setBlockState(pos.add(0, yOffset, 0), BlocksAether.moa_egg.getDefaultState()))
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

				return ActionResultType.SUCCESS;
			}
		}

		return ActionResultType.PASS;
	}

	private boolean checkCollision(BlockPos pos, World world, float yOffset)
	{
		return false;
	}

	@Override
	public boolean shouldSyncTag()
	{
		return true;
	}

	private static class ModelProperty implements IItemPropertyGetter
	{
		private final String propertyName;

		ModelProperty(final String propertyName)
		{
			this.propertyName = propertyName;
		}

		@Override
		public float call(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity)
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
