package com.gildedgames.aether.client.gui.container;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.registry.recipes.IIndexableRecipe;
import com.gildedgames.aether.api.registry.recipes.IRecipeIndexRegistry;
import com.gildedgames.aether.client.gui.container.crafting.GhostItemStackButton;
import com.gildedgames.aether.client.gui.container.crafting.ItemStackButton;
import com.gildedgames.aether.client.gui.container.crafting.RecipeButton;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.ContainerSkyrootWorkbench;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

public class GuiSkyrootWorkbench extends GuiContainer
{
	private static final ResourceLocation CONTAINER_TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");

	private static final ResourceLocation BOOK_TEXTURE = AetherCore.getResource("textures/gui/inventory/book_recipes.png");

	private static final ResourceLocation BOOK_SCROLL_BAR = AetherCore.getResource("textures/gui/inventory/book_scroll_bar.png");

	private final Set<ItemStack> uniqueItems = new HashSet<>();

	private final List<IIndexableRecipe> craftableRecipes = new ArrayList<>();

	private final List<RecipeButton> recipeButtons = new ArrayList<>();

	private final List<GhostItemStackButton> ghostMatrixButtons = new ArrayList<>();

	private float scroll;

	private IIndexableRecipe selectedRecipe;

	private boolean isIndexDirty = true;

