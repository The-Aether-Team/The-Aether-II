package com.gildedgames.aether.common.items.miscellaneous;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.moa.EntityMoa;
import com.gildedgames.aether.common.entities.moa.MoaGenetics;
import com.gildedgames.aether.common.tile_entities.TileEntityMoaEgg;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
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
		this.setMaxStackSize(1);

		this.addPropertyOverride(new ResourceLocation("circles"), new ModelProperty("circles"));
		this.addPropertyOverride(new ResourceLocation("curves"), new ModelProperty("curves"));
		this.addPropertyOverride(new ResourceLocation("ladder"), new ModelProperty("ladder"));
		this.addPropertyOverride(new ResourceLocation("lines"), new ModelProperty("lines"));
		this.addPropertyOverride(new ResourceLocation("spots"), new ModelProperty("spots"));
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List creativeList, boolean par4)
	{
		NBTTagCompound tag = par1ItemStack.getTagCompound();

		if (tag != null)
		{
			if (tag.hasKey("jumps"))
			{
				creativeList.add(tag.getInteger("jumps") + " Jumps");
			}
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		IBlockState state = world.getBlockState(pos);

		boolean replaceable = state != null && state.getBlock().isReplaceable(world, pos);

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
					EntityMoa moa = new EntityMoa(world);
					moa.setPosition(pos.getX() + 0.5F, pos.getY() + (moa.height / 2), pos.getZ() + 0.5F);

					if (stack.getTagCompound() != null)
					{
						moa.setGeneticSeed(stack.getTagCompound().getInteger("fatherGeneticSeed"));

						moa.setFatherSeed(stack.getTagCompound().getInteger("fatherGeneticSeed"));
						moa.setMotherSeed(stack.getTagCompound().getInteger("motherGeneticSeed"));
						moa.setRaisedByPlayer(true);
					}

					world.spawnEntityInWorld(moa);
				}

				--stack.stackSize;

				return EnumActionResult.SUCCESS;
			}
			else if (world.checkNoEntityCollision(BlocksAether.moa_egg.getCollisionBoundingBox(world.getBlockState(pos.add(0, yOffset, 0)), world, pos.add(0, yOffset, 0))) && world.setBlockState(pos.add(0, yOffset, 0), BlocksAether.moa_egg.getDefaultState()))
			{
				SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

				TileEntityMoaEgg egg = (TileEntityMoaEgg) world.getTileEntity(pos.add(0, yOffset, 0));

				if (egg != null && stack.getTagCompound() != null)
				{
					egg.setFatherSeed(stack.getTagCompound().getInteger("fatherGeneticSeed"));
					egg.setMotherSeed(stack.getTagCompound().getInteger("motherGeneticSeed"));
					egg.setPlayerPlaced();
				}

				--stack.stackSize;

				return EnumActionResult.SUCCESS;
			}
		}

		return EnumActionResult.PASS;
	}

	public static NBTTagCompound getNBTFromGenetics(MoaGenetics genetics)
	{
		NBTTagCompound nbt = new NBTTagCompound();

		genetics.writeToNBT(nbt);

		nbt.setBoolean("genericDisplay", false);

		return nbt;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();

		if (tag != null && stack.getTagCompound().hasKey("name") && !this.creativeEgg)
		{
			return I18n.format(tag.getString("name")) + " " + I18n.format(super.getUnlocalizedName(stack) + ".name");
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
			if (stack != null && stack.getTagCompound() != null)
			{
				String mark = this.formatToGetName(stack.getTagCompound().getString("markBack"));

				if (mark.equals(this.propertyName))
				{
					return 1.0F;
				}
			}

			return 0.0F;
		}

		private final String formatToGetName(String resourcePath)
		{
			return resourcePath.substring(resourcePath.lastIndexOf("/") + 1, resourcePath.length()).replace(".png", "");
		}

	}

}
