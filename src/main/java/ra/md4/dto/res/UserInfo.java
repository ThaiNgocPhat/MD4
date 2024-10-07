package ra.md4.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.md4.models.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserInfo {
    private Integer id;
    private String username;
    private String phone;
    private String email;
    private String avatar;
    private String address;
    private String fullName;
    private boolean status;
    private boolean role;

    public UserInfo(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.avatar = user.getAvatar();
        this.status = user.isStatus();
        this.role = user.isRole();
    }
}