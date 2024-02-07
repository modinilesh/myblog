package com.springboot.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//lombok annotations
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "comments")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//By default columns will be generated as per variables name
	private String name;
	private String email;
	private String body;
	
	//Many comments can be mapped to single post so use @ManyToOne
	@ManyToOne(fetch = FetchType.LAZY) //as we dont want to get unnecessary data associated with Post object, we use LAZY 
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
	

}
