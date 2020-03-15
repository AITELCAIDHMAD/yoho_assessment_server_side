[1mdiff --git a/src/main/java/com/popleads/entities/AppRole.java b/src/main/java/com/popleads/entities/AppRole.java[m
[1mindex 5108397..c85efe1 100644[m
[1m--- a/src/main/java/com/popleads/entities/AppRole.java[m
[1m+++ b/src/main/java/com/popleads/entities/AppRole.java[m
[36m@@ -6,6 +6,8 @@[m [mimport javax.persistence.Entity;[m
 import javax.persistence.GeneratedValue;[m
 import javax.persistence.Id;[m
 [m
[32m+[m[32mimport com.fasterxml.jackson.annotation.JsonIgnore;[m
[32m+[m
 import lombok.AllArgsConstructor;[m
 import lombok.Data;[m
 import lombok.EqualsAndHashCode;[m
[36m@@ -28,6 +30,7 @@[m [mpublic class AppRole extends BaseEntity  implements Serializable {[m
 [m
 	@Id[m
 	@GeneratedValue[m
[32m+[m	[32m@JsonIgnore[m
 	private Long id;[m
 	[m
 	private String role;[m
[1mdiff --git a/src/main/java/com/popleads/entities/AppUser.java b/src/main/java/com/popleads/entities/AppUser.java[m
[1mindex caeb22d..03cf43c 100644[m
[1m--- a/src/main/java/com/popleads/entities/AppUser.java[m
[1m+++ b/src/main/java/com/popleads/entities/AppUser.java[m
[36m@@ -10,6 +10,7 @@[m [mimport javax.persistence.GeneratedValue;[m
 import javax.persistence.Id;[m
 import javax.persistence.ManyToMany;[m
 [m
[32m+[m[32mimport com.fasterxml.jackson.annotation.JsonIgnore;[m
 import com.fasterxml.jackson.annotation.JsonIgnoreProperties;[m
 import com.fasterxml.jackson.annotation.JsonProperty;[m
 [m
[36m@@ -23,7 +24,6 @@[m [mimport lombok.NoArgsConstructor;[m
 @NoArgsConstructor[m
 @AllArgsConstructor[m
 @EqualsAndHashCode(callSuper = true)[m
[31m-@JsonIgnoreProperties( value = "password")[m
 public class AppUser extends BaseEntity  implements Serializable {[m
 	/**[m
 	 * [m
[36m@@ -35,7 +35,7 @@[m [mpublic class AppUser extends BaseEntity  implements Serializable {[m
 	private Long id;[m
 	[m
 	private String username;[m
[31m-	[m
[32m+[m	[32m@JsonIgnore[m
 	private String password;[m
 	@ManyToMany(fetch = FetchType.EAGER)[m
 	private Collection<AppRole> roles = new ArrayList<>();[m
[1mdiff --git a/src/main/java/com/popleads/entities/BaseEntity.java b/src/main/java/com/popleads/entities/BaseEntity.java[m
[1mindex df974ee..c2e1179 100644[m
[1m--- a/src/main/java/com/popleads/entities/BaseEntity.java[m
[1m+++ b/src/main/java/com/popleads/entities/BaseEntity.java[m
[36m@@ -10,6 +10,8 @@[m [mimport javax.persistence.MappedSuperclass;[m
 import org.hibernate.annotations.CreationTimestamp;[m
 import org.hibernate.annotations.UpdateTimestamp;[m
 [m
[32m+[m[32mimport com.fasterxml.jackson.annotation.JsonIgnore;[m
[32m+[m
 import lombok.Data;[m
 import lombok.NoArgsConstructor;[m
 import lombok.NonNull;[m
[36m@@ -23,7 +25,9 @@[m [mpublic abstract class BaseEntity {[m
 	[m
     @Column(updatable = false)[m
     @CreationTimestamp[m
[32m+[m[32m    @JsonIgnore[m
     private LocalDateTime createdAt;[m
     @UpdateTimestamp[m
[32m+[m[32m    @JsonIgnore[m
     private LocalDateTime updatedAt;[m
 }[m
\ No newline at end of file[m
[1mdiff --git a/src/main/java/com/popleads/sec/SecurityConfig.java b/src/main/java/com/popleads/sec/SecurityConfig.java[m
[1mindex 9dd5b9e..a93aa32 100644[m
[1m--- a/src/main/java/com/popleads/sec/SecurityConfig.java[m
[1m+++ b/src/main/java/com/popleads/sec/SecurityConfig.java[m
[36m@@ -29,7 +29,7 @@[m [mpublic class SecurityConfig extends WebSecurityConfigurerAdapter {[m
 		http.csrf().disable()[m
 // don't create session[m
 				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()[m
[31m-				.antMatchers("/login").permitAll().[m
[32m+[m				[32m.antMatchers("/login/**").permitAll().[m
 				[m
 				// Admin has access to ALL API[m
 				 antMatchers("/api/**")[m
[1mdiff --git a/src/main/java/com/popleads/web/CompanyController.java b/src/main/java/com/popleads/web/CompanyController.java[m
[1mindex e50db5f..f512006 100644[m
[1m--- a/src/main/java/com/popleads/web/CompanyController.java[m
[1m+++ b/src/main/java/com/popleads/web/CompanyController.java[m
[36m@@ -12,6 +12,7 @@[m [mimport org.springframework.web.bind.annotation.RequestBody;[m
 import org.springframework.web.bind.annotation.RestController;[m
 [m
 import com.popleads.dao.CompanyRepository;[m
[32m+[m[32mimport com.popleads.entities.AppUser;[m
 import com.popleads.entities.Company;[m
 import com.popleads.service.AccountService;[m
 [m
[36m@@ -50,8 +51,14 @@[m [mpublic class CompanyController {[m
 [m
 	@PostMapping("/api/company/update")[m
 	public Company updateCompany(@RequestBody Company company) {[m
[32m+[m		[32mAppUser appUser=accountService.findUserByUsername(company.getAppUser().getUsername());[m
[32m+[m[41m        [m
[32m+[m		[32mif(appUser!=null) {[m
[32m+[m			[32mthrow new RuntimeException("This username is already used");[m
[32m+[m		[32m}[m
[32m+[m[41m		[m
 		company.getAppUser().setPassword(bCryptPasswordEncoder.encode(company.getAppUser().getPassword())); [m
[31m-[m
[32m+[m[41m		[m
 		System.out.println(" updateCompany " + company);[m
 		return companyRepository.save(company);[m
 	}[m
[1mdiff --git a/src/main/java/com/popleads/web/StoreController.java b/src/main/java/com/popleads/web/StoreController.java[m
[1mindex 921fdfa..83bf420 100644[m
[1m--- a/src/main/java/com/popleads/web/StoreController.java[m
[1m+++ b/src/main/java/com/popleads/web/StoreController.java[m
[36m@@ -12,6 +12,7 @@[m [mimport org.springframework.web.bind.annotation.RequestBody;[m
 import org.springframework.web.bind.annotation.RestController;[m
 [m
 import com.popleads.dao.StoreRepository;[m
[32m+[m[32mimport com.popleads.entities.AppUser;[m
 import com.popleads.entities.Store;[m
 import com.popleads.service.AccountService;[m
 [m
[36m@@ -41,6 +42,12 @@[m [mpublic class StoreController {[m
 	@PostMapping("/api/store")[m
 	public Store addStore(@RequestBody Store store) {[m
 		System.out.println("add Store " + store);[m
[32m+[m			[32mAppUser appUser=accountService.findUserByUsername(store.getAppUser().getUsername());[m
[32m+[m[41m        [m
[32m+[m		[32mif(appUser!=null) {[m
[32m+[m			[32mthrow new RuntimeException("This username is already used");[m
[32m+[m		[32m}[m
[32m+[m[41m		[m
 		store.getAppUser().setPassword(bCryptPasswordEncoder.encode(store.getAppUser().getPassword())); [m
 		Store storeReurned=storeRepository.save(store);[m
 		[m
[1mdiff --git a/src/main/java/com/popleads/web/UserController.java b/src/main/java/com/popleads/web/UserController.java[m
[1mindex b77301d..b986866 100644[m
[1m--- a/src/main/java/com/popleads/web/UserController.java[m
[1m+++ b/src/main/java/com/popleads/web/UserController.java[m
[36m@@ -21,21 +21,6 @@[m [mpublic class UserController {[m
 	@Autowired[m
 	private AccountService accountService;[m
 [m
[31m-	@RequestMapping(value = "/api/user/user3", method = RequestMethod.GET)[m
[31m-    @ResponseBody[m
[31m-    public String currentUserName1(Authentication authentication) {[m
[31m-		String username;[m
[31m-		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();[m
[31m-		if (principal instanceof UserDetails) {[m
[31m-		   username = ((UserDetails)principal).toString();[m
[31m-		} else {[m
[31m-		   username = principal.toString();[m
[31m-		}[m
[31m-		System.out.println("/api/user is =>");[m
[31m-		System.out.println(principal);[m
[31m-[m
[31m-		return username;[m
[31m-    }[m
 	[m
 	@RequestMapping(value = "/api/user", method = RequestMethod.GET)[m
     @ResponseBody[m
[36m@@ -49,10 +34,4 @@[m [mpublic class UserController {[m
 [m
        return appUser;[m
     }[m
[31m-	[m
[31m-	@RequestMapping(value = "/api/user/user2", method = RequestMethod.GET)[m
[31m-    @ResponseBody[m
[31m-    public String currentUserName(Authentication authentication) {[m
[31m-        return authentication.getName();[m
[31m-     }[m
 }[m
\ No newline at end of file[m
