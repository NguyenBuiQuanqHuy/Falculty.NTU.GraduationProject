package ntu.granduationproject.ntu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ntu.granduationproject.ntu.services.UserService;

@Controller
@RequestMapping("/")
public class LoginController {

  @Autowired
  private UserService userService;

  @GetMapping("/login")
  public String LoginForm() {
    return "views/login";  
  }

  @PostMapping("/login")
  public String processLogin(@RequestParam String tendn,
                             @RequestParam String matkhau,
                             Model model) {
    if (userService.Login(tendn, matkhau)) {
      return "redirect:/home";  
    } else {
      model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
      return "views/login";  
    }
  }

  @GetMapping("/home")
  public String home() {
    return "views/home"; 
  }
}