package onedsix.core.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.*;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpRequestHeader;
import com.badlogic.gdx.Net.HttpResponseListener;
import onedsix.core.util.Logger;

public class Requests {
    
    abstract static class AbstractRequest implements HttpResponseListener {
    
        public Logger L;
        public final String url;
        public final String[] topics = {"main/java/onedsix", "1d6", "1D6M"};
        public final String[] validity = {"mod", "addon", "libgdx", "gdx", "tacos", "pizza"}; // these last two are just for fun
        public final String[] languages = {"Java", "Kotlin", "Scala"};
        public final String[] extensions = {"java", "kt", "scala"};
        
        public AbstractRequest(String url) {
            this.url = url;
        }
    
        public abstract void makeRequest();
    
        @Override
        public void failed(Throwable t) {
            L.error(t.getMessage(), t);
        }
    
        @Override
        public void cancelled() {
            L.info("Cancelled!");
        }
        
    }
    
    public static class GithubRequest extends AbstractRequest {
        
        public GithubRequest() {
            super("https://api.github.com/search/repositories?q=");
            L = new Logger(this.getClass());
        }
        
        @Override
        public void makeRequest() {
            HttpRequest req = new HttpRequestBuilder()
                .newRequest()
                .url(url + topics[1])
                .method(HttpMethods.GET)
                .header(HttpRequestHeader.Accept, "application/vnd.github+json")
                .build();
    
            Gdx.net.sendHttpRequest(req, this);
        }
    
        @Override
        public void handleHttpResponse(HttpResponse res) {
            L.info(res.getResultAsString());
        }
    }
}
