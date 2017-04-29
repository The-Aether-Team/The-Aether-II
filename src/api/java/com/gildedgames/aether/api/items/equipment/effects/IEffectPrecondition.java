package com.gildedgames.aether.api.items.equipment.effects;

import com.gildedgames.aether.api.player.IPlayerAether;
import net.minecraft.item.ItemStack;

public interface IEffectPrecondition
{
	/**
	 * Returns whether or not this effect can be applied to the entity. If this changes
	 * while the effect is currently applied, the equipment system will re-evaluate the
	 * entity's effects.
	 *
	 * @param player The {@link IPlayerAether} this provider will be applied to if the
	 *               result is true.
	 *
	 * @param stack The {@link ItemStack} this provider belongs to, ItemStack.EMPTY if none. This
	 *              will typically be non-empty unless the effect is being applied from
	 *              another source by force. You should take care to handle an empty stack.
	 *
	 * @return True if this provider can provide an effect to {@param entity}, otherwise false.
	 */
	boolean canApply(IPlayerAether player, ItemStack stack);
}
