package com.food.apnajalpaan.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "images")
public class Image {
    @Id
    private String imageId;
    private String name;
    private String type;
    private byte[] imageData;
}
