package ssipgeukbbok.shoppingjpapractice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ssipgeukbbok.shoppingjpapractice.domain.contstant.RoleType;
import ssipgeukbbok.shoppingjpapractice.dto.UserAccountDto;
import ssipgeukbbok.shoppingjpapractice.dto.request.UserAccountRequestDto;
import ssipgeukbbok.shoppingjpapractice.service.UserAccountService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @GetMapping("/new")
    public String signup(Model model){
        model.addAttribute("memberFormDto", new UserAccountRequestDto());
        return "member/memberForm";
    }

    @PostMapping("/new")
    public String signup(@Valid UserAccountRequestDto userAccountRequestDto, BindingResult bindingResult, Model model){

        if ( bindingResult.hasErrors() ) {
            return "member/memberForm";
        }

        UserAccountDto userAccountDto = userAccountService.save(
                userAccountRequestDto.getName(),
                userAccountRequestDto.getEmail(),
                userAccountRequestDto.getPassword(),
                userAccountRequestDto.getAddress(),
                RoleType.USER);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginMember(){
        System.out.println("userAccountService = " + userAccountService);
        return "/member/memberLoginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }
}
