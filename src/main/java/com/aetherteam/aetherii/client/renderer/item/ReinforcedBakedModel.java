package com.aetherteam.aetherii.client.renderer.item;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReinforcedBakedModel extends BakedModelWrapper<BakedModel> {
    private static final List<String> ITEM_NAMES = List.of(
            "arkenium_axe",
            "arkenium_hammer",
            "arkenium_pickaxe",
            "arkenium_pike",
            "arkenium_shortsword",
            "arkenium_shovel",
            "arkenium_trowel",
            "gravitite_axe",
            "gravitite_hammer",
            "gravitite_pickaxe",
            "gravitite_pike",
            "gravitite_shortsword",
            "gravitite_shovel",
            "gravitite_trowel",
            "holystone_axe",
            "holystone_hammer",
            "holystone_pickaxe",
            "holystone_pike",
            "holystone_shortsword",
            "holystone_shovel",
            "holystone_trowel",
            "icestone_pendant",
            "skyroot_axe",
            "skyroot_hammer",
            "skyroot_pickaxe",
            "skyroot_pike",
            "skyroot_shortsword",
            "skyroot_shovel",
            "skyroot_trowel",
            "zanite_axe",
            "zanite_hammer",
            "zanite_pendant",
            "zanite_pickaxe",
            "zanite_pike",
            "zanite_shortsword",
            "zanite_shovel",
            "zanite_trowel");

    private final ItemOverrides overrides;

    public ReinforcedBakedModel(BakedModel originalModel, String itemName, BakedModel reinforced1, BakedModel reinforced2) {
        super(originalModel);
        this.overrides = new ReinforcedOverrides(this, originalModel, originalModel.getOverrides(), reinforced1, reinforced2, finalTier(itemName));
    }

    @Override
    public ItemOverrides getOverrides() {
        return this.overrides;
    }

    public static List<String> itemNames() {
        return ITEM_NAMES;
    }

    public static List<ResourceLocation> requiredModels() {
        List<ResourceLocation> modelLocations = new ArrayList<>();
        for (String itemName : ITEM_NAMES) {
            modelLocations.add(itemModel(itemName + "_reinforced_1"));
            modelLocations.add(itemModel(itemName + "_reinforced_2"));
        }
        return modelLocations;
    }

    public static ResourceLocation itemModel(String path) {
        return new ResourceLocation(AetherII.MODID, "item/" + path);
    }

    private static int finalTier(String itemName) {
        return itemName.startsWith("arkenium_") ? ReinforcementTier.FOURTH.getTierNumber() : ReinforcementTier.THIRD.getTierNumber();
    }

    private static class ReinforcedOverrides extends ItemOverrides {
        private final BakedModel baseModel;
        private final BakedModel originalModel;
        private final ItemOverrides originalOverrides;
        private final BakedModel reinforced1;
        private final BakedModel reinforced2;
        private final int finalTier;

        private ReinforcedOverrides(BakedModel baseModel, BakedModel originalModel, ItemOverrides originalOverrides, BakedModel reinforced1, BakedModel reinforced2, int finalTier) {
            this.baseModel = baseModel;
            this.originalModel = originalModel;
            this.originalOverrides = originalOverrides;
            this.reinforced1 = reinforced1;
            this.reinforced2 = reinforced2;
            this.finalTier = finalTier;
        }

        @Override
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            ReinforcementTier tier = AetherIIDataComponents.get(stack, AetherIIDataComponents.REINFORCEMENT_TIER);
            if (tier != null) {
                int tierNumber = tier.getTierNumber();
                if (tierNumber >= this.finalTier) {
                    return this.reinforced2;
                } else if (tierNumber >= ReinforcementTier.FIRST.getTierNumber()) {
                    return this.reinforced1;
                }
            }
            BakedModel originalResolved = this.originalOverrides.resolve(this.originalModel, stack, level, entity, seed);
            return originalResolved != this.originalModel ? originalResolved : this.baseModel;
        }
    }
}
