package com.gildedgames.aether.api.damage_system;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class DamageSystemTables
{
	private static List<LevelRange> LEVEL_RANGES;

	private static List<LevelRange> getLevelRangeToValue()
	{
		if (LEVEL_RANGES == null)
		{
			LEVEL_RANGES = Lists.newArrayList();

			LEVEL_RANGES.add(new LevelRange(1, 4, 1));
			LEVEL_RANGES.add(new LevelRange(5, 8, 2));
			LEVEL_RANGES.add(new LevelRange(9, 12, 3));
			LEVEL_RANGES.add(new LevelRange(13, 16, 4));
			LEVEL_RANGES.add(new LevelRange(17, 20, 5));
			LEVEL_RANGES.add(new LevelRange(21, 24, 6));
			LEVEL_RANGES.add(new LevelRange(25, 28, 7));
			LEVEL_RANGES.add(new LevelRange(29, 32, 8));
			LEVEL_RANGES.add(new LevelRange(33, 36, 9));
			LEVEL_RANGES.add(new LevelRange(37, 40, 10));
		}

		return LEVEL_RANGES;
	}

	public static int getValueFromLevelRange(double level)
	{
		if (level <= 0)
		{
			return 0;
		}

		for (LevelRange range : getLevelRangeToValue())
		{
			if (level >= range.getFrom() && level <= range.getTo())
			{
				return range.getValue();
			}
		}

		return 0;
	}

	public static class LevelRange
	{
		private int from, to, value;

		public LevelRange(int from, int to, int value)
		{
			if (from > to)
			{
				throw new IllegalArgumentException("From is greater than to on LevelRange. Not possible");
			}

			this.from = from;
			this.to = to;
			this.value = value;
		}

		public int getFrom()
		{
			return this.from;
		}

		public int getTo()
		{
			return this.to;
		}

		public int getValue()
		{
			return this.value;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (obj == this)
			{
				return true;
			}

			if (!(obj instanceof LevelRange))
			{
				return false;
			}

			LevelRange other = (LevelRange) obj;

			return new EqualsBuilder().append(this.from, other.from).append(this.to, other.to).append(this.value, other.value).isEquals();
		}

		@Override
		public int hashCode()
		{
			return new HashCodeBuilder().append(this.from).append(this.to).append(this.value).toHashCode();
		}
	}
}
