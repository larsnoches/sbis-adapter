package org.cyrilselyanin.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class RegCashResponseDto {
    private RegCashResponseResultDto result;

    @JsonGetter("Result")
    public RegCashResponseResultDto getResult() {
        return this.result;
    }

    @JsonSetter("Result")
    public void setResult(RegCashResponseResultDto result) {
        this.result = result;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class RegCashResponseResultDto {
        public String payId;
    }
}
