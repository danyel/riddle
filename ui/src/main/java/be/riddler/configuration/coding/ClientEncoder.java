package be.riddler.configuration.coding;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.encoding.HttpEncoding;
import org.springframework.cloud.openfeign.support.FeignEncoderProperties;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.http.MediaType.MULTIPART_MIXED;
import static org.springframework.http.MediaType.MULTIPART_RELATED;

/// ClientEncoder
///
/// @author dnoulet
/// @version 1.0.0 04/04/2026
@RequiredArgsConstructor
public class ClientEncoder implements Encoder {
    private final SpringFormEncoder springFormEncoder = new SpringFormEncoder();
    private final FeignEncoderProperties encoderProperties = new FeignEncoderProperties();
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientEncoder.class);
    private final HttpMessageConverter<Object> messageConverter;
    private static final List<MediaType> binaryContentTypes = List.of(
            MediaType.APPLICATION_CBOR,
            MediaType.APPLICATION_OCTET_STREAM,
            MediaType.APPLICATION_PDF,
            MediaType.IMAGE_GIF,
            MediaType.IMAGE_JPEG,
            MediaType.IMAGE_PNG
    );

    @Override
    public void encode(Object requestBody, Type bodyType, RequestTemplate request) throws EncodeException {
        if (requestBody != null) {
            Collection<String> contentTypes = request.headers().get(HttpEncoding.CONTENT_TYPE);

            MediaType requestContentType = null;
            if (contentTypes != null && !contentTypes.isEmpty()) {
                String type = contentTypes.iterator().next();
                requestContentType = MediaType.valueOf(type);
            }

            if (isFormRelatedContentType(requestContentType)) {
                springFormEncoder.encode(requestBody, bodyType, request);
                return;
            } else {
                if (bodyType == MultipartFile.class) {
                    LOGGER.warn("For MultipartFile to be handled correctly, the 'consumes' parameter of @RequestMapping "
                            + "should be specified as MediaType.MULTIPART_FORM_DATA_VALUE");
                }
            }
            encodeWithMessageConverter(requestBody, request, requestContentType);
        }
    }

    private boolean isFormRelatedContentType(MediaType requestContentType) {
        return isMultipartType(requestContentType) || isFormUrlEncoded(requestContentType);
    }

    private boolean isMultipartType(MediaType requestContentType) {
        return Arrays.asList(MULTIPART_FORM_DATA, MULTIPART_MIXED, MULTIPART_RELATED).contains(requestContentType);
    }

    private boolean isFormUrlEncoded(MediaType requestContentType) {
        return Objects.equals(APPLICATION_FORM_URLENCODED, requestContentType);
    }

    private void logBeforeWrite(Object requestBody, MediaType requestContentType) {
        if (LOGGER.isDebugEnabled()) {
            if (requestContentType != null) {
                LOGGER.debug("Writing [{}] as \"{}\" using [{}]", requestBody, requestContentType, messageConverter);
            } else {
                LOGGER.debug("Writing [{}] using [{}]", requestBody, messageConverter);
            }
        }
    }

    private FeignOutputMessage checkAndWrite(Object body, MediaType contentType, RequestTemplate request) throws IOException {
        if (messageConverter.canWrite(body.getClass(), contentType)) {
            logBeforeWrite(body, contentType);
            FeignOutputMessage outputMessage = new FeignOutputMessage(request);
            messageConverter.write(body, contentType, outputMessage);
            return outputMessage;
        } else {
            return null;
        }
    }

    private boolean binaryContentType(FeignOutputMessage outputMessage) {
        MediaType contentType = outputMessage.getHeaders().getContentType();
        return contentType == null || binaryContentTypes.contains(contentType);
    }

    private boolean shouldHaveNullCharset(HttpMessageConverter<?> messageConverter, FeignOutputMessage outputMessage) {
        return binaryContentType(outputMessage)
                || messageConverter instanceof ByteArrayHttpMessageConverter
                || messageConverter instanceof ProtobufHttpMessageConverter
                && ProtobufHttpMessageConverter.PROTOBUF.isCompatibleWith(outputMessage.getHeaders().getContentType());
    }

    private void encodeWithMessageConverter(Object requestBody, RequestTemplate request,
                                            MediaType requestContentType) {
        FeignOutputMessage outputMessage;
        try {
            outputMessage = checkAndWrite(requestBody, requestContentType, request);
        } catch (IOException | HttpMessageConversionException ex) {
            throw new EncodeException("Error converting request body", ex);
        }
        if (outputMessage != null) {
            request.headers(null);
            MultiValueMap<String, String> newHeaders = new LinkedMultiValueMap<>();
            outputMessage.getHeaders().forEach(newHeaders::put);
            request.headers(new LinkedHashMap<>(newHeaders));
            Charset charset;
            MediaType contentType = outputMessage.getHeaders().getContentType();
            Charset charsetFromContentType = contentType != null ? contentType.getCharset() : null;
            if (encoderProperties.isCharsetFromContentType() && charsetFromContentType != null) {
                charset = charsetFromContentType;
            } else if (shouldHaveNullCharset(messageConverter, outputMessage)) {
                charset = null;
            } else {
                charset = StandardCharsets.UTF_8;
            }
            request.body(outputMessage.getOutputStream().toByteArray(), charset);
            return;
        }
        String message = "Could not write request: no suitable HttpMessageConverter found for request type [%s]"
                .formatted(requestBody.getClass().getName());
        if (requestContentType != null) {
            message += " and content type [" + requestContentType + "]";
        }
        throw new EncodeException(message);
    }
}
