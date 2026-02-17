package com.aetherteam.aetherii.client.gui.screen;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlphaInfoScreen extends Screen {
    private static final WidgetSprites ARROW_LEFT_TEXTURES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/arrow_left"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/arrow_left"));
    private static final WidgetSprites ARROW_RIGHT_TEXTURES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/arrow_right"),
            ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "guidebook/arrow_right"));
    private static final MutableComponent TITLE = Component.literal("The Aether II Alpha Test");

    protected final Screen lastScreen;

    private int textPosition;
    private int textWidth;

    private ImageButton previousButton, nextButton;
    private final Map<Integer, List<FormattedCharSequence>> pages = new HashMap<>();
    private int currentPageNumber = 0;

    public AlphaInfoScreen(Screen lastScreen) {
        super(TITLE);
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        super.init();

        String description = """
Ready for Testing

- Basic Dimension Material Progression

Skyroot + Beast Hide Equipment
Skyroot's reworked ability is implemented and working, Beast Hide Equipment ability works but is limited in scope atm

Holystone + Burrukai Plate Equipment
Holystone ability may be reworked in the future

Amber Hourglass for refining Zanite
Amber Hourglass balance is quite WIP but ready for testing for progression. Archaeology functionality not included yet

Zanite Equipment
Zanite Armor ability balance is WIP, needs feedback/tweaking

Altar for refining Metals
New Altar functionality and UI is implemented, costs and timing may need feedback

Arkenium Equipment
Arkenium currently has no ability instead can be upgraded further than other craftables in the Forge, may get a distinct ability in the future

Arkenium Forge for enhancing equipment
Forge mechanics for upgrading and affixing Charms implemented and working

Gravitite Equipment
New abilities implemented, feedback would be appreciated
        
        
- Revamped Dimension Terrain Generation

Playable Biomes

Highfields Theme (Missing unique mobs and loot)

Flourishing Fields (mostly visually finalised)
Verdant Woods (mostly visually finalised)
Shimmering Basin (playable but WIP)
Shrouded Forest (playable but WIP)

Magnetic Theme (Missing environmental mechanics, unique mobs, and loot)

Magnetic Scar (mostly visually finalised)
Violet Highwoods (mostly visually finalised)
Turquoise Forest (playable but WIP)
Glistening Swamp (very WIP)

Arctic Theme (Missing environmental mechanics, unique mobs, and loot)

Frigid Sierra (playable but WIP)
Enduring Woodland (mostly visually finalised)
Frozen Lakes (mostly visually finalised)
Sheer Tundra (very WIP)

Irradiated Theme (Missing key features, very WIP)

Contaminated Jungle (visuals need work, very WIP)
Battleground Wastes (missing most features, very WIP)

Cloud Sea Theme (Missing key features, very WIP)

Expanse (very basic implementation)
            
            
- Structures

Traveller's Outposts
Campfire Respawn point is implemented and seems to work correctly
Outpost NPCs and fast travel are not yet implemented
Will get more structure variants over alpha development

Miscellaneous Structures

Camp Sites
Variations and Biome specific versions
Loot is WIP and needs properly balancing

Watchtowers
More variations will be added over time
Loot is WIP and needs properly balancing

Sentry Ruins
Mini Dungeon with Sentry Enemies and Slider Boss
Structure is in a playable state with some missing decoration
Mobs and Boss are implemented and playable but need refining
Loot is likely to be expanded and rebalanced over time
Dungeon placement feedback would be appreciated
How easy are Sentry Ruins to locate?
How rare do they feel?
How easy are they to navigate?


Work in Progress
- Moas (partially implemented/playable)
- Traveller's Guidebook (equipment and discovery pages implemented, status screen disabled)
(other stuff Im sure)

Unimplemented
- Infected Guardian Tree Dungeon
- Lost Valkyrie Temple Mini-Dungeon
- Primordial Foundry Dungeon
- Sun Acolyte Hideout Mini-Dungeon
- Bandit King's Fortress Dungeon
- Outpost NPCs
                """;

        this.textPosition = Math.max(85, (this.width - 325) / 2);
        this.textWidth = Math.min(this.width - (85 * 2), 325);

        ImageButton goBackButton = new ImageButton(this.textPosition - 49, 12, 8, 8, Guidebook.RETURN, (button) -> {
            Minecraft.getInstance().setScreen(this.lastScreen);
        });
        goBackButton.setTooltip(Tooltip.create(Component.translatable("gui.aether_ii.guidebook.description.button.close")));

        this.addRenderableWidget(goBackButton);
        this.previousButton = this.addRenderableWidget(new ImageButton(this.textPosition - 53, this.height - 28, 16, 16, ARROW_LEFT_TEXTURES, (button) -> {
            if (this.currentPageNumber > 0) {
                this.currentPageNumber--;
            }
        }));
        this.nextButton = this.addRenderableWidget(new ImageButton(this.textPosition + this.textWidth + 47, this.height - 28, 16, 16, ARROW_RIGHT_TEXTURES, (button) -> {
            if (this.currentPageNumber < this.pages.size() - 1) {
                this.currentPageNumber++;
            }
        }));
        this.pages.clear();
        this.createPages(Component.literal(description)); // Sets up pages.
        if (this.currentPageNumber > this.pages.size() - 1) {
            this.currentPageNumber = this.pages.size() - 1;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, TITLE.withStyle(ChatFormatting.UNDERLINE), this.width / 2, 10, 0xffffffff);

        this.createText(guiGraphics, this.pages.get(this.currentPageNumber), this.textPosition, 30);

        guiGraphics.drawCenteredString(this.font, Component.literal(String.valueOf(this.currentPageNumber + 1)).append("/").append(String.valueOf(this.pages.size())), this.width / 2, this.height - 20, 0xffffffff);

        this.previousButton.active = this.currentPageNumber > 0;
        this.nextButton.active = this.currentPageNumber < this.pages.size() - 1;
    }

    private void createPages(Component entry) {
        List<FormattedCharSequence> formattedText = new ArrayList<>(this.font.split(entry, this.textWidth));
        List<FormattedCharSequence> firstPage;
        int lines = (this.height - 70) / 10;
        if (formattedText.size() < lines) {
            firstPage = formattedText.subList(0, formattedText.size());
            this.pages.put(0, firstPage);
        } else {
            firstPage = formattedText.subList(0, lines);
            this.pages.put(0, firstPage);

            List<FormattedCharSequence> remainingPages = formattedText.subList(lines, formattedText.size());
            final List<List<FormattedCharSequence>> list = Lists.partition(remainingPages, lines);

            for (int i = 1; i < list.size() + 1; i++) {
                this.pages.put(i, list.get(i - 1));
            }
        }
    }

    private void createText(GuiGraphics guiGraphics, List<FormattedCharSequence> reorderingProcessors, int x, int y) {
        int length = 0;
        for (FormattedCharSequence line : reorderingProcessors) {
            guiGraphics.drawString(this.font, line,  x, y + (length * 10), 0xffffffff, false);
            length++;
        }
    }

    @Override
    protected void renderBlurredBackground(GuiGraphics guiGraphics) { }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
