package com.aetherteam.aetherii.item;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIMapDecorationTypes {
    public static final DeferredRegister<MapDecorationType> MAP_DECORATION_TYPES = DeferredRegister.create(BuiltInRegistries.MAP_DECORATION_TYPE, AetherII.MODID);

    public static final Holder<MapDecorationType> VERADEXIAN_LIBRARY = MAP_DECORATION_TYPES.register("veradexian_library", () -> new MapDecorationType(Identifier.fromNamespaceAndPath(AetherII.MODID, "veradexian_library"), true, 0xB59C73, false, true));

    public AetherIIMapDecorationTypes() {
    }
}