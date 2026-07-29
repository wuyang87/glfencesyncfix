package com.github.glfencesyncfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Redirects GL33C.glFenceSync in GlCommandEncoder#submit to a no-op.
 * Fixes the ~50-60% FPS regression in Minecraft 26.2 OpenGL.
 *
 * @see <a href="https://mojira.dev/MC-309939">MC-309939</a>
 */
@Mixin(targets = "com.mojang.blaze3d.opengl.GlCommandEncoder")
public class GlFenceSyncMixin {

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
