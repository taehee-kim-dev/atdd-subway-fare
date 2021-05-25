package wooteco.subway.station.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import wooteco.subway.station.domain.Station;

public class StationRequest {
    @NotBlank
    @Pattern(regexp = "^[가-힣|0-9]+역$")
    private String name;

    public StationRequest() {
    }

    public StationRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Station toStation() {
        return new Station(name);
    }
}
