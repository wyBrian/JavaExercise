package brian.wang.middleware.demo.elk.service;


import brian.wang.middleware.demo.elk.domain.ProfileDocument;

import com.google.gson.Gson;
import java.io.IOException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

@Service
public class ProfileService {

    @Autowired
    private RestHighLevelClient client;

    private static final Type profileType = new TypeToken<ProfileDocument>() {}.getType();

    public ProfileDocument getProfile(String id) {
        GetRequest getRequest = new GetRequest("profiles", id);
        try {
            GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
            if (response != null && response.isExists())
                return new Gson().fromJson(response.getSourceAsString(), profileType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean postProfile(String id, String body) {
        IndexRequest request = new IndexRequest("profiles", id);
        request.source(body, XContentType.JSON);
        try {
            IndexResponse res = client.index(request,RequestOptions.DEFAULT);
            System.out.println(res);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}