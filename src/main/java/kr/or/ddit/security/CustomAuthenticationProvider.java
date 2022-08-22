package kr.or.ddit.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.jsp.dto.MemberVO;
import com.jsp.exception.InvalidPasswordException;
import com.jsp.exception.NotFoundIdException;
import com.jsp.service.LoginSearchMemberService;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	private LoginSearchMemberService memberService;

	public void setMemberService(LoginSearchMemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String login_id = (String) auth.getPrincipal(); // 로그인 시도한 ID를 가져온다
		String login_pwd = (String) auth.getCredentials(); // 로그인 시도한 Password 를 가져온다.

		try {
			memberService.login(login_id, login_pwd);

			MemberVO member = memberService.getMember(login_id);

			UserDetails authUser = new User(member);
			boolean invalidCheck = authUser.isAccountNonExpired() 
					&& authUser.isAccountNonLocked()
					&& authUser.isCredentialsNonExpired() 
					&& authUser.isEnabled();

			if (invalidCheck) {
				// 스프링 시큐리티 내부 클래스로 인증 토큰 생성한다.
				UsernamePasswordAuthenticationToken result 
							= new UsernamePasswordAuthenticationToken(
						authUser.getUsername(), authUser.getPassword(), 
						authUser.getAuthorities());
				// 전달할 내용을 설정한 후
				result.setDetails(authUser);
				// 리턴한다. successHandler로 전송된다.
				return result;
			}
			
			throw new BadCredentialsException("상태변경으로 로그인이 불가합니다.");
		} catch (NotFoundIdException e) { // id 불일치
			throw new BadCredentialsException("존재하지 않는 아이디입니다.");
		} catch (InvalidPasswordException e) { // pwd 불일치
			throw new BadCredentialsException("패스워드가 일치하지 않습니다.");
		} catch (Exception e) {// 에러
			e.printStackTrace();
			throw new BadCredentialsException("서버 장애로 서비스가 불가합니다.");
		}

	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

}










