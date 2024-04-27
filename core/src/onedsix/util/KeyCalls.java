package onedsix.util;

import lombok.Getter;
import lombok.Setter;

public class KeyCalls {
    
    @Setter @Getter private boolean debug = true;
    @Setter @Getter private boolean movement = true;
    @Setter @Getter private boolean inventory = true;
    @Setter @Getter private boolean accessibility = true;
    
    public KeyCalls() {}
}
