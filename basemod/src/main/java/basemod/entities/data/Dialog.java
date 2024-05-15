package basemod.entities.data;

import com.moandjiezana.toml.Toml;
import lombok.Data;

import java.util.LinkedList;
import java.util.Objects;

/** Acts as a converging tree of values, meaning you can make extensive dialog trees. */
public class Dialog {

    public final LinkedList<DialogSnippet> dialogSnippets;
    public LinkedList<String> pastIDs;

    public Dialog(LinkedList<DialogSnippet> dialogSnippets, LinkedList<String> pastIDs) {
        this.dialogSnippets = dialogSnippets;
        this.pastIDs = pastIDs;
    }

	/** Takes in a raw .toml file content and reads its values, turning it into a dialog tree.
	public Dialog(Toml toml) {

	}
 	*/

    public DialogSnippet accessDialog(String ID, boolean shouldTrack) {
        DialogSnippet out = null;
        for (DialogSnippet ds : this.dialogSnippets) {
            if (Objects.equals(ID, ds.ID)) {
                out = ds;
                if (shouldTrack) {
                    pastIDs.add(ID);
                }
            }
        }
        if (out == null) {
            String exceptionText = "This Dialog ID "+ID+" does not exist!";
            throw new NullPointerException(exceptionText);
        }
        return out;
    }

	@Data
    public static class DialogSnippet {
        public final String ID;
        public final String[] text;
        public final DialogOption[] option;
    }

	@Data
	public static class DialogOption {

		public final String To;
		public final String[] Text;
	}

}
