package basemod.entities.data;

import lombok.Data;
import onedsix.core.assets.abstracts.Asset;
import onedsix.core.assets.abstracts.Effect;

import java.util.LinkedList;

public class Memories {

	public final LinkedList<Memory> memories = new LinkedList<>();

	public void newMemory(byte intensity, Class<? extends Asset> cause, double ago, Effect[] effects) {
		memories.add(new Memory(intensity, cause, ago, effects));
	}

	@Data
	public static class Memory  {
		/** The intensity of the memory. High is good, Low is bad. Capped between -127 and +128 */
		public final byte intensity;
		/** What causes the memory. Can be any class that extends {@link Asset} */
		public final Class<? extends Asset> cause;
		/** How long ago the event occurred. */
		public final double ago;
		/** What effects this memory has on the data. */
		public final Effect[] effects;
	}
}
