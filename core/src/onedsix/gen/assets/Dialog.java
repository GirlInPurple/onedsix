package onedsix.gen.assets;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Objects;

public class Dialog {
    
    @Getter private final LinkedList<DialogSnippet> dialogSnippets;
    @Getter @Setter private LinkedList<String> pastIDs;
    
    public Dialog(LinkedList<DialogSnippet> dialogSnippets, LinkedList<String> pastIDs) {
        this.dialogSnippets = dialogSnippets;
        this.pastIDs = pastIDs;
    }
    
    public DialogSnippet accessDialog(String ID, boolean shouldTrack) {
        DialogSnippet out = null;
        for (DialogSnippet ds : this.dialogSnippets) {
            if (Objects.equals(ID, ds.getID())) {
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
        
        @Getter private final String ID;
        @Getter private final String[] text;
        @Getter private final DialogOption[] option;
    
        public DialogSnippet(String id, String[] text, DialogOption[] option) {
            this.ID = id;
            this.text = text;
            this.option = option;
        }
    
        public static class DialogOption {
            
            @Getter private final String To;
            @Getter private final String[] Text;
    
            public DialogOption(String to, String[] text) {
                To = to;
                Text = text;
            }
        }
    }
    
}
