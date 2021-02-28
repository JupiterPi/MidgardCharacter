package jupiterpapi.midgardcharacter.backend.configuration;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.*;
import java.util.function.Function;

public class ReadUtility<T> {

    public Map<String,T> readToMap(String filename, Function<List<String>,T> create, Function<T,String> key)
            throws IOException, InternalException {
        File file = new File("src/main/resources/"+filename);
        Collection<T> list = read(file,create);

        HashMap<String,T> map = new HashMap<>();
        for (T entry: list) {
            if ( entry == null) throw new InternalException();
            map.put(key.apply(entry),entry);
        }
        return map;
    }

    private Collection<T> read(File file, Function<List<String>,T> create) throws IOException, InternalException {
        CSVReader csvReader = init(file);

        Collection<T> list = new ArrayList<>();
        String[] nextRecord;
        int i = 0;
        while ((nextRecord = csvReader.readNext()) != null) {
            List<String> args = new ArrayList<>(Arrays.asList(nextRecord));
            args.add(Integer.toString(i));
            T entry = create.apply(args);
            list.add(entry);
            i++;
        }
        csvReader.close();
        return list;
    }

    private CSVReader init(File file) throws InternalException, IOException {
        InputStream stream = new FileInputStream(file);
        Reader reader = new InputStreamReader(stream);
        CSVReader csvReader = new CSVReader(reader, ';');
        if (csvReader.readNext() == null) { // Header line
            throw new InternalException();
        }
        return csvReader;
    }

}

