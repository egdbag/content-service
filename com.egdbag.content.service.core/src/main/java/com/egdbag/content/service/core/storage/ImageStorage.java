package com.egdbag.content.service.core.storage;

import com.egdbag.content.service.core.interfaces.IImageStorage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.text.MessageFormat;
import java.util.List;

@Service
public class ImageStorage implements IImageStorage {

    private static final Path STORAGE_DIRECTORY = Path.of(System.getProperty("java.io.tmpdir"),
            "content-service-storage");

    @Autowired
    private WebClient webClient;

    private final List<String> allowedFileExtensions = List.of("jpg", "jpeg", "png");

    @Override
    @SneakyThrows
    public URL getImage(String fileName)
    {
        return STORAGE_DIRECTORY.resolve(fileName).toUri().toURL();
    }

    @Override
    public Mono<String> downloadImage(URI uri, Integer id) {
        String fileExtension = getFileExtension(uri);
        if (!allowedFileExtensions.contains(fileExtension)) {
            throw new IllegalStateException(
                    MessageFormat.format("Only {0} file extensions are allowed", allowedFileExtensions));
        }
        String fileName = id + "." + fileExtension;
        return  DataBufferUtils.write(webClient.get()
                        .uri(uri)
                        .accept(MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG)
                        .retrieve()
                        .bodyToFlux(DataBuffer.class),
                STORAGE_DIRECTORY.resolve(fileName), StandardOpenOption.CREATE)
                .thenReturn(fileName);
    }

    @Override
    @SneakyThrows
    public void deleteImage(String fileName)
    {
        Files.delete(STORAGE_DIRECTORY.resolve(fileName));
    }

    private String getFileExtension(URI uri)
    {
        String uriString = uri.toString();
        int questionIndex = uriString.lastIndexOf('?');
        String fileExtension = uriString.substring(uriString.lastIndexOf('.') + 1, questionIndex == -1 ? uriString.length() : questionIndex);
        return fileExtension;
    }
}
