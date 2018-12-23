import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseWrapper {

    @JsonProperty("num_results")
    private int numResults;

    @JsonProperty("results")
    private List<ResultsObject> resultsObjects = new ArrayList<>();

    public List<ResultsObject> getResultsObjects(){
        return  resultsObjects;
    }

    public int getNumResults() {
        return numResults;
    }
}
