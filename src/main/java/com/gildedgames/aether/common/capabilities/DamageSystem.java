package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.damage.IDamageLevelsHolder;
import com.gildedgames.aether.api.entity.damage.IDefenseLevelsHolder;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.init.ParticlesAether;
import com.gildedgames.aether.common.items.accessories.ItemDamageCharm;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketParticles;
import com.gildedgames.aether.common.util.helpers.AetherHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class DamageSystem
{
    public static float modifier;

    public static boolean blocked;

    public static int timer;

    @SubscribeEvent
    public static void onTick(TickEvent.WorldTickEvent event)
    {
        if (blocked)
        {
            timer = timer + 1;

            if (timer > 10)
            {
                blocked = false;
                timer = 0;
            }
        }
    }

    @SubscribeEvent
    public static void onLivingEntityHurt(LivingHurtEvent event)
    {
        EntityLivingBase target = event.getEntityLiving();

        if (target instanceof IDefenseLevelsHolder)
        {
            if (event.getSource().getImmediateSource() instanceof EntityPlayer)
            {
                EntityPlayer source = (EntityPlayer) event.getSource().getImmediateSource();

                IInventoryEquipment inventory = PlayerAether.getPlayer(source).getModule(PlayerEquipmentModule.class).getInventory();

                ItemStack gloveStack = inventory.getStackInSlot(2);

                if (source.getHeldItemMainhand().getItem() instanceof IDamageLevelsHolder)
                {
                    IDamageLevelsHolder itemMainhand = (IDamageLevelsHolder) source.getHeldItemMainhand().getItem();

                    float slashDamage = 0, pierceDamage = 0, impactDamage = 0, bonusSlash = 0, bonusPierce = 0, bonusImpact = 0;

                    if (source.getHeldItemMainhand().getItem() == ItemsAether.zanite_sword)
                    {
                        bonusSlash = ((float) (source.getHeldItemMainhand().getItemDamage() * 4) / source.getHeldItemMainhand().getItem().getMaxDamage());
                    }

                    if (itemMainhand.getSlashDamageLevel() > 0)
                    {
                        float damageAmount = (itemMainhand.getSlashDamageLevel() + getBonusDamageFromAccessories("slash", inventory) + bonusSlash);
                        damageAmount += target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue();
                        slashDamage = Math.max(damageAmount, 1.0F);
                    }
                    if (itemMainhand.getPierceDamageLevel() > 0)
                    {
                        float damageAmount = (itemMainhand.getPierceDamageLevel() + getBonusDamageFromAccessories("pierce", inventory) + bonusPierce);
                        damageAmount += target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue();
                        pierceDamage = Math.max(damageAmount, 1.0F);
                    }
                    if (itemMainhand.getImpactDamageLevel() > 0)
                    {
                        float damageAmount = (itemMainhand.getImpactDamageLevel() + getBonusDamageFromAccessories("impact", inventory) + bonusImpact);
                        damageAmount += target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue();
                        impactDamage = Math.max(damageAmount, 1.0F);
                    }

                    float totalDamage = (slashDamage + pierceDamage + impactDamage) * modifier;

                    float cooldownTracker = PlayerAether.getPlayer(source).getCooldownTracker();

                    event.setAmount(totalDamage * cooldownTracker);

                    spawnParticles(target,
                            itemMainhand.getSlashDamageLevel(),
                            itemMainhand.getPierceDamageLevel(),
                            itemMainhand.getImpactDamageLevel(),
                            target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());

                    handleSounds(target,
                            itemMainhand.getSlashDamageLevel(),
                            itemMainhand.getPierceDamageLevel(),
                            itemMainhand.getImpactDamageLevel(),
                            target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());
                }
                else if (source.getHeldItemOffhand().getItem() instanceof IDamageLevelsHolder)
                {
                    IDamageLevelsHolder itemOffhand = (IDamageLevelsHolder) source.getHeldItemOffhand().getItem();

                    float slashDamage = 0, pierceDamage = 0, impactDamage = 0, bonusSlash = 0, bonusPierce = 0, bonusImpact = 0;

                    if (source.getHeldItemMainhand().getItem() == ItemsAether.zanite_sword)
                    {
                        bonusSlash = ((float) (source.getHeldItemMainhand().getItemDamage() * 4) / source.getHeldItemMainhand().getItem().getMaxDamage());
                    }

                    if (itemOffhand.getSlashDamageLevel() > 0)
                    {
                        float damageAmount = (itemOffhand.getSlashDamageLevel() + getBonusDamageFromAccessories("slash", inventory) + bonusSlash);
                        damageAmount += target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue();
                        slashDamage = Math.max(damageAmount, 1.0F);
                    }
                    if (itemOffhand.getPierceDamageLevel() > 0)
                    {
                        float damageAmount = (itemOffhand.getPierceDamageLevel() + getBonusDamageFromAccessories("pierce", inventory) + bonusPierce);
                        damageAmount += target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue();
                        pierceDamage = Math.max(damageAmount, 1.0F);
                    }
                    if (itemOffhand.getImpactDamageLevel() > 0)
                    {
                        float damageAmount = (itemOffhand.getImpactDamageLevel() + getBonusDamageFromAccessories("impact", inventory) + bonusImpact);
                        damageAmount += target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue();
                        impactDamage = Math.max(damageAmount, 1.0F);
                    }

                    float totalDamage = (slashDamage + pierceDamage + impactDamage) * modifier;

                    float cooldownTracker = PlayerAether.getPlayer(source).getCooldownTracker();

                    event.setAmount(totalDamage * cooldownTracker);

                    spawnParticles(target,
                            itemOffhand.getSlashDamageLevel(),
                            itemOffhand.getPierceDamageLevel(),
                            itemOffhand.getImpactDamageLevel(),
                            target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());

                    handleSounds(target,
                            itemOffhand.getSlashDamageLevel(),
                            itemOffhand.getPierceDamageLevel(),
                            itemOffhand.getImpactDamageLevel(),
                            target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());
                }
                else if (source.getHeldItemMainhand().isEmpty() && gloveStack.getItem() instanceof ItemAetherGloves && !gloveStack.isEmpty())
                {
                    ItemAetherGloves gloves = (ItemAetherGloves) gloveStack.getItem();

                    float slashDamage = 0, pierceDamage = 0, impactDamage = 0, bonusSlash = 0, bonusPierce = 0, bonusImpact = 0;

                    if (gloveStack.getItem() == ItemsAether.zanite_gloves)
                    {
                        bonusImpact = ((float) (gloveStack.getItemDamage() * 4) / gloveStack.getItem().getMaxDamage());
                    }

                    if (gloves.getSlashDamageLevel() > 0)
                    {
                        float damageAmount = (gloves.getSlashDamageLevel() + getBonusDamageFromAccessories("slash", inventory) + bonusSlash);
                        damageAmount += target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue();
                        slashDamage = Math.max(damageAmount, 1.0F);
                    }
                    if (gloves.getPierceDamageLevel() > 0)
                    {
                        float damageAmount = (gloves.getPierceDamageLevel() + getBonusDamageFromAccessories("pierce", inventory) + bonusPierce);
                        damageAmount += target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue();
                        pierceDamage = Math.max(damageAmount, 1.0F);
                    }
                    if (gloves.getImpactDamageLevel() > 0)
                    {
                        float damageAmount = (gloves.getImpactDamageLevel() + getBonusDamageFromAccessories("impact", inventory) + bonusImpact);
                        damageAmount += target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue();
                        impactDamage = Math.max(damageAmount, 1.0F);
                    }

                    float totalDamage = (slashDamage + pierceDamage + impactDamage) * modifier;

                    float cooldownTracker = PlayerAether.getPlayer(source).getCooldownTracker();

                    event.setAmount(totalDamage * cooldownTracker);

                    spawnParticles(target,
                            gloves.getSlashDamageLevel(),
                            gloves.getPierceDamageLevel(),
                            gloves.getImpactDamageLevel(),
                            target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());

                    handleSounds(target,
                            gloves.getSlashDamageLevel(),
                            gloves.getPierceDamageLevel(),
                            gloves.getImpactDamageLevel(),
                            target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());
                }
                else
                {
                    float damageAmount = Math.max(event.getAmount() - 4.0F, 1.0F);
                    event.setAmount(damageAmount);
                }
            }
            else if (event.getSource().getImmediateSource() instanceof IDamageLevelsHolder)
            {
                IDamageLevelsHolder entitySource = (IDamageLevelsHolder) event.getSource().getImmediateSource();

                float slashDamage = 0, pierceDamage = 0, impactDamage = 0;

                if (entitySource.getSlashDamageLevel() > 0)
                {
                    float damageAmount = (float) (entitySource.getSlashDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue());
                    slashDamage = Math.max(damageAmount, 1.0F);
                }
                if (entitySource.getPierceDamageLevel() > 0)
                {
                    float damageAmount = (float) (entitySource.getPierceDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue());
                    pierceDamage = Math.max(damageAmount, 1.0F);
                }
                if (entitySource.getImpactDamageLevel() > 0)
                {
                    float damageAmount = (float) (entitySource.getImpactDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());
                    impactDamage = Math.max(damageAmount, 1.0F);
                }

                float totalDamage = (slashDamage + pierceDamage + impactDamage);

                event.setAmount(totalDamage);

                spawnParticles(target,
                        entitySource.getSlashDamageLevel(),
                        entitySource.getPierceDamageLevel(),
                        entitySource.getImpactDamageLevel(),
                        target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                        target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                        target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());

                handleSounds(target,
                        entitySource.getSlashDamageLevel(),
                        entitySource.getPierceDamageLevel(),
                        entitySource.getImpactDamageLevel(),
                        target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                        target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                        target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());
            }
            else
            {
                float damageAmount = Math.max(event.getAmount() - 4.0F, 1.0F);
                event.setAmount(damageAmount);
            }
        }
    }

    @SubscribeEvent
    public static void handleVanillaGloveDamage(LivingHurtEvent event)
    {
        EntityLivingBase target = event.getEntityLiving();

        if (!(target instanceof IDefenseLevelsHolder))
        {
            if (event.getSource().getImmediateSource() instanceof EntityPlayer)
            {
                EntityPlayer source = (EntityPlayer) event.getSource().getImmediateSource();

                ItemStack gloveStack = PlayerAether.getPlayer(source).getModule(PlayerEquipmentModule.class).getInventory().getStackInSlot(2);

                if (source.getHeldItemMainhand().isEmpty() && gloveStack.getItem() instanceof ItemAetherGloves && !gloveStack.isEmpty())
                {
                    ItemAetherGloves gloves = (ItemAetherGloves) gloveStack.getItem();

                    float vanillaDamage = 0;

                    if (gloveStack.getItem() == ItemsAether.zanite_gloves)
                    {
                        vanillaDamage = 6f + ((float) (gloveStack.getItemDamage() * 4)
                                / gloveStack.getItem().getMaxDamage());
                    }
                    else
                    {
                        if (gloves.getSlashDamageLevel() > 0)
                        {
                            vanillaDamage = gloves.getSlashDamageLevel();
                        }
                        if (gloves.getPierceDamageLevel() > 0)
                        {
                            vanillaDamage = gloves.getPierceDamageLevel();
                        }
                        if (gloves.getImpactDamageLevel() > 0)
                        {
                            vanillaDamage = gloves.getImpactDamageLevel();
                        }
                    }

                    event.setAmount(vanillaDamage * modifier);
                }
            }
        }
    }

    public static float getBonusDamageFromAccessories(String damageType, IInventoryEquipment inventory)
    {
        float damage = 0;

        for (int i = 0; i < 7; i++)
        {
            ItemStack charmStack = inventory.getStackInSlot(7 + i);

            if (charmStack.getItem() instanceof ItemDamageCharm && !charmStack.isEmpty())
            {
                ItemDamageCharm charm = (ItemDamageCharm) charmStack.getItem();

                switch (damageType)
                {
                    case "slash":
                    {
                        if (charm.getSlashDamageLevel() > 0)
                        {
                            damage += charm.getSlashDamageLevel();
                        }

                        break;
                    }
                    case "pierce":
                    {
                        if (charm.getPierceDamageLevel() > 0)
                        {
                            damage += charm.getPierceDamageLevel();
                        }

                        break;
                    }
                    case "impact":
                    {
                        if (charm.getImpactDamageLevel() > 0)
                        {
                            damage += charm.getImpactDamageLevel();
                        }

                        break;
                    }
                }
            }
        }

        return damage;
    }

    @SubscribeEvent
    public static void checkForCritical(CriticalHitEvent event)
    {
        modifier = event.getDamageModifier();
    }

    @SubscribeEvent
    public static void onEntityLoad(final AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityLivingBase)
        {
            EntityLivingBase living = (EntityLivingBase) event.getObject();

            living.getAttributeMap().registerAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL);
            living.getAttributeMap().registerAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL);
            living.getAttributeMap().registerAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL);
        }
    }

    public static void spawnParticles(EntityLivingBase target, double slashDamage, double pierceDamage, double impactDamage, double slashDefense, double pierceDefense, double impactDefense)
    {
        final double radius = 0.3;
        final double randX = target.getRNG().nextDouble() * (target.getRNG().nextBoolean() ? 1.0 : -1.0) * radius;
        final double randZ = target.getRNG().nextDouble() * (target.getRNG().nextBoolean() ? 1.0 : -1.0) * radius;

        double x = target.posX + randX;
        double y = target.posY + (double) target.height;
        double z = target.posZ + randZ;

        for (int i = 0; i < 20; i++)
        {
            if (slashDamage > 0 && slashDefense > 0.0F || pierceDamage > 0 && pierceDefense > 0.0F || impactDamage > 0 && impactDefense > 0.0F)
            {
                if (slashDamage > 0 && slashDefense > 0.0F)
                {
                    NetworkingAether
                            .sendPacketToDimension(new PacketParticles(ParticlesAether.SLASH, x, y, z, randX, 0.0D, randZ), target.dimension);
                }

                if (pierceDamage > 0 && pierceDefense > 0.0F)
                {
                    NetworkingAether
                            .sendPacketToDimension(new PacketParticles(ParticlesAether.PIERCE, x, y, z, randX, 0.0D, randZ), target.dimension);
                }

                if (impactDamage > 0 && impactDefense > 0.0F)
                {
                    NetworkingAether
                            .sendPacketToDimension(new PacketParticles(ParticlesAether.IMPACT, x, y, z, randX, 0.0D, randZ), target.dimension);
                }

                handleSounds(target, slashDamage, pierceDamage, impactDamage, slashDefense, pierceDefense, impactDefense);
            }
        }
    }

    public static void handleSounds(EntityLivingBase target, double slashDamage, double pierceDamage, double impactDamage, double slashDefense, double pierceDefense, double impactDefense)
    {
        if ((slashDamage > 0 && slashDefense < 0.0F) || (pierceDamage > 0 && pierceDefense < 0.0F) || (impactDamage > 0 && impactDefense < 0.0F))
        {
            blocked = true;
            target.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 0.8F, 0.8F + target.world.rand.nextFloat() * 0.4F);
        }
        else
        {
            blocked = false;
            if (slashDamage > 0 && slashDefense > 0.0F || pierceDamage > 0 && pierceDefense > 0.0F || impactDamage > 0 && impactDefense > 0.0F)
            {
                target.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, 0.8F, 0.8F + target.world.rand.nextFloat() * 0.4F);
            }
        }
    }
}