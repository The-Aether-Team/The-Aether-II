package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;

import java.util.ArrayList;
import java.util.Collection;

public class CurrencyAttachment {
    private int amount;

    public static final MapCodec<CurrencyAttachment> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("amount").forGetter(CurrencyAttachment::getAmount)
    ).apply(instance, CurrencyAttachment::new));
    public static final StreamCodec<FriendlyByteBuf, CurrencyAttachment> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, CurrencyAttachment::getAmount,
            CurrencyAttachment::new);

    protected CurrencyAttachment(int amount) {
        this.amount = amount;
    }

    public CurrencyAttachment() {
        this.amount = 0;
    }

    public void dropAll(Player player, Collection<ItemEntity> drops) {
        if (player instanceof ServerPlayer serverPlayer && player.level() instanceof ServerLevel serverLevel) {
            GameRules gameRules = serverLevel.getGameRules();
            if (!gameRules.getBoolean(GameRules.RULE_KEEPINVENTORY)) {
                int amount = AetherIIDataAttachments.get(player, AetherIIDataAttachments.CURRENCY).getAmount();
                int fullStacks = Math.floorDiv(amount, 64);
                int leftoverStack = amount % 64;
                Collection<ItemEntity> newStacks = new ArrayList<>();
                for (int i = 0; i < fullStacks; i++) {
                    ItemStack itemStack = new ItemStack(AetherIIItems.GLINT_COIN.get(), 64);
                    ItemEntity itemEntity = new ItemEntity(serverPlayer.level(), serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), itemStack.copy());
                    itemEntity.setDefaultPickUpDelay();
                    newStacks.add(itemEntity);
                }
                if (leftoverStack > 0) {
                    ItemStack itemStack = new ItemStack(AetherIIItems.GLINT_COIN.get(), leftoverStack);
                    ItemEntity itemEntity = new ItemEntity(serverPlayer.level(), serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), itemStack.copy());
                    itemEntity.setDefaultPickUpDelay();
                    newStacks.add(itemEntity);
                }
                drops.addAll(newStacks);
                AetherIIDataAttachments.get(player, AetherIIDataAttachments.CURRENCY).setAmount(0);
            }
        }
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }
}
