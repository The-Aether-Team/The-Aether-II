package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.*;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Rect;
import com.gildedgames.orbis.lib.client.rect.RectModifier;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiScrollableGuidebook extends GuiElement
{
    private static final ResourceLocation SCROLL_KNOB = AetherCore.getResource("textures/gui/guidebook/guidebook_scroll_knob.png");

    private static final ResourceLocation SCROLL_KNOB_DISABLED = AetherCore.getResource("textures/gui/guidebook/guidebook_scroll_knob_disabled.png");

    private static final ResourceLocation SCROLL_BAR = AetherCore.getResource("textures/gui/guidebook/guidebook_scroll_bar.png");

    private final boolean scrollBarOnRightSide;

    private IGuiElement window;

    private final IGuiEvent<IGuiElement> scissorEvent = new IGuiEvent<IGuiElement>()
    {
        @Override
        public void onPreDraw(final IGuiElement element)
        {
            final ScaledResolution res = new ScaledResolution(GuiScrollableGuidebook.this.viewer().mc());
            final IGuiViewer viewer = GuiScrollableGuidebook.this.viewer();

            final double scaleW = viewer.mc().displayWidth / res.getScaledWidth_double();
            final double scaleH = viewer.mc().displayHeight / res.getScaledHeight_double();

            final IGuiElement window = GuiScrollableGuidebook.this.window;
            final boolean rightBar = GuiScrollableGuidebook.this.scrollBarOnRightSide;
            final double scrollBarWidth = GuiScrollableGuidebook.this.scrollBar.dim().width();

            final double windowWidth = window.dim().width();
            final double windowHeight = window.dim().height();

            final int scissorX = (int) ((window.dim().x()) * scaleW);
            final int scissorY = (int) (viewer.mc().displayHeight - ((window.dim().y() + window.dim().height()) * scaleH));
            final int scissorWidth = (int) ((window.dim().width() - (rightBar ? scrollBarWidth : 0)) * scaleW);
            final int scissorHeight = (int) (window.dim().height() * scaleH);

            GL11.glEnable(GL11.GL_SCISSOR_TEST);

            if (!(windowWidth < 0 || windowHeight < 0))
            {
                GL11.glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
            }
        }

        @Override
        public void onPostDraw(final IGuiElement element)
        {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }

        @Override
        public void onMouseClicked(final IGuiElement element, final int mouseX, final int mouseY, final int mouseButton)
        {

        }

        @Override
        public boolean isMouseClickedEnabled(final IGuiElement element, final int mouseX, final int mouseY, final int mouseButton)
        {
            final boolean enabled = element == GuiScrollableGuidebook.this.window || GuiScrollableGuidebook.this.window.state().isHovered();

            if (!enabled)
            {
                for (final IGuiEvent event : element.state().getEvents())
                {
                    if (event instanceof IInputEnabledOutsideBounds)
                    {
                        final IInputEnabledOutsideBounds input = (IInputEnabledOutsideBounds) event;

                        input.onMouseClickedOutsideBounds(element, mouseX, mouseY, mouseButton);

                        return false;
                    }
                }
            }

            return enabled;
        }

        @Override
        public boolean isMouseClickMoveEnabled(final IGuiElement element, final int mouseX, final int mouseY, final int clickedMouseButton,
                                               final long timeSinceLastClick)
        {
            final boolean enabled = element == GuiScrollableGuidebook.this.window || GuiScrollableGuidebook.this.window.state().isHovered();

            if (!enabled)
            {
                for (final IGuiEvent event : element.state().getEvents())
                {
                    if (event instanceof IInputEnabledOutsideBounds)
                    {
                        final IInputEnabledOutsideBounds input = (IInputEnabledOutsideBounds) event;

                        input.onMouseClickMoveOutsideBounds(element, mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

                        return false;
                    }
                }
            }

            return enabled;
        }

        @Override
        public boolean isMouseReleasedEnabled(final IGuiElement element, final int mouseX, final int mouseY, final int state)
        {
            final boolean enabled = element == GuiScrollableGuidebook.this.window || GuiScrollableGuidebook.this.window.state().isHovered();

            if (!enabled)
            {
                for (final IGuiEvent event : element.state().getEvents())
                {
                    if (event instanceof IInputEnabledOutsideBounds)
                    {
                        final IInputEnabledOutsideBounds input = (IInputEnabledOutsideBounds) event;

                        input.onMouseReleasedOutsideBounds(element, mouseX, mouseY, state);

                        return false;
                    }
                }
            }

            return enabled;
        }

        @Override
        public boolean isMouseWheelEnabled(final IGuiElement element, final int state)
        {
            final boolean enabled = element == GuiScrollableGuidebook.this.window || GuiScrollableGuidebook.this.window.state().isHovered();

            if (!enabled)
            {
                for (final IGuiEvent event : element.state().getEvents())
                {
                    if (event instanceof IInputEnabledOutsideBounds)
                    {
                        final IInputEnabledOutsideBounds input = (IInputEnabledOutsideBounds) event;

                        input.onMouseWheelOutsideBounds(element, state);
                    }
                }
            }

            return enabled;
        }

        @Override
        public boolean isHandleMouseClickEnabled(final IGuiElement element, final Slot slotIn, final int slotId, final int mouseButton, final ClickType type)
        {
            final boolean enabled = element == GuiScrollableGuidebook.this.window || GuiScrollableGuidebook.this.window.state().isHovered();

            if (!enabled)
            {
                for (final IGuiEvent event : element.state().getEvents())
                {
                    if (event instanceof IInputEnabledOutsideBounds)
                    {
                        final IInputEnabledOutsideBounds input = (IInputEnabledOutsideBounds) event;

                        input.onHandleMouseClickOutsideBounds(element, slotIn, slotId, mouseButton, type);

                        return false;
                    }
                }
            }

            return enabled;
        }

        @Override
        public boolean canBeHovered(final IGuiElement element)
        {
            for (final IGuiEvent event : element.state().getEvents())
            {
                if (event instanceof IInputEnabledOutsideBounds)
                {
                    if (((IInputEnabledOutsideBounds) event).shouldHoverOutsideBounds(element))
                    {
                        return true;
                    }
                }
            }

            return element == GuiScrollableGuidebook.this.window || GuiScrollableGuidebook.this.window.state().isHovered();
        }
    };

    private IGuiElement pane;

    private IGuiElement decorated;

    private float scroll;

    private GuiTexture scrollKnob, scrollBar;

    public GuiScrollableGuidebook(final IGuiElement decorated, final Rect pane)
    {
        this(decorated, pane, false);
    }

    public GuiScrollableGuidebook(final IGuiElement decorated, final Rect pane, final boolean scrollBarOnRightSide)
    {
        super(pane, true);

        this.scrollBarOnRightSide = scrollBarOnRightSide;

        this.setDecorated(decorated);
    }

    public void setDecorated(final IGuiElement decorated)
    {
        this.dim().mod().x(decorated.dim().x()).y(decorated.dim().y()).flush();

        this.decorated = decorated;

        this.window = new GuiElement(Dim2D.build().width(0).x(0).y(0).flush(), false);
        this.pane = new GuiElement(Dim2D.build().x(this.scrollBarOnRightSide ? 0 : 16).y(0).flush(), false);

        this.window.dim().add("scrollableArea", this, RectModifier.ModifierType.AREA);
        this.pane.dim().add("scrollableArea", this, RectModifier.ModifierType.AREA);

        this.tryRebuild();
    }

    public float getScrollBarWidth()
    {
        return this.scrollBar.dim().width();
    }

    @Override
    public void build()
    {
        this.decorated.dim().mod().x(0).y(0).flush();

        this.pane.build(this.viewer());

        this.pane.context().addChildren(this.decorated);

        this.scrollKnob = new GuiTexture(Dim2D.build().width(6).height(9).x(1).y(1).flush(), SCROLL_KNOB);
        this.scrollBar = new GuiTexture(Dim2D.build().width(8).flush(), SCROLL_BAR);

        if (this.scrollBarOnRightSide)
        {
            this.scrollBar.dim().mod().x(this.dim().width() - this.scrollBar.dim().width()).flush();
            this.scrollKnob.dim().mod().x(this.scrollBar.dim().x() + 1).flush();
        }

        this.scrollBar.dim().add("scrollableHeight", this, RectModifier.ModifierType.HEIGHT);

        this.context().addChildren(this.window, this.pane, this.scrollBar, this.scrollKnob);

        this.window.state().setCanBeTopHoverElement(true);
    }

    @Override
    public void onGlobalContextChanged(final GuiElement element)
    {
        for (final IGuiElement child : GuiLibHelper.getAllChildrenRecursivelyFor(this.decorated))
        {
            child.state().addEvent(this.scissorEvent);
        }
    }

    @Override
    public void onDraw(final GuiElement element)
    {
        if (this.dim().height() >= this.decorated.dim().height())
        {
            this.scrollKnob.setResourceLocation(SCROLL_KNOB_DISABLED);
        }
        else
        {
            this.scrollKnob.setResourceLocation(SCROLL_KNOB);
        }
    }

    @Override
    public void onMouseWheel(final GuiElement element, final int state)
    {
        if (this.window.state().isHoveredAndTopElement())
        {
            final float height = this.decorated.dim().height() - this.dim().height();

            this.scroll -= (float) (state / 120) * 10.0F;

            this.scroll = Math.max(0.0F, Math.min(height, this.scroll));

            this.pane.dim().mod().y(-this.scroll).flush();

            final float percent = this.scroll / height;
            final float yScrollBarPadding = 1;
            final float y = percent * (this.dim().height() - this.scrollKnob.dim().height() - (yScrollBarPadding * 2));

            this.scrollKnob.dim().mod().y(y + yScrollBarPadding).flush();
        }
    }

    public void resetScroll()
    {
        this.pane.dim().mod().y(0).flush();

        this.scrollKnob.dim().mod().y(1).flush();

        this.scroll = 0.0F;
    }

    public interface IInputEnabledOutsideBounds<T extends IGuiElement>
    {
        default void onMouseClickedOutsideBounds(final T element, final int mouseX, final int mouseY, final int mouseButton)
        {

        }

        default void onMouseClickMoveOutsideBounds(final T element, final int mouseX, final int mouseY, final int clickedMouseButton,
                                                   final long timeSinceLastClick)
        {

        }

        default void onMouseReleasedOutsideBounds(final T element, final int mouseX, final int mouseY, final int state)
        {

        }

        default void onMouseWheelOutsideBounds(final T element, final int state)
        {

        }

        default void onHandleMouseClickOutsideBounds(final T element, final Slot slotIn, final int slotId, final int mouseButton, final ClickType type)
        {

        }

        default boolean shouldHoverOutsideBounds(final T element)
        {
            return false;
        }
    }

    public static class InputEnabledOutsideBounds<T extends IGuiElement> implements IGuiEvent<T>, IInputEnabledOutsideBounds<T>
    {

    }
}
