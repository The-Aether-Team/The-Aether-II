package com.aetherteam.aetherii.util;

import net.minecraft.util.Mth;

public final class ARGB {
    private ARGB() {
    }

    public static int alpha(int color) {
        return color >>> 24;
    }

    public static int red(int color) {
        return color >> 16 & 0xFF;
    }

    public static int green(int color) {
        return color >> 8 & 0xFF;
    }

    public static int blue(int color) {
        return color & 0xFF;
    }

    public static float alphaFloat(int color) {
        return alpha(color) / 255.0F;
    }

    public static float redFloat(int color) {
        return red(color) / 255.0F;
    }

    public static float greenFloat(int color) {
        return green(color) / 255.0F;
    }

    public static float blueFloat(int color) {
        return blue(color) / 255.0F;
    }

    public static int opaque(int color) {
        return color | 0xFF000000;
    }

    public static int white(float alpha) {
        return colorFromFloat(alpha, 1.0F, 1.0F, 1.0F);
    }

    public static int color(int red, int green, int blue) {
        return color(255, red, green, blue);
    }

    public static int color(int alpha, int red, int green, int blue) {
        return clampChannel(alpha) << 24 | clampChannel(red) << 16 | clampChannel(green) << 8 | clampChannel(blue);
    }

    public static int colorFromFloat(float alpha, float red, float green, float blue) {
        return color(Math.round(alpha * 255.0F), Math.round(red * 255.0F), Math.round(green * 255.0F), Math.round(blue * 255.0F));
    }

    public static int srgbLerp(float delta, int start, int end) {
        return color(
                Mth.lerpInt(delta, alpha(start), alpha(end)),
                Mth.lerpInt(delta, red(start), red(end)),
                Mth.lerpInt(delta, green(start), green(end)),
                Mth.lerpInt(delta, blue(start), blue(end))
        );
    }

    public static int scaleRGB(int color, float scale) {
        return scaleRGB(color, scale, scale, scale);
    }

    public static int scaleRGB(int color, float redScale, float greenScale, float blueScale) {
        return color(alpha(color), Math.round(red(color) * redScale), Math.round(green(color) * greenScale), Math.round(blue(color) * blueScale));
    }

    public static int multiply(int first, int second) {
        return color(
                alpha(first) * alpha(second) / 255,
                red(first) * red(second) / 255,
                green(first) * green(second) / 255,
                blue(first) * blue(second) / 255
        );
    }

    public static int setBrightness(int color, float brightness) {
        int value = clampChannel(Math.round(Mth.clamp(brightness, 0.0F, 1.0F) * 255.0F));
        return color(alpha(color), value, value, value);
    }

    private static int clampChannel(int value) {
        return Mth.clamp(value, 0, 255);
    }
}
