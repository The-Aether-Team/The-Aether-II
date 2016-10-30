package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.api.capabilites.items.properties.TemperatureProperties;
import com.gildedgames.aether.common.entities.living.mounts.EntityMoa;
import com.gildedgames.aether.common.entities.util.AnimalGender;
import com.gildedgames.aether.common.entities.util.MoaNest;
import com.gildedgames.aether.common.entities.genes.moa.MoaGenePool;
import com.gildedgames.aether.common.items.ItemIrradiated;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.misc.ItemMoaEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class TemperatureHandler implements TemperatureProperties
{

    @Override
    public int getTemperature(ItemStack stack)
    {
        if (stack != null)
        {
            if (stack.getItem() == ItemsAether.icestone)
            {
                return -3;
            }
            else if (stack.getItem() == ItemsAether.irradiated_dust)
            {
                return 1;
            }
            else if (stack.getItem() == ItemsAether.ambrosium_chunk)
            {
                return 1;
            }
        }

        return 0;
    }

    @Override
    public int getTemperatureThreshold(ItemStack stack)
    {
        if (stack != null)
        {
            if (stack.getItem() instanceof ItemIrradiated)
            {
                return -50000;
            }
            else if (stack.getItem() == ItemsAether.moa_egg)
            {
                return 100000;
            }
        }

        return 0;
    }

    @Override
    public ItemStack getResultWhenCooled(World world, BlockPos pos, ItemStack stack, Random rand)
    {
        if (stack != null)
        {
            if (stack.getItem() instanceof ItemIrradiated)
            {
                ItemIrradiated irradiated = (ItemIrradiated)stack.getItem();

                return irradiated.getItemSelector().create(rand);
            }
        }

        return stack;
    }

    @Override
    public ItemStack getResultWhenHeated(World world, BlockPos pos, ItemStack stack, Random rand)
    {
        if (stack != null)
        {
            if (stack.getItem() == ItemsAether.moa_egg)
            {
                if (!world.isRemote)
                {
                    MoaGenePool genes = ItemMoaEgg.getGenePool(stack);

                    MoaNest familyNest = new MoaNest(world);

                    EntityMoa babyMoa = new EntityMoa(world, familyNest, genes.getStorage().getFatherSeed(), genes.getStorage().getMotherSeed());

                    babyMoa.setGrowingAge(-24000);
                    babyMoa.setPosition(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
                    babyMoa.setGender(rand.nextBoolean() ? AnimalGender.FEMALE : AnimalGender.MALE);
                    babyMoa.setRaisedByPlayer(true);
                    babyMoa.setAnimalPack(familyNest.getAnimalPack());
                    babyMoa.setIsHungry(true);
                    babyMoa.setFoodRequired(3);

                    world.spawnEntityInWorld(babyMoa);
                }
            }
        }

        return stack;
    }

    @Override
    public boolean shouldDecreaseStackSize(boolean heating)
    {
        if (heating)
        {
            return true;
        }

        return false;
    }

    @Override
    public String getCooledName(ItemStack stack)
    {
        if (stack != null)
        {
            if (stack.getItem() == ItemsAether.irradiated_chunk)
            {
                return "gui.aether.random_item";
            }

            if (stack.getItem() == ItemsAether.irradiated_sword)
            {
                return "gui.aether.random_sword";
            }

            if (stack.getItem() == ItemsAether.irradiated_armor)
            {
                return "gui.aether.random_armor";
            }

            if (stack.getItem() == ItemsAether.irradiated_tool)
            {
                return "gui.aether.random_tool";
            }

            if (stack.getItem() == ItemsAether.irradiated_ring)
            {
                return "gui.aether.random_ring";
            }

            if (stack.getItem() == ItemsAether.irradiated_neckwear)
            {
                return "gui.aether.random_neckwear";
            }

            if (stack.getItem() == ItemsAether.irradiated_charm)
            {
                return "gui.aether.random_charm";
            }
        }

        return null;
    }

    @Override
    public String getHeatedName(ItemStack stack)
    {
        if (stack != null)
        {
            if (stack.getItem() == ItemsAether.moa_egg)
            {
                return "gui.aether.baby_moa";
            }
        }

        return null;
    }

}
