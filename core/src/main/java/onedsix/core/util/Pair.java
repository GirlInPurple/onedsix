package onedsix.core.util;

import lombok.Data;

/** A pair of two associated non-key values. */
@Data
public class Pair<K, T> {
	public final K k;
	public final T t;
}
