package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.damage.IDamageLevelsHolder;
import com.gildedgames.aether.api.entity.damage.IDefenseLevelsHolder;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.init.ParticlesAether;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.items.weapons.swords.ItemAetherSword;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DamageSystem
{
    @SubscribeEvent
    public static void onLivingEntityHurt(LivingHurtEvent event)
    {
        EntityLivingBase target = event.getEntityLiving();

        if (target instanceof IDefenseLevelsHolder)
        {
            if (event.getSource().getImmediateSource() instanceof EntityPlayer)
            {
                EntityPlayer source = (EntityPlayer) event.getSource().getImmediateSource();

                ItemStack gloveStack = PlayerAether.getPlayer(source).getModule(PlayerEquipmentModule.class).getInventory().getStackInSlot(2);

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
                        float damageAmount = (float) (itemMainhand.getSlashDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue() + bonusSlash);
                        slashDamage = Math.max(damageAmount, 1.0F);
                    }
                    if (itemMainhand.getPierceDamageLevel() > 0)
                    {
                        float damageAmount = (float) (itemMainhand.getPierceDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue() + bonusPierce);
                        pierceDamage = Math.max(damageAmount, 1.0F);
                    }
                    if (itemMainhand.getImpactDamageLevel() > 0)
                    {
                        float damageAmount = (float) (itemMainhand.getImpactDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue() + bonusImpact);
                        impactDamage = Math.max(damageAmount, 1.0F);
                    }

                    float totalDamage = slashDamage + pierceDamage + impactDamage;

                    float cooldownTracker = PlayerAether.getPlayer(source).getCooldownTracker();

                    event.setAmount(totalDamage * cooldownTracker);

                    spawnParticles(target,
                            itemMainhand.getSlashDamageLevel(),
                            itemMainhand.getPierceDamageLevel(),
                            itemMainhand.getImpactDamageLevel(),
                            target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());

                    blockSound(target,
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
                        float damageAmount = (float) (itemOffhand.getSlashDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue() + bonusSlash);
                        slashDamage = Math.max(damageAmount, 1.0F);
                    }
                    if (itemOffhand.getPierceDamageLevel() > 0)
                    {
                        float damageAmount = (float) (itemOffhand.getPierceDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue() + bonusPierce);
                        pierceDamage = Math.max(damageAmount, 1.0F);
                    }
                    if (itemOffhand.getImpactDamageLevel() > 0)
                    {
                        float damageAmount = (float) (itemOffhand.getImpactDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue() + bonusImpact);
                        impactDamage = Math.max(damageAmount, 1.0F);
                    }

                    float totalDamage = slashDamage + pierceDamage + impactDamage;

                    event.setAmount(totalDamage);

                    spawnParticles(target,
                            itemOffhand.getSlashDamageLevel(),
                            itemOffhand.getPierceDamageLevel(),
                            itemOffhand.getImpactDamageLevel(),
                            target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());

                    blockSound(target,
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
                        float damageAmount = (float) (gloves.getSlashDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue() + bonusSlash);
                        slashDamage = Math.max(damageAmount, 1.0F);
                    }
                    if (gloves.getPierceDamageLevel() > 0)
                    {
                        float damageAmount = (float) (gloves.getPierceDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue() + bonusPierce);
                        pierceDamage = Math.max(damageAmount, 1.0F);
                    }
                    if (gloves.getImpactDamageLevel() > 0)
                    {
                        float damageAmount = (float) (gloves.getImpactDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue() + bonusImpact);
                        impactDamage = Math.max(damageAmount, 1.0F);
                    }

                    float totalDamage = slashDamage + pierceDamage + impactDamage;

                    event.setAmount(totalDamage);

                    spawnParticles(target,
                            gloves.getSlashDamageLevel(),
                            gloves.getPierceDamageLevel(),
                            gloves.getImpactDamageLevel(),
                            target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());

                    blockSound(target,
                            gloves.getSlashDamageLevel(),
                            gloves.getPierceDamageLevel(),
                            gloves.getImpactDamageLevel(),
                            target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                            target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());
                }
                else
                {
                    float damageAmount = Math.max(event.getAmount() - 2.0F, 1.0F);
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

                float totalDamage = slashDamage + pierceDamage + impactDamage;

                event.setAmount(totalDamage);

                spawnParticles(target,
                        entitySource.getSlashDamageLevel(),
                        entitySource.getPierceDamageLevel(),
                        entitySource.getImpactDamageLevel(),
                        target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                        target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                        target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());

                blockSound(target,
                        entitySource.getSlashDamageLevel(),
                        entitySource.getPierceDamageLevel(),
                        entitySource.getImpactDamageLevel(),
                        target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue(),
                        target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue(),
                        target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());
            }
            else
            {
                float damageAmount = Math.max(event.getAmount() - 2.0F, 1.0F);
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

                    event.setAmount(vanillaDamage);
                }
            }
        }
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
        }
    }

    public static void blockSound(EntityLivingBase target, double slashDamage, double pierceDamage, double impactDamage, double slashDefense, double pierceDefense, double impactDefense)
    {
        if ((slashDamage > 0 && slashDefense < 0.0F) || (pierceDamage > 0 && pierceDefense < 0.0F) || (impactDamage > 0 && impactDefense < 0.0F))
        {
            target.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 0.8F, 0.8F + target.world.rand.nextFloat() * 0.4F);
        }
    }
}