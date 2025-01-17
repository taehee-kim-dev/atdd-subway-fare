package wooteco.subway.path.dto;

import java.util.List;
import wooteco.subway.path.domain.SubwayPath;
import wooteco.subway.station.dto.StationResponse;

public class PathResponse {
    private List<StationResponse> stations;
    private int distance;
    private int totalFare;

    public PathResponse() {
    }

    public PathResponse(List<StationResponse> stations, int distance, int totalFare) {
        this.stations = stations;
        this.distance = distance;
        this.totalFare = totalFare;
    }

    public PathResponse(List<StationResponse> stationResponses, SubwayPath subwayPath) {
        stations = stationResponses;
        distance = subwayPath.getTotalDistance();
        totalFare = subwayPath.getTotalFare();
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getTotalFare() {
        return totalFare;
    }
}
