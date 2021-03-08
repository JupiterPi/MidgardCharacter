package jupiterpapi.user.backend.service;

import jupiterpapi.user.backend.model.User;
import jupiterpapi.user.backend.model.UserCreateDTO;
import jupiterpapi.user.backend.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@SuppressWarnings("WrongUsageOfMappersFactory")
@Mapper(componentModel = "spring")
public interface UserUIMapper {
    UserUIMapper INSTANCE = Mappers.getMapper(UserUIMapper.class);

    User map(UserCreateDTO user);

    UserDTO map(User user);
}

