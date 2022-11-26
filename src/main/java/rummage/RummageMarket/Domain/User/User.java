package rummage.RummageMarket.Domain.User;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rummage.RummageMarket.Domain.Post.Post;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
	
	// 전처리는 Validation(유효성 검사)을 체크
	// 후처리는 exceptionhandler로 처리
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length=30, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String nickname;
	
	private String bio;
	private String email;
	private String profileImage;
	private String role;
	
	@JsonIgnoreProperties({"user"})
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	private List<Post> posts;
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
