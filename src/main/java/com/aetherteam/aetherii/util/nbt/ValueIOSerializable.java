package com.aetherteam.aetherii.util.nbt;

public interface ValueIOSerializable {
    void serialize(ValueOutput valueOutput);

    void deserialize(ValueInput valueInput);
}
