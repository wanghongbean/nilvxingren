package com.whb.demo.validator.dto;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserDTO implements Serializable {
    @NotNull(message = "id不能为空")
    private Long userId;

    @NotBlank(message = "名称不能为空")
    @Length(max = 20, message = "用户名不能超过20个字符")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9\\*]*$", message = "用户昵称限制：最多20字符，包含文字、字母和数字")
    private String name;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String mobile;

    @Future(message = "时间必须是将来时间")
    private Date createTime;

    public static void main(String[] args) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setCreateTime(new Date());
        userDTO.setMobile("18877779999");
        userDTO.setName("20s");
        System.out.println(userDTO.toString());
    }
}
