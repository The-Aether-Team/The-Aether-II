package com.gildedgames.aether.common.dungeons;

import com.gildedgames.orbis.lib.core.world_objects.BlueprintRegion;
import com.gildedgames.orbis.lib.data.blueprint.BlueprintData;
import com.gildedgames.orbis.lib.data.region.Region;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class DungeonViewer
{
    public static BlueprintData data1 = new BlueprintData(new Region(BlockPos.ORIGIN, new BlockPos(5, 5, 5)));
    public static BlueprintData data2 = new BlueprintData(new Region(BlockPos.ORIGIN, new BlockPos(15, 5, 5)));
    public static BlueprintData data3 = new BlueprintData(new Region(BlockPos.ORIGIN, new BlockPos(25, 5, 15)));

    public static InfectedTreeDungeonDefinition def = new InfectedTreeDungeonDefinition(Lists.newArrayList(data1, data2, data3));

    private IDungeon dungeon;
    private IDungeonGenerator generator;

    public DungeonViewer() {
        this.generator = new DungeonGenerator();
        this.dungeon = this.generator.generate(def, new Random());
    }

    @SubscribeEvent
    public void drawScreen(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiMainMenu) {
           event.setGui(new GuiDungeonViewer(this.generator, this.dungeon));
        }
    }

}
