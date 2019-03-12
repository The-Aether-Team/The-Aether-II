package com.gildedgames.aether.common.entities.util;

import com.gildedgames.aether.common.entities.util.flying.advanced.FlightPath;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;

import javax.vecmath.Point3d;
import java.util.Optional;

public class AetherDataSerializers
{
	public static final DataSerializer<Optional<FlightPath>> PATH = new DataSerializer<Optional<FlightPath>>()
	{
		@Override
		public void write(PacketBuffer buf, Optional<FlightPath> value)
		{
			boolean exists = value.isPresent();

			buf.writeBoolean(exists);

			if (exists)
			{
				FlightPath path = value.get();

				buf.writeBlockPos(path.getStartPos());

				Point3d startHandle = path.getStartHandle();

				buf.writeDouble(startHandle.getX());
				buf.writeDouble(startHandle.getY());
				buf.writeDouble(startHandle.getZ());

				Point3d endHandle = path.getEndHandle();

				buf.writeDouble(endHandle.getX());
				buf.writeDouble(endHandle.getY());
				buf.writeDouble(endHandle.getZ());

				buf.writeBlockPos(path.getEndPos());
			}
		}

		@Override
		public Optional<FlightPath> read(PacketBuffer buf)
		{
			if (buf.readBoolean())
			{
				return Optional.of(new FlightPath(buf.readBlockPos(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readBlockPos()));
			}
			else
			{
				return Optional.empty();
			}
		}

		@Override
		public DataParameter<Optional<FlightPath>> createKey(int id)
		{
			return new DataParameter<>(id, this);
		}

		@Override
		public Optional<FlightPath> copyValue(Optional<FlightPath> value)
		{
			return value;
		}
	};

	public static final DataSerializer<Optional<FloatList>> FLOAT_LIST = new DataSerializer<Optional<FloatList>>()
	{
		@Override
		public void write(PacketBuffer buf, Optional<FloatList> value)
		{
			if (value.isPresent())
			{
				FloatList stack = value.get();

				buf.writeInt(stack.size());

				for (Float val : stack)
				{
					buf.writeFloat(val);
				}
			}
			else
			{
				buf.writeInt(0);
			}
		}

		@Override
		public Optional<FloatList> read(PacketBuffer buf)
		{
			int size = buf.readInt();

			if (size == 0)
			{
				return Optional.empty();
			}
			else
			{
				FloatList stack = new FloatArrayList();

				for (int i = 0; i < size; i++)
				{
					stack.add(buf.readFloat());
				}

				return Optional.of(stack);
			}
		}

		@Override
		public DataParameter<Optional<FloatList>> createKey(int id)
		{
			return new DataParameter<>(id, this);
		}

		@Override
		public Optional<FloatList> copyValue(Optional<FloatList> value)
		{
			return value;
		}
	};

	public static void registerSerializers()
	{
		DataSerializers.registerSerializer(PATH);
		DataSerializers.registerSerializer(FLOAT_LIST);
	}
}
