package onedsix.event.settings;

import onedsix.systems.GameSettings.SettingsJson;

import java.util.EventObject;

public class SettingsChangeEvent extends EventObject {
    public SettingsChangeEvent(Object source, SettingsJson sj) {
        super(source);
    }
}
