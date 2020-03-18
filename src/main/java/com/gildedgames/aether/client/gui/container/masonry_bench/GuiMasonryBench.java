package com.gildedgames.aether.client.gui.container.masonry_bench;

import com.gildedgames.aether.client.gui.container.IExtendedContainer;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketMasonryProcessOutput;
import com.gildedgames.aether.common.network.packets.PacketMasonryProcessOutputShift;
import com.gildedgames.aether.common.recipes.MasonryRecipes;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiMasonryBench extends GuiContainer implements IExtendedContainer
{
    private static final ResourceLocation MASONRY_BENCH = AetherCore.getResource("textures/gui/inventory/masonry_bench.png");

    private static final ResourceLocation SCROLL_BAR = AetherCore.getResource("textures/gui/inventory/scroll_bar.png");

    private final int ySize = 206;

    private final List<GuiMasonryCraftingOption> options = new ArrayList<>(20);

    public final List<ItemStack> recipes = Lists.newArrayList();

    private final InventoryPlayer playerInventory;

    private final IInventory tileMasonry;

    private ContainerMasonryBench container;

    public List<String> hoverDescription;

    private ItemStack hoveredStack;

    private float currentScroll;

    private boolean isScrolling;

    private boolean wasClicking;

    public GuiMasonryBench(EntityPlayer player, InventoryPlayer playerInv, IInventory masonryInv)
    {
        super(new ContainerMasonryBench(player, playerInv, masonryInv));
        this.playerInventory = playerInv;
        this.tileMasonry = masonryInv;
        this.container = new ContainerMasonryBench(player, playerInv, masonryInv);

        this.allowUserInput = true;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        int guiLeft = 134 + ((this.width - this.xSize) / 2);
        int guiTop = ((this.height - this.ySize) / 2) - 3;

        for (int i = 0; i < 5; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                GuiMasonryCraftingOption option = new GuiMasonryCraftingOption(j + i * 4, guiLeft - 126 + (j * 18), guiTop + (21 + i * 18), ItemStack.EMPTY);

                this.buttonList.add(option);
                this.options.add(j + i * 4, option);
            }
        }

        this.updateCraftingOptions();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();

        boolean flag = Mouse.isButtonDown(0);
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

        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.hoverDescription != null && this.hoverDescription.size() > 0)
        {
            if (this.hoveredStack != null)
            {
                GuiUtils.preItemToolTip(this.hoveredStack);
                GuiUtils.drawHoveringText(this.hoverDescription, mouseX, mouseY, width, height, -1,
                        Minecraft.getMinecraft().fontRenderer);
                GuiUtils.postItemToolTip();
            }
            else
            {
                GuiUtils.drawHoveringText(this.hoverDescription, mouseX, mouseY, width, height, -1,
                        Minecraft.getMinecraft().fontRenderer);
            }
        }

        this.hoverDescription = null;

        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();

        if (!this.container.inventorySlots.get(0).getHasStack())
        {
            this.container.setOutput(ItemStack.EMPTY);
            this.updateCraftingOptions();
        }

        if (this.container.inventorySlots.get(0).getHasStack())
        {
            this.updateCraftingOptions();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s = this.tileMasonry.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, -14, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 116 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(MASONRY_BENCH);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        int i = 88 + this.guiLeft;
        int j = this.guiTop - 2;
        int k = j + 108;
        this.mc.getTextureManager().bindTexture(SCROLL_BAR);

        this.drawTexturedModalRect(i, j + (int) ((float) (k - j - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);

        if (this.getSlotUnderMouse() != null && this.getSlotUnderMouse().slotNumber == 0)
        {
            this.updateCraftingOptions();
        }

        if (this.getSlotUnderMouse() != null && this.getSlotUnderMouse().slotNumber == 1)
        {
            if (this.container.inventorySlots.get(1).getHasStack())
            {
                if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                {
                    NetworkingAether.sendPacketToServer(new PacketMasonryProcessOutput());

                    ItemStack outputStack = this.container.inventorySlots.get(1).getStack();

                    if (this.mc.player.inventory.getItemStack().isEmpty())
                    {
                        this.mc.player.inventory.setItemStack(outputStack.copy());
                    }
                    else if (ItemHelper.getKeyForItemStack(this.mc.player.inventory.getItemStack()) == ItemHelper
                            .getKeyForItemStack(this.container.inventorySlots.get(1).getStack()))
                    {
                        this.mc.player.inventory.getItemStack().setCount(this.mc.player.inventory.getItemStack().getCount() + 1);
                    }

                    this.container.inventorySlots.get(0).decrStackSize(1);
                }
                else
                {
                    NetworkingAether.sendPacketToServer(new PacketMasonryProcessOutputShift());

                    ItemStack newOutputStack = this.container.inventorySlots.get(1).getStack().copy();
                    newOutputStack.setCount(this.container.inventorySlots.get(0).getStack().getCount());

                    this.mc.player.inventory.addItemStackToInventory(newOutputStack);

                    this.container.inventorySlots.get(0).decrStackSize(this.container.inventorySlots.get(0).getStack().getCount());
                }
            }
        }
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type)
    {
        super.handleMouseClick(slotIn, slotId, mouseButton, type);

        if (slotIn != null && slotIn.slotNumber == 1)
        {
            this.updateCraftingOptions();
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button instanceof GuiMasonryCraftingOption)
        {
            GuiMasonryCraftingOption option = (GuiMasonryCraftingOption) button;

            if (option.getOutputItemStack() != null)
            {
                if (option.getOutputItemStack() != this.container.inventorySlots.get(1).getStack())
                {
                    this.container.setOutput(option.getOutputItemStack().copy());
                }
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();

        int i = Mouse.getEventDWheel();

        if (i != 0)
        {
            if (this.needsScrollBars())
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

    private void scrollTo(float scroll)
    {
        int i = (this.recipes.size() + 4 - 1) / 4 - 6;
        int j = (int) ((double) (scroll * (float) i) + 0.5D);

        if (j < 0)
        {
            j = 0;
        }

        for (int k = 0; k < 5; ++k)
        {
            for (int l = 0; l < 4; ++l)
            {
                int i1 = l + (k + j) * 4;

                if (i1 >= 0 && i1 < this.recipes.size())
                {
                    this.options.get(l + k * 4).setOutputItemStack(this.recipes.get(i1));
                }
                else
                {
                    this.options.get(l + k * 4).setOutputItemStack(ItemStack.EMPTY);
                }
            }
        }
    }

    private boolean canScroll()
    {
        return this.recipes.size() > this.options.size();
    }

    private boolean needsScrollBars()
    {
        return this.canScroll();
    }

    private void updateCraftingOptions()
    {
        this.recipes.clear();

        if (!this.tileMasonry.getStackInSlot(0).isEmpty())
        {
            ItemStack[] outputStack = MasonryRecipes.instance().getOutput(this.tileMasonry.getStackInSlot(0));

            if (outputStack != null)
            {
                this.recipes.addAll(Arrays.asList(outputStack));
            }
        }

        this.currentScroll = 0.0F;
        this.scrollTo(0.0F);
    }

    @Override
    public void setHoveredDescription(ItemStack stack, List<String> desc)
    {
        this.hoveredStack = stack;
        this.hoverDescription = desc;
    }
}