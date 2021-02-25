package am.vardanmk.etllambda.cloudfunction;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import am.vardanmk.etllambda.domain.Notes;
import am.vardanmk.etllambda.respository.DataRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class EtlJobForCsvFile implements Supplier<String> {

    private final AmazonS3 s3Client;
    private final DataRepo dataRepo;

    @Value("${application.bucket.name}")
    private String bucketName;

    private final String CSV_FILE_NAME = System.currentTimeMillis() + "_" + "CsvForDemo.csv";

    @Autowired
    public EtlJobForCsvFile(AmazonS3 s3Client, DataRepo dataRepo) {
        this.s3Client = s3Client;
        this.dataRepo = dataRepo;
    }

    @SneakyThrows
    @Override
    public String get() {
        List<Notes> notes = (List<Notes>) dataRepo.findAll();

        String csvString = "noteId,userEmail,title,note,crateDate,lastUpdateDate" + "\n" +
                notes.stream().map(Notes::toString).collect(Collectors.joining("\n"));

        byte[] bytes = csvString.getBytes(StandardCharsets.UTF_8);
        InputStream inputStream = new ByteArrayInputStream(bytes);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(bytes.length);
        objectMetadata.setContentType("text/plain");


        s3Client.putObject(bucketName, CSV_FILE_NAME, inputStream, objectMetadata);

        return "completed and upload to s3, CSV file with name: " + CSV_FILE_NAME;
    }
}
