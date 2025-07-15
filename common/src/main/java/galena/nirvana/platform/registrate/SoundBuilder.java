package galena.nirvana.platform.registrate;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

public abstract class SoundBuilder<P> extends AbstractBuilder<SoundEvent, SoundEvent, P, SoundBuilder<P>> {

    @Nullable
    private String subtitleKey;
    private final Set<ResourceLocation> sounds = new HashSet<>();

    protected SoundBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback) {
        super(owner, parent, name, callback, Registries.SOUND_EVENT);
    }

    protected abstract SoundBuilder<P> lang(String key, String translation);

    public final SoundBuilder<P> with(ResourceLocation... sounds) {
        this.sounds.addAll(Arrays.asList(sounds));
        return this;
    }

    public final SoundBuilder<P> with(String... sounds) {
        for (var sound : sounds) {
            this.sounds.add(ResourceLocation.fromNamespaceAndPath(getOwner().getModid(), sound));
        }
        return this;
    }

    public final SoundBuilder<P> lang(String translation) {
        if (getSubtitleKey() == null) subtitleKey = "subtitle." + getName();
        return lang(getSubtitleKey(), translation);
    }

    @Nullable
    protected final String getSubtitleKey() {
        return this.subtitleKey;
    }

    protected final Stream<ResourceLocation> getSounds() {
        return this.sounds.stream();
    }

    @Override
    protected final SoundEvent createEntry() {
        if (sounds.isEmpty()) {
            throw new IllegalStateException("cannot create SoundEvent without any sounds");
        }
        var id = ResourceLocation.fromNamespaceAndPath(getOwner().getModid(), getName());
        return SoundEvent.createFixedRangeEvent(id, 1F);
    }

}