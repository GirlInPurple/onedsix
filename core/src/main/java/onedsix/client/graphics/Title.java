package onedsix.client.graphics;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Title {

    public static final String title = "1D6";
    public static final LinkedList<String> splashes = new LinkedList<>();

    static {
        Collections.addAll(
            splashes,
            "Community-Developed Data-Gen Derived Dynamically Distributed Distractor", // 50%
            "Data-Gen Derived Distributed Adventure Quests", // 25%
            "Dynamic Data-Gen Distributed Distractor", // 12.5%
            "Open-Source Distributed Questing Software", // 6.25%
            "Dumb Game Be Distributed, Opensource, and Dynamic", // 3.125%
            "These splashes went from \"1d6 = this\" to \"uwu mfer :3\"", // 1.0625%
            "meow :3"// 0.53125%
        );
    }

    public static String randomSplash() {
        StringBuilder re = new StringBuilder(title);
        Random ra = new Random();
        for (String s : splashes) {
            if (ra.nextBoolean()) {
                re.append(": ").append(s);
                break;
            }
        }
        return re.toString();
    }
}
