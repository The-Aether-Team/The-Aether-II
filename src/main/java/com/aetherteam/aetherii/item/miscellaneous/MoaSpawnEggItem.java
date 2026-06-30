package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.blockentity.Spawner;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.MoaVariant;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class MoaSpawnEggItem extends ForgeSpawnEggItem {
    public MoaSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties properties) {
        super(type, backgroundColor, highlightColor, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level instanceof ServerLevel serverLevel) {
            ItemStack stack = context.getItemInHand();
            BlockPos pos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState state = level.getBlockState(pos);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof Spawner spawner) {
                EntityType<?> entityType = this.getType(stack.getTag());
                spawner.setEntityId(entityType, level.getRandom());
                level.sendBlockUpdated(pos, state, state, 3);
                level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, pos);
                shrinkUnlessCreative(context, stack);
            } else {
                BlockPos relativePos = state.getCollisionShape(level, pos).isEmpty() ? pos : pos.relative(direction);
                EntityType<?> entityType = this.getType(stack.getTag());
                ItemStack spawnStack = withMoaVariantTag(stack, level.getRandom());
                Entity entity = entityType.spawn(serverLevel, spawnStack, context.getPlayer(), relativePos, MobSpawnType.SPAWN_EGG, true, !Objects.equals(pos, relativePos) && direction == Direction.UP);
                if (entity != null) {
                    applyMoaVariant(spawnStack, entity);
                    shrinkUnlessCreative(context, stack);
                    level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private static void shrinkUnlessCreative(UseOnContext context, ItemStack stack) {
        if (context.getPlayer() == null || !context.getPlayer().getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    private static ItemStack withMoaVariantTag(ItemStack stack, RandomSource random) {
        MoaVariant variant = getOrCreateMoaVariant(stack, random);
        ItemStack spawnStack = stack.copy();
        AetherIIDataComponents.set(spawnStack, AetherIIDataComponents.MOA_VARIANT, variant);
        CompoundTag entityTag = spawnStack.getOrCreateTag().getCompound("EntityTag").copy();
        writeMoaVariant(entityTag, variant);
        spawnStack.getOrCreateTag().put("EntityTag", entityTag);
        return spawnStack;
    }

    private static void applyMoaVariant(ItemStack stack, Entity entity) {
        if (entity instanceof Moa moa) {
            moa.setVariant(getOrCreateMoaVariant(stack, moa.getRandom()));
        }
    }

    private static MoaVariant getOrCreateMoaVariant(ItemStack stack, RandomSource random) {
        MoaVariant variant = AetherIIDataComponents.get(stack, AetherIIDataComponents.MOA_VARIANT);
        return variant != null ? variant : randomMoaVariant(random);
    }

    private static MoaVariant randomMoaVariant(RandomSource random) {
        return new MoaVariant(
                Moa.KeratinColor.getRandom(random, false),
                Moa.EyeColor.getRandom(random, false),
                Moa.FeatherColor.getRandom(random, false),
                Moa.FeatherShape.getRandom(random, false),
                Optional.empty());
    }

    private static void writeMoaVariant(CompoundTag tag, MoaVariant variant) {
        tag.putString("FeatherShape", variant.featherShape().getSerializedName());
        tag.putString("KeratinColor", variant.keratinColor().getSerializedName());
        tag.putString("EyeColor", variant.eyeColor().getSerializedName());
        tag.putString("FeatherColor", variant.featherColor().getSerializedName());
        variant.specialVariant().ifPresentOrElse(specialVariant -> Moa.SpecialVariant.INT_CODEC.encodeStart(NbtOps.INSTANCE, specialVariant).result().ifPresent(encoded -> tag.put("MoaVariant", encoded)), () -> tag.remove("MoaVariant"));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltipAdder, flag);
        MoaVariant moaVariant = AetherIIDataComponents.get(stack, AetherIIDataComponents.MOA_VARIANT);
        if (moaVariant != null) {
            var keratinColor = moaVariant.keratinColor();
            var eyeColor = moaVariant.eyeColor();
            var featherColor = moaVariant.featherColor();
            var featherShape = moaVariant.featherShape();
            var specialVariant = moaVariant.specialVariant().orElse(null);
            if (specialVariant != null) {
                if (specialVariant.keratinColorOverride != null) {
                    keratinColor = specialVariant.keratinColorOverride;
                }
                if (specialVariant.eyeColorOverride != null) {
                    eyeColor = specialVariant.eyeColorOverride;
                }
                if (specialVariant.featherColorOverride != null) {
                    featherColor = specialVariant.featherColorOverride;
                }
                if (specialVariant.featherShapeOverride != null) {
                    featherShape = specialVariant.featherShapeOverride;
                }
            }
            var style = Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(true);
            var keratinText = Component.translatable("aether_ii.tooltip.item.moa_egg.keratin", Component.translatable("aether_ii.tooltip.item.moa_egg.keratin_color." + keratinColor.getSerializedName())).withStyle(style);
            var eyeColorText = Component.translatable("aether_ii.tooltip.item.moa_egg.eyes", Component.translatable("aether_ii.tooltip.item.moa_egg.eye_color." + eyeColor.getSerializedName())).withStyle(style);
            var featherText = Component.translatable("aether_ii.tooltip.item.moa_egg.feathers", Component.translatable("aether_ii.tooltip.item.moa_egg.feather_shape." + featherShape.getSerializedName()), Component.translatable("aether_ii.tooltip.item.moa_egg.feather_color." + featherColor.getSerializedName())).withStyle(style);
            if (specialVariant != null) {
                keratinText.append(Component.literal(keratinColor == moaVariant.keratinColor() ? "*" : "**"));
                eyeColorText.append(Component.literal(eyeColor == moaVariant.eyeColor() ? "*" : "**"));
                featherText.append(Component.literal("*".repeat(1 + (featherColor == moaVariant.featherColor() ? 0 : 1) + (featherShape == moaVariant.featherShape() ? 0 : 1))));
            }
            tooltipAdder.add(keratinText);
            tooltipAdder.add(eyeColorText);
            tooltipAdder.add(featherText);
        }
    }
}
