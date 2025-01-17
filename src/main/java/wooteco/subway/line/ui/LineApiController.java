package wooteco.subway.line.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.subway.line.application.LineService;
import wooteco.subway.line.dto.LineResponse;

@RestController
public class LineApiController {
    private final LineService lineService;

    public LineApiController(LineService lineService) {
        this.lineService = lineService;
    }

    @GetMapping("/api/lines")
    public ResponseEntity<List<LineResponse>> findAllLines() {
        return ResponseEntity.ok(lineService.findLineResponses());
    }
}
