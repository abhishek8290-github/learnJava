package in.abhi8290.helloworld.user.mapper;

import in.abhi8290.helloworld.user.model.AuthProvider;
import in.abhi8290.helloworld.user.model.User;
import in.abhi8290.helloworld.user.dto.UserRequestDto;

public class UserMapper {

    public static User toEntity(UserRequestDto dto) {
        if (dto == null) return null;
        return new User(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPassword(),
                AuthProvider.LOCAL
        );
    }

    public static UserRequestDto toDto(User user) {
        if (user == null) return null;
        UserRequestDto dto = new UserRequestDto();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword()); // only if password is safe to expose
        return dto;
    }
}
