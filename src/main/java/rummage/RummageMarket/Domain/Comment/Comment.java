package rummage.RummageMarket.Domain.Comment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rummage.RummageMarket.Domain.Post.Post;
import rummage.RummageMarket.Domain.User.User;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String content;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({ "posts" })
    private User user;

    @JoinColumn(name = "postId")
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
