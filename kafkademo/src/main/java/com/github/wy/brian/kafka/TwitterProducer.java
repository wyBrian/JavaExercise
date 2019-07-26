package com.github.wy.brian.kafka;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterProducer {

    private Logger logger = LoggerFactory.getLogger(TwitterProducer.class.getName());

    public TwitterProducer(){}

    public static void main(String[] args) {
        new TwitterProducer().run();
    }

    private void run() {
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(1000);
        Client client = createTwitterClient(msgQueue);
        client.connect();
        while (!client.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                client.stop();
            }
            if (null != msg) {
                logger.info(msg);
            }
        }
        logger.info("App ends");
    }

    public Client createTwitterClient(BlockingQueue<String> msgQueue){
        Hosts hosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        List<String> terms = Lists.newArrayList("google");
        endpoint.trackTerms(terms);
        Properties prop = DeveloperCredentials.getProperties();
        Authentication auth = new OAuth1(
            prop.getProperty("consumerKey"),
            prop.getProperty("consumerSecret"),
            prop.getProperty("token"),
            prop.getProperty("secret"));
        ClientBuilder builder = new ClientBuilder()
            .name("Client-Demo-01")
            .hosts(hosts)
            .authentication(auth)
            .endpoint(endpoint)
            .processor(new StringDelimitedProcessor(msgQueue));

        Client client = builder.build();
        return client;
    }

}
