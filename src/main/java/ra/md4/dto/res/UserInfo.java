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
    private Integer userId;
    private String username;
    private String avatar;
    private boolean status;
    private boolean role;

    public UserInfo(User user){
        this.userId = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.status = user.isStatus();
        this.role = user.isRole();
    }

    public static UserInfo builder(User user){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userInfo.getUserId());
        return userInfo;
    }
}
