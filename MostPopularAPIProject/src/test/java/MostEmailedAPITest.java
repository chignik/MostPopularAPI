import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class MostEmailedAPITest extends RequestSpecificationSetup{

    @DataProvider
    public static Object[][] allSectionsProvider(){
        return new Object[][]{
                {"all-sections", 1},
                {"all-sections", 7},
                {"all-sections", 30}
        };
    }

    @DataProvider
    public static Object[][] sectionProvider(){
        return new Object[][]{
                {"Arts", 1},
                {"Automobiles", 1},
                {"Blogs", 1},
                {"Business Day", 1},
                {"Education", 1},
                {"Fashion & Style", 1},
                {"Food", 1},
                {"Health", 1},
                {"Job Market", 1},
                {"Magazine", 1},
                {"membercenter", 1},
                {"Movies", 1},
                {"Multimedia", 1},
                {"N.Y.%20%2F%20Region", 1},
                {"NYT Now", 1},
                {"Obituaries", 1},
                {"Open", 1},
                {"Opinion", 1},
                {"Public Editor", 1},
                {"Real Estate", 1},
                {"Science", 1},
                {"Sports", 1},
                {"Style", 1},
                {"Sunday Review", 1},
                {"T Magazine", 1},
                {"Technology", 1},
                {"The Upshot", 1},
                {"Theater", 1},
                {"Times Insider", 1},
                {"Today’s Paper", 1},
                {"Travel", 1},
                {"U.S.", 1},
                {"World", 1},
                {"Your Money", 1},

                {"Arts", 7},
                {"Automobiles", 7},
                {"Blogs", 7},
                {"Business Day", 7},
                {"Education", 7},
                {"Fashion & Style", 7},
                {"Food", 7},
                {"Health", 7},
                {"Job Market", 7},
                {"Magazine", 7},
                {"membercenter", 7},
                {"Movies", 7},
                {"Multimedia", 7},
                {"N.Y.%20%2F%20Region", 7},
                {"NYT Now", 7},
                {"Obituaries", 7},
                {"Open", 7},
                {"Opinion", 7},
                {"Public Editor", 7},
                {"Real Estate", 7},
                {"Science", 7},
                {"Sports", 7},
                {"Style", 7},
                {"Sunday Review", 7},
                {"T Magazine", 7},
                {"Technology", 7},
                {"The Upshot", 7},
                {"Theater", 7},
                {"Times Insider", 7},
                {"Today’s Paper", 7},
                {"Travel", 7},
                {"U.S.", 7},
                {"World", 7},
                {"Your Money", 7},

                {"Arts", 30},
                {"Automobiles", 30},
                {"Blogs", 30},
                {"Business Day", 30},
                {"Education", 30},
                {"Fashion & Style", 30},
                {"Food", 30},
                {"Health", 30},
                {"Job Market", 30},
                {"Magazine", 30},
                {"membercenter", 30},
                {"Movies", 30},
                {"Multimedia", 30},
                {"N.Y.%20%2F%20Region", 30},
                {"NYT Now", 30},
                {"Obituaries", 30},
                {"Open", 30},
                {"Opinion", 30},
                {"Public Editor", 30},
                {"Real Estate", 30},
                {"Science", 30},
                {"Sports", 30},
                {"Style", 30},
                {"Sunday Review", 30},
                {"T Magazine", 30},
                {"Technology", 30},
                {"The Upshot", 30},
                {"Theater", 30},
                {"Times Insider", 30},
                {"Today’s Paper", 30},
                {"Travel", 30},
                {"U.S.", 30},
                {"World", 30},
                {"Your Money", 30}
        };
    }

    @Test(dataProvider = "allSectionsProvider")
    public void checkAllSectionsQuantity(String section, int timePeriod){
        Response response = given()
                .pathParam("section", section)
                .pathParam("time-period", timePeriod)
                .when()
                .get();

        ResponseWrapper responseWrapper = response.as(ResponseWrapper.class);
        List<ResultsObject> resultsObjectList = responseWrapper.getResultsObjects();
        List<String> allSectionsFromResponseList = putSectionsFromResponseToList(resultsObjectList);
        int numResultsFromResponse = responseWrapper.getNumResults();
        Map<String, Integer> mapAllSectionsToQuantity = sectionsQuantityAsMap(allSectionsFromResponseList);
        LOGGER.info("Quantity of encountered sections in response for section [" + section + "] and time-period [" + timePeriod + "]: \n" + mapAllSectionsToQuantity);

        assertEquals(allSectionsFromResponseList.size(), numResultsFromResponse,
                "Number of results is not equal: num_results is " + numResultsFromResponse + ", quantity of sections in response is equal to: " + allSectionsFromResponseList.size());
    }

    @Test(dataProvider = "sectionProvider")
    public void checkSpecificSectionQuantity(String section, int timePeriod){
        Response responseAllSections = given()
                .pathParam("section", "all-sections")
                .pathParam("time-period", timePeriod)
                .when()
                .get();

        ResponseWrapper responseWrapper = responseAllSections.as(ResponseWrapper.class);
        List<ResultsObject> resultsObjectList = responseWrapper.getResultsObjects();
        List<String> allSectionsFromResponseList = putSectionsFromResponseToList(resultsObjectList);
        Map<String, Integer> mapAllSectionsToQuantity = sectionsQuantityAsMap(allSectionsFromResponseList);

        Response responseSpecificSection = given()
                .pathParam("section", section)
                .pathParam("time-period", timePeriod)
                .when()
                .get();

        ResponseWrapper responseWrapperSpecificSection = responseSpecificSection.as(ResponseWrapper.class);
        List<ResultsObject> resultsObjectListSpecificSection = responseWrapperSpecificSection.getResultsObjects();
        List<String> specificSectionList = putSectionsFromResponseToList(resultsObjectListSpecificSection);
        Map<String, Integer> mapSpecificSectionToQuantity = sectionsQuantityAsMap(specificSectionList);
        LOGGER.info("Quantity of encountered sections in response for section [all-sections] and time-period [" + timePeriod + "]: \n" + mapAllSectionsToQuantity);
        LOGGER.info("Quantity of encountered sections in response for section [" + section + "] and time-period [" + timePeriod + "]: \n" + mapSpecificSectionToQuantity);

        assertEquals(mapSpecificSectionToQuantity.get(section), mapAllSectionsToQuantity.get(section),
                "Quantity of sections is not equal: all-sections response contains [" + mapAllSectionsToQuantity.get(section) + "] sections, " +
                        "but response for specific sections contains [" + mapSpecificSectionToQuantity.get(section) + "] sections.");
    }

    @Test(dataProvider = "sectionProvider")
    public void checkResponseContainsSpecificSection(String section, int timePeriod){
        Response responseSpecificSection = given()
                .pathParam("section", section)
                .pathParam("time-period", timePeriod)
                .when()
                .get();

        ResponseWrapper responseWrapperSpecificSection = responseSpecificSection.as(ResponseWrapper.class);
        List<ResultsObject> resultsObjectListSpecificSection = responseWrapperSpecificSection.getResultsObjects();
        List<String> specificSectionList = putSectionsFromResponseToList(resultsObjectListSpecificSection);
        Map<String, Integer> mapSpecificSectionToQuantity = sectionsQuantityAsMap(specificSectionList);
        LOGGER.info("Quantity of encountered sections in response for section [" + section + "] and time-period [" + timePeriod + "]: \n" + mapSpecificSectionToQuantity);

        assertTrue(specificSectionList.stream().allMatch(x -> x.equals(section)),
                "Expected all sections in response to be equal " + section + " , but actual sections are: " + specificSectionList);
    }

    private List<String> putSectionsFromResponseToList(List<ResultsObject> resultsObjects){
        List<String> list = new ArrayList<>();
        for (ResultsObject resultsObject : resultsObjects){
            list.add(resultsObject.getSection());
        }
        return list;
    }

    private Map<String, Integer> sectionsQuantityAsMap(List<String> list){
        Map<String, Integer> map = new HashMap<>();
        Multiset<String> multiset = HashMultiset.create(list);
        for (String string : multiset.elementSet()){
            map.put(string, multiset.count(string));
        }
        return map;
    }

}
