package com.gildedgames.aether.common.blocks.util;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockRadiationHandler
{
    public static void tickRadiation(final World world, final BlockPos pos, int radiationDistance, int radiationAmount)
    {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1)).grow(radiationDistance);
        List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);

        for (EntityPlayer entityplayer : list)
        {
            inflictRadiation(entityplayer, entityplayer.world, radiationAmount);
        }
    }

    public static void inflictRadiation(EntityPlayer player, World world, int radiationAmount)
    {
        IAetherStatusEffectPool statusEffectPool = player.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

        if (!player.isCreative())
        {
            if (statusEffectPool != null)
            {
                boolean tick = world.getTotalWorldTime() % 2 == 0;

                if (tick)
                {
                    if (!statusEffectPool.isEffectApplied(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING))
                    {
                        statusEffectPool.applyStatusEffect(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING, radiationAmount);
                    }
                    else
                    {
                        statusEffectPool.modifyActiveEffectBuildup(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING,
                                statusEffectPool.getBuildupFromEffect(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING) + radiationAmount);
                    }
                }
            }
        }
    }
}