	public GuiSkyrootWorkbench(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition)
	{
		super(new ContainerSkyrootWorkbench(playerInv, worldIn, blockPosition));

		this.ySize = 174;
		this.xSize = 330;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		if (this.mc.player.inventory.getItemStack().isEmpty())
		{
			for (GhostItemStackButton button : this.ghostMatrixButtons)
			{
				boolean hovered = this.isPointInRegion(button.x, button.y, button.width, button.height, mouseX, mouseY);

				if (hovered && button.slot.getStack().isEmpty())
				{
					ArrayList<String> list = new ArrayList<>();

					if (button.stacks.size() > 1)
					{
						list.add(TextFormatting.GOLD + "" + TextFormatting.ITALIC + "(wildcard)");
					}

					list.add(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + "Required Item");

					this.drawTooltip(button.getItemStackForRender(), mouseX, mouseY, list);
				}
			}

			for (RecipeButton button : this.recipeButtons)
			{
				if (this.isPointInRegion(button.x, button.y, button.width, button.height, mouseX, mouseY))
				{
					this.drawTooltip(button.getItemStackForRender(), mouseX, mouseY,
							button.complete ? null : Collections.singletonList(TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Almost craftable!"));
				}
			}
		}
	}

	private void drawScroller()
	{
		this.mc.getTextureManager().bindTexture(BOOK_SCROLL_BAR);

//		this.drawTexturedModalRect(20, 20, 230, 230, 20, 20);
	}

	private void drawTooltip(ItemStack stack, int x, int y, @Nullable Collection<String> extra)
	{
		List<String> list = stack.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips);

		for (int i = 0; i < list.size(); ++i)
		{
			if (i == 0)
			{
				list.set(i, stack.getRarity().rarityColor + list.get(i));
			}
			else
			{
				list.set(i, TextFormatting.GRAY + list.get(i));
			}
		}

		if (extra != null)
		{
			list.add("");
			list.addAll(extra);
		}

		FontRenderer font = stack.getItem().getFontRenderer(stack);

		GuiUtils.preItemToolTip(stack);
		this.drawHoveringText(list, x, y, (font == null ? this.fontRenderer : font));
		GuiUtils.postItemToolTip();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		if (mouseButton == 0)
		{
			for (RecipeButton button : this.recipeButtons)
			{
				if (this.isPointInRegion(button.x, button.y, button.width, button.height, mouseX, mouseY))
				{
					button.performClick(mouseX, mouseY);

					this.setSelectedRecipe(this.craftableRecipes.get(button.id));

					return;
				}
			}
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type)
	{
		super.handleMouseClick(slotIn, slotId, mouseButton, type);

		this.isIndexDirty = true;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(I18n.format("container.crafting"), 178, 12, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 158, this.ySize - 96, 4210752);

		this.fontRenderer.drawString(I18n.format("container.aether_workbench.recipes"), 24, 12, 4210752);

		if (this.isIndexDirty)
		{
			this.rebuildIndex();
		}

		this.drawRecipeList(mouseX, mouseY);
		this.drawMatrixHelper(mouseX, mouseY);

		this.drawScroller();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;

		this.mc.getTextureManager().bindTexture(BOOK_TEXTURE);

		this.drawTexturedModalRect(i, j, 0, 0, 200, this.ySize);

		this.mc.getTextureManager().bindTexture(CONTAINER_TEXTURE);

		this.drawTexturedModalRect(i + 150, j + 6, 0, 0, 200, this.ySize);
	}

	private void drawRecipeList(int mouseX, int mouseY)
	{
		if (this.craftableRecipes.size() <= 0)
		{
			this.drawGradientRect(24, 26, 95, 133, 0x99000000, 0x99000000);

			this.drawCenteredString(this.fontRenderer, "Nothing to", 60, 70, 0xFFFFFFFF);
			this.drawCenteredString(this.fontRenderer, "craft!", 60, 82, 0xFFFFFFFF);

			return;
		}

		this.drawButtons(this.recipeButtons, mouseX, mouseY);
	}

	private void rebuildIndex()
	{
		this.isIndexDirty = false;

		this.updateUniqueItems();

		this.craftableRecipes.clear();
		this.craftableRecipes.addAll(this.getPossibleRecipes());
		this.craftableRecipes.sort(Comparator.comparing(recipe -> Item.getIdFromItem(recipe.getCraftingResult().getItem())));

		if (!this.craftableRecipes.contains(this.selectedRecipe))
		{
			this.setSelectedRecipe(null);
		}

		this.rebuildList();
		this.rebuildMatrix();
	}

	private void rebuildList()
	{
		this.recipeButtons.clear();

		int start = 0;

		for (int i = start; i < this.craftableRecipes.size() && i < start + 24; i++)
		{
			IIndexableRecipe recipe = this.craftableRecipes.get(i);

			int x = 25 + (18 * (i % 4));
			int y = 27 + (18 * (i / 4));

			this.recipeButtons.add(new RecipeButton(i, this, recipe, x, y, this.canPlayerCraft(recipe)));
		}
	}

	private boolean canPlayerCraft(IIndexableRecipe recipe)
	{
		for (Object obj : recipe.getCraftingMatrix())
		{
			if (obj == null)
			{
				continue;
			}

			final List<ItemStack> stacks = this.getItemStack(obj);

			boolean oneMatched = stacks.size() <= 0;

			for (ItemStack stack : stacks)
			{
				if (!stack.isEmpty())
				{
					if (this.uniqueItems.stream().anyMatch(o -> ItemStack.areItemsEqual(o, stack)))
					{
						oneMatched = true;
					}
				}
			}

			if (!oneMatched)
			{
				return false;
			}
		}

		return true;
	}

	private List<ItemStack> getItemStack(Object obj)
	{
		if (obj instanceof ItemStack)
		{
			return Collections.singletonList((ItemStack) obj);
		}
		else if (obj instanceof Collection)
		{
			return (List<ItemStack>) obj;
		}
		else
		{
			return Collections.emptyList();
		}
	}

	private void rebuildMatrix()
	{
		this.ghostMatrixButtons.clear();

		if (this.getSelectedRecipe() == null)
		{
			return;
		}

		List<Object> matrix = this.getSelectedRecipe().getCraftingMatrix();

		for (int i = 0; i < matrix.size(); i++)
		{
			Object obj = matrix.get(i);

			List<ItemStack> stacks = this.getItemStack(obj);

			if (!stacks.isEmpty())
			{
				int x = (i % this.getSelectedRecipe().getRecipeWidth());
				int y = (i / this.getSelectedRecipe().getRecipeWidth());

				if (y > this.getSelectedRecipe().getRecipeHeight())
				{
					break;
				}

				Slot slot = this.inventorySlots.getSlot(1 + (x + (y * 3)));

				this.ghostMatrixButtons.add(new GhostItemStackButton(this, i, slot, stacks, slot.xPos, slot.yPos));
			}
		}
	}

	private HashSet<IIndexableRecipe> getPossibleRecipes()
	{
		IRecipeIndexRegistry index = AetherAPI.content().craftable();

		HashSet<IIndexableRecipe> recipes = new HashSet<>();

		for (ItemStack item : this.uniqueItems)
		{
			Collection<IIndexableRecipe> t = index.getRecipesContainingItem(item);
			t.stream().filter(this::canPlayerCraft).forEach(recipes::add);
		}

		return recipes;
	}

	private void updateUniqueItems()
	{
		this.uniqueItems.clear();

		for (Slot slot : this.inventorySlots.inventorySlots)
		{
			if (slot instanceof SlotCrafting)
			{
				continue;
			}

			if (!slot.getStack().isEmpty())
			{
				this.uniqueItems.add(slot.getStack());
			}
		}

		ItemStack transitStack = Minecraft.getMinecraft().player.inventory.getItemStack();

		if (!transitStack.isEmpty())
		{
			this.uniqueItems.add(transitStack);
		}
	}

	private void drawMatrixHelper(int mouseX, int mouseY)
	{
		this.drawButtons(this.ghostMatrixButtons, mouseX, mouseY);
	}

	private void drawButtons(Collection<? extends ItemStackButton> buttons, int mouseX, int mouseY)
	{
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableDepth();

		for (ItemStackButton button : buttons)
		{
			boolean hovered = this.isPointInRegion(button.x, button.y, button.width, button.height, mouseX, mouseY);

			button.drawButton(mouseX, mouseY, hovered, false);
		}

		GlStateManager.disableDepth();
		RenderHelper.disableStandardItemLighting();
	}

	private void setSelectedRecipe(IIndexableRecipe recipe)
	{
		if (recipe != null)
		{
			Validate.isTrue(this.craftableRecipes.contains(recipe), "Recipe is not in craftable list");
		}

		this.selectedRecipe = recipe;

		this.rebuildMatrix();
	}

	public IIndexableRecipe getSelectedRecipe()
	{
		return this.selectedRecipe;
	}

	public boolean isDragging(Slot slot)
	{
		return this.dragSplittingSlots.contains(slot);
	}
}
