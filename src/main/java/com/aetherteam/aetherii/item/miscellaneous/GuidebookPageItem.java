package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.api.guidebook.GuidebookEntry;
import com.aetherteam.aetherii.api.guidebook.MutableEntry;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.player.GuidebookDiscoveryAttachment;
import com.aetherteam.aetherii.client.gui.component.toast.GuidebookToast;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.GuidebookEntryData;
import com.aetherteam.aetherii.network.packet.clientbound.GuidebookToastPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import com.aetherteam.aetherii.item.components.TooltipDisplay;
import net.minecraft.world.level.Level;
import com.aetherteam.aetherii.network.PacketDistributor;

import java.util.List;
import java.util.function.Consumer;

public class GuidebookPageItem extends Item {
    public GuidebookPageItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        RandomSource random = player.getRandom();
        ItemStack stack = player.getItemInHand(usedHand);
        if (player instanceof ServerPlayer serverPlayer) {
            GuidebookDiscoveryAttachment attachment = AetherIIDataAttachments.get(serverPlayer, AetherIIDataAttachments.GUIDEBOOK_DISCOVERY);
            List<GuidebookEntryData> dataList = AetherIIDataComponents.get(stack, AetherIIDataComponents.GUIDEBOOK_ENTRY_DATA);
            if (dataList != null) {
                for (GuidebookEntryData data : dataList) {
                    if (data.registry().toString().equals(AetherIIRegistries.BESTIARY_ENTRY.location().toString())) {
                        return this.unlockEntries(serverPlayer, stack, attachment, data, attachment.getBestiaryEntries());
                    } else if (data.registry().toString().equals(AetherIIRegistries.EFFECTS_ENTRY.location().toString())) {
                        return this.unlockEntries(serverPlayer, stack, attachment, data, attachment.getEffectsEntries());
                    } else if (data.registry().toString().equals(AetherIIRegistries.EXPLORATION_ENTRY.location().toString())) {
                        return this.unlockEntries(serverPlayer, stack, attachment, data, attachment.getExplorationEntries());
                    }
                }
            } else {
                List<? extends MutableEntry> entries = random.nextBoolean() ? attachment.getBestiaryEntries() : random.nextBoolean() ? attachment.getEffectsEntries() : attachment.getExplorationEntries();
                MutableEntry entry = entries.get(serverPlayer.getRandom().nextInt(entries.size()));
                GuidebookToast.Icons icon = null;
                for (String name : entry.getClientValues().keySet()) {
                    entry.getClientValues().get(name).reveal();
                    icon = attachment.getIconForEntry(entry);
                }
                if (icon != null) {
                    PacketDistributor.sendToPlayer(serverPlayer, new GuidebookToastPacket(GuidebookToast.Type.DISCOVERY, icon));
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    return InteractionResultHolder.success(stack);
                }
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    private InteractionResultHolder<ItemStack> unlockEntries(ServerPlayer serverPlayer, ItemStack stack, GuidebookDiscoveryAttachment attachment, GuidebookEntryData data, List<? extends MutableEntry> entries) {
        if (entries != null) {
            for (MutableEntry entry : entries) {
                GuidebookToast.Icons icon = null;
                if (entry instanceof GuidebookEntry guidebookEntry) {
                    if (guidebookEntry.getId().toString().equals(data.name())) {
                        for (String name : entry.getClientValues().keySet()) {
                            if (data.values().isEmpty() || data.values().contains(name)) {
                                if (!entry.getClientValues().get(name).isVisible()) {
                                    entry.getClientValues().get(name).reveal();
                                    icon = attachment.getIconForEntry(entry);
                                }
                            }
                        }
                    }
                }
                if (icon != null) {
                    PacketDistributor.sendToPlayer(serverPlayer, new GuidebookToastPacket(GuidebookToast.Type.DISCOVERY, icon));
                    if (!serverPlayer.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    return InteractionResultHolder.success(stack);
                }
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipAdder, TooltipFlag flag) {
        List<GuidebookEntryData> dataList = AetherIIDataComponents.get(stack, AetherIIDataComponents.GUIDEBOOK_ENTRY_DATA);
        if (dataList != null) {
            for (GuidebookEntryData data : dataList) {
                ResourceKey registryKey = ResourceKey.createRegistryKey(data.registry());
                ResourceKey resourceKey = ResourceKey.create(registryKey, new ResourceLocation(data.name()));
                if (level != null) {
                    level.registryAccess().lookupOrThrow(registryKey).get(resourceKey).ifPresent((object) -> {
                        if (object instanceof Holder holder && holder.value() instanceof GuidebookEntry guidebookEntry) {
                            tooltipAdder.add(Component.translatable(guidebookEntry.getName()).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
                        }
                    });
                }
            }
        } else {
            tooltipAdder.add(Component.literal("Random").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)); //todo
        }
    }
}
