package com.aetherteam.aetherii.block;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AetherIIWoodTypes {
    public static final BlockSetType SKYROOT_BLOCK_SET = new BlockSetType(Identifier.fromNamespaceAndPath(AetherII.MODID, "skyroot").toString());
    public static final WoodType SKYROOT = new WoodType(Identifier.fromNamespaceAndPath(AetherII.MODID, "skyroot").toString(), SKYROOT_BLOCK_SET);

    public static final BlockSetType GREATROOT_BLOCK_SET = new BlockSetType(Identifier.fromNamespaceAndPath(AetherII.MODID, "greatroot").toString());
    public static final WoodType GREATROOT = new WoodType(Identifier.fromNamespaceAndPath(AetherII.MODID, "greatroot").toString(), GREATROOT_BLOCK_SET);

    public static final BlockSetType WISPROOT_BLOCK_SET = new BlockSetType(Identifier.fromNamespaceAndPath(AetherII.MODID, "wisproot").toString());
    public static final WoodType WISPROOT = new WoodType(Identifier.fromNamespaceAndPath(AetherII.MODID, "wisproot").toString(), WISPROOT_BLOCK_SET);

    public static final BlockSetType AMBEROOT_BLOCK_SET = new BlockSetType(Identifier.fromNamespaceAndPath(AetherII.MODID, "amberoot").toString());
    public static final WoodType AMBEROOT = new WoodType(Identifier.fromNamespaceAndPath(AetherII.MODID, "amberoot").toString(), AMBEROOT_BLOCK_SET);

    public static final BlockSetType CRYSTALROOT_BLOCK_SET = new BlockSetType(Identifier.fromNamespaceAndPath(AetherII.MODID, "crystalroot").toString());
    public static final WoodType CRYSTALROOT = new WoodType(Identifier.fromNamespaceAndPath(AetherII.MODID, "crystalroot").toString(), CRYSTALROOT_BLOCK_SET);
}