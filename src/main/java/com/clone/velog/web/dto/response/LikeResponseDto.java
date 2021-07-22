package com.clone.velog.web.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LikeResponseDto {
    private Long postingId;
    private Long memberId;
}
