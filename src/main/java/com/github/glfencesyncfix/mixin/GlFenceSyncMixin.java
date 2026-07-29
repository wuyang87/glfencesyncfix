package com.github.glfencesyncfix.mixin;

import org.lwjgl.opengl.GL33C;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.blaze3d.opengl.GlCommandEncoder;

@Mixin(GlCommandEncoder.class)
public class GlFenceSyncMixin {

    /**
     * Redirect GL33C.glFenceSync call in GlCommandEncoder#submit to a no-op.
     * Fixes the ~50-60% FPS regression introduced in Minecraft 26.2 Pre-release 1.
     *
     * @see <a href="https://mojira.dev/MC-309939">MC-309939</a>
     */
    @Redirect(
        method = "submit",
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/opengl/GL33C;glFenceSync(II)J",
            remap = false
        )
    )
    private long glFenceSyncNull(int condition, int flags) {
        return 0L;
    }
}
