package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.registry.minecraft.CreativeTabsAether;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemAetherTool extends ItemTool
{
	protected final EnumToolType toolType;

	protected final String name;

	public ItemAetherTool(Item.ToolMaterial material, String name, EnumToolType toolType, float attackDamage, float attackSpeed)
	{
		super(attackDamage, attackSpeed, material, toolType.getEffectiveBlocks());

		this.toolType = toolType;
		this.name = name;

		ObfuscationReflectionHelper.setPrivateValue(ItemTool.class, this, toolType.getToolClass(), ReflectionAether.TOOL_CLASS.getMappings());

		this.setCreativeTab(CreativeTabsAether.TOOLS);
	}

	public EnumToolType getToolType()
	{
		return this.toolType;
	}

	protected boolean isAbilityPassive()
	{
		return true;
	}

	protected boolean hasAbility()
	{
		return true;
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn)
	{
		if (this.toolType.getToolClass() == "shovel")
		{
			Block block = blockIn.getBlock();
			return block == Blocks.SNOW_LAYER ? true : block == Blocks.SNOW;
		}
		else if (this.toolType.getToolClass() == "pickaxe")
		{
			Block block = blockIn.getBlock();

			if (block == Blocks.OBSIDIAN)
			{
				return this.toolMaterial.getHarvestLevel() == 3;
			}
			else if (block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE)
			{
				if (block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK)
				{
					if (block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE)
					{
						if (block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE)
						{
							if (block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE)
							{
								if (block != Blocks.REDSTONE_ORE && block != Blocks.LIT_REDSTONE_ORE)
								{
									Material material = blockIn.getMaterial();
									return material == Material.ROCK ? true : (material == Material.IRON ? true : material == Material.ANVIL);
								}
								else
								{
									return this.toolMaterial.getHarvestLevel() >= 2;
								}
							}
							else
							{
								return this.toolMaterial.getHarvestLevel() >= 1;
							}
						}
						else
						{
							return this.toolMaterial.getHarvestLevel() >= 1;
						}
					}
					else
					{
						return this.toolMaterial.getHarvestLevel() >= 2;
					}
				}
				else
				{
					return this.toolMaterial.getHarvestLevel() >= 2;
				}
			}
			else
			{
				return this.toolMaterial.getHarvestLevel() >= 2;
			}
		}

		return super.canHarvestBlock(blockIn);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		if (this.toolType.getToolClass() == "axe")
		{
			Material material = state.getMaterial();
			return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE ? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
		}
		else if (this.toolType.getToolClass() == "pickaxe")
		{
			Material material = state.getMaterial();
			return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK ? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
		}

		return super.getStrVsBlock(stack, state);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass)
	{
		int level = super.getHarvestLevel(stack, toolClass);

		if (level == -1 && toolClass.equals(this.toolType.getToolClass()))
		{
			return this.toolMaterial.getHarvestLevel();
		}
		else
		{
			return level;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		if (this.hasAbility())
		{
			tooltip.add(String.format("%s: %s",
					TextFormatting.BLUE + I18n.format("item.aether.tooltip.ability"),
					TextFormatting.WHITE + I18n.format("item.aether.tool." + this.name + ".ability.desc")));

			if (!this.isAbilityPassive())
			{
				tooltip.add(String.format("%s: %s",
						TextFormatting.DARK_AQUA + I18n.format("item.aether.tooltip.use"),
						TextFormatting.WHITE + I18n.format("item.aether.tool." + this.name + ".use.desc")));
			}
		}
	}
}
