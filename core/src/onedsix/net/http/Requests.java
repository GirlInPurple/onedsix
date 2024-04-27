package onedsix.net.http;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.*;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpRequestHeader;
import com.badlogic.gdx.Net.HttpResponseListener;
import onedsix.util.Logger;

public class Requests {
    private static final Logger L = new Logger(Requests.class);
    
    public static class GithubRequest {
        private static final String url = "https://api.github.com/search/repositories?q=topic:";
        private static final String[] topics = {"onedsix", "1d6", "1D6M"};
        private static final String[] validity = {"mod", "addon", "libgdx", "gdx", "tacos", "pizza"}; // these last two are just for fun
        private static final String[] languages = {"java", "kotlin", "scala"};
        
        public static void makeRequest() {
            HttpRequest req = new HttpRequestBuilder()
               .newRequest()
               .method(HttpMethods.GET)
               .header(HttpRequestHeader.Accept, "application/vnd.github+json")
               .content("q=" + topics[0])
               .build();
    
            Gdx.net.sendHttpRequest(req, new HttpResponseListener() {
                @Override
                public void handleHttpResponse(HttpResponse res) {
                
                
                
                }
    
                @Override
                public void failed(Throwable t) {
        
                }
    
                @Override
                public void cancelled() {
        
                }
            });
        }
    }
}
