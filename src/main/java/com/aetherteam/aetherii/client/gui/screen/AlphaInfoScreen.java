package com.aetherteam.aetherii.client.gui.screen;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.gui.screen.guidebook.Guidebook;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlphaInfoScreen extends Screen {
    private static final WidgetSprites ARROW_LEFT_TEXTURES = new WidgetSprites(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/arrow_left"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/arrow_left"));
    private static final WidgetSprites ARROW_RIGHT_TEXTURES = new WidgetSprites(
            Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/arrow_right"),
            Identifier.fromNamespaceAndPath(AetherII.MODID, "guidebook/arrow_right"));
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
            
Features that are marked Ready for Testing are things we're largely happy with the implementation of and would like players to give feedback on.
            
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

Basic Farming
Blueberry Bushes, Orange Trees, Carrion Sprouts, and Aechor Plants form a very basic suite of farming interactions
These blocks and mobs can all be interacted with using Trowel tools


- New Damage System

The Aether II's weapons carry specialised damage types, Slash, Impact, and Pierce.
Mobs in the dimension have differing defense values that can be checked in the Bestiary, using the correct damage type on a mob will deal bonus damage and special audiovisual feedback effects
Stronger mobs, such as those found exclusively underground or in dungeons, may also be resistant to certain damage types.
Using a damage type a mob is strong against will reduce the damage taken, cause a small amount of knockback to the player, and play a blocking sound effect
The player is able to craft melee weapons with each of the three damage types which also carry special attacks based on the vanilla sweep attack
Shortswords deal Slash Damage and carry a Slash attack similar to the vanilla sweep
Hammers deal Impact Damage and carry a Smash attack that deals heavy knockback in a small area around a target
Pikes and Crossbows deal Pierce Damage, Pikes carry a Stab attack that heavily damages enemies directly behind a target
In the future there will be special loot items that carry the attack characteristics of craftable weapons while dealing differing damage types (like a spiked hammer that deals pierce damage while still doing the hammer shock attack)

   
- New Status Effects System

The Aether II also features multiple new status effects, but they work slightly different to how vanilla handles status effects.
Instead of being obtained immediately and then being active for a duration, Aether II's effects are obtained via "buildup".
Environmental hazards, enemy attacks, and some items can deal an amount of Status Effect buildup, if an effect reaches 100% buildup it will trigger.
Effects are generally negative, so it's usually a good idea to reduce buildup before the effect can trigger. Items that reduce buildup or cure effects are detailed in the Effects section of the Guidebook.

   
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
            
Cloud Sea Theme (Missing key features and proper generation, very WIP)
            
Expanse (unimplemented)
            
Playable Cave and Undercloud Generation
            
Caves now intersect The Aether's island terrain allowing for a better experience exploring and mining for ores and other resources
Beneath the Cloud Sea is a new area made of a rock called Undershale known as The Undercloud
The Undercloud lacks an Aercloud safety net and has numerous hazards such as unstable blocks, toxic gas, and acid pools
Arkenium, Gravitite, and Corrobonite Ore can only be found in The Undercloud
In the future there will be unique Undercloud mobs
In the future there will be a wide variety of Undercloud cave biomes
       
       
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
            
- Basic Loot Progression
            
The Sentry Ruins give the player access to our first Relic, the Kinetic Thrusters, and our first Charms, special items that can be equipped to high tier gear using the Arkenium Forge.
Crafted Equipment can gain a Charm slot by reaching their maximum upgrade level in the Forge, while Uncraftable Dungeon Loot has Charm slots by default. The Neptune Armour set and the Hammer of Demolition all come with Charm slots by default.
Once a Charm has been affixed to a Charm slot it cannot be removed.
Dungeon Loot equipment cannot be repaired in an Altar but also cannot be permanently broken either, when durability hits 0 it enters a broken state that can be repaired via a crafting recipe using spare materials obtained by uncrafting loot via the Amber Hourglass.
In the future this will be replaced with a specialised NPC who will repair Dungeon Loot for Glint and extra materials and will also be able to salvage Charms from equipment.
Currently we have one wearable equipment piece, being the Zanite Pendant, in the future there will be multiple craftable Wearables and lots of Loot Wearables, with a focus on Loot Wearables having multiple Charm Slots.

- New Music Player

The Aether II uses new Engraved Disc items for our music loot, these can be played in the new portable Music Player tool to listen to full stereo music tracks completely portably.
            
Work in Progress
            
Features that are marked Work in Progress are things we're actively working on getting to a more finalised state and currently do not require feedback on.
            
- Moas
            
Moas are functional but most of their planned features have not been implemented yet so currently we aren't looking for feedback on their implementation beyond bugs and unintended behaviour
            
Wild Moas will spawn at nests (but currently do not stay near them) and will attack players that enter their nest
Moa Eggs can be obtained by breaking the Egg Block found in Moa Nests
Moa Eggs can be hatched by placing them on a Woven Skyroot Sticks Block
Once hatched a Baby Moa will request food (either Aechor Petals or Moa Feed work) after three feedings it will grow into an Adult Moa
Adult Moas can be saddled with the new Moa Saddle item and ridden as a flying mount
Moas have two modes of flight, by default they will be in a "hover" state where the jump input will use a chunk of stamina to gain vertical height, by using the sprint input you can switch to a partially implemented "glide" state where the Moa will move in the direction of the camera and the jump input will use a chunk of stamina to boost forward.
In the future the plan is for the Glide state of the Moa to feel close to the Elytra in function.
In the future Moas will have varied stats based on how they were raised, trained, and their genetic profile.
Currently all Moas have the same stats.
            
