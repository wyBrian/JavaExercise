package brian.wang.middleware.demo.elk.domain;

import lombok.Data;

@Data
public class ProfileDocument {

    private String firstName;
    private String lastName;
    private String email;

}