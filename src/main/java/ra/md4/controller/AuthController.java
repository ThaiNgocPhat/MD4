package ra.md4.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ra.md4.dto.req.FormLogin;
import ra.md4.dto.req.FormRegister;
import ra.md4.dto.res.UserInfo;
import ra.md4.exception.AuthenticationException;
import ra.md4.service.user.IUserService;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    @Autowired
    private IUserService userService;


    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }
    @GetMapping("/register")
    public String register(){
        return "auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute FormRegister request){
        try{
            userService.register(request);
            return "auth/login";
        }catch (Exception e) {
            return "redirect:/error?message="+e.getMessage();
        }
    }
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute FormLogin request, HttpSession session, Model model){
        try{
            UserInfo userInfo = userService.login(request);
            session.setAttribute("userLogin",userInfo);
            if (userInfo.isRole()){
                return "redirect:/admin";
            }else {
                return "redirect:/";
            }
        }catch (AuthenticationException e){
            model.addAttribute("error",e.getMessage());
            return "auth/login";
        }
    }
}
