import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultsObject {

    @JsonProperty("section")
    private String section;

    public String getSection() {
        return section;
    }
}
