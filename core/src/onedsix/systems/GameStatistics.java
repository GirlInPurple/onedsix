package onedsix.systems;

import lombok.Getter;
import lombok.Setter;

public class GameStatistics {
    
    @Getter @Setter private long startups;
    @Getter @Setter private long totalGeometryCreated;
    @Getter @Setter private long totalGeometryLoaded;
}
