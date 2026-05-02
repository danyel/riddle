package be.riddler.configuration.coding;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.HttpMessageConverterExtractor;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;

/// ClientDecoder
///
/// @author dnoulet
/// @version 1.0.0 04/04/2026
@RequiredArgsConstructor
public class ClientDecoder implements Decoder {
    private final HttpMessageConverter<Object> messageConverter;

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (type instanceof Class || type instanceof ParameterizedType || type instanceof WildcardType) {
            @SuppressWarnings({"unchecked", "rawtypes"})
            HttpMessageConverterExtractor<?> extractor = new HttpMessageConverterExtractor(type, List.of(messageConverter));
            return extractor.extractData(new FeignResponseAdapter(response));
        }
        throw new DecodeException(response.status(), "type is not an instance of Class or ParameterizedType: " + type, response.request());
    }
}
