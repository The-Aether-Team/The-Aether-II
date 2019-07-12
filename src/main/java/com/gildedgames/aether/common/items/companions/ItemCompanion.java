package com.gildedgames.aether.common.items.companions;

import com.gildedgames.aether.common.items.InformationProvider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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

	public ItemCompanion()
	{
		this.setMaxStackSize(1);
		//this.setCreativeTab(CreativeTabsAether.COMPANIONS);
	}

	public ItemCompanion(final InformationProvider informationProvider)
	{
		this();

		this.informationProvider = informationProvider;
	}

	public static void setRespawnTimer(final ItemStack stack, final World world, final int timer)
	{
		CompoundNBT compound = stack.getTagCompound();

		if (compound == null)
		{
			stack.setTagCompound(compound = new CompoundNBT());
		}

		compound.putLong("respawnTimer", world.getTotalWorldTime() + timer);
	}

	public static long getTicksUntilRespawn(final ItemStack stack, final World world)
	{
		final CompoundNBT compound = stack.getTagCompound();

		if (compound == null || !compound.contains("respawnTimer"))
		{
			return 0;
		}

		return compound.getLong("respawnTimer") - world.getTotalWorldTime();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn)
	{
		final long respawn = ItemCompanion.getTicksUntilRespawn(stack, worldIn);

		if (respawn > 0)
		{
			tooltip.add(TextFormatting.RED + "" + TextFormatting.ITALIC + "Disabled! Respawns in " + this.parseTicks(respawn) + ".");
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
