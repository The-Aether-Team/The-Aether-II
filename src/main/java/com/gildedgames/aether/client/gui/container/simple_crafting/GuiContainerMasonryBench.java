package com.gildedgames.aether.client.gui.container.simple_crafting;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.recipes.simple.ISimpleRecipe;
import com.gildedgames.aether.api.recipes.simple.ISimpleRecipeGroup;
import com.gildedgames.aether.client.gui.container.IExtendedContainer;
import com.gildedgames.aether.client.gui.util.ButtonCallbacks;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketMasonryRecipeChanged;
import com.gildedgames.aether.common.recipes.simple.OreDictionaryRequirement;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import com.gildedgames.orbis.lib.util.InputHelper;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GuiContainerMasonryBench extends ContainerScreen<ContainerMasonryBench> implements IExtendedContainer
{

	private static final ResourceLocation MASONRY_BENCH = AetherCore.getResource("textures/gui/inventory/masonry_bench.png");

	private static final ResourceLocation BOOK_SCROLL_BAR = AetherCore.getResource("textures/gui/inventory/book_scroll_bar.png");

	private static final ResourceLocation BOOK_RECIPES = AetherCore.getResource("textures/gui/inventory/book_recipes.png");

	private static final ResourceLocation DARK_CRAFTING_OVERLAY = AetherCore.getResource("textures/gui/inventory/dark_crafting_overlay.png");

	private static final ResourceLocation UP_ARROW = AetherCore.getResource("textures/gui/inventory/up_arrow.png");

	private static final ResourceLocation DOWN_ARROW = AetherCore.getResource("textures/gui/inventory/down_arrow.png");

	private final GuiCraftingOption[] options = new GuiCraftingOption[24];

	private final GuiRequiredMaterial[] materials = new GuiRequiredMaterial[9];

	public final List<ISimpleRecipe> recipes = Lists.newArrayList();

	public List<String> hoverDescription;

	private ItemStack hoveredStack;

	/** Amount scrolled in crafting options inventory (0 = top, 1 = bottom) */
	private float currentScroll;

	/** True if the scrollbar is being dragged */
	private boolean isScrolling;

	/** True if the left mouse button was held down last time drawScreen was called. */
	private boolean wasClicking;

	private GuiRequiredMaterial result;

	private ISimpleRecipe currentRecipe;

	private GuiXButton xButton;

	private ItemStack lastStack = ItemStack.EMPTY;

	private GuiCounterButton up, down;

	public GuiContainerMasonryBench(ContainerMasonryBench container, PlayerInventory inventory, ITextComponent name)
	{
		super(container, inventory, name);
	}

	@Override
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type)
	{
		super.handleMouseClick(slotIn, slotId, mouseButton, type);

		if (slotIn != null && slotIn.getHasStack())
		{
			this.updateCraftingOptions();
		}
	}

	private void onRecipeButtonClicked(Button button)
	{
		if (button instanceof GuiCraftingOption)
		{
			GuiCraftingOption option = (GuiCraftingOption) button;

			if (option.getRecipe() != null)
			{
				for (int k = 0; k < 3; ++k)
				{
					for (int l = 0; l < 3; ++l)
					{
						int index = l + k * 3;

						if (index >= 0 && index < option.getRecipe().getRequired().length)
						{
							Object req = option.getRecipe().getRequired()[index];

							if (req instanceof ItemStack)
							{
								this.materials[index].setRequiredObject(req);
							}
							else if (req instanceof OreDictionaryRequirement)
							{
								OreDictionaryRequirement oreReq = (OreDictionaryRequirement) req;

								this.materials[index].setRequiredObject(oreReq);
							}
						}
						else
						{
							this.materials[index].setRequiredObject(null);
						}
					}
				}

				this.currentRecipe = option.getRecipe();

				this.container.onNewRecipe(this.currentRecipe);

				NetworkingAether.sendPacketToServer(new PacketMasonryRecipeChanged(this.currentRecipe));

				if (hasShiftDown())
				{
					this.container.setInputCount(64);
				}

				if (!RecipeUtil.canCraft(this.playerInventory, option.getRecipe()) && option.getRecipe() != null)
				{
					this.result.setRequiredObject(option.getRecipe().getResult());
				}
				else
				{
					this.result.setRequiredObject(null);
				}
			}
		}
	}

	private void scrollTo(float scroll)
	{
		int i = (this.recipes.size() + 4 - 1) / 4 - 6;
		int j = (int) ((double) (scroll * (float) i) + 0.5D);

		if (j < 0)
		{
			j = 0;
		}

		for (int k = 0; k < 6; ++k)
		{
			for (int l = 0; l < 4; ++l)
			{
				int i1 = l + (k + j) * 4;

				if (i1 >= 0 && i1 < this.recipes.size())
				{
					this.options[l + k * 4].setRecipe(this.recipes.get(i1));
				}
				else
				{
					this.options[l + k * 4].setRecipe(null);
				}
			}
		}
	}

	private boolean canScroll()
	{
		return this.recipes.size() > this.options.length;
	}

	private void updateCraftingOptions()
	{
		this.recipes.clear();

		List<ItemStack> uniqueStacks = Lists.newArrayList();

		for (ItemStack stack : this.playerInventory.mainInventory)
		{
			if (!stack.isEmpty() && !uniqueStacks.contains(stack))
			{
				uniqueStacks.add(stack);
			}
		}

		for (ItemStack stack : uniqueStacks)
		{
			Collection<ISimpleRecipeGroup> groups = AetherAPI.content().masonry().getRecipesFromRequirement(stack);

			for (ISimpleRecipeGroup group : groups)
			{
				if (group != null)
				{
					this.recipes.addAll(group.getRecipes());
				}
			}
		}

		PlayerEntity p = this.minecraft.player;

		this.recipes.sort((o1, o2) ->
		{
			int countO1 = RecipeUtil.getTotalTimesCanCraft(p, o1);
			int countO2 = RecipeUtil.getTotalTimesCanCraft(p, o2);

			int id1 = Item.getIdFromItem(o1.getResult().getItem());
			int id2 = Item.getIdFromItem(o2.getResult().getItem());

			if (countO1 > 0 && countO2 <= 0)
			{
				return 1;
			}

			if (countO2 > 0 && countO1 <= 0)
			{
				return -1;
			}

			return Integer.compare(id1, id2);
		});

		Collections.reverse(this.recipes);

		this.currentScroll = 0.0F;
		this.scrollTo(0.0F);
	}

	@Override
	public void init()
	{
		super.init();

		this.ySize = 170;

		this.guiLeft = 30 + ((this.width - this.xSize) / 2);
		this.guiTop = ((this.height - this.ySize) / 2);

		for (int i = 0; i < 6; ++i)
		{
			for (int j = 0; j < 4; ++j)
			{
				GuiCraftingOption option = new GuiCraftingOption(this.guiLeft - 126 + (j * 18), this.guiTop + (21 + i * 18), null, this::onRecipeButtonClicked);

				this.addButton(option);

				this.options[j + i * 4] = option;
			}
		}

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 3; ++j)
			{
				GuiRequiredMaterial material = new GuiRequiredMaterial(this.guiLeft + 50 + (j * 18), this.guiTop + (36 + i * 18), null, ButtonCallbacks.DUMMY);

				material.resultStack = true;

				if (this.currentRecipe != null && j + i * 3 < this.currentRecipe.getRequired().length)
				{
					Object req = this.currentRecipe.getRequired()[j + i * 3];

					if (req instanceof ItemStack)
					{
						material.setRequiredObject(req);
					}
					else if (req instanceof OreDictionaryRequirement)
					{
						OreDictionaryRequirement oreReq = (OreDictionaryRequirement) req;

						material.setRequiredObject(oreReq);
					}
				}

				this.addButton(material);

				this.materials[j + i * 3] = material;
			}
		}

		this.result = new GuiRequiredMaterial(this.guiLeft + 105, this.guiTop + 36, null, ButtonCallbacks.DUMMY);
		this.result.resultStack = true;

		this.addButton(this.result);

		this.xButton = new GuiXButton(this.guiLeft + 38, this.guiTop + 36, (btn) -> {
			this.currentRecipe = null;

			this.container.onNewRecipe(null);

			NetworkingAether.sendPacketToServer(new PacketMasonryRecipeChanged(null));

			for (GuiRequiredMaterial material : this.materials)
			{
				material.setRequiredObject(null);
			}
		});

		this.xButton.visible = false;

		this.addButton(this.xButton);

		this.up = new GuiCounterButton(this.guiLeft + 109, this.guiTop + 22, UP_ARROW, (btn) -> {
			this.container.setInputCount(this.container.getInputCount() + 1);
		});

		this.down = new GuiCounterButton( this.guiLeft + 109, this.guiTop + 61, DOWN_ARROW, (btn) -> {
			this.container.setInputCount(this.container.getInputCount() - 1);
		});

		this.addButton(this.up);
		this.addButton(this.down);

		this.updateCraftingOptions();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		if (this.lastStack.isEmpty() != this.playerInventory.getItemStack().isEmpty())
		{
			this.updateCraftingOptions();
		}

		this.lastStack = this.playerInventory.getItemStack();

		this.renderBackground();

		if (this.currentRecipe != null)
		{
			if (!RecipeUtil.areEqual(this.result.getRequiredObject(), this.currentRecipe.getResult()) &&
					!RecipeUtil.canCraft(this.playerInventory, this.currentRecipe))
			{
				this.result.setRequiredObject(this.currentRecipe.getResult());
			}
		}
		else
		{
			this.result.setRequiredObject(null);
		}

		boolean flag = this.minecraft.mouseHelper.isLeftDown();
		int i = this.guiLeft;
		int j = this.guiTop;
		int k = i - 48;
		int l = j + 22;
		int i1 = k + 14;
		int j1 = l + 108;

		if (!this.wasClicking && flag && mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1)
		{
			this.isScrolling = this.needsScrollBars();
		}

		if (!flag)
		{
			this.isScrolling = false;
		}

		this.wasClicking = flag;

		if (this.isScrolling)
		{
			this.currentScroll = ((float) (mouseY - l) - 7.5F) / ((float) (j1 - l) - 15.0F);
			this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0F, 1.0F);

			this.scrollTo(this.currentScroll);
		}

		super.render(mouseX, mouseY, partialTicks);

		if (this.hoverDescription != null && this.hoverDescription.size() > 0)
		{
			if (this.hoveredStack != null)
			{
				GuiUtils.preItemToolTip(this.hoveredStack);
				GuiUtils.drawHoveringText(this.hoverDescription, mouseX, mouseY, this.width, this.height, -1, this.font);
				GuiUtils.postItemToolTip();
			}
			else
			{
				GuiUtils.drawHoveringText(this.hoverDescription, mouseX, mouseY, this.width, this.height, -1, this.font);
			}
		}

		this.hoverDescription = null;

		this.renderHoveredToolTip(mouseX, mouseY);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String name = I18n.format("container.masonry_bench");

		this.font.drawString(name, 88 - (this.font.getStringWidth(name) / 2), 6, 4210752);
		this.font.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		this.font.drawString(I18n.format("gui.aether.masonry.label.recipes"), -126, 7, 4210752);
	}

	/**
	 * Draws the background layer of this container (behind the items).
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.minecraft.getTextureManager().bindTexture(BOOK_RECIPES);
		int x = this.guiLeft - 150;
		int z = this.guiTop - 5;
		this.blit(x, z, 0, 0, 146, 178);

		this.minecraft.getTextureManager().bindTexture(MASONRY_BENCH);
		x = ((this.width - this.xSize) / 2) + 30;
		z = (this.height - this.ySize) / 2;
		this.blit(x, z, 0, 0, this.xSize, this.ySize);

		int i = this.guiLeft - 48;
		int j = this.guiTop + 22;
		int k = j + 108;
		this.minecraft.getTextureManager().bindTexture(BOOK_SCROLL_BAR);

		this.blit(i, j + (int) ((float) (k - j - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);

		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();

		if (this.currentRecipe != null)
		{
			this.up.visible = true;
			this.down.visible = true;
			this.xButton.visible = true;
		}
		else
		{
			this.up.visible = false;
			this.down.visible = false;
			this.xButton.visible = false;
		}

		if (this.recipes.size() <= 0)
		{
			this.minecraft.getTextureManager().bindTexture(DARK_CRAFTING_OVERLAY);

			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
					GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

			AbstractGui.blit(this.guiLeft - 126, this.guiTop + 21, 0, 0, 72, 108, 72, 126);
		}
	}

	private boolean needsScrollBars()
	{
		return this.canScroll();
	}

	@Override
	public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double amount)
	{
		if (!super.mouseScrolled(p_mouseScrolled_1_, p_mouseScrolled_3_, amount))
		{
			Slot s = this.container.craftingResult;

			double mouseX = InputHelper.getMouseX();
			double mouseY = InputHelper.getMouseY();

			int i = (int) amount;

			if (i != 0)
			{
				if (this.isMouseOverSlot(s, (int) mouseX, (int) mouseY) && this.currentRecipe != null)
				{
					if (i > 0)
					{
						i = 1;
					}

					if (i < 0)
					{
						i = -1;
					}

					this.container.setInputCount(this.container.getInputCount() + i);
				}
				else if (this.needsScrollBars())
				{
					int j = (this.recipes.size() + 4 - 1) / 4 - 6;

					if (i > 0)
					{
						i = 1;
					}

					if (i < 0)
					{
						i = -1;
					}

					this.currentScroll = (float) ((double) this.currentScroll - (double) i / (double) j);
					this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0F, 1.0F);

					this.scrollTo(this.currentScroll);
				}
			}
		}

		return true;
	}

	@Override
	public void setHoveredDescription(ItemStack stack, List<String> desc)
	{
		this.hoveredStack = stack;
		this.hoverDescription = desc;
	}

	private boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY)
	{
		return this.isPointInRegion(slotIn.xPos, slotIn.yPos, 16, 16, mouseX, mouseY);
	}

}
