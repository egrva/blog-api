package ru.aegorova.blog.api.models;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String header;
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;
}
