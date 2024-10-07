package ra.md4.dto.req;

import lombok.Data;

@Data
public class FormRegister {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private String password;
    private String confirmPassword;
    private String phone;
    private String address;
}
