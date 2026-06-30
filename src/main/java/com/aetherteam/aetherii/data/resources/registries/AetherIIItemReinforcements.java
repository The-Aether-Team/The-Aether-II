package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.api.ItemReinforcement;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.components.DataComponents;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredPikeItem;
import com.aetherteam.aetherii.item.equipment.weapons.TieredShortswordItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import com.aetherteam.aetherii.item.components.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.item.components.ItemStackTemplate;
import com.aetherteam.aetherii.item.components.ItemAttributeModifiers;
import com.aetherteam.aetherii.item.components.Tool;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class AetherIIItemReinforcements {
    private static final ResourceLocation BASE_ATTACK_DAMAGE_ID = new ResourceLocation("minecraft", "base_attack_damage");
    private static final ResourceLocation BASE_ATTACK_SPEED_ID = new ResourceLocation("minecraft", "base_attack_speed");

    private static ItemReinforcement.Cost COST_TIER_1_MATERIAL_1 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 1));
    private static ItemReinforcement.Cost COST_TIER_2_MATERIAL_1 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 2), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 1));
    private static ItemReinforcement.Cost COST_TIER_3_MATERIAL_1 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 4), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 3));

    private static ItemReinforcement.Cost COST_TIER_1_MATERIAL_2 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 2));
    private static ItemReinforcement.Cost COST_TIER_2_MATERIAL_2 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 3), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 1));
    private static ItemReinforcement.Cost COST_TIER_3_MATERIAL_2 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 5), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 3));

    private static ItemReinforcement.Cost COST_TIER_1_MATERIAL_3 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 3));
    private static ItemReinforcement.Cost COST_TIER_2_MATERIAL_3 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 4), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 1));
    private static ItemReinforcement.Cost COST_TIER_3_MATERIAL_3 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 6), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 3));

    private static ItemReinforcement.Cost COST_TIER_4 = new ItemReinforcement.Cost(new ItemStackTemplate(AetherIIItems.ARKENIUM_PLATE, 6), new ItemStackTemplate(AetherIIItems.CORROBONITE_CRYSTAL, 4));

    public static void bootstrap(BootstapContext<ItemReinforcement> context) {
        HolderGetter<Block> blockLookup = context.lookup(Registries.BLOCK);
        bootstrapTools(context, blockLookup);
        bootstrapWeapons(context);
        bootstrapArmor(context);
    }

    public static void bootstrapTools(BootstapContext<ItemReinforcement> context, HolderGetter<Block> blockLookup) {
        register(context, AetherIIItems.SKYROOT_PICKAXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(incorrectForStoneTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE), 4.0F),
                        toolAttributesComponent(1.0F, 1.0F, -2.8F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.SKYROOT_AXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(incorrectForStoneTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_AXE), 4.0F),
                        toolAttributesComponent(1.5F, 1.0F, -3.2F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.SKYROOT_SHOVEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(incorrectForStoneTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), 4.0F),
                        toolAttributesComponent(1.5F, 1.0F, -3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.SKYROOT_TROWEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(incorrectForStoneTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_HOE), 4.0F),
                        toolAttributesComponent(0.5F, 1.0F, -2.5F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.HOLYSTONE_PICKAXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(incorrectForIronTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE), 6.0F),
                        toolAttributesComponent(1.0F, 2.0F, -2.8F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.HOLYSTONE_AXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(incorrectForIronTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_AXE), 6.0F),
                        toolAttributesComponent(1.5F, 2.0F, -3.2F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.HOLYSTONE_SHOVEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(incorrectForIronTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), 6.0F),
                        toolAttributesComponent(1.5F, 2.0F, -3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.HOLYSTONE_TROWEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        toolComponent(incorrectForIronTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_HOE), 6.0F),
                        toolAttributesComponent(0.5F, 2.0F, -2.5F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.ZANITE_PICKAXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        toolComponent(incorrectForDiamondTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE), 8.0F),
                        toolAttributesComponent(1.0F, 3.0F, -2.8F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ZANITE_AXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        toolComponent(incorrectForDiamondTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_AXE), 8.0F),
                        toolAttributesComponent(1.5F, 3.0F, -3.2F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ZANITE_SHOVEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        toolComponent(incorrectForDiamondTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), 8.0F),
                        toolAttributesComponent(1.5F, 3.0F, -3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ZANITE_TROWEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        toolComponent(incorrectForDiamondTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_HOE), 8.0F),
                        toolAttributesComponent(0.5F, 3.0F, -2.5F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.ARKENIUM_PICKAXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE)
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        toolComponent(incorrectForDiamondTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE), 8.0F),
                        toolAttributesComponent(1.0F, 3.0F, -2.8F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));
        register(context, AetherIIItems.ARKENIUM_AXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE)
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        toolComponent(incorrectForDiamondTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_AXE), 8.0F),
                        toolAttributesComponent(1.5F, 3.0F, -3.2F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));
        register(context, AetherIIItems.ARKENIUM_SHOVEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE)
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        toolComponent(incorrectForDiamondTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), 8.0F),
                        toolAttributesComponent(1.5F, 3.0F, -3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));
        register(context, AetherIIItems.ARKENIUM_TROWEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE)
                                .append(CommonComponents.NEW_LINE).append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        toolComponent(incorrectForDiamondTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_HOE), 8.0F),
                        toolAttributesComponent(0.5F, 3.0F, -2.5F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));

        register(context, AetherIIItems.GRAVITITE_PICKAXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        toolComponent(incorrectForNetheriteTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_PICKAXE), 9.0F),
                        toolAttributesComponent(1.0F, 4.0F, -2.8F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.GRAVITITE_AXE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        toolComponent(incorrectForNetheriteTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_AXE), 9.0F),
                        toolAttributesComponent(1.5F, 4.0F, -3.2F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.GRAVITITE_SHOVEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        toolComponent(incorrectForNetheriteTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_SHOVEL), 9.0F),
                        toolAttributesComponent(1.5F, 4.0F, -3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.GRAVITITE_TROWEL, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        toolComponent(incorrectForNetheriteTool(blockLookup), blockLookup.getOrThrow(BlockTags.MINEABLE_WITH_HOE), 9.0F),
                        toolAttributesComponent(0.5F, 4.0F, -2.5F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.TOOL, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
    }

    public static void bootstrapWeapons(BootstapContext<ItemReinforcement> context) {
        register(context, AetherIIItems.SKYROOT_SHORTSWORD, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        shortswordAttributesComponent(3.0F, 1.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.SKYROOT_HAMMER, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        hammerAttributesComponent(3.0F, 1.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.SKYROOT_PIKE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        pikeAttributesComponent(3.0F, 1.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.HOLYSTONE_SHORTSWORD, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        shortswordAttributesComponent(3.0F, 2.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.HOLYSTONE_HAMMER, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        hammerAttributesComponent(3.0F, 2.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.HOLYSTONE_PIKE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        pikeAttributesComponent(3.0F, 2.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.ZANITE_SHORTSWORD, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        shortswordAttributesComponent(3.0F, 3.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ZANITE_HAMMER, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        hammerAttributesComponent(3.0F, 3.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ZANITE_PIKE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        pikeAttributesComponent(3.0F, 3.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.ARKENIUM_SHORTSWORD, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        shortswordAttributesComponent(3.0F, 3.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));
        register(context, AetherIIItems.ARKENIUM_HAMMER, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        hammerAttributesComponent(3.0F, 3.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));
        register(context, AetherIIItems.ARKENIUM_PIKE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        pikeAttributesComponent(3.0F, 3.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));

        register(context, AetherIIItems.GRAVITITE_SHORTSWORD, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        shortswordAttributesComponent(3.0F, 4.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.GRAVITITE_HAMMER, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        hammerAttributesComponent(3.0F, 4.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.GRAVITITE_PIKE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        pikeAttributesComponent(3.0F, 4.0F, -2.4F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.WEAPON, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
    }

    public static void bootstrapArmor(BootstapContext<ItemReinforcement> context) {
        register(context, AetherIIItems.BEAST_PELT_BOOTS, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.BOOTS, 1, 0.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.BEAST_PELT_LEGGINGS, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.LEGGINGS, 4, 0.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.BEAST_PELT_CHESTPLATE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.CHESTPLATE, 5, 0.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.BEAST_PELT_HELMET, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.HELMET, 2, 0.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.BEAST_PELT_GLOVES, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.BURRUKAI_PLATE_BOOTS, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.BOOTS, 2, 0.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.BURRUKAI_PLATE_LEGGINGS, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.LEGGINGS, 5, 0.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.BURRUKAI_PLATE_CHESTPLATE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.CHESTPLATE, 6, 0.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.BURRUKAI_PLATE_HELMET, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.HELMET, 2, 0.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.BURRUKAI_PLATE_GLOVES, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_1,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_1,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_1,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.ZANITE_BOOTS, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.BOOTS, 3, 2.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ZANITE_LEGGINGS, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.LEGGINGS, 6, 2.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ZANITE_CHESTPLATE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.CHESTPLATE, 8, 2.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ZANITE_HELMET, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.HELMET, 3, 2.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ZANITE_GLOVES, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.ARKENIUM_BOOTS, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.BOOTS, 3, 2.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));
        register(context, AetherIIItems.ARKENIUM_LEGGINGS, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.LEGGINGS, 6, 2.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));
        register(context, AetherIIItems.ARKENIUM_CHESTPLATE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.CHESTPLATE, 8, 2.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));
        register(context, AetherIIItems.ARKENIUM_HELMET, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.TWO)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.HELMET, 3, 2.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));
        register(context, AetherIIItems.ARKENIUM_GLOVES, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        tierComponent(ReinforcementTier.THIRD)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.TWO).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_4,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.TWO)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.FOURTH)
                )
        ));

        register(context, AetherIIItems.GRAVITITE_BOOTS, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.BOOTS, 3, 3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.GRAVITITE_LEGGINGS, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.LEGGINGS, 6, 3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.GRAVITITE_CHESTPLATE, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.CHESTPLATE, 8, 3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.GRAVITITE_HELMET, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        toolTierTooltip().append(CommonComponents.NEW_LINE)
                                .append(charmTooltip(1, Charms.Tier.ONE)).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        armorAttributesComponent(ArmorItem.Type.HELMET, 3, 3.0F),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.GRAVITITE_GLOVES, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_3,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_3,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_3,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));

        register(context, AetherIIItems.ZANITE_PENDANT, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
        register(context, AetherIIItems.ICESTONE_PENDANT, new ItemReinforcement(
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(50),
                        COST_TIER_1_MATERIAL_2,
                        durabilityComponent(50),
                        tierComponent(ReinforcementTier.FIRST)
                ),
                new ItemReinforcement.Upgrade(
                        durabilityTooltip(100),
                        COST_TIER_2_MATERIAL_2,
                        durabilityComponent(100),
                        tierComponent(ReinforcementTier.SECOND)
                ),
                new ItemReinforcement.Upgrade(
                        charmTooltip(1, Charms.Tier.ONE).append(CommonComponents.NEW_LINE)
                                .append(durabilityTooltip(150)),
                        COST_TIER_3_MATERIAL_2,
                        durabilityComponent(150),
                        charmsComponent(new Charms.CharmHolder(Charms.Type.ARMOR, Charms.Tier.ONE)),
                        rarityComponent(),
                        tierComponent(ReinforcementTier.THIRD)
                )
        ));
    }

    private static MutableComponent toolTierTooltip() {
        return CommonComponents.SPACE.copy().append(Component.translatable("gui.aether_ii.arkenium_forge.tooltip.tier").withStyle(ChatFormatting.GRAY));
    }

    public static MutableComponent charmTooltip(int amount, Charms.Tier tier) {
        if (amount > 1) {
            return CommonComponents.SPACE.copy().append(Component.translatable("gui.aether_ii.arkenium_forge.tooltip.charms", Component.literal(String.valueOf(amount)), Charms.createCharmTierComponent(tier)).withStyle(ChatFormatting.GRAY));
        } else {
            return CommonComponents.SPACE.copy().append(Component.translatable("gui.aether_ii.arkenium_forge.tooltip.charm", Component.literal(String.valueOf(amount)), Charms.createCharmTierComponent(tier)).withStyle(ChatFormatting.GRAY));
        }
    }

    public static MutableComponent durabilityTooltip(int value) {
        return Component.translatable("gui.aether_ii.arkenium_forge.tooltip.durability", Component.literal(String.valueOf(value))).withStyle(ChatFormatting.GRAY);
    }

    private static ItemReinforcement.Add durabilityComponent(int amount) {
        return new ItemReinforcement.Add(DataComponents.MAX_DAMAGE, amount);
    }

    private static ItemReinforcement.Set toolComponent(List<HolderSet<Block>> incorrectBlocksForDrops, HolderSet<Block> minesEfficiently, float speed) {
        List<Tool.Rule> rules = new java.util.ArrayList<>();
        for (HolderSet<Block> incorrectBlocks : incorrectBlocksForDrops) {
            rules.add(Tool.Rule.deniesDrops(incorrectBlocks));
        }
        rules.add(Tool.Rule.minesAndDrops(minesEfficiently, speed));
        return new ItemReinforcement.Set(DataComponents.TOOL, new Tool(rules, 1.0F, 1, true));
    }

    private static List<HolderSet<Block>> incorrectForStoneTool(HolderGetter<Block> blockLookup) {
        return List.of(blockLookup.getOrThrow(BlockTags.NEEDS_IRON_TOOL), blockLookup.getOrThrow(BlockTags.NEEDS_DIAMOND_TOOL));
    }

    private static List<HolderSet<Block>> incorrectForIronTool(HolderGetter<Block> blockLookup) {
        return List.of(blockLookup.getOrThrow(BlockTags.NEEDS_DIAMOND_TOOL));
    }

    private static List<HolderSet<Block>> incorrectForDiamondTool(HolderGetter<Block> blockLookup) {
        return List.of();
    }

    private static List<HolderSet<Block>> incorrectForNetheriteTool(HolderGetter<Block> blockLookup) {
        return List.of();
    }

    private static ItemReinforcement.Set toolAttributesComponent(float attackDamageBaseline, float attackDamageBonus, float attackSpeedBaseline) {
        return new ItemReinforcement.Set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, AetherIIStats.modifier(BASE_ATTACK_DAMAGE_ID, attackDamageBaseline + attackDamageBonus, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, AetherIIStats.modifier(BASE_ATTACK_SPEED_ID, attackSpeedBaseline, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .build()
        );
    }

    private static ItemReinforcement.Set shortswordAttributesComponent(float attackDamageBaseline, float attackDamageBonus, float attackSpeedBaseline) {
        return new ItemReinforcement.Set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                .add(AetherIIAttributes.SLASH_DAMAGE, AetherIIStats.modifier(AetherIIItems.BASE_SLASH_DAMAGE_ID, attackDamageBaseline + attackDamageBonus, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE, AetherIIStats.modifier(BASE_ATTACK_DAMAGE_ID, attackDamageBaseline + attackDamageBonus, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, AetherIIStats.modifier(BASE_ATTACK_SPEED_ID, attackSpeedBaseline, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .add(AetherIIAttributes.SWEEP_RANGE, AetherIIStats.modifier(TieredShortswordItem.BASE_SWEEP_RANGE_ID, 2.0, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .build()
        );
    }

    private static ItemReinforcement.Set hammerAttributesComponent(float attackDamageBaseline, float attackDamageBonus, float attackSpeedBaseline) {
        return new ItemReinforcement.Set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                .add(AetherIIAttributes.IMPACT_DAMAGE, AetherIIStats.modifier(AetherIIItems.BASE_PIERCE_DAMAGE_ID, attackDamageBaseline + attackDamageBonus, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE, AetherIIStats.modifier(BASE_ATTACK_DAMAGE_ID, attackDamageBaseline + attackDamageBonus, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, AetherIIStats.modifier(BASE_ATTACK_SPEED_ID, attackSpeedBaseline, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .add(AetherIIAttributes.SHOCK_RANGE, AetherIIStats.modifier(TieredHammerItem.BASE_SHOCK_RANGE_ID, 2.0, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .build()
        );
    }

    private static ItemReinforcement.Set pikeAttributesComponent(float attackDamageBaseline, float attackDamageBonus, float attackSpeedBaseline) {
        return new ItemReinforcement.Set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                .add(AetherIIAttributes.PIERCE_DAMAGE, AetherIIStats.modifier(AetherIIItems.BASE_PIERCE_DAMAGE_ID, attackDamageBaseline + attackDamageBonus, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_DAMAGE, AetherIIStats.modifier(BASE_ATTACK_DAMAGE_ID, attackDamageBaseline + attackDamageBonus, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, AetherIIStats.modifier(BASE_ATTACK_SPEED_ID, attackSpeedBaseline, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .add(AetherIIAttributes.STAB_RADIUS, AetherIIStats.modifier(TieredPikeItem.BASE_STAB_RADIUS_ID, 1.5, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .add(AetherIIAttributes.STAB_DISTANCE, AetherIIStats.modifier(TieredPikeItem.BASE_STAB_DISTANCE_ID, 5.0, AttributeModifier.Operation.ADDITION), EquipmentSlotGroup.MAINHAND)
                .build()
        );
    }

    private static ItemReinforcement.Set armorAttributesComponent(ArmorItem.Type type, double defense, double toughness) {
        EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(type.getSlot());
        ResourceLocation modifierId = new ResourceLocation("minecraft", "armor." + type.getName());
        return new ItemReinforcement.Set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                .add(Attributes.ARMOR, AetherIIStats.modifier(modifierId, defense, AttributeModifier.Operation.ADDITION), slotGroup)
                .add(Attributes.ARMOR_TOUGHNESS, AetherIIStats.modifier(modifierId, toughness, AttributeModifier.Operation.ADDITION), slotGroup)
                .build()
        );
    }

    private static ItemReinforcement.Set charmsComponent(Charms.CharmHolder charmHolder) {
        return new ItemReinforcement.Set(AetherIIDataComponents.CHARMS.get(), new Charms(charmHolder));
    }

    private static ItemReinforcement.Set rarityComponent() {
        return new ItemReinforcement.Set(DataComponents.RARITY, AetherIIItems.AETHER_II_UPGRADED);
    }

    private static ItemReinforcement.Set tierComponent(ReinforcementTier tier) {
        return new ItemReinforcement.Set(AetherIIDataComponents.REINFORCEMENT_TIER.get(), tier);
    }

    public static void register(BootstapContext<ItemReinforcement> context, RegistryObject<?> key, ItemReinforcement itemReinforcement) {
        ItemReinforcement design = itemReinforcement;
        context.register(ResourceKey.create(AetherIIRegistries.ITEM_REINFORCEMENT, key.getId()), design);
    }

    public static Registry<ItemReinforcement> getRegistry(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AetherIIRegistries.ITEM_REINFORCEMENT);
    }

    public static ItemReinforcement get(RegistryAccess registryAccess, ItemStack stack) {
        return getRegistry(registryAccess).getOptional(BuiltInRegistries.ITEM.getKey(stack.getItem())).orElse(null);
    }
}
