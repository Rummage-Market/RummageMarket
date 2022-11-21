package rummage.RummageMarket.Domain.User;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data // Getter, Setter
@Entity // DB에 테이블을 생성
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String username;
	private String password;
	private String nickname;
	private String bio;
	private String email;
	private String profileImage;
	
	private LocalDateTime createDate;
	
	@PrePersist // DB에 insert 되기 직전에 실행, 나중에 DB에 값을 넣을 때 위에 값만 넣어주면 createDate는 자동으로 들어감.
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