- Traveller's Guidebook
            
The Traveller's Guidebook is The Aether II's custom player GUI that combines a lot of mechanics and information relevant to playing the mod, it can be accessed either through a button in the vanilla player UI or via a keybind
            
Currently only two of the planned Guidebook tabs have been implemented, the Equipment Screen and the Discovery Screen
            
The Aether II adds multiple new equipment slots to add extra gameplay mechanics and loot progression, these are accessed through the Equipment Screen, which has a page dedicated to Equipped Items and a page dedicated to the Player Inventory.
The Equipment Screen adds 5 new equipment slots:
Relics (x2) are powerful loot items that introduce new mechanics for the player or significantly alter their abilities, two can be equipped at the same time
Wearables are items that offer decoration for the player model while providing buffs or extra Charm slots
Gloves are new armor items that increase the effectiveness of blocking and stamina regeneration for actions like block recovery and gliding
The final slot is a special equipment slot called the Glint Pouch, this is the player's wallet for storing Glint items, The Aether's currency. It has an infinite stack size so all Glint items can be stored in this slot and automatically converted into coins, which can be taken out of the pouch for storage in chests or for eventually trading with NPCs. Glint currently comes in the form of gemstones (worth 10 glint) or coins (worth 1 glint)
            
The other tab of the Guidebook is the Discovery Screen, which adds information for the player to read about the content they find throughout the mod. Currently only 2 of the 3 planned tabs have been implemented.
The first tab is the mod's Bestiary, when encountering a mob in the dimension its information will be added to the Bestiary to view. This includes the mob's visuals, health, damage type weaknesses and resistances, effect weaknesses and resistances, feed items, item drops, and an in-world description of the mob giving insight into its gameplay functions and lore.
The second tab is the mod's Effects, when encountering one of the mod's unique status effects its information will be added to the Effects screen to view. This includes the Effect's icon, a brief gameplay description of how it works, and a list of items that can be used to treat or cure the Effect.
The third tab will eventually be a list of discovered points of interest including artwork and information about biomes, dungeons, settlements, and other structures found throughout the dimension.
            
The Guidebook will eventually have other Tabs for additional gameplay information, such as a Status Screen for tracking player information and mount information or a Journal tab for tracking progression information and important documents.
We also are wanting to eventually have a dedicated Map Screen as part of the Guidebook.
            
The Guidebook also requires a visual overhaul as it is still using assets based on older iterations.
            
- Irradiated Biomes
            
The Irradiated Biome theme (Contaminated Jungle and Battleground Wastes biomes) are planned to have rich environmental storytelling and a suite of biome specific mobs and features, currently only the basic generation has been worked on and Irradiated Dust Blocks can be found in water pools and mined to obtain Irradiated Items. We have also noticed this biome frequently fails to properly generate terrain so its atmospheric effects sometimes trigger in the Cloud Sea due to a small Irradiated Biome with no terrain spawning.
            
- The Undercloud
            
The Undercloud generates properly and contributes significantly to the feel of resource gathering in the mod, but it is planned to have significant additional unique content such as structures, mobs, and additional biomes.
            
            
Unimplemented
            
This is a list of some major features that have not yet been implemented but are expected to be worked on in the near future.
            
- Infected Guardian Tree Dungeon
- Lost Valkyrie Temple Mini-Dungeon
- Primordial Foundry Dungeon
- Sun Acolyte Hideout Mini-Dungeon
- Bandit King's Fortress Dungeon
- Environmental Atmosphere and Mechanics
- Expanse Biome
- Biome Specific Environmental Mechanics
- Biome Specific Mobs
- Additional Miscellaneous Structures
- Additional Undercloud Biomes
- Undercloud Specific Mobs
- Outpost NPCs
- Settlements
- Non-Dungeon Loot items
- New Moa Mechanics
- Guidebook Status Screen
- Patreon Cosmetic Rewards""";

        this.textPosition = Math.max(65, (this.width - 350) / 2);
        this.textWidth = Math.min(this.width - (65 * 2), 350);

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
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.extractBlurredBackground(guiGraphics);
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.centeredText(this.font, TITLE.withStyle(ChatFormatting.UNDERLINE), this.width / 2, 10, 0xffffffff);

        this.createText(guiGraphics, this.pages.get(this.currentPageNumber), this.textPosition, 30);

        guiGraphics.centeredText(this.font, Component.literal(String.valueOf(this.currentPageNumber + 1)).append("/").append(String.valueOf(this.pages.size())), this.width / 2, this.height - 20, 0xffffffff);

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

    private void createText(GuiGraphicsExtractor guiGraphics, List<FormattedCharSequence> reorderingProcessors, int x, int y) {
        int length = 0;
        for (FormattedCharSequence line : reorderingProcessors) {
            guiGraphics.text(this.font, line,  x, y + (length * 10), 0xffffffff, false);
            length++;
        }
    }

    @Override
    protected void extractBlurredBackground(GuiGraphicsExtractor graphics) {
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean isInGameUi() {
        return true;
    }
}
