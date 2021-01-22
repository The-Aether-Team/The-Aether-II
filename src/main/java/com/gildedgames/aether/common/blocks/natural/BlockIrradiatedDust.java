package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.blocks.IBlockRadiation;
import com.gildedgames.aether.common.util.selectors.ItemEntry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class BlockIrradiatedDust extends Block implements IBlockRadiation
{
    private int radiationDistance, radiationAmount, radiationRate;

    private final List<ItemEntry> drops = new ArrayList<>();

    public BlockIrradiatedDust()
    {
        super(Material.ROCK);

        this.setHardness(1.0f);
        this.setResistance(5.0f);
        this.setHarvestLevel("pickaxe", 2);
        this.setLightLevel(0.4f);
        this.setTickRandomly(true);

        this.useNeighborBrightness = true;

        this.setSoundType(SoundType.SAND);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        worldIn.scheduleUpdate(pos, worldIn.getBlockState(pos).getBlock(), 1);
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
    {
    }

    @Override
    public void updateTick(final World world, final BlockPos pos, final IBlockState state, final Random rand)
    {
        this.tickRadiation(world, pos, this.getRadiationDistance(), this.getRadiationAmount(), this.getRadiationRate());

        world.scheduleUpdate(pos, world.getBlockState(pos).getBlock(), 1);
    }

    @Override
    public BlockIrradiatedDust setRadiationDistance(int distance)
    {
        this.radiationDistance = distance;
        return this;
    }

    @Override
    public int getRadiationDistance()
    {
        return this.radiationDistance;
    }

    @Override
    public BlockIrradiatedDust setRadiationAmount(int amount)
    {
        this.radiationAmount = amount;
        return this;
    }

    @Override
    public int getRadiationAmount()
    {
        return this.radiationAmount;
    }

    @Override
    public Block setRadiationRate(int rate)
    {
        this.radiationRate = rate;
        return this;
    }

    @Override
    public int getRadiationRate()
    {
        return this.radiationRate;
    }

    private void tickRadiation(final World world, final BlockPos pos, int radiationAmount, int radiationDistance, int radiationRate)
    {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1)).grow(radiationDistance);
        List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);

        for (EntityPlayer player : list)
        {
            IAetherStatusEffectPool statusEffectPool = player.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

            if (!player.isCreative())
            {
                boolean tick = world.getTotalWorldTime() % radiationRate == 0;

                if (tick)
                {
                    if (statusEffectPool != null)
                    {
                        if (!statusEffectPool.effectExists(IAetherStatusEffects.effectTypes.AMBROSIUM_POISONING))
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

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        this.initializeDrops();

        Random rand = new Random();
        ItemEntry itemEntry = WeightedRandom.getRandomItem(rand, this.drops);
        if (itemEntry.getItem() != null)
        {
            int dropCount = 2;

            if (itemEntry.getItem() == ItemsAether.irradiated_dust)
            {
                //2-4 Irradiated Dust
                dropCount = dropCount + rand.nextInt(3);
            }

            drops.add(new ItemStack(itemEntry.getItem(), dropCount));
        }
    }

    private void initializeDrops()
    {
        this.drops.add(new ItemEntry(ItemsAether.irradiated_dust, 50));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_tool, 20));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_sword, 20));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_armor, 20));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_chunk, 15));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_charm, 10));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_ring, 7));
        this.drops.add(new ItemEntry(ItemsAether.irradiated_neckwear, 5));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        if (world instanceof ChunkCache)
        {
            return 0;
        }

        return super.getLightValue(state, world, pos);
    }
}
