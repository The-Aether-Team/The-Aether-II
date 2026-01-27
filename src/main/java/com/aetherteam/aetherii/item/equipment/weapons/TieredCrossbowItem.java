package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TieredCrossbowItem extends CrossbowItem {
    public static final Predicate<ItemStack> BOLT_ONLY = stack -> stack.is(AetherIIItems.SCATTERGLASS_BOLT);

    public TieredCrossbowItem(ToolMaterial tier, Properties properties) {
        super(properties.durability(tier.durability()));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ChargedProjectiles chargedProjectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedProjectiles == null || chargedProjectiles.isEmpty()) {
            if (!player.getProjectile(stack).isEmpty()) {
                player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).setCrossbowSpecial(player.isCrouching());
                player.syncData(AetherIIDataAttachments.ABILITY_BEHAVIOR);
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
        if (projectile instanceof AbstractArrow abstractArrow) {
            abstractArrow.setBaseDamage(1.0);
            abstractArrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        }
        return projectile;
    }

    @Override
    protected int getDurabilityUse(ItemStack stack) {
        return 1;
    }

    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return BOLT_ONLY;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return BOLT_ONLY;
    }

    @Override
    public ItemStack getDefaultCreativeAmmo(@Nullable Player player, ItemStack projectileWeaponItem) {
        return AetherIIItems.SCATTERGLASS_BOLT.get().getDefaultInstance();
    }

    public float getChargeTime(ItemStack stack, LivingEntity shooter, float crossbowChargingTime) {
        return 1.25F;
    }

    public int getProjectileCount(ServerLevel level, ItemStack tool, Entity entity, int projectileCount) {
        return projectileCount;
    }

    public float getProjectileSpread(ServerLevel level, ItemStack tool, Entity entity, float projectileSpread) {
        return projectileSpread;
    }

    public float getCrossbowShootingPower(ChargedProjectiles projectile) {
        return 3.15F;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {

    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }
}
