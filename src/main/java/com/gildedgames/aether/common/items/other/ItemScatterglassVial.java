package com.gildedgames.aether.common.items.other;

import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Objects;

public class ItemScatterglassVial extends Item
{
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        RayTraceResult rayTraceResult = this.rayTrace(world, player, true);

        if (rayTraceResult != null)
        {
             if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
             {
                 BlockPos blockpos = rayTraceResult.getBlockPos();

                 if (!world.isBlockModifiable(player, blockpos) || !player.canPlayerEdit(blockpos.offset(rayTraceResult.sideHit), rayTraceResult.sideHit, stack))
                 {
                     return new ActionResult<>(EnumActionResult.PASS, stack);
                 }

                 if (world.getBlockState(blockpos).getMaterial() == Material.WATER)
                 {
                     world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                     return new ActionResult<>(EnumActionResult.SUCCESS, this.turnVialIntoItem(stack, player, new ItemStack(ItemsAether.water_vial)));
                 }
             }
        }

        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    protected ItemStack turnVialIntoItem(ItemStack inputStack, EntityPlayer player, ItemStack stack)
    {
        inputStack.shrink(1);
        player.addStat(Objects.requireNonNull(StatList.getObjectUseStats(this)));

        if (inputStack.isEmpty())
        {
            return stack;
        }
        else
        {
            if (!player.inventory.addItemStackToInventory(stack))
            {
                player.dropItem(stack, false);
            }

            return inputStack;
        }
    }
}
