package com.clone.velog.web.dto.request;

import com.clone.velog.web.domain.common.Authority;
import com.clone.velog.web.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    @NotEmpty(message = "이메일을 입력하세요.")
    @Email(message = "올바른 이메일을 입력하세요")
    private String email;

    @Size(min = 4)
    @NotEmpty(message = "비밀번호를 입력하세요")
    private String pwd;

//    private String pwdCheck;

    @Size(max = 10)
    @NotEmpty
    private String nickname;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .pwd(passwordEncoder.encode(pwd))
                .nickname(nickname)
                .status(true)
                .authority(Authority.ROLE_USER)
                .build();
    }

//    public void pwdCheck(){
//        if(!this.pwd.equals(this.pwdCheck)){
//            throw new ArithmeticException("비밀번호가 일치하지 않습니다.");
//        }else if (!this.pwd.contains(nickname)){
//            throw new ArithmeticException("비밀번호에 닉네임이 포함될 수 없습니다.");
//        }
//    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, pwd);
    }
}
