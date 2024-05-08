package onedsix.core.assets.data;

import java.util.LinkedList;
import java.util.Objects;

public class Dialog {
    
    public final LinkedList<DialogSnippet> dialogSnippets;
    public LinkedList<String> pastIDs;
    
    public Dialog(LinkedList<DialogSnippet> dialogSnippets, LinkedList<String> pastIDs) {
        this.dialogSnippets = dialogSnippets;
        this.pastIDs = pastIDs;
    }
    
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
    
    public static class DialogSnippet {
        
        public final String ID;
        public final String[] text;
        public final DialogOption[] option;
    
        public DialogSnippet(String id, String[] text, DialogOption[] option) {
            this.ID = id;
            this.text = text;
            this.option = option;
        }
    
        public static class DialogOption {
            
            public final String To;
            public final String[] Text;
    
            public DialogOption(String to, String[] text) {
                To = to;
                Text = text;
            }
        }
    }
    
}
