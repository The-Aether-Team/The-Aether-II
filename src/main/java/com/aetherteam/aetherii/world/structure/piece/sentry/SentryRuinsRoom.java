package com.aetherteam.aetherii.world.structure.piece.sentry;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.registries.holyisles.HolyIslesConfiguredFeatures;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.monster.dungeon.SentryGolem;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.world.structure.piece.AetherIIStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

/**
 * A normal Bronze Dungeon room or hallway.
 */
public class SentryRuinsRoom extends SentryRuinsPiece {
    public SentryRuinsRoom(StructureTemplateManager manager, String name, BlockPos pos, Rotation rotation, Holder<StructureProcessorList> processors) {
        super(AetherIIStructurePieceTypes.SENTRY_RUINS_ROOM.get(), manager, name, new StructurePlaceSettings().setRotation(rotation), pos, processors);
    }

    public SentryRuinsRoom(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherIIStructurePieceTypes.SENTRY_RUINS_ROOM.get(), context.registryAccess(), tag, context.structureTemplateManager(), resourceLocation -> new StructurePlaceSettings());
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);

        if (name.equals("Sentry Golem")) {
            SentryGolem sentryGolem = new SentryGolem(AetherIIEntityTypes.SENTRY_GOLEM.get(), level.getLevel());
            sentryGolem.setPos(Vec3.atBottomCenterOf(pos));
            sentryGolem.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AetherIIItems.HAMMER_OF_DEMOLITION.asItem()));
            sentryGolem.setLeftHanded(true);
            level.addFreshEntity(sentryGolem);
        }

        else if (random.nextInt(4) > 1) {
            if (name.equals("Entrance Moss")) {
                if (level.getBiome(pos).is(AetherIITags.Biomes.ARCTIC)) {
                    Objects.requireNonNull(level.registryAccess().get(HolyIslesConfiguredFeatures.SHAYELINN_MOSS_STRUCTURE).orElse(null)).value().place((WorldGenLevel) level, level.getLevel().getChunkSource().getGenerator(), random, pos);
                } else if (level.getBiome(pos).is(AetherIITags.Biomes.IRRADIATED)) {
                    Objects.requireNonNull(level.registryAccess().get(HolyIslesConfiguredFeatures.AMBRELINN_MOSS_STRUCTURE).orElse(null)).value().place((WorldGenLevel) level, level.getLevel().getChunkSource().getGenerator(), random, pos);
                }
                else Objects.requireNonNull(level.registryAccess().get(HolyIslesConfiguredFeatures.BRYALINN_MOSS_STRUCTURE).orElse(null)).value().place((WorldGenLevel) level, level.getLevel().getChunkSource().getGenerator(), random, pos);
            } else {
                ConfiguredFeature<?, ?> feature = Objects.requireNonNull(level.registryAccess().get(ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.parse(name))).orElse(null)).value();
                feature.place((WorldGenLevel) level, level.getLevel().getChunkSource().getGenerator(), random, pos);
            }
        }
    }
}