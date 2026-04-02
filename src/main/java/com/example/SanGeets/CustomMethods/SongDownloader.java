package com.example.SanGeets.CustomMethods;
/*
import com.example.SanGeets.DTO.Request.SongRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Service
public class SongDownloader {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;
    private final String youtubeApiKey;
    private final Path downloadDirectory;

    public SongDownloader(
            ObjectMapper objectMapper,
            @Value("${youtubeApi.keys:}") String youtubeApiKey,
            @Value("${song.download-dir:}") String configuredDownloadDirectory
    ) {
        this.objectMapper = objectMapper;
        this.youtubeApiKey = sanitizeProperty(youtubeApiKey);
        this.downloadDirectory = configuredDownloadDirectory == null || configuredDownloadDirectory.isBlank()
                ? Path.of(System.getProperty("user.dir"), "Songs")
                : Path.of(configuredDownloadDirectory.trim());
    }

    public static String extractVideoId(String url) {
        if (url == null || url.isBlank()) {
            return null;
        }

        String trimmedUrl = url.trim();
        try {
            URI uri = URI.create(trimmedUrl);
            String host = uri.getHost();

            if (host == null) {
                return trimmedUrl.matches("[A-Za-z0-9_-]{11}") ? trimmedUrl : null;
            }

            if (host.contains("youtu.be")) {
                String path = uri.getPath();
                if (path != null && path.length() > 1) {
                    return path.substring(1);
                }
            }

            if (host.contains("youtube.com")) {
                String queryVideoId = getQueryParameter(uri.getRawQuery(), "v");
                if (queryVideoId != null && !queryVideoId.isBlank()) {
                    return queryVideoId;
                }

                String path = uri.getPath();
                if (path != null) {
                    String[] parts = path.split("/");
                    for (int i = 0; i < parts.length - 1; i++) {
                        if ("embed".equals(parts[i]) || "shorts".equals(parts[i]) || "live".equals(parts[i])) {
                            return parts[i + 1];
                        }
                    }
                }
            }
        } catch (IllegalArgumentException ignored) {
            if (trimmedUrl.matches("[A-Za-z0-9_-]{11}")) {
                return trimmedUrl;
            }
        }

        return null;
    }

    public String getVideoDetails(String videoId) {
        if (videoId == null || videoId.isBlank()) {
            throw new IllegalArgumentException("Invalid YouTube video URL");
        }
        if (youtubeApiKey.isBlank()) {
            throw new IllegalStateException("youtubeApi.keys is not configured");
        }

        String url = "https://www.googleapis.com/youtube/v3/videos"
                + "?id=" + videoId
                + "&part=snippet,contentDetails"
                + "&key=" + youtubeApiKey;

        return restTemplate.getForObject(url, String.class);
    }

    public long convertDuration(String iso) {
        if (iso == null || iso.isBlank()) {
            return 0L;
        }

        try {
            return Duration.parse(iso).getSeconds();
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Invalid ISO-8601 duration: " + iso, exception);
        }
    }

    public MultipartFile downloadAudio(String videoUrl) throws IOException, InterruptedException {
        String videoId = extractVideoId(videoUrl);
        if (videoId == null) {
            throw new IllegalArgumentException("Invalid YouTube video URL");
        }

        Files.createDirectories(downloadDirectory);

        Path outputPath = downloadDirectory.resolve(videoId + ".mp3");

        ProcessBuilder processBuilder = new ProcessBuilder(
                "yt-dlp",
                "-x",
                "--audio-format", "mp3",
                "-o", downloadDirectory.resolve("%(id)s.%(ext)s").toString(),
                videoUrl
        );
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        try (InputStream processOutput = process.getInputStream()) {
            processOutput.transferTo(OutputStream.nullOutputStream());
        }

        int exitCode = process.waitFor();
        if (exitCode != 0 || !Files.exists(outputPath)) {
            throw new IOException("yt-dlp failed to download audio for " + videoUrl);
        }

        byte[] audioBytes = Files.readAllBytes(outputPath);
        return new InMemoryMultipartFile(
                "audio",
                outputPath.getFileName().toString(),
                "audio/mpeg",
                audioBytes
        );
    }

    public SongRequest getSongDetails(String url) throws IOException, InterruptedException {
        String videoId = extractVideoId(url);
        String response = getVideoDetails(videoId);
        if (response == null || response.isBlank()) {
            throw new IllegalStateException("YouTube API returned an empty response for id " + videoId);
        }

        JsonNode root = objectMapper.readTree(response);
        JsonNode item = root.path("items").path(0);
        if (item.isMissingNode()) {
            throw new IllegalStateException("No YouTube video details found for id " + videoId);
        }

        String title = item.path("snippet").path("title").asText();
        String durationISO = item.path("contentDetails").path("duration").asText();

        SongRequest song = new SongRequest();
        song.setTitle(title);
        song.setDuration(convertDuration(durationISO));
        song.setAudio(downloadAudio(url));

        return song;
    }

    private static String sanitizeProperty(String propertyValue) {
        if (propertyValue == null) {
            return "";
        }
        return propertyValue.trim().replace("\"", "");
    }

    private static String getQueryParameter(String rawQuery, String key) {
        if (rawQuery == null || rawQuery.isBlank()) {
            return null;
        }

        String prefix = key + "=";
        for (String pair : rawQuery.split("&")) {
            if (pair.startsWith(prefix)) {
                return URLDecoder.decode(pair.substring(prefix.length()), StandardCharsets.UTF_8);
            }
        }

        return null;
    }

    private static final class InMemoryMultipartFile implements MultipartFile {
        private final String name;
        private final String originalFilename;
        private final String contentType;
        private final byte[] content;

        private InMemoryMultipartFile(String name, String originalFilename, String contentType, byte[] content) {
            this.name = name;
            this.originalFilename = originalFilename;
            this.contentType = contentType;
            this.content = content.clone();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return originalFilename;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return content.length == 0;
        }

        @Override
        public long getSize() {
            return content.length;
        }

        @Override
        public byte[] getBytes() {
            return content.clone();
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(content);
        }

        @Override
        public void transferTo(java.io.File dest) throws IOException {
            Objects.requireNonNull(dest, "Destination file must not be null");
            Files.write(dest.toPath(), content);
        }

        @Override
        public void transferTo(Path dest) throws IOException {
            Objects.requireNonNull(dest, "Destination path must not be null");
            Files.write(dest, content);
        }
    }
}
*/