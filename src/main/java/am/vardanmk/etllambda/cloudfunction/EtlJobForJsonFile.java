package am.vardanmk.etllambda.cloudfunction;

import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.databind.ObjectMapper;
import am.vardanmk.etllambda.domain.Notes;
import am.vardanmk.etllambda.respository.DataRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class EtlJobForJsonFile implements Supplier<String> {

    private final AmazonS3 s3Client;
    private final DataRepo dataRepo;

    @Value("${application.bucket.name}")
    private String bucketName;

    private final String JSON_FILE_NAME = System.currentTimeMillis() + "_" + "JsonForDemo.json";

    @Autowired
    public EtlJobForJsonFile(AmazonS3 s3Client, DataRepo dataRepo) {
        this.s3Client = s3Client;
        this.dataRepo = dataRepo;
    }

    @SneakyThrows
    @Override
    public String get() {
        List<Notes> notes = (List<Notes>) dataRepo.findAll();

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = objectMapper.writeValueAsString(notes);
//        byte[] jsonObj = objectMapper.writeValueAsBytes(notes);

        s3Client.putObject(bucketName, JSON_FILE_NAME, jsonString);

        return "completed and upload to s3, json file with name: " + JSON_FILE_NAME;
    }
}
