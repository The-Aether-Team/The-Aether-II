package com.aetherteam.aetherii.client.renderer.level;

import com.aetherteam.aetherii.client.renderer.AetherIIDimensionRenderers;

public class HolyIslesSkyboxRenderer {
    public boolean isSunriseOrSunset(float timeOfDay) {
        return AetherIIDimensionRenderers.isSunriseOrSunset(timeOfDay);
    }

    public int getSunriseOrSunsetColor(float timeOfDay) {
        return AetherIIDimensionRenderers.getSunriseOrSunsetColor(timeOfDay);
    }
}
