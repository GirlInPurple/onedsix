package onedsix.gen.asm;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import onedsix.gen.assets.Attributes;
import onedsix.gen.assets.Item;
import onedsix.gen.assets.Recipe;
import onedsix.util.Logger;

public class AsmTest {
    
    private static final Logger L = new Logger(AsmTest.class);
    
    public static void asmTest() {
        try {
            
            Class<? extends Item> dynamicType = new ByteBuddy()
                .subclass(Item.class)
                
                .method(ElementMatchers.named("toString")).intercept(FixedValue.value("Hello World!"))
                .defineMethod("tester", String.class, Visibility.PUBLIC).intercept(FixedValue.value("this is a test"))
                
                .make().load(AsmTest.class.getClassLoader()).getLoaded();
            
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
