package jupiterpapi.user.backend.service;

import jupiterpapi.user.backend.model.User;
import jupiterpapi.user.backend.model.UserDB;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserDBMapper {
    @SuppressWarnings("WrongUsageOfMappersFactory")
    UserDBMapper INSTANCE = Mappers.getMapper(UserDBMapper.class);

    User map(UserDB user);

    UserDB map(User user);
}
