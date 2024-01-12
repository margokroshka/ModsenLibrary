package Library.endpoint;

import Library.domain.PutBookInTheStorage;
import Library.domain.response.BookLogResponse;
import Library.domain.response.GetFreeBookResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class LibraryEndpoint {
    @Value("${url.library}")
    private String LOCAL_LIBRARY_SERVICE_URL;
    private final RestTemplate restTemplate;

    public ResponseEntity<List<GetFreeBookResponse>> getIdsOfBusyBooks() {
        return restTemplate.exchange(
                LOCAL_LIBRARY_SERVICE_URL + "/free",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
    }

    public ResponseEntity<BookLogResponse> changeBookStatus(Integer id, PutBookInTheStorage putBookInTheStorageDto) {
        return restTemplate.exchange(
                LOCAL_LIBRARY_SERVICE_URL + "/update/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(putBookInTheStorageDto),
                BookLogResponse.class
        );
    }



}
