package com.gildedgames.aether.client.gui.container.simple_crafting;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.registry.simple_crafting.ISimpleRecipe;
import com.gildedgames.aether.api.registry.simple_crafting.ISimpleRecipeGroup;
import com.gildedgames.aether.client.util.gui.GuiUtil;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.ContainerSimpleCrafting;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.SimpleRecipeChangedPacket;
import com.gildedgames.aether.common.recipes.simple.OreDictionaryRequirement;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiSimpleCrafting extends GuiContainer
{

	private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/crafting_table.png");

	private static final ResourceLocation BOOK_SCROLL_BAR = AetherCore.getResource("textures/gui/inventory/book_scroll_bar.png");

	private static final ResourceLocation BOOK_RECIPES = AetherCore.getResource("textures/gui/inventory/book_recipes.png");

	private static final ResourceLocation DARK_CRAFTING_OVERLAY = AetherCore.getResource("textures/gui/inventory/dark_crafting_overlay.png");

	private static final ResourceLocation CRAFTING_MATRIX_NO_INTERACTION = AetherCore.getResource("textures/gui/inventory/crafting_matrix_no_interaction.png");

	/** Amount scrolled in crafting options inventory (0 = top, 1 = bottom) */
	private float currentScroll;

	/** True if the scrollbar is being dragged */
	private boolean isScrolling;

	/** True if the left mouse button was held down last time drawScreen was called. */
	private boolean wasClicking;

	/** The player inventory bound to this GUI. */
	private final InventoryPlayer playerInventory;

	public List<ISimpleRecipe> recipes = Lists.newArrayList();

	private final List<GuiCraftingOption> options = new ArrayList<>(24);

	private final List<GuiRequiredMaterial> materials = new ArrayList<>(9);

	private GuiRequiredMaterial result;

	private ISimpleRecipe currentRecipe;

	public String hoverDescription;

	private GuiXButton xButton;

	private ItemStack lastStack;

	public GuiSimpleCrafting(EntityPlayer player, BlockPos blockPosition)
	{
		super(new ContainerSimpleCrafting(player, blockPosition));

		this.playerInventory = player.inventory;
		this.allowUserInput = true;
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		super.mouseReleased(mouseX, mouseY, state);

		if ((this.lastStack == null && this.playerInventory.getItemStack() != null) || (this.lastStack != null && this.playerInventory.getItemStack() == null))
		{
			this.updateCraftingOptions();
		}

		this.lastStack = this.playerInventory.getItemStack();
	}

	@Override
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type)
	{
		super.handleMouseClick(slotIn, slotId, mouseButton, type);

		if (slotIn != null && (slotIn.getHasStack() || this.playerInventory.getItemStack() != null))
		{
			this.updateCraftingOptions();
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.id == 31)
		{
			this.currentRecipe = null;

			ContainerSimpleCrafting container = (ContainerSimpleCrafting)this.inventorySlots;
			container.onNewRecipe(null);
			NetworkingAether.sendPacketToServer(new SimpleRecipeChangedPacket(null));

			for (int i = 0; i < 9; i++)
			{
				this.materials.get(i).setItemStack(null);
			}
		}

		if (button instanceof GuiCraftingOption)
		{
			GuiCraftingOption option = (GuiCraftingOption)button;

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
								this.materials.get(index).setItemStack((ItemStack) req);
							}
							else if (req instanceof OreDictionaryRequirement)
							{
								OreDictionaryRequirement oreReq = (OreDictionaryRequirement)req;

								this.materials.get(index).setItemStack(OreDictionary.getOres(oreReq.getKey()).get(0));
							}
						}
						else
						{
							this.materials.get(index).setItemStack(null);
						}
					}
				}

				this.currentRecipe = option.getRecipe();

				ContainerSimpleCrafting container = (ContainerSimpleCrafting)this.inventorySlots;

				container.onNewRecipe(this.currentRecipe);

				NetworkingAether.sendPacketToServer(new SimpleRecipeChangedPacket(this.currentRecipe));

				if (!RecipeUtil.canCraft(Minecraft.getMinecraft().thePlayer, option.getRecipe()) && option.getRecipe() != null)
				{
					this.result.setItemStack(option.getRecipe().getResult());
				}
				else
				{
					this.result.setItemStack(null);
				}
			}
		}
	}

	public void scrollTo(float p_148329_1_)
	{
		int i = (this.recipes.size() + 4 - 1) / 4 - 6;
		int j = (int)((double)(p_148329_1_ * (float)i) + 0.5D);

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
					this.options.get(l + k * 4).setRecipe(this.recipes.get(i1));
				}
				else
				{
					this.options.get(l + k * 4).setRecipe(null);
				}
			}
		}
	}

	public boolean canScroll()
	{
		return this.recipes.size() > this.options.size();
	}

	private void updateCraftingOptions()
	{
		this.recipes.clear();

		List<ItemStack> uniqueStacks = Lists.newArrayList();

		outerloop: for (ItemStack stack : this.playerInventory.mainInventory)
		{
			for (ItemStack uniqueStack : uniqueStacks)
			{
				if (RecipeUtil.areEqual(stack, uniqueStack))
				{
					continue outerloop;
				}
			}

			if (stack != null)
			{
				uniqueStacks.add(stack);
			}
		}

		for (ItemStack stack : uniqueStacks)
		{
			ISimpleRecipeGroup[] groups = AetherAPI.crafting().getRecipesFromRequirement(stack);

			for (ISimpleRecipeGroup group : groups)
			{
				if (group != null)
				{
					outer: for (ISimpleRecipe recipe : group.getRecipes())
					{
						for (ISimpleRecipe r : this.recipes) //TODO: Shouldn't be returning duplicate recipes..
						{
							if (r.equals(recipe))
							{
								continue outer;
							}
						}

						this.recipes.add(recipe);
					}
				}
			}
		}

		EntityPlayer p = Minecraft.getMinecraft().thePlayer;

		this.recipes.sort((o1, o2) ->
		{
			int countO1 = RecipeUtil.getTotalTimesCanCraft(p, o1);
			int countO2 = RecipeUtil.getTotalTimesCanCraft(p, o2);

			if (countO1 > 0 && countO2 <= 0)
			{
				return 1;
			}

			if (countO2 > 0 && countO1 <= 0)
			{
				return -1;
			}

			return Item.getIdFromItem(o1.getResult().getItem()) > Item.getIdFromItem(o2.getResult().getItem()) ? 1 : -1;
		});

		Collections.reverse(this.recipes);

		this.currentScroll = 0.0F;
		this.scrollTo(0.0F);
	}

	@Override
	public void initGui()
	{
		super.initGui();

		for (int i = 0; i < 6; ++i)
		{
			for (int j = 0; j < 4; ++j)
			{
				GuiCraftingOption option = new GuiCraftingOption(j + i * 4, this.guiLeft - 96 + (j * 18), this.guiTop + (21 + i * 18), null);

				this.buttonList.add(option);
				this.options.add(j + i * 4, option);
			}
		}

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 3; ++j)
			{
				GuiRequiredMaterial material = new GuiRequiredMaterial(20 + (j + i * 3), this.guiLeft + 59 + (j * 18), this.guiTop + (16 + i * 18), null);

				if (this.currentRecipe != null && j + i * 3 < this.currentRecipe.getRequired().length)
				{
					Object req = this.currentRecipe.getRequired()[j + i * 3];

					if (req instanceof ItemStack)
					{
						material.setItemStack((ItemStack) req);
					}
					else if (req instanceof OreDictionaryRequirement)
					{
						OreDictionaryRequirement oreReq = (OreDictionaryRequirement)req;

						material.setItemStack(OreDictionary.getOres(oreReq.getKey()).get(0));
					}
				}

				this.buttonList.add(material);
				this.materials.add(j + i * 3, material);
			}
		}

		this.result = new GuiRequiredMaterial(30, this.guiLeft + 153, this.guiTop + 34, null);
		this.result.resultStack = true;

		this.buttonList.add(this.result);

		this.xButton = new GuiXButton(31, this.guiLeft + 46, this.guiTop + 16);
		this.xButton.visible = false;

		this.buttonList.add(this.xButton);

		this.updateCraftingOptions();
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		if (this.currentRecipe != null)
		{
			if (this.result.getItemStack() != this.currentRecipe.getResult() && !RecipeUtil.canCraft(Minecraft.getMinecraft().thePlayer, this.currentRecipe))
			{
				this.result.setItemStack(this.currentRecipe.getResult());
			}
		}
		else
		{
			this.result.setItemStack(null);
		}

		boolean flag = Mouse.isButtonDown(0);
		int i = this.guiLeft;
		int j = this.guiTop;
		int k = i - 18;
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
			this.currentScroll = ((float)(mouseY - l) - 7.5F) / ((float)(j1 - l) - 15.0F);
			this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);

			this.scrollTo(this.currentScroll);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();

		if (this.currentRecipe != null)
		{
			this.mc.getTextureManager().bindTexture(CRAFTING_MATRIX_NO_INTERACTION);
			Gui.drawModalRectWithCustomSizedTexture(this.guiLeft + 59, this.guiTop + 16, 0, 0, 54, 54, 54, 54);
			this.xButton.visible = true;
		}
		else
		{
			this.xButton.visible = false;
		}

		if (this.recipes.size() <= 0)
		{
			this.mc.getTextureManager().bindTexture(DARK_CRAFTING_OVERLAY);

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

			Gui.drawModalRectWithCustomSizedTexture(this.guiLeft - 96, this.guiTop + 21, 0, 0, 72, 108, 72, 126);

			this.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Nothing left", this.guiLeft - 60, this.guiTop + 47, 0xFFFFFF);
			this.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "to craft!", this.guiLeft - 60, this.guiTop + 57, 0xFFFFFF);
		}

		if (this.hoverDescription != null && this.hoverDescription.length() > 0)
		{
			GuiUtil.drawHoveringText(Collections.singletonList(this.hoverDescription), mouseX, mouseY, Minecraft.getMinecraft().fontRendererObj);
		}

		this.hoverDescription = null;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(I18n.format("container.crafting"), 58, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 38, this.ySize - 96 + 2, 4210752);

		this.fontRendererObj.drawString("Recipes", -96, 7, 4210752);
	}

	/**
	 * Draws the background layer of this container (behind the items).
	 */
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(BOOK_RECIPES);
		int x = this.guiLeft - 120;
		int z = this.guiTop - 5;
		this.drawTexturedModalRect(x, z, 0, 0, 246, 178);

		this.mc.getTextureManager().bindTexture(CRAFTING_TABLE_GUI_TEXTURES);
		x = ((this.width - this.xSize) / 2) + 30;
		z = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, z, 0, 0, this.xSize, this.ySize);

		int i = this.guiLeft - 18;
		int j = this.guiTop + 22;
		int k = j + 108;
		this.mc.getTextureManager().bindTexture(BOOK_SCROLL_BAR);

		this.drawTexturedModalRect(i, j + (int)((float)(k - j - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
	}

	private boolean needsScrollBars()
	{
		return this.canScroll();
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();

		if (i != 0 && this.needsScrollBars())
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

			this.currentScroll = (float)((double)this.currentScroll - (double)i / (double)j);
			this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);

			this.scrollTo(this.currentScroll);
		}
	}

}
