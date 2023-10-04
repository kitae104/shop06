package kr.inhatc.shop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberFormDto {

    @NotBlank(message = "이름은 필수 항목 입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 항목 입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 항목 입니다.")
    @Length(min = 4, max = 12, message = "비밀번호는 최소 4자, 최대 12자를 입력해주세요.")
    private String password;

    @NotEmpty(message = "주소는 필수 항목 입니다.")
    private String address;
}
