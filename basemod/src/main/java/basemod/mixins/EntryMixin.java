package basemod.mixins;

import lombok.extern.slf4j.Slf4j;
import onedsix.Entrypoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Slf4j
@Mixin(Entrypoint.class)
public class EntryMixin {

	@Inject(method = "main([Ljava/lang/String;)V", at = @At(value = "HEAD"), remap = false)
	private static void mainInitRun(CallbackInfo ci) {
		log.info("BaseMod Mixins have succeeded.");
		log.info("Mixed and Baked :)");
	}
}

