package com.gildedgames.aether.client.gui.container;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.recipes.IIndexableRecipe;
import com.gildedgames.aether.api.registry.recipes.IRecipeIndexRegistry;
import com.gildedgames.aether.client.gui.container.crafting.CraftingBookMatrixButton;
import com.gildedgames.aether.client.gui.container.crafting.CraftingBookPageButton;
import com.gildedgames.aether.client.gui.container.crafting.CraftingBookRecipeButton;
import com.gildedgames.aether.client.gui.container.crafting.CraftingBookTabButton;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.ContainerSkyrootWorkbench;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GuiSkyrootWorkbench extends GuiContainer implements IContainerListener
{
	private static final ResourceLocation CONTAINER_TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");

	private static final ResourceLocation BOOK_TEXTURE = AetherCore.getResource("textures/gui/inventory/book_recipes.png");

	private static final ResourceLocation BOOK_SCROLL_BAR = AetherCore.getResource("textures/gui/inventory/book_scroll_bar.png");

	// Used to see when the player's inventory changes, keeps the UI up-to-date
	private final NonNullList<ItemStack> recordedInv;

	private final Set<ItemStack> uniqueItems = new HashSet<>();

	private List<IIndexableRecipe> availableRecipes = new ArrayList<>(),
			filteredRecipes = new ArrayList<>();

	private List<CreativeTabs> availableTabs = new ArrayList<>();

	private CreativeTabs selectedTab = CreativeTabsAether.CONSTRUCTION;

	private IIndexableRecipe selectedRecipe;

	private CraftingBookPageButton upPageBtn, downPageBtn;

	private int tabPageIndex = 0, tabPageMax;

	private boolean indexDirty;

	public GuiSkyrootWorkbench(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition)
	{
		super(new ContainerSkyrootWorkbench(playerInv, worldIn, blockPosition));

		this.ySize = 174;
		this.xSize = 330;

		this.recordedInv = NonNullList.withSize(this.inventorySlots.getInventory().size(), ItemStack.EMPTY);
	}

	@Override
	public void initGui()
	{
		super.initGui();

		this.inventorySlots.removeListener(this);
		this.inventorySlots.addListener(this);

		this.rebuildGui();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button instanceof CraftingBookTabButton)
		{
			CraftingBookTabButton tabBtn = ((CraftingBookTabButton) button);

			if (tabBtn.getTab() != null)
			{
				this.selectedTab = tabBtn.getTab();
				this.rebuildGui();
			}
		}
		else if (button instanceof CraftingBookRecipeButton)
		{
			this.setSelectedRecipe(this.filteredRecipes.get(button.id));

			this.rebuildGui();
		}
		else if (button instanceof CraftingBookPageButton)
		{
			CraftingBookPageButton pageBtn = ((CraftingBookPageButton) button);

			if (pageBtn.enabled)
			{
				if (pageBtn == this.upPageBtn)
				{
					this.tabPageIndex += 1;
				}
				else if (pageBtn == this.downPageBtn)
				{
					this.tabPageIndex -= 1;
				}

				this.rebuildGui();
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		for (int i = 0; i < this.inventorySlots.getInventory().size(); i++)
		{
			if (!ItemStack.areItemStacksEqual(this.recordedInv.get(i), this.inventorySlots.getInventory().get(i)))
			{
				this.indexDirty = true;

				break;
			}
		}

		if (this.indexDirty)
		{
			this.rebuildGui();
		}

		super.drawScreen(mouseX, mouseY, partialTicks);

		for (GuiButton btn : this.buttonList)
		{
			if (btn instanceof CraftingBookMatrixButton)
			{
				CraftingBookMatrixButton ghostBtn = (CraftingBookMatrixButton) btn;

				if (ghostBtn.isMouseOver() && ghostBtn.slot.getStack().isEmpty())
				{
					ArrayList<String> list = new ArrayList<>();

					if (ghostBtn.stacks.size() > 1)
					{
						list.add(TextFormatting.GOLD + "" + TextFormatting.ITALIC + "(wildcard)");
					}

					list.add(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + "Required Item");

					this.drawTooltip(ghostBtn.getItemStackForRender(), mouseX, mouseY, list);
				}
			}
			else if (btn instanceof CraftingBookRecipeButton)
			{
				CraftingBookRecipeButton recipeBtn = (CraftingBookRecipeButton) btn;

				if (recipeBtn.isMouseOver())
				{
					this.drawTooltip(recipeBtn.getItemStackForRender(), mouseX, mouseY,
							recipeBtn.complete ? null : Collections.singletonList(TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Almost craftable!"));
				}
			}
			else if (btn instanceof CraftingBookTabButton)
			{
				CraftingBookTabButton guiTab = ((CraftingBookTabButton) btn);

				if (guiTab.isMouseOver())
				{
					this.drawHoveringText(Collections.singletonList(I18n.format(guiTab.getTab().getTranslatedTabLabel())),
							mouseX, mouseY, this.fontRenderer);
				}
			}
		}
	}

	private void drawScroller()
	{
		this.mc.getTextureManager().bindTexture(BOOK_SCROLL_BAR);
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
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(I18n.format("container.crafting"), 178, 12, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 158, this.ySize - 96, 4210752);

		this.fontRenderer.drawString(I18n.format("container.aether_workbench.recipes",
				this.filteredRecipes.size(), this.availableRecipes.size()), 24, 12, 4210752);

		this.drawRecipeList(mouseX, mouseY);
		this.drawScroller();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		this.mc.getTextureManager().bindTexture(BOOK_TEXTURE);

		this.drawTexturedModalRect(x, y, 0, 0, 150, this.ySize);

		this.mc.getTextureManager().bindTexture(CONTAINER_TEXTURE);

		this.drawTexturedModalRect(x + 150, y + 6, 0, 0, 180, this.ySize);
	}

	private void drawRecipeList(int mouseX, int mouseY)
	{
		if (this.availableRecipes.size() <= 0)
		{
			this.drawGradientRect(24, 26, 95, 133, 0x99000000, 0x99000000);

			this.drawCenteredString(this.fontRenderer, "Nothing to", 60, 70, 0xFFFFFFFF);
			this.drawCenteredString(this.fontRenderer, "craft!", 60, 82, 0xFFFFFFFF);
		}
	}

	private void rebuildGui()
	{
		this.indexDirty = false;

		this.buttonList.clear();

		this.updateUniqueItems();

		this.availableRecipes.clear();
		this.availableRecipes.addAll(this.getPossibleRecipes());
		this.availableRecipes.sort(Comparator.comparing(recipe -> Item.getIdFromItem(recipe.getCraftingResult().getItem())));

		this.rebuildTabs();
		this.rebuildList();
		this.rebuildMatrix();
	}

	private void rebuildList()
	{
		int start = 0;

		this.filteredRecipes = this.availableRecipes.stream()
				.filter(recipe -> recipe.getCraftingResult().getItem().getCreativeTab() == this.selectedTab)
				.collect(Collectors.toList());

		for (int i = start; i < this.filteredRecipes.size() && i < start + 24; i++)
		{
			IIndexableRecipe recipe = this.filteredRecipes.get(i);

			int x = 25 + (18 * (i % 4));
			int y = 27 + (18 * (i / 4));

			this.buttonList.add(new CraftingBookRecipeButton(i, this, recipe, this.guiLeft + x, this.guiTop + y, this.canPlayerCraft(recipe)));
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

	private void rebuildTabs()
	{
		this.buttonList.clear();

		this.availableTabs = new ArrayList<>(this.availableRecipes.stream()
				.map(recipe -> recipe.getCraftingResult().getItem().getCreativeTab())
				.collect(Collectors.toSet()));

		this.availableTabs.sort(Comparator.comparingInt(CreativeTabs::getTabIndex));

		this.tabPageMax = this.availableTabs.size() / 5;

		// Add page buttons
		if (this.tabPageMax > 0)
		{
			this.downPageBtn = new CraftingBookPageButton(1, this.guiLeft - 11, this.guiTop + 140, "<<");
			this.upPageBtn = new CraftingBookPageButton(0, this.guiLeft - 11, this.guiTop + 124, ">>");

			this.upPageBtn.enabled = this.tabPageIndex < this.tabPageMax;
			this.downPageBtn.enabled = this.tabPageIndex > 0;

			this.buttonList.add(this.downPageBtn);
			this.buttonList.add(this.upPageBtn);
		}

		for (int i = 0; ((this.tabPageIndex * 5) + i) < this.availableTabs.size() && i < (this.tabPageIndex * 5) + 5; i++)
		{
			CreativeTabs tab = this.availableTabs.get((this.tabPageIndex * 5) + i);

			this.buttonList.add(new CraftingBookTabButton(this, tab, this.guiLeft - 16, this.guiTop + 8 + (i * 22)));
		}

		if (this.selectedTab != null)
		{
			if (this.availableTabs.size() > 0)
			{
				if (!this.availableTabs.contains(this.selectedTab))
				{
					this.selectedTab = this.availableTabs.get(0);
					this.tabPageIndex = 0;
				}
				else if (this.tabPageIndex > this.tabPageMax)
				{
					this.tabPageIndex = this.availableTabs.indexOf(this.selectedTab) / 5;
				}
			}
			else
			{
				this.selectedTab = null;
			}
		}
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
		if (!this.availableRecipes.contains(this.selectedRecipe))
		{
			this.setSelectedRecipe(null);
		}

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

				this.buttonList.add(new CraftingBookMatrixButton(this, i, slot, stacks, this.guiLeft + slot.xPos, this.guiTop + slot.yPos));
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

	public IIndexableRecipe getSelectedRecipe()
	{
		return this.selectedRecipe;
	}

	private void setSelectedRecipe(IIndexableRecipe recipe)
	{
		this.selectedRecipe = recipe;

		if (this.selectedRecipe != null)
		{
			Validate.isTrue(this.availableRecipes.contains(recipe), "Recipe is not in craftable array");

			this.rebuildGui();
		}
	}

	public CreativeTabs getSelectedTab()
	{
		return this.selectedTab;
	}

	@Override
	public void updateCraftingInventory(Container containerToSend, NonNullList<ItemStack> itemsList)
	{
		this.indexDirty = true;
	}

	@Override
	public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack)
	{
		this.indexDirty = true;
	}

	@Override
	public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) { }

	@Override
	public void sendAllWindowProperties(Container containerIn, IInventory inventory) { }
}
