package com.gildedgames.aether.common.blocks.decorative;

import com.gildedgames.aether.common.blocks.util.BlockRotatable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockParentPillar extends BlockRotatable
{
	private final Block parentBlock;

	public BlockParentPillar(final Material material, final Block parentBlock)
	{
		super(material);

		this.parentBlock = parentBlock;
	}

	@Override
	public BlockParentPillar setSoundType(final SoundType type)
	{
		super.setSoundType(type);

		return this;
	}

	@Override
	public String getUnlocalizedName()
	{
		return this.parentBlock.getUnlocalizedName();
	}

	@Override
    @SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, @Nullable final World player, final List<String> tooltip, final ITooltipFlag advanced)
	{
		tooltip.add(TextFormatting.GRAY + I18n.format("tile." + this.parentBlock.getRegistryName() + ".pillar.name"));
	}
}
