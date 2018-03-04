
package com.weissbeerger.demo.model.omdb;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Source",
    "Value"
})
@NoArgsConstructor
@Entity
public class Rating {
    @JsonIgnore
    @javax.persistence.Id
    @GeneratedValue
    int id;

    @JsonProperty("Source")
    private String source;
    @JsonProperty("Value")
    private String value;
    @Transient
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Source")
    public String getSource() {
        return source;
    }

    @JsonProperty("Source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("Value")
    public String getValue() {
        return value;
    }

    @JsonProperty("Value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
