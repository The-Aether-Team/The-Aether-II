package com.aetherteam.aetherii.world.structure.piece;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.world.structure.piece.sentry.SentryRuinsBossRoom;
import com.aetherteam.aetherii.world.structure.piece.sentry.SentryRuinsRoom;
import com.aetherteam.aetherii.world.structure.piece.sentry.SentryRuinsTunnel;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Locale;

public class AetherIIStructurePieceTypes {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, AetherII.MODID);
    
    public static final RegistryObject<StructurePieceType> SENTRY_RUINS_BOSS_ROOM = register("SRBossRoom", SentryRuinsBossRoom::new);
    public static final RegistryObject<StructurePieceType> SENTRY_RUINS_ROOM = register("SRDungeonRoom", SentryRuinsRoom::new);
    public static final RegistryObject<StructurePieceType> SENTRY_RUINS_TUNNEL = register("SRTunnel", SentryRuinsTunnel::new);

    private static RegistryObject<StructurePieceType> register(String name, StructurePieceType structurePieceType) {
        return STRUCTURE_PIECE_TYPES.register(name.toLowerCase(Locale.ROOT), () -> structurePieceType);
    }
}