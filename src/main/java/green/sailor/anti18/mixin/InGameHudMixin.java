package green.sailor.anti18.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    // getHeartCount seems to be used to get the number of health bars for your riding entity, or something?
    // either way, it's called twice, once for hunger and once for the riding.
    // we redirect the *first* call (which is called in the hunger routine)
    // if it's 0, then the hunger bar is rendered, (if (this.method_1744(livingEntity_1) == 0) { ... })
    // but if it's any other value, the hunger bar is simply not rendered.
    // so, we change it to -1 (which doesn't render it).
    @Redirect(
            method = "renderStatusBars",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;method_1744(Lnet/minecraft/entity/LivingEntity;)I"
            )
    )
    int proxyGetHeartCount(InGameHud hud, LivingEntity ent) {
        return -1;
    }
}
