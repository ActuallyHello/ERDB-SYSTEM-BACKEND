package com.ustu.erdbsystem.external.excel;

import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.external.TestDataLoader;
import com.ustu.erdbsystem.external.exception.LoadTestDataException;
import com.ustu.erdbsystem.external.exception.UploadTestDataException;
import lombok.extern.slf4j.Slf4j;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Component
public class TestDataLoaderExcel implements TestDataLoader {

    private static final String RUNTIME_FOLDER = "src\\main\\resources\\runtime\\";
    private static final String EXCEL_EXTENSION = ".xlsx";

    @Override
    public List<List<String>> loadData(String modelTitle, List<String> attributes, Integer testDataAmount) {
        try (FileInputStream file = new FileInputStream(RUNTIME_FOLDER + modelTitle + EXCEL_EXTENSION);
             ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Sheet sheet = wb.getFirstSheet();
            List<Row> rows = sheet.read();
            if (rows.size() <= 1) {
                log.error("ERROR! TEST DATA FILE {} IS EMPTY!", modelTitle + EXCEL_EXTENSION);
                throw new LoadTestDataException("Error! Test data file is empty!");
            }

            var headRow = rows.get(0);
            int[] columnsToFetch = IntStream.range(0, headRow.getCellCount())
                    .filter(index -> attributes.contains(headRow.getCell(index).getRawValue().toLowerCase()))
                    .toArray();
            if (columnsToFetch.length != attributes.size()) {
                log.error("ERROR! ATTRIBUTE LIST DO NOT MATCH COLUMNS ({})!", modelTitle + EXCEL_EXTENSION);
                throw new LoadTestDataException(
                        "Error! Attributes do not match columns in file %s!".formatted(modelTitle + EXCEL_EXTENSION)
                );
            }

            List<List<String>> results = new ArrayList<>();
            int size = rows.size() > testDataAmount ? testDataAmount : rows.size();
            for (int i = 1; i < size; i++) {
                List<String> values = new ArrayList<>();
                for (int index : columnsToFetch) {
                    Cell cell = rows.get(i).getCell(index);
                    values.add(cell.getRawValue());
                }
                results.add(values);
            }
            log.info("READ {} TEST DATA", results.size());
            System.out.println(results);
            return results;
        } catch (FileNotFoundException exceptionFNFE) {
            log.error(
                    "ERROR! NO SUCH FILE FOR MODEL {}: {}",
                    modelTitle + EXCEL_EXTENSION,
                    exceptionFNFE.getMessage()
            );
            throw new LoadTestDataException(
                    "Error when opening test data file %s!".formatted(modelTitle + EXCEL_EXTENSION),
                    exceptionFNFE
            );
        } catch (IOException exceptionIO) {
            log.error(
                    "ERROR WHEN PROCESSING TEST DATA FILE {} FOR MODEL: {}",
                    modelTitle + EXCEL_EXTENSION,
                    exceptionIO.getMessage()
            );
            throw new LoadTestDataException(
                    "Error when processing test data file %s!".formatted(modelTitle + EXCEL_EXTENSION),
                    exceptionIO
            );
        }
    }

    @Override
    public void uploadTestDataFileToModel(Model model, MultipartFile file) {
        Path testDataFilePath = Paths.get(RUNTIME_FOLDER + model.getTitle() + EXCEL_EXTENSION);
        checkIfFileAlreadyExists(testDataFilePath);
        try {
            byte[] bytes = file.getBytes();
            Files.write(testDataFilePath, bytes);
            log.info("File {} upload successfully to a model with id={}!", model.getTitle(), model.getId());
        } catch (IOException exception) {
            log.error("ERROR WHEN UPLOADING FILE TO A MODEL WITH ID={}: {}", model.getId(), exception.getMessage());
            throw new UploadTestDataException(
                    String.format("Error when uploading the file to a model with id=%d", model.getId()),
                    exception
            );
        }
    }

    private void checkIfFileAlreadyExists(Path path) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(RUNTIME_FOLDER))) {
            for (Path f : stream) {
                if (Files.isRegularFile(f)) {
                    System.out.println(f + " " + path);
                    System.out.println(f.getFileName() + " " + path.getFileName());
                    if (f.getFileName().equals(path.getFileName())) {
                        log.error("ERROR UPLOADING TEST DATA FILE! FILE WITH THIS NAME ALREADY EXISTS!");
                        throw new UploadTestDataException("The file with this name already exists!");
                    }
                }
            }
        } catch (IOException exception) {
            log.error("ERROR UPLOADING TEST DATA FILE! {}", exception.getMessage());
            throw new UploadTestDataException("Error when uploading the file!", exception);
        }
    }
}
