package wooteco.subway.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static wooteco.subway.auth.AuthAcceptanceTest.내_회원_정보_조회_요청;
import static wooteco.subway.auth.AuthAcceptanceTest.로그인되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.subway.AcceptanceTest;
import wooteco.subway.auth.dto.TokenResponse;
import wooteco.subway.member.dto.MemberRequest;
import wooteco.subway.member.dto.MemberResponse;

public class MemberAcceptanceTest extends AcceptanceTest {
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final int AGE = 20;
    public static final String NEW_EMAIL = "new_email@email.com";
    public static final String NEW_PASSWORD = "new_password";
    public static final int NEW_AGE = 30;

    @DisplayName("회원 정보를 관리한다.")
    @Test
    void manageMember() {
        ExtractableResponse<Response> createResponse = 회원_생성을_요청(EMAIL, PASSWORD, AGE);
        회원_생성됨(createResponse);

        TokenResponse 사용자 = 로그인되어_있음(EMAIL, PASSWORD);

        ExtractableResponse<Response> findResponse = 내_회원_정보_조회_요청(사용자);
        회원_정보_조회됨(findResponse, EMAIL, AGE);

        ExtractableResponse<Response> updateResponse = 내_회원_정보_수정_요청(사용자, EMAIL, NEW_PASSWORD, NEW_AGE);
        회원_정보_수정됨(updateResponse);

        ExtractableResponse<Response> deleteResponse = 내_회원_삭제_요청(사용자);
        회원_삭제됨(deleteResponse);
    }

    @DisplayName("이메일 중복 에러")
    @Test
    void signUpEmailDuplicateException() {
        ExtractableResponse<Response> createResponse = 회원_생성을_요청(EMAIL, PASSWORD, AGE);
        회원_생성됨(createResponse);

        회원_생성을_요청_실패(EMAIL, PASSWORD, AGE);
    }

    public static ExtractableResponse<Response> 회원_생성을_요청(String email, String password, Integer age) {
        MemberRequest memberRequest = new MemberRequest(email, password, age);

        return RestAssured
            .given(spec).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(document("sign-up", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .body(memberRequest)
            .when().post("/members")
            .then().log().all()
            .extract();
    }

    public static void 회원_생성을_요청_실패(String email, String password, Integer age) {
        MemberRequest memberRequest = new MemberRequest(email, password, age);

        ExtractableResponse<Response> response = RestAssured
            .given(spec).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(document("sign-up-email-duplicate-fail", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .body(memberRequest)
            .when().post("/members")
            .then().log().all()
            .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public static ExtractableResponse<Response> 내_회원_정보_수정_요청(TokenResponse tokenResponse, String email, String password, Integer age) {
        MemberRequest memberRequest = new MemberRequest(email, password, age);

        return RestAssured
            .given(spec).log().all()
            .auth().oauth2(tokenResponse.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(document("update-my-info", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .body(memberRequest)
            .when().put("/members/me")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 내_회원_삭제_요청(TokenResponse tokenResponse) {
        return RestAssured
            .given(spec).log().all()
            .auth().oauth2(tokenResponse.getAccessToken())
            .filter(document("delete-my-account", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .when().delete("/members/me")
            .then().log().all()
            .extract();
    }

    public static void 회원_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 회원_정보_조회됨(ExtractableResponse<Response> response, String email, int age) {
        MemberResponse memberResponse = response.as(MemberResponse.class);
        assertThat(memberResponse.getId()).isNotNull();
        assertThat(memberResponse.getEmail()).isEqualTo(email);
        assertThat(memberResponse.getAge()).isEqualTo(age);
    }

    public static void 회원_정보_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 회원_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
