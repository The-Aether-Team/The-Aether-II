package com.aetherteam.aetherii.data.generators;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.data.generators.models.AetherIIBlockModels;
import com.aetherteam.aetherii.data.generators.models.AetherIIItemModels;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class AetherIIModelData extends ModelProvider {
    private final PackOutput.PathProvider blockStatePathProvider;
    private final PackOutput.PathProvider itemInfoPathProvider;
    private final PackOutput.PathProvider modelPathProvider;

    public AetherIIModelData(PackOutput packOutput) {
        super(packOutput, AetherII.MODID);
        this.blockStatePathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.itemInfoPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
        this.modelPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        ItemInfoCollector itemModelOutput = new ItemInfoCollector(this::getKnownItems);
        BlockStateGeneratorCollector blockModelOutput = new BlockStateGeneratorCollector(this::getKnownBlocks);
        SimpleModelCollector modelOutput = new SimpleModelCollector();
        this.registerModels(new AetherIIBlockModels(blockModelOutput, itemModelOutput, modelOutput), new AetherIIItemModels(itemModelOutput, modelOutput));
        blockModelOutput.validate();
        itemModelOutput.finalizeAndValidate();
        return CompletableFuture.allOf(blockModelOutput.save(output, this.blockStatePathProvider), modelOutput.save(output, this.modelPathProvider), itemModelOutput.save(output, this.itemInfoPathProvider));
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.listElements().filter((holder) -> holder.getKey().identifier().getNamespace().equals(this.modId) && !(holder.value() instanceof LiquidBlock));
    }
}
