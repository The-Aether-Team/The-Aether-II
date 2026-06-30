package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.Holder;
import com.aetherteam.aetherii.item.components.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import com.aetherteam.aetherii.item.components.ChargedProjectiles;
import com.aetherteam.aetherii.item.components.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TieredCrossbowItem extends CrossbowItem {
    public static final Predicate<ItemStack> BOLT_ONLY = stack -> stack.is(AetherIIItems.SCATTERGLASS_BOLT.get());

    public TieredCrossbowItem(Tier tier, Properties properties) {
        super(properties.durability(tier.getUses()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ChargedProjectiles chargedProjectiles = AetherIIDataComponents.get(stack, DataComponents.CHARGED_PROJECTILES);
        if (chargedProjectiles == null || chargedProjectiles.isEmpty()) {
            if (!player.getProjectile(stack).isEmpty()) {
                AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).setCrossbowSpecial(player.isCrouching());
                AetherIIDataAttachments.sync(player, AetherIIDataAttachments.ABILITY_BEHAVIOR);
            }
        }
        return super.use(level, player, hand);
    }

    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        Projectile projectile = new Arrow(level, shooter);
        if (projectile instanceof AbstractArrow abstractArrow) {
            abstractArrow.setBaseDamage(1.0);
            abstractArrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        }
        return projectile;
    }

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

    public float getCrossbowShootingPower(ItemStack stack) {
        return 3.15F;
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipAdder, TooltipFlag flag) {

    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}
