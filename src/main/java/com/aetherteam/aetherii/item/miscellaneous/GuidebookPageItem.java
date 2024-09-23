package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.api.guidebook.BestiaryEntry;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import com.aetherteam.aetherii.data.resources.registries.AetherIIBestiaryEntries;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class GuidebookPageItem extends Item {
    public GuidebookPageItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (level instanceof ServerLevel serverLevel) {
            if (player instanceof ServerPlayer serverPlayer) {
                RegistryAccess registryAccess = serverLevel.registryAccess();
                MinecraftServer server = serverLevel.getServer();
                ServerAdvancementManager serverAdvancementManager = server.getAdvancements();
                PlayerAdvancements playerAdvancements = serverPlayer.getAdvancements();
                GuidebookDiscoveryAttachment attachment = serverPlayer.getData(AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);

                Registry<BestiaryEntry> registry = registryAccess.registryOrThrow(AetherIIBestiaryEntries.BESTIARY_ENTRY_REGISTRY_KEY);
                List<Holder.Reference<BestiaryEntry>> registryEntries = new ArrayList<>(registry.holders().toList());

                for (int attempts = 0; attempts <= registryEntries.size(); attempts++) {
                    int bounds = registryEntries.size();
                    Holder.Reference<BestiaryEntry> randomEntry = registryEntries.get(serverLevel.getRandom().nextInt(bounds));
                    if (!attachment.hasAnyBestiaryEntryStatus(randomEntry)) {
                        ResourceLocation observe = randomEntry.value().observationAdvancement();
                        ResourceLocation understand = randomEntry.value().understandingAdvancement();
                        this.unlockAdvancement(serverAdvancementManager, playerAdvancements, observe);
                        this.unlockAdvancement(serverAdvancementManager, playerAdvancements, understand);
                        break;
                    } else {
                        registryEntries.remove(randomEntry);
                    }
                }
            }
        }
        stack.consume(1, player);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void unlockAdvancement(ServerAdvancementManager serverAdvancementManager, PlayerAdvancements playerAdvancements, ResourceLocation location) {
        AdvancementHolder understandAdvancement = serverAdvancementManager.get(location);
        if (understandAdvancement != null) {
            AdvancementProgress progress = playerAdvancements.getOrStartProgress(understandAdvancement);
            if (!progress.isDone()) {
                for (String criteria : progress.getRemainingCriteria()) {
                    playerAdvancements.award(understandAdvancement, criteria);
                }
            }
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Random Bestiary Entry").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
