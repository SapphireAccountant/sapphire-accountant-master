package ae.kbgroup.sapphire.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	// just to emulate user data and credentials retrieval from a DB, or
	// whatsoever authentication service
	private static Map<String, UserDetails> userRepository = new HashMap<String, UserDetails>();

	static {
		GrantedAuthority authorityAdmin = new GrantedAuthorityImpl("ROLE_ADMIN");
		GrantedAuthority authorityGuest = new GrantedAuthorityImpl("ROLE_GUEST");

		/* user1/password1 --> ADMIN */
		Set<GrantedAuthority> authorities1 = new HashSet<GrantedAuthority>();
		authorities1.add(authorityAdmin);
		UserDetails user1 = new UserDetailsImpl("admin@gmail.com", "admin", authorities1);
		userRepository.put("admin@gmail.com", user1);

		/* user2/password2 --> GUEST */
		Set<GrantedAuthority> authorities2 = new HashSet<GrantedAuthority>();
		authorities2.add(authorityGuest);
		UserDetails user2 = new UserDetailsImpl("user2@gmail.com", "password2", authorities2);
		userRepository.put("user2@gmail.com", user2);

		/* user3/password3 --> ADMIN + GUEST */
		Set<GrantedAuthority> authorities3 = new HashSet<GrantedAuthority>();
		authorities3.add(authorityAdmin);
		authorities3.add(authorityGuest);
		UserDetails user3 = new UserDetailsImpl("user3@gmail.com", "password3", authorities3);
		userRepository.put("user3@gmail.com", user3);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails matchingUser = userRepository.get(username);

		if (matchingUser == null) {
			throw new UsernameNotFoundException("Wrong username or password");
		}

		return matchingUser;
	}

}