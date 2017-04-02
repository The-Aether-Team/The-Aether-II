package com.gildedgames.aether.common.items.misc;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePoolDataSet;
import com.gildedgames.aether.common.entities.genes.util.DataGene;
import com.gildedgames.aether.common.entities.genes.util.GeneUtil;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.tiles.TileEntityMoaEgg;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
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
import java.awt.*;
import java.util.List;

public class ItemMoaFeather extends Item
{

	public ItemMoaFeather()
	{
		super();

		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		ItemStack feather = new ItemStack(itemIn);
		ItemMoaFeather.setColor(feather, I18n.format("moa.feathers.dawn"), new Color(0x83c4e2).getRGB());

		subItems.add(feather);
	}

	public static void setColor(ItemStack stack, String colorName, int color)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}

		NBTTagCompound tag = new NBTTagCompound();

		tag.setString("colorName", colorName);
		tag.setInteger("color", color);

		stack.getTagCompound().setTag("featherColor", tag);
	}

	public static int getColor(ItemStack stack)
	{
		if (stack.getTagCompound() == null)
		{
			ItemMoaFeather.setColor(stack, "", 0xFFFFFF);
		}

		NBTTagCompound tag = stack.getTagCompound().getCompoundTag("featherColor");

		return tag.getInteger("color");
	}

	@SideOnly(Side.CLIENT)
	public static String getColorName(ItemStack stack)
	{
		if (stack.getTagCompound() == null)
		{
			ItemMoaFeather.setColor(stack, "", 0xFFFFFF);
		}

		NBTTagCompound tag = stack.getTagCompound().getCompoundTag("featherColor");

		return I18n.format(tag.getString("colorName"));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		String colorName = ItemMoaFeather.getColorName(stack);

		if (!colorName.isEmpty() && AetherCore.isClient())
		{
			return colorName + " " + I18n.format(super.getUnlocalizedName(stack) + ".name");
		}

		return I18n.format(super.getUnlocalizedName(stack) + ".name");
	}


	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return this.getUnlocalizedName(stack);
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

}
