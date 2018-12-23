import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.BeforeSuite;

import java.util.logging.Logger;

public class RequestSpecificationSetup {
    protected static final Logger LOGGER = Logger.getLogger(String.valueOf(RequestSpecificationSetup.class));

    private static String BASE_URI = "http://api.nytimes.com";
    private static String BASE_PATH = "/svc/mostpopular/v2/mostemailed/{section}/{time-period}/.json";
    private static String API_KEY = "00a18b6859d14f2d9145dd0885257b36";
    private static String API_KEY_HEADER = "api-key";

    @BeforeSuite
    public void before(){
        BasicConfigurator.configure();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(BASE_PATH)
                .setContentType(ContentType.ANY)
                .setAccept(ContentType.ANY)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .addHeader(API_KEY_HEADER, API_KEY)
                .build();
    }

}
