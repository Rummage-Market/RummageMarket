package rummage.RummageMarket.Domain.Post;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rummage.RummageMarket.Domain.Interest.Interest;
import rummage.RummageMarket.Domain.Comment.Comment;
import rummage.RummageMarket.Domain.User.User;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties({ "posts" })
	private User user;
	
	@OrderBy("id DESC")
	@JsonIgnoreProperties({"post"})
	@OneToMany(mappedBy = "post")
	private List<Comment> Comments;

	private String imageUrl;
	
	private String title; 
	private String content; 
	private String address1; 
	private String address2; 
	private String place; 
	private String item; 
	private int price;
	
	@JsonIgnoreProperties({"post"})
	@OneToMany(mappedBy = "post")
	private List<Interest> interest; 
	
  @Transient
  private boolean interestState;
	
  @Transient
  private int interestCount;
	
	private LocalDateTime createDate;

	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
