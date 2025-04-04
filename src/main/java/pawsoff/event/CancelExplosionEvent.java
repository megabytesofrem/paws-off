package pawsoff.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import pawsoff.MainMod;

import java.util.List;

@EventBusSubscriber(modid = MainMod.MODID)
public class CancelExplosionEvent {

    @SubscribeEvent
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        Entity source = event.getExplosion().getDirectSourceEntity();

        // We only care about preventing creepers from harming pets
        if (!(source instanceof Creeper)) return;

        // Remove the affected entities via a predicate
        List<Entity> affectedEntities = event.getAffectedEntities();
        affectedEntities.removeIf(entity ->
                (entity instanceof TamableAnimal && (((TamableAnimal) entity).isTame())) ||
                (entity instanceof AbstractHorse && ((AbstractHorse) entity).isTamed()));
     }
}
