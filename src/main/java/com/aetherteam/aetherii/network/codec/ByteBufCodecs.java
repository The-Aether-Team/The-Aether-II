package com.aetherteam.aetherii.network.codec;

import com.aetherteam.aetherii.client.ClientNetworkUtil;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

public final class ByteBufCodecs {
    public static final StreamCodec<ByteBuf, Boolean> BOOL = StreamCodec.of((buffer, value) -> buffer.writeBoolean(value), ByteBuf::readBoolean);
    public static final StreamCodec<ByteBuf, Integer> INT = StreamCodec.of((buffer, value) -> buffer.writeInt(value), ByteBuf::readInt);
    public static final StreamCodec<ByteBuf, Integer> VAR_INT = StreamCodec.of((buffer, value) -> friendly(buffer).writeVarInt(value), buffer -> friendly(buffer).readVarInt());
    public static final StreamCodec<ByteBuf, Float> FLOAT = StreamCodec.of((buffer, value) -> buffer.writeFloat(value), ByteBuf::readFloat);
    public static final StreamCodec<ByteBuf, Double> DOUBLE = StreamCodec.of((buffer, value) -> buffer.writeDouble(value), ByteBuf::readDouble);
    public static final StreamCodec<ByteBuf, String> STRING_UTF8 = StreamCodec.of((buffer, value) -> friendly(buffer).writeUtf(value), buffer -> friendly(buffer).readUtf());
    public static final StreamCodec<ByteBuf, ResourceLocation> RESOURCE_LOCATION = StreamCodec.of((buffer, value) -> friendly(buffer).writeResourceLocation(value), buffer -> friendly(buffer).readResourceLocation());
    public static final StreamCodec<ByteBuf, BlockPos> BLOCK_POS = StreamCodec.of((buffer, value) -> friendly(buffer).writeBlockPos(value), buffer -> friendly(buffer).readBlockPos());
    public static final StreamCodec<ByteBuf, Direction> DIRECTION = StreamCodec.of((buffer, value) -> friendly(buffer).writeEnum(value), buffer -> friendly(buffer).readEnum(Direction.class));
    public static final StreamCodec<ByteBuf, UUID> UUID = StreamCodec.of((buffer, value) -> friendly(buffer).writeUUID(value), buffer -> friendly(buffer).readUUID());
    public static final StreamCodec<ByteBuf, Vec3> VEC3 = StreamCodec.of((buffer, value) -> {
        buffer.writeDouble(value.x());
        buffer.writeDouble(value.y());
        buffer.writeDouble(value.z());
    }, buffer -> new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
    public static final StreamCodec<ByteBuf, CompoundTag> COMPOUND_TAG = StreamCodec.of((buffer, value) -> friendly(buffer).writeNbt(value), buffer -> {
        CompoundTag tag = friendly(buffer).readNbt();
        return tag == null ? new CompoundTag() : tag;
    });
    public static final StreamCodec<ByteBuf, Vector3fc> VECTOR3F = StreamCodec.of((buffer, value) -> {
        buffer.writeFloat(value.x());
        buffer.writeFloat(value.y());
        buffer.writeFloat(value.z());
    }, buffer -> new Vector3f(buffer.readFloat(), buffer.readFloat(), buffer.readFloat()));

    private ByteBufCodecs() {
    }

    public static <T> StreamCodec<ByteBuf, T> idMapper(IntFunction<T> byId, ToIntFunction<T> idGetter) {
        return StreamCodec.of((buffer, value) -> friendly(buffer).writeVarInt(idGetter.applyAsInt(value)), buffer -> byId.apply(friendly(buffer).readVarInt()));
    }

    public static <B, T> StreamCodec<B, Optional<T>> optional(StreamCodec<? super B, T> codec) {
        return StreamCodec.of((buffer, value) -> {
            if (value.isPresent()) {
                BOOL.encode(asByteBuf(buffer), true);
                codec.encode(buffer, value.get());
            } else {
                BOOL.encode(asByteBuf(buffer), false);
            }
        }, buffer -> BOOL.decode(asByteBuf(buffer)) ? Optional.of(codec.decode(buffer)) : Optional.empty());
    }

    public static <B, T> java.util.function.Function<StreamCodec<B, T>, StreamCodec<B, List<T>>> list() {
        return list(Integer.MAX_VALUE);
    }

    public static <B, T> java.util.function.Function<StreamCodec<B, T>, StreamCodec<B, List<T>>> list(int maxSize) {
        return codec -> StreamCodec.of((buffer, value) -> {
            friendly(asByteBuf(buffer)).writeVarInt(value.size());
            for (T entry : value) {
                codec.encode(buffer, entry);
            }
        }, buffer -> {
            int size = friendly(asByteBuf(buffer)).readVarInt();
            if (size > maxSize) {
                throw new IllegalArgumentException("List size " + size + " exceeds " + maxSize);
            }
            return IntStream.range(0, size).mapToObj(i -> codec.decode(buffer)).toList();
        });
    }

    public static <B, K, V, M extends Map<K, V>> StreamCodec<B, M> map(Supplier<M> factory, StreamCodec<? super B, K> keyCodec, StreamCodec<? super B, V> valueCodec) {
        return StreamCodec.of((buffer, value) -> {
            friendly(asByteBuf(buffer)).writeVarInt(value.size());
            value.forEach((key, object) -> {
                keyCodec.encode(buffer, key);
                valueCodec.encode(buffer, object);
            });
        }, buffer -> {
            M map = factory.get();
            int size = friendly(asByteBuf(buffer)).readVarInt();
            for (int i = 0; i < size; i++) {
                map.put(keyCodec.decode(buffer), valueCodec.decode(buffer));
            }
            return map;
        });
    }

    public static <T> StreamCodec<FriendlyByteBuf, T> registry(ResourceKey<? extends Registry<T>> registryKey) {
        return StreamCodec.of((buffer, value) -> {
            Registry<T> registry = lookupRegistry(registryKey);
            ResourceLocation key = registry.getKey(value);
            if (key == null) {
                throw new IllegalArgumentException("Unknown registry value in " + registryKey.location() + ": " + value);
            }
            buffer.writeResourceLocation(key);
        }, buffer -> {
            ResourceLocation key = buffer.readResourceLocation();
            T value = lookupRegistry(registryKey).get(key);
            if (value == null) {
                throw new IllegalArgumentException("Unknown registry key in " + registryKey.location() + ": " + key);
            }
            return value;
        });
    }

    public static <T> StreamCodec<FriendlyByteBuf, Holder<T>> holderRegistry(ResourceKey<? extends Registry<T>> registryKey) {
        return StreamCodec.of((buffer, value) -> {
            ResourceLocation key = value.unwrapKey()
                    .map(ResourceKey::location)
                    .orElseGet(() -> {
                        Registry<T> registry = lookupRegistry(registryKey);
                        ResourceLocation directKey = registry.getKey(value.value());
                        if (directKey == null) {
                            throw new IllegalArgumentException("Unknown registry holder in " + registryKey.location() + ": " + value);
                        }
                        return directKey;
                    });
            buffer.writeResourceLocation(key);
        }, buffer -> lookupRegistry(registryKey).getHolderOrThrow(ResourceKey.create(registryKey, buffer.readResourceLocation())));
    }

    public static <B, T> StreamCodec<B, Holder<T>> holder(ResourceKey<? extends Registry<T>> registryKey, StreamCodec<B, T> directCodec) {
        return directCodec.map(Holder::direct, Holder::value);
    }

    public static <T> StreamCodec<FriendlyByteBuf, T> fromCodecWithRegistries(Codec<T> codec) {
        return fromCodec(codec);
    }

    public static <T> StreamCodec<FriendlyByteBuf, T> fromCodec(Codec<T> codec) {
        return StreamCodec.of((buffer, value) -> {
            Tag tag = codec.encodeStart(NbtOps.INSTANCE, value).getOrThrow(false, ignored -> {
            });
            CompoundTag wrapper = new CompoundTag();
            wrapper.put("value", tag);
            buffer.writeNbt(wrapper);
        }, buffer -> {
            CompoundTag wrapper = buffer.readNbt();
            if (wrapper == null || !wrapper.contains("value")) {
                throw new IllegalArgumentException("Missing encoded codec value");
            }
            return codec.parse(NbtOps.INSTANCE, wrapper.get("value")).getOrThrow(false, ignored -> {
            });
        });
    }

    @SuppressWarnings("unchecked")
    private static <T> Registry<T> lookupRegistry(ResourceKey<? extends Registry<T>> registryKey) {
        Registry<?> registry = BuiltInRegistries.REGISTRY.get(registryKey.location());
        if (registry != null) {
            return (Registry<T>) registry;
        }

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            return server.registryAccess().registry(registryKey)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown registry: " + registryKey.location()));
        }

        Player player = ClientNetworkUtil.getPlayer();
        if (player != null) {
            return player.level().registryAccess().registry(registryKey)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown registry: " + registryKey.location()));
        }

        throw new IllegalArgumentException("Unknown registry: " + registryKey.location());
    }

    private static FriendlyByteBuf friendly(ByteBuf buffer) {
        return buffer instanceof FriendlyByteBuf friendly ? friendly : new FriendlyByteBuf(buffer);
    }

    private static ByteBuf asByteBuf(Object buffer) {
        if (buffer instanceof ByteBuf byteBuf) {
            return byteBuf;
        }
        throw new IllegalArgumentException("Expected ByteBuf, got " + buffer.getClass().getName());
    }
}
