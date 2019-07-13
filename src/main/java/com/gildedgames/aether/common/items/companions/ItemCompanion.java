package com.gildedgames.aether.common.items.companions;

import com.gildedgames.aether.common.items.InformationProvider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.List;

public class ItemCompanion extends Item
{
	private final DecimalFormat timeFormat = new DecimalFormat("0.0");

	private InformationProvider informationProvider;

	public ItemCompanion(Item.Properties properties)
	{
		this(properties, null);
	}

	public ItemCompanion(Properties properties, final InformationProvider informationProvider)
	{
		super(properties);

		this.informationProvider = informationProvider;
	}

	public static void setRespawnTimer(final ItemStack stack, final World world, final int timer)
	{
		CompoundNBT compound = stack.getTag();

		if (compound == null)
		{
			stack.setTag(compound = new CompoundNBT());
		}

		compound.putLong("respawnTimer", world.getGameTime() + timer);
	}

	public static long getTicksUntilRespawn(final ItemStack stack, final World world)
	{
		final CompoundNBT compound = stack.getTag();

		if (compound == null || !compound.contains("respawnTimer"))
		{
			return 0;
		}

		return compound.getLong("respawnTimer") - world.getGameTime();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<ITextComponent> tooltip, final ITooltipFlag flagIn)
	{
		final long respawn = ItemCompanion.getTicksUntilRespawn(stack, worldIn);

		if (respawn > 0)
		{
			tooltip.add(new StringTextComponent("Disabled! Respawns in " + this.parseTicks(respawn) + ".").setStyle(new Style().setColor(TextFormatting.RED).setItalic(true)));
		}

		if (this.informationProvider != null)
		{
			this.informationProvider.addInformation(stack, tooltip, flagIn);
		}

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	protected String parseTicks(final long ticks)
	{
		final boolean useMinutes = ticks > (20 * 60);

		return this.timeFormat.format(ticks / (useMinutes ? 20f * 60 : 20f)) + " " + (useMinutes ? "minutes" : "seconds");
	}
}
