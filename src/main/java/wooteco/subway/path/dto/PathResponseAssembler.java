package wooteco.subway.path.dto;

import java.util.List;
import java.util.stream.Collectors;
import wooteco.subway.path.domain.SubwayPath;
import wooteco.subway.path.domain.fare.Fare;
import wooteco.subway.station.dto.StationResponse;

public class PathResponseAssembler {
    public static PathResponse assemble(SubwayPath subwayPath, Fare fare) {
        List<StationResponse> stationResponses = subwayPath.getStations().stream()
            .map(it -> StationResponse.of(it))
            .collect(Collectors.toList());

        int distance = subwayPath.calculateDistance();
        return new PathResponse(stationResponses, distance, fare.getFare());
    }
}
