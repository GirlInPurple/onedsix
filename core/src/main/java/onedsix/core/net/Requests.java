package onedsix.core.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.*;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpRequestHeader;
import com.badlogic.gdx.Net.HttpResponseListener;
import lombok.extern.slf4j.Slf4j;
import onedsix.core.util.Logger;

public class Requests {

	@Slf4j
    abstract static class AbstractRequest implements HttpResponseListener {
        public final String url;
        public final String[] topics = {"onedsix", "1d6", "1D6M"};
        public final String[] validity = {"mod", "addon", "libgdx", "gdx", "tacos", "pizza"}; // these last two are just for fun
        public final String[] languages = {"Java", "Kotlin", "Scala"};
        public final String[] extensions = {"java", "kt", "scala"};

        public AbstractRequest(String url) {
            this.url = url;
        }

        public abstract void makeRequest();

        @Override
        public void failed(Throwable t) {
            log.error(t.getMessage(), t);
        }

        @Override
        public void cancelled() {
            log.info("Cancelled!");
        }

    }

	@Slf4j
    public static class GithubRequest extends AbstractRequest {

        public GithubRequest() {
            super("https://api.github.com/search/repositories?q=");
        }

        @Override
        public void makeRequest() {
            HttpRequest req = new HttpRequestBuilder()
                .newRequest()
                .url(this.url + topics[1])
                .method(HttpMethods.GET)
                .header(HttpRequestHeader.Accept, "application/vnd.github+json")
                .build();

            Gdx.net.sendHttpRequest(req, this);
        }

        @Override
        public void handleHttpResponse(HttpResponse res) {
            log.info(res.getResultAsString());
        }
    }
}
