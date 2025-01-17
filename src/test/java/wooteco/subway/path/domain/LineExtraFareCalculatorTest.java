package wooteco.subway.path.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import wooteco.subway.line.domain.Line;
import wooteco.subway.path.domain.fare.Fare;
import wooteco.subway.path.domain.fare.LineExtraFareCalculator;

class LineExtraFareCalculatorTest {
    private LineExtraFareCalculator lineExtraFareCalculator;

    @BeforeEach
    void setUp() {
        this.lineExtraFareCalculator = new LineExtraFareCalculator();
    }

    @DisplayName("경로상 노선들의 추가 요금에 따른 총 요금을 계산한다.")
    @ParameterizedTest
    @MethodSource
    void getFareWithLineExtraFare(Fare currentFare, Fare expectedFare, List<Line> lines) {
        Fare actualFare = lineExtraFareCalculator.getFare(currentFare, lines);
        assertThat(actualFare).isEqualTo(expectedFare);
    }

    static Stream<Arguments> getFareWithLineExtraFare() {
        return Stream.of(
            Arguments.of(new Fare(1250), new Fare(2150),
                Collections.singletonList(new Line("1호선", "1번색깔", new Fare(900)))),
            Arguments.of(new Fare(1350), new Fare(2250),
                Collections.singletonList(new Line("1호선", "1번색깔", new Fare(900)))),
            Arguments.of(new Fare(1250), new Fare(2150), Arrays.asList(
                new Line("1호선", "1번색깔", new Fare(0)),
                new Line("2호선", "2번색깔", new Fare(500)),
                new Line("3호선", "3번색깔", new Fare(900))
                )
            )
        );
    }
}