package com.gildedgames.aether.client.ui.minecraft.util;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.common.Ui;
import com.gildedgames.aether.client.ui.data.rect.Rect;
import com.gildedgames.aether.client.ui.minecraft.util.events.MinecraftHoveredDesc;
import com.gildedgames.aether.client.ui.minecraft.util.wrappers.MinecraftButtonItemStack;
import com.gildedgames.aether.client.ui.minecraft.util.wrappers.MinecraftItemStackRender;
import com.gildedgames.aether.client.ui.util.events.slots.SlotStackFactory;
import com.gildedgames.aether.client.ui.util.factory.ContentFactory;
import com.gildedgames.aether.client.ui.util.factory.Factory;
import com.gildedgames.aether.common.util.helpers.ItemUtil;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameData;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ItemStackButtonFactory<T> implements ContentFactory<Ui>
{

	public enum StackTypes
	{

		BLOCKS
				{
					@Override
					List<ItemStack> createStacks()
					{
						List<ItemStack> blockStacks = new ArrayList<>();

						for (final Block block : GameData.getBlockRegistry().typeSafeIterable())//TODO: Make sure this gets all blocks
						{
							if (block == null)
							{
								continue;
							}

							final Item item = Item.getItemFromBlock(block);

							if (item == null)
							{
								continue;
							}

							List<ItemStack> subBlocks = new ArrayList<>();

							block.getSubBlocks(item, item.getCreativeTab(), subBlocks);

							for (final ItemStack stack : subBlocks)
							{
								if (ItemUtil.getBlockState(stack) == Blocks.AIR.getDefaultState())
								{
									continue;
								}

								blockStacks.add(stack);
							}
						}

						return blockStacks;
					}
				},
		ITEMS
				{
					@Override
					List<ItemStack> createStacks()
					{
						return null;
					}
				},
		ALL
				{
					@Override
					List<ItemStack> createStacks()
					{
						List<ItemStack> stacks = new ArrayList<>();

						stacks.addAll(ITEMS.createStacks());
						stacks.addAll(BLOCKS.createStacks());

						return stacks;
					}
				};

		abstract List<ItemStack> createStacks();

	}

	private StackTypes stackTypes;

	private Function<ItemStack, T> dataFactory;

	public ItemStackButtonFactory(StackTypes stackTypes, Function<ItemStack, T> dataFactory)
	{
		this.stackTypes = stackTypes;
		this.dataFactory = dataFactory;
	}

	@Override
	public LinkedHashMap<String, Ui> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea)
	{
		LinkedHashMap<String, Ui> buttons = new LinkedHashMap<>();

		for (ItemStack stack : this.stackTypes.createStacks())
		{
			final MinecraftButtonItemStack button = new MinecraftButtonItemStack(stack);

			button.events().set("description", new MinecraftHoveredDesc(GuiFactory.text(stack.getDisplayName(), Color.WHITE)));

			button.events().set("draggableBehavior", new SlotStackFactory<>(new Function<T, GuiFrame>()
			{

				@Override
				public GuiFrame apply(T input)
				{
					return new MinecraftItemStackRender(button.getItemStack());
				}

			},
					new Factory<T>()
					{

						@Override
						public T create()
						{
							return ItemStackButtonFactory.this.dataFactory.apply(button.getItemStack());
						}

					}));

			buttons.put(stack.getUnlocalizedName(), button);
		}

		return buttons;
	}

}
