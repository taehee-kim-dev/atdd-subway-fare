package wooteco.subway.path.domain.fare;

import java.util.Objects;

public class Fare {
    private final int fare;

    public Fare(int fare) {
        validate(fare);
        this.fare = fare;
    }

    public Fare(double fare) {
        int castedFare = (int) fare;
        validate(castedFare);
        this.fare = (castedFare);
    }

    private void validate(int fare) {
        if (fare < 0) {
            throw new IllegalArgumentException("음수 값은 요금이 될 수 없습니다.");
        }
    }

    public Fare add(Fare fare) {
        return new Fare(this.fare + fare.getFare());
    }

    public Fare sub(Fare subFare) {
        return new Fare(this.fare - subFare.getFare());
    }

    public Fare discount(double rate) {
        return new Fare((int) Math.round(this.fare * (1 - rate)));
    }

    public int getFare() {
        return fare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fare fare1 = (Fare) o;
        return fare == fare1.fare;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fare);
    }

    @Override
    public String toString() {
        return "Fare{" +
            "fare=" + fare +
            '}';
    }
}
