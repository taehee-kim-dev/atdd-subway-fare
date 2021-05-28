package wooteco.subway.path.dto;

import java.util.List;
import java.util.stream.Collectors;
import wooteco.subway.path.domain.SubwayPath;
import wooteco.subway.station.dto.StationResponse;

public class PathResponseAssembler {
    public static PathResponse assemble(SubwayPath subwayPath) {
        List<StationResponse> stationResponses = subwayPath.getStations().stream()
            .map(StationResponse::of)
            .collect(Collectors.toList());

        return new PathResponse(stationResponses, subwayPath);
    }
}
