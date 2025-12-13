package com.aetherteam.aetherii.world.structure.piece;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.structure.piece.sentry.SentryWorkshopBossRoom;
import com.aetherteam.aetherii.world.structure.piece.sentry.SentryWorkshopRoom;
import com.aetherteam.aetherii.world.structure.piece.sentry.SentryWorkshopTunnel;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;

public class AetherIIStructurePieceTypes {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, AetherII.MODID);
    
    public static final DeferredHolder<StructurePieceType, StructurePieceType> SENTRY_WORKSHOP_BOSS_ROOM = register("SWBossRoom", SentryWorkshopBossRoom::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType> SENTRY_WORKSHOP_ROOM = register("SWDungeonRoom", SentryWorkshopRoom::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType> SENTRY_WORKSHOP_TUNNEL = register("SWTunnel", SentryWorkshopTunnel::new);

    private static DeferredHolder<StructurePieceType, StructurePieceType> register(String name, StructurePieceType structurePieceType) {
        return STRUCTURE_PIECE_TYPES.register(name.toLowerCase(Locale.ROOT), () -> structurePieceType);
    }
}