package top.frankxxj.codeai.plugin.entity.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import top.frankxxj.codeai.plugin.entity.LoginDTO;

import java.io.IOException;

public class LoginDTOConverter extends AbstractHttpMessageConverter<LoginDTO> {
    @Override
    protected boolean supports(Class<?> clazz) {
        return LoginDTO.class == clazz;
    }

    @Override
    protected LoginDTO readInternal(Class<? extends LoginDTO> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(LoginDTO loginDTO, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
    }
}
