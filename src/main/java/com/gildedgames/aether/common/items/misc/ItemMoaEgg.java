package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.genes.util.GeneUtil;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.tiles.TileEntityMoaEgg;
import com.gildedgames.util.core.UtilModule;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
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

	private boolean creativeEgg;

	public ItemMoaEgg(boolean creativeEgg)
	{
		super();

		this.creativeEgg = creativeEgg;

		this.setHasSubtypes(true);

		this.addPropertyOverride(new ResourceLocation("circles"), new ModelProperty("circles"));
		this.addPropertyOverride(new ResourceLocation("curves"), new ModelProperty("curves"));
		this.addPropertyOverride(new ResourceLocation("ladder"), new ModelProperty("ladder"));
		this.addPropertyOverride(new ResourceLocation("lines"), new ModelProperty("lines"));
		this.addPropertyOverride(new ResourceLocation("spots"), new ModelProperty("spots"));
	}

	public static void setGenePool(ItemStack stack, MoaGenePool pool)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}

		NBTTagCompound poolTag = new NBTTagCompound();

		pool.write(poolTag);

		stack.getTagCompound().setTag("pool", poolTag);
	}

	public static MoaGenePool getGenePool(ItemStack stack)
	{
		/*IItemExtraDataCapability extraData = stack.getCapability(AetherCapabilities.ITEM_EXTRA_DATA, null);

		if (extraData != null)
		{
			if (!extraData.has("genePool"))
			{
				extraData.set("genePool", new MoaGenePool(new SimpleGeneStorage()));
			}

			return extraData.get("genePool");
		}*/
		if (stack.getTagCompound() == null)
		{
			ItemMoaEgg.setGenePool(stack, new MoaGenePool());
		}

		NBTTagCompound poolTag = stack.getTagCompound().getCompoundTag("pool");

		MoaGenePool pool = new MoaGenePool();

		pool.read(poolTag);

		return pool;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List<String> creativeList, boolean par4)
	{
		MoaGenePool genePool = ItemMoaEgg.getGenePool(stack);

		if (genePool.getFeathers() != null && stack.getItem() != ItemsAether.rainbow_moa_egg)
		{
			creativeList.add("\u2022 " + genePool.getFeathers().gene().localizedName() + " " + I18n.format("moa.feathers"));
			creativeList.add("\u2022 " + genePool.getKeratin().gene().localizedName() + " " + I18n.format("moa.keratin"));
			creativeList.add("\u2022 " + genePool.getEyes().gene().localizedName() + " " + I18n.format("moa.eyes"));

			creativeList.add("");

			creativeList.add( TextFormatting.YELLOW + "" + TextFormatting.ITALIC + "" + genePool.getWingStrength().gene().data() + " " + I18n.format("moa.jumps"));
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		IBlockState state = world.getBlockState(pos);

		boolean replaceable = state.getBlock().isReplaceable(world, pos);

		int yOffset = replaceable ? 0 : 1;

		if (stack.stackSize == 0)
		{
			return EnumActionResult.FAIL;
		}
		else if (!player.canPlayerEdit(pos, facing, stack))
		{
			return EnumActionResult.FAIL;
		}
		else if ((world.isAirBlock(pos.add(0, yOffset, 0)) || replaceable) && BlocksAether.moa_egg.canPlaceBlockAt(world, pos.add(0, yOffset, 0)))
		{
			if (player.capabilities.isCreativeMode || this.creativeEgg)
			{
				if (!world.isRemote)
				{
					EntityMoa moa = new EntityMoa(world, GeneUtil.getRandomSeed(world));
					moa.setPosition(pos.getX() + 0.5F, pos.getY() + (moa.height / 2), pos.getZ() + 0.5F);

					MoaGenePool stackGenePool = ItemMoaEgg.getGenePool(stack);

					moa.setRaisedByPlayer(true);

					world.spawnEntityInWorld(moa);

					MoaGenePool genePool = moa.getGenePool();

					if (this.creativeEgg)
					{
						genePool.transformFromSeed(GeneUtil.getRandomSeed(world));
					}
					else
					{
						genePool.transformFromParents(stackGenePool.getStorage().getSeed(), stackGenePool.getStorage().getFatherSeed(), stackGenePool.getStorage().getMotherSeed());
					}
				}

				--stack.stackSize;

				return EnumActionResult.SUCCESS;
			}
			else if (world.checkNoEntityCollision(BlocksAether.moa_egg.getCollisionBoundingBox(world.getBlockState(pos.add(0, yOffset, 0)), world, pos.add(0, yOffset, 0))) && world.setBlockState(pos.add(0, yOffset, 0), BlocksAether.moa_egg.getDefaultState()))
			{
				SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

				TileEntityMoaEgg egg = (TileEntityMoaEgg) world.getTileEntity(pos.add(0, yOffset, 0));

				if (egg != null)
				{
					MoaGenePool stackGenes = ItemMoaEgg.getGenePool(stack);
					MoaGenePool teGenes = egg.getGenePool();

					teGenes.transformFromParents(stackGenes.getStorage().getSeed(), stackGenes.getStorage().getFatherSeed(), stackGenes.getStorage().getMotherSeed());

					egg.setPlayerPlaced();
				}

				--stack.stackSize;

				return EnumActionResult.SUCCESS;
			}
		}

		return EnumActionResult.PASS;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		MoaGenePool genePool = ItemMoaEgg.getGenePool(stack);

		if (genePool.getMarks() != null && !this.creativeEgg && UtilModule.isClient())
		{
			return genePool.getMarks().gene().localizedName() + " " + I18n.format(super.getUnlocalizedName(stack) + ".name");
		}

		return super.getUnlocalizedName();
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return this.creativeEgg ? super.getItemStackDisplayName(stack) : this.getUnlocalizedName(stack);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{

	}

	@Override
	public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
	{
		return super.initCapabilities(stack, nbt);
	}

	private static class ModelProperty implements IItemPropertyGetter
	{

		private String propertyName;

		public ModelProperty(String propertyName)
		{
			this.propertyName = propertyName;
		}

		@SideOnly(Side.CLIENT)
		public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
		{
			if (stack != null)
			{
				MoaGenePool genePool = ItemMoaEgg.getGenePool(stack);

				if (genePool.getMarks() != null)
				{
					String mark = genePool.getMarks().gene().getResourceName();

					if (mark.equals(this.propertyName))
					{
						return 1.0F;
					}
				}
			}

			return 0.0F;
		}

	}

}
