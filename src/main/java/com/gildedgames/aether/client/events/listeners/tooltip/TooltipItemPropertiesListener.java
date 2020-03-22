package com.gildedgames.aether.client.events.listeners.tooltip;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.items.equipment.ItemEquipmentSlot;
import com.gildedgames.aether.api.items.equipment.effects.EffectInstance;
import com.gildedgames.aether.api.items.equipment.effects.IEffectFactory;
import com.gildedgames.aether.api.items.equipment.effects.IEffectPool;
import com.gildedgames.aether.api.items.equipment.effects.IEffectProvider;
import com.gildedgames.aether.api.items.properties.IItemProperties;
import com.gildedgames.aether.api.items.properties.ItemRarity;
import com.gildedgames.aether.client.gui.util.ToolTipCurrencyHelper;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.aether.common.shop.ShopCurrencyGilt;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

@Mod.EventBusSubscriber(Side.CLIENT)
public class TooltipItemPropertiesListener
{
	private static final ToolTipCurrencyHelper toolTipHelper = new ToolTipCurrencyHelper();

	@SubscribeEvent
	@SuppressWarnings("unchecked")
	public static void onTooltipConstruction(final ItemTooltipEvent event)
	{
		final IItemProperties properties = AetherAPI.content().items().getProperties(event.getItemStack().getItem());

		final double value = AetherAPI.content().currency().getValue(event.getItemStack(), ShopCurrencyGilt.class);

		// Armor Effects
		if (event.getItemStack().getItem() instanceof ItemAetherArmor)
		{
			ItemAetherArmor aetherArmor = (ItemAetherArmor) event.getItemStack().getItem();

			event.getToolTip().removeIf(tooltip -> tooltip.contains("Slash") || tooltip.contains("Pierce") || tooltip.contains("Impact"));

			if (aetherArmor.getImpactLevel() > 0)
			{
				event.getToolTip().add(TextFormatting.BLUE + " +" + aetherArmor.getImpactLevel() + " " + I18n.format("attribute.name.aether.impactDefenseLevel"));
			}
			if (aetherArmor.getPierceLevel() > 0)
			{
				event.getToolTip().add(TextFormatting.BLUE + " +" + aetherArmor.getPierceLevel() + " " + I18n.format("attribute.name.aether.pierceDefenseLevel"));
			}
			if (aetherArmor.getSlashLevel() > 0)
			{
				event.getToolTip().add(TextFormatting.BLUE + " +" + aetherArmor.getSlashLevel() + " " + I18n.format("attribute.name.aether.slashDefenseLevel"));
			}
		}

		// Equipment Effects
		for (final IEffectProvider provider : properties.getEffectProviders())
		{
			final IEffectFactory<IEffectProvider> factory = AetherAPI.content().effects().getFactory(provider.getFactory());

			final EffectPoolTemporary pool = new EffectPoolTemporary(event.getItemStack(), provider);

			TextFormatting textFormatting1 = TextFormatting.BLUE;
			TextFormatting textFormatting2 = TextFormatting.RED;

			if (properties.getEquipmentSlot() == ItemEquipmentSlot.NONE)
			{
				textFormatting1 = TextFormatting.GRAY;
				textFormatting2 = TextFormatting.GRAY;
			}

			factory.createInstance(pool).addInformation(event.getToolTip(), textFormatting1, textFormatting2);
		}

		// Slot Type
		if (properties.getEquipmentSlot() != ItemEquipmentSlot.NONE)
		{
			final ItemEquipmentSlot slot = properties.getEquipmentSlot();

			event.getToolTip().add("");
			event.getToolTip().add(I18n.format(slot.getUnlocalizedName()));
		}

		// Rarity
		if (properties.getRarity() != ItemRarity.NONE)
		{
			event.getToolTip().add(I18n.format(properties.getRarity().getUnlocalizedName()));
		}

		//Currency
		if (value != 0)
		{
			event.getToolTip().addAll(toolTipHelper.getText(value));
		}
	}

	@SubscribeEvent
	public static void onToolTipRender(final RenderTooltipEvent.PostText event)
	{
		final double value = AetherAPI.content().currency().getValue(event.getStack(), ShopCurrencyGilt.class);

		toolTipHelper.render(event.getFontRenderer(), event.getX(), event.getY(), event.getHeight(), value);
	}

	private static class EffectPoolTemporary<T extends IEffectProvider> implements IEffectPool<T>
	{
		private final ItemStack stack;

		private final T provider;

		public EffectPoolTemporary(final ItemStack stack, final T provider)
		{
			this.stack = stack;
			this.provider = provider;
		}

		@Override
		public ItemStack getProvider(final IEffectProvider instance)
		{
			return this.provider == instance ? this.stack : ItemStack.EMPTY;
		}

		@Override
		public Collection<T> getActiveProviders()
		{
			return Collections.singleton(this.provider);
		}

		@Override
		public Optional<EffectInstance> getInstance()
		{
			return Optional.empty();
		}

		@Override
		public boolean isEmpty()
		{
			return false;
		}
	}
}
