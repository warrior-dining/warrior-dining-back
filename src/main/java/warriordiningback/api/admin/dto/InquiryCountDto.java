package warriordiningback.api.admin.dto;

import lombok.Data;

@Data
public class InquiryCountDto {

    private String value;
    private Long count;

    public InquiryCountDto(String value, Long count) {
        this.value = value;
        this.count = count;
    }
}
