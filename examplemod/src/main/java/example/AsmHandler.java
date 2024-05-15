package example;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import onedsix.core.assets.data.Attributes;
import onedsix.core.assets.abstracts.Item;
import onedsix.core.assets.data.Recipe;
import onedsix.core.util.Logger;

public class AsmHandler {

    private static final Logger L = new Logger(AsmHandler.class);

    public void onAsm() {

        try {

            Class<? extends Item> dynamicType = new ByteBuddy()
                .subclass(Item.class)

                .method(ElementMatchers.named("toString")).intercept(FixedValue.value("Hello World!"))
                .defineMethod("tester", String.class, Visibility.PUBLIC).intercept(FixedValue.value("this is a test"))

                .make().load(AsmHandler.class.getClassLoader()).getLoaded();

            Item instance = dynamicType.getConstructor(Item.class).newInstance(
                    new Attributes(),
                    new Recipe(null, null, null, null),
                    0L
            );

            L.info((String) dynamicType.getMethod("toString").invoke(instance));
            L.info((String) dynamicType.getMethod("tester").invoke(instance));
            L.info(String.valueOf(instance.roughCost));

        } catch (Exception e) {
            L.error(e.getMessage(), e);
        }
    }
}
