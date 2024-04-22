package tech.ada.school.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.ada.school.domain.dto.ActivityDto;

@Service
@RequiredArgsConstructor
public class RestBoredApi {
    private final RestTemplate restTemplate;

    public String activity() {
        return restTemplate
                .getForEntity("https://www.boredapi.com/api/activity", ActivityDto.class)
                .getBody()
                .activity();
    }
}
