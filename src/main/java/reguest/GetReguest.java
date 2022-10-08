package reguest;

import cats.Cats;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GetReguest {
    static final String URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("Cat Service")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build()) {

            HttpGet reguest = new HttpGet(URL);

            try (CloseableHttpResponse response = httpClient.execute(reguest)) {
                List<Cats> responseList = mapper.readValue(response.getEntity().getContent(),
                        new TypeReference<List<Cats>>() {});
                List<String> factsAboutCats = responseList.stream()
                        .filter(vote -> vote.getUpvotes() != 0)
                        .map(cats -> cats.getText())
                        .collect(Collectors.toList());
                factsAboutCats.stream().forEach(System.out::println);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

