package brian.wang.spring.demo.quartz.component;

public class AppConst {
    public static final class DbKey {
        public static final String DEFAULT = "default";
        public static final String WRITE = "write";
        public static final String READ = "read";
    }

    public static final class Env {
        public static final String DEV = "dev";
        public static final String TEST = "test";
        public static final String PROD = "prod";
    }

    public static final class HttpMethod {
        public static final String GET = "get";
        public static final String POST = "post";
    }

    public static final class JobType {
        public static final String TEST_JOB = "test_job";
        public static final String HTTP_JOB = "http_job";
    }
}