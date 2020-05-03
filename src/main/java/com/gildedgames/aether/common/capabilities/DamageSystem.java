package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.damage.IDamageLevelsHolder;
import com.gildedgames.aether.api.entity.damage.IDefenseLevelsHolder;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEquipmentModule;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.items.armor.ItemAetherGloves;
import com.gildedgames.aether.common.items.weapons.swords.ItemAetherSword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
                        slashDamage = (float) (itemMainhand.getSlashDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue() + bonusSlash);
                    }
                    if (itemMainhand.getPierceDamageLevel() > 0)
                    {
                        pierceDamage = (float) (itemMainhand.getPierceDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue() + bonusPierce);
                    }
                    if (itemMainhand.getImpactDamageLevel() > 0)
                    {
                        impactDamage = (float) (itemMainhand.getImpactDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue() + bonusImpact);
                    }

                    float totalDamage = slashDamage + pierceDamage + impactDamage;

                    float cooldownTracker = PlayerAether.getPlayer(source).getCooldownTracker();

                    event.setAmount(totalDamage * cooldownTracker);
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
                        slashDamage = (float) (itemOffhand.getSlashDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue() + bonusSlash);
                    }
                    if (itemOffhand.getPierceDamageLevel() > 0)
                    {
                        pierceDamage = (float) (itemOffhand.getPierceDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue() + bonusPierce);
                    }
                    if (itemOffhand.getImpactDamageLevel() > 0)
                    {
                        impactDamage = (float) (itemOffhand.getImpactDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue() + bonusImpact);
                    }

                    float totalDamage = slashDamage + pierceDamage + impactDamage;

                    event.setAmount(totalDamage);
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
                        slashDamage = (float) (gloves.getSlashDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue() + bonusSlash);
                    }
                    if (gloves.getPierceDamageLevel() > 0)
                    {
                        pierceDamage = (float) (gloves.getPierceDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue() + bonusPierce);
                    }
                    if (gloves.getImpactDamageLevel() > 0)
                    {
                        impactDamage = (float) (gloves.getImpactDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue() + bonusImpact);
                    }

                    float totalDamage = slashDamage + pierceDamage + impactDamage;

                    event.setAmount(totalDamage);
                }
                else if (event.getAmount() < 2.0F)
                {
                    event.setAmount(event.getAmount() - 2.0F);
                }
            }
            else if (event.getSource().getImmediateSource() instanceof IDamageLevelsHolder)
            {
                IDamageLevelsHolder entitySource = (IDamageLevelsHolder) event.getSource().getImmediateSource();

                float slashDamage = 0, pierceDamage = 0, impactDamage = 0;

                if (entitySource.getSlashDamageLevel() > 0)
                {
                    slashDamage = (float) (entitySource.getSlashDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue());
                }
                if (entitySource.getPierceDamageLevel() > 0)
                {
                    pierceDamage = (float) (entitySource.getPierceDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue());
                }
                if (entitySource.getImpactDamageLevel() > 0)
                {
                    impactDamage = (float) (entitySource.getImpactDamageLevel() + target.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue());
                }

                float totalDamage = slashDamage + pierceDamage + impactDamage;

                event.setAmount(totalDamage);
            }
            else if (event.getAmount() < 2.0F)
            {
                event.setAmount(event.getAmount() - 2.0F);
            }
        }

        System.out.println(event.getAmount());
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

                    System.out.println(event.getAmount());
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
}


    /*
		if (slashDamageLevel > 0)
		{
			NetworkingAether.sendPacketToDimension(new PacketParticles(ParticlesAether.SLASH, x, y, z, offsetX, 0.0D, offsetZ), entitySource.dimension);
		}

		if (pierceDamageLevel > 0)
		{
			NetworkingAether
					.sendPacketToDimension(new PacketParticles(ParticlesAether.PIERCE, x, y, z, offsetX, 0.0D, offsetZ), entitySource.dimension);
		}

		if (impactDamageLevel > 0)
		{
			NetworkingAether
					.sendPacketToDimension(new PacketParticles(ParticlesAether.IMPACT, x, y, z, offsetX, 0.0D, offsetZ), entitySource.dimension);
		}
	}
	 */